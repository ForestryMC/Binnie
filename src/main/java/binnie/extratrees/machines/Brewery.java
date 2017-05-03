package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.liquid.IFluidType;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.item.ExtraTreeItems;
import cpw.mods.fml.relauncher.Side;
import forestry.api.core.INBTTagable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brewery {
	public static int[] slotRecipeGrains = new int[]{0, 1, 2};
	public static int slotRecipeInput = 3;
	public static int slotRecipeYeast = 4;
	public static int[] slotInventory = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13};
	public static int tankInput = 0;
	public static int tankOutput = 1;

	private static List<IBreweryRecipe> recipes = new ArrayList<>();

	public static boolean isValidGrain(ItemStack itemstack) {
		for (IBreweryRecipe recipe : Brewery.recipes) {
			for (ItemStack ingredient : recipe.getGrains()) {
				if (ingredient.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidYeast(ItemStack itemstack) {
		for (IBreweryRecipe recipe : Brewery.recipes) {
			for (ItemStack ingredient : recipe.getYeasts()) {
				if (ingredient.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static FluidStack getOutput(FluidStack stack) {
		BreweryCrafting crafting = new BreweryCrafting(stack, null, null, null);
		for (IBreweryRecipe recipe : Brewery.recipes) {
			if (recipe.getOutput(crafting) != null) {
				return recipe.getOutput(crafting);
			}
		}
		return null;
	}

	public static FluidStack getOutput(BreweryCrafting crafting) {
		if (crafting.currentInput == null || crafting.yeast == null) {
			return null;
		}

		for (IBreweryRecipe recipe : Brewery.recipes) {
			if (recipe.getOutput(crafting) != null) {
				return recipe.getOutput(crafting);
			}
		}
		return null;
	}

	public static boolean isValidIngredient(ItemStack itemstack) {
		for (IBreweryRecipe recipe : Brewery.recipes) {
			for (ItemStack ingr : recipe.getIngredient()) {
				if (ingr.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidInputLiquid(FluidStack fluid) {
		for (IBreweryRecipe recipe : Brewery.recipes) {
			for (FluidStack ingr : recipe.getInput()) {
				if (ingr.isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(FluidStack fluid) {
		for (IBreweryRecipe recipe : Brewery.recipes) {
			for (FluidStack ingr : recipe.getOutput()) {
				if (ingr.isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addRecipe(FluidStack input, FluidStack output) {
		Brewery.recipes.add(new BreweryRecipe(input, output));
	}

	public static void addBeerAndMashRecipes() {
		Brewery.recipes.add(new BeerRecipe());
	}

	private interface IBreweryRecipe {
		FluidStack getOutput(BreweryCrafting crafting);

		FluidStack[] getInput();

		FluidStack[] getOutput();

		ItemStack[] getGrains();

		ItemStack[] getIngredient();

		ItemStack[] getYeasts();
	}

	public static class PackageBrewery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackageBrewery() {
			super("brewery", ExtraTreeTexture.breweryTexture, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Brewery);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Brewery.slotRecipeGrains, "grain");
			for (InventorySlot slot : inventory.getSlots(Brewery.slotRecipeGrains)) {
				slot.setValidator(new SlotValidatorBreweryGrain());
				slot.setType(InventorySlot.Type.Recipe);
			}

			inventory.addSlotArray(Brewery.slotInventory, "inventory");
			for (InventorySlot slot : inventory.getSlots(Brewery.slotInventory)) {
				slot.forbidExtraction();
			}

			inventory.addSlot(Brewery.slotRecipeYeast, "yeast");
			inventory.getSlot(Brewery.slotRecipeYeast).setValidator(new SlotValidatorYeast());
			inventory.getSlot(Brewery.slotRecipeYeast).setType(InventorySlot.Type.Recipe);
			inventory.addSlot(Brewery.slotRecipeInput, "ingredient");
			inventory.getSlot(Brewery.slotRecipeInput).setValidator(new SlotValidatorBreweryIngredient());
			inventory.getSlot(Brewery.slotRecipeInput).setType(InventorySlot.Type.Recipe);

			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(Brewery.tankInput, "input", 5000);
			tanks.getTankSlot(Brewery.tankInput).setValidator(new TankValidatorFermentInput());
			tanks.getTankSlot(Brewery.tankInput).setOutputSides(MachineSide.TopAndBottom);
			tanks.addTank(Brewery.tankOutput, "output", 5000);
			tanks.getTankSlot(Brewery.tankOutput).setValidator(new TankValidatorFermentOutput());
			tanks.getTankSlot(Brewery.tankOutput).forbidInsertion();
			tanks.getTankSlot(Brewery.tankOutput).setOutputSides(MachineSide.Sides);

			new ComponentPowerReceptor(machine);
			new ComponentBreweryLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class BreweryCrafting implements INBTTagable {
		public FluidStack currentInput;
		public ItemStack[] inputs;
		public ItemStack ingr;
		public ItemStack yeast;

		public BreweryCrafting(FluidStack currentInput, ItemStack ingr, ItemStack[] inputs, ItemStack yeast) {
			this.currentInput = currentInput;
			this.inputs = ((inputs == null) ? new ItemStack[3] : inputs);
			this.ingr = ingr;
			this.yeast = yeast;
		}

		public BreweryCrafting(NBTTagCompound nbt) {
			readFromNBT(nbt);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			currentInput = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluid"));
			ingr = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ingr"));

			inputs = new ItemStack[3];
			inputs[0] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in1"));
			inputs[1] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in2"));
			inputs[2] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in3"));
			yeast = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("yeast"));
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			NBTTagCompound fluidTag = new NBTTagCompound();
			currentInput.writeToNBT(fluidTag);
			nbt.setTag("fluid", fluidTag);
			nbt.setTag("ingr", getNBT(ingr));
			nbt.setTag("in1", getNBT(inputs[0]));
			nbt.setTag("in2", getNBT(inputs[1]));
			nbt.setTag("in3", getNBT(inputs[2]));
			nbt.setTag("yeast", getNBT(yeast));
		}

		private NBTTagCompound getNBT(ItemStack ingr) {
			if (ingr == null) {
				return new NBTTagCompound();
			}

			NBTTagCompound nbt = new NBTTagCompound();
			ingr.writeToNBT(nbt);
			return nbt;
		}
	}

	public static class ComponentBreweryLogic extends ComponentProcessSetCost implements IProcess, INetwork.GuiNBT {
		public BreweryCrafting currentCrafting;

		public ComponentBreweryLogic(Machine machine) {
			super(machine, 16000, 800);
			currentCrafting = null;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isTankEmpty(Brewery.tankInput) && currentCrafting == null) {
				return new ErrorState.InsufficientLiquid("No Input Liquid", Brewery.tankInput);
			}
			if (Brewery.getOutput(getInputCrafting()) == null && currentCrafting == null) {
				return new ErrorState("No Recipe", "Brewing cannot occur with these ingredients");
			}
			if (!getUtil().hasIngredients(new int[]{0, 1, 2, 3, 4}, Brewery.slotInventory) && currentCrafting == null) {
				return new ErrorState("Insufficient Ingredients", "Not enough ingredients for Brewing");
			}
			return super.canWork();
		}

		private BreweryCrafting getInputCrafting() {
			return new BreweryCrafting(getUtil().getFluid(Brewery.tankInput), getUtil().getStack(Brewery.slotRecipeInput), getUtil().getStacks(Brewery.slotRecipeGrains), getUtil().getStack(Brewery.slotRecipeYeast));
		}

		@Override
		public ErrorState canProgress() {
			if (currentCrafting == null) {
				return new ErrorState("Brewery Empty", "No liquid in Brewery");
			}
			if (!getUtil().spaceInTank(Brewery.tankOutput, 1000)) {
				return new ErrorState.TankSpace("No Space for Fermented Liquid", Brewery.tankOutput);
			}
			if (getUtil().getFluid(Brewery.tankOutput) != null && !getUtil().getFluid(Brewery.tankOutput).isFluidEqual(Brewery.getOutput(currentCrafting))) {
				return new ErrorState.TankSpace("Different fluid in tank", Brewery.tankOutput);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			FluidStack output = Brewery.getOutput(currentCrafting).copy();
			output.amount = 1000;
			getUtil().fillTank(Brewery.tankOutput, output);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			if (canWork() != null || currentCrafting != null || getUtil().getTank(Brewery.tankInput).getFluidAmount() < 1000) {
				return;
			}

			FluidStack stack = getUtil().drainTank(Brewery.tankInput, 1000);
			currentCrafting = getInputCrafting();
			currentCrafting.currentInput = stack;
		}

		@Override
		public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
			NBTTagCompound nbt = new NBTTagCompound();
			if (currentCrafting == null) {
				nbt.setBoolean("null", true);
			} else {
				currentCrafting.writeToNBT(nbt);
			}
			nbts.put("brewery-recipe", nbt);
		}

		@Override
		public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
			if (!name.equals("brewery-recipe")) {
				return;
			}

			if (nbt.getBoolean("null")) {
				currentCrafting = null;
			} else {
				currentCrafting = new BreweryCrafting(nbt);
			}
		}

		@Override
		public String getTooltip() {
			if (currentCrafting == null) {
				return "Empty";
			}
			// TODO remove deprecated
			return "Creating " + Brewery.getOutput(currentCrafting).getFluid().getLocalizedName();
		}
	}

	public static class SlotValidatorBreweryGrain extends SlotValidator {
		public SlotValidatorBreweryGrain() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Brewery.isValidGrain(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Brewing Grains";
		}
	}

	public static class SlotValidatorBreweryIngredient extends SlotValidator {
		public SlotValidatorBreweryIngredient() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Brewery.isValidIngredient(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Brewing Ingredient";
		}
	}

	public static class SlotValidatorYeast extends SlotValidator {
		public SlotValidatorYeast() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Brewery.isValidYeast(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Yeast";
		}
	}

	public static class TankValidatorFermentInput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Brewery.isValidInputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Fermentable Liquids";
		}
	}

	public static class TankValidatorFermentOutput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Brewery.isValidOutputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Fermented Liquids";
		}
	}

	public static class BreweryRecipe implements IBreweryRecipe {
		protected FluidStack input;
		protected ItemStack specificYeast;
		protected FluidStack output;

		public BreweryRecipe(FluidStack input, FluidStack output) {
			this(input, null, output);
		}

		public BreweryRecipe(FluidStack input, ItemStack specificYeast, FluidStack output) {
			this.input = input;
			this.specificYeast = specificYeast;
			this.output = output;
		}

		@Override
		public FluidStack getOutput(BreweryCrafting crafting) {
			if (specificYeast != null && !specificYeast.isItemEqual(crafting.yeast)) {
				return null;
			}
			if (input.isFluidEqual(crafting.currentInput)) {
				return output;
			}
			return null;
		}

		@Override
		public FluidStack[] getInput() {
			return new FluidStack[]{input};
		}

		@Override
		public FluidStack[] getOutput() {
			return new FluidStack[0];
		}

		@Override
		public ItemStack[] getGrains() {
			return new ItemStack[0];
		}

		@Override
		public ItemStack[] getIngredient() {
			return new ItemStack[0];
		}

		@Override
		public ItemStack[] getYeasts() {
			return new ItemStack[]{ExtraTreeItems.Yeast.get(1)};
		}
	}

	public static class BeerRecipe implements IBreweryRecipe {
		protected static String barley = "seedBarley";
		protected static String wheat = "seedWheat";
		protected static String rye = "seedRye";
		protected static String corn = "seedCorn";
		protected static String roasted = "seedRoasted";

		Map<ItemStack, String> grainCrops;
		List<FluidStack> outputs;
		String[] grains;
		FluidStack water;
		ItemStack hops;

		public BeerRecipe() {
			grainCrops = new HashMap<>();
			outputs = new ArrayList<>();
			grains = new String[]{BeerRecipe.barley, BeerRecipe.wheat, BeerRecipe.rye, BeerRecipe.corn, BeerRecipe.roasted};
			for (String grainType : grains) {
				for (ItemStack stack : OreDictionary.getOres(grainType)) {
					grainCrops.put(stack, grainType);
				}
			}

			water = Binnie.Liquid.getLiquidStack("water", 5);
			hops = OreDictionary.getOres("cropHops").get(0);
			add(Alcohol.Ale);
			add(Alcohol.Lager);
			add(Alcohol.Stout);
			add(Alcohol.CornBeer);
			add(Alcohol.RyeBeer);
			add(Alcohol.WheatBeer);
			add(Alcohol.Barley);
			add(Alcohol.Corn);
			add(Alcohol.Rye);
			add(Alcohol.Wheat);
		}

		private void add(IFluidType fluid) {
			outputs.add(fluid.get(1));
		}

		@Override
		public FluidStack getOutput(BreweryCrafting crafting) {
			if (!crafting.currentInput.isFluidEqual(water)) {
				return null;
			}

			int roasted = 0;
			int barley = 0;
			int wheat = 0;
			int rye = 0;
			int corn = 0;

			for (ItemStack stack : crafting.inputs) {
				if (stack == null) {
					return null;
				}

				String name = "";
				for (Map.Entry<ItemStack, String> entry : grainCrops.entrySet()) {
					if (entry.getKey().isItemEqual(stack)) {
						name = entry.getValue();
						break;
					}
				}

				if (name.isEmpty()) {
					return null;
				}

				if (name.equals(BeerRecipe.roasted)) {
					roasted++;
				}
				if (name.equals(BeerRecipe.barley)) {
					barley++;
				}
				if (name.equals(BeerRecipe.wheat)) {
					wheat++;
				}
				if (name.equals(BeerRecipe.rye)) {
					rye++;
				}
				if (name.equals(BeerRecipe.corn)) {
					corn++;
				}
			}

			boolean isBeer = crafting.ingr != null;
			boolean isLager = crafting.yeast.isItemEqual(ExtraTreeItems.LagerYeast.get(1));
			if (roasted >= 2 && isBeer) {
				return Alcohol.Stout.get(5);
			}
			if (wheat >= 2) {
				return isBeer ? Alcohol.WheatBeer.get(5) : Alcohol.Wheat.get(5);
			}
			if (rye >= 2) {
				return isBeer ? Alcohol.RyeBeer.get(5) : Alcohol.Rye.get(5);
			}
			if (corn >= 2) {
				return isBeer ? Alcohol.CornBeer.get(5) : Alcohol.Corn.get(5);
			}
			return isBeer ? (isLager ? Alcohol.Lager.get(5) : Alcohol.Ale.get(5)) : Alcohol.Barley.get(5);
		}

		@Override
		public FluidStack[] getInput() {
			return new FluidStack[]{water};
		}

		@Override
		public FluidStack[] getOutput() {
			return outputs.toArray(new FluidStack[0]);
		}

		@Override
		public ItemStack[] getGrains() {
			return grainCrops.keySet().toArray(new ItemStack[0]);
		}

		@Override
		public ItemStack[] getIngredient() {
			return new ItemStack[]{hops};
		}

		@Override
		public ItemStack[] getYeasts() {
			return new ItemStack[]{
				ExtraTreeItems.Yeast.get(1),
				ExtraTreeItems.LagerYeast.get(1)
			};
		}
	}
}
