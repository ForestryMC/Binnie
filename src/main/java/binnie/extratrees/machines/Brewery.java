// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.liquid.IFluidType;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.*;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
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

public class Brewery
{
	public static int[] slotRecipeGrains;
	public static int slotRecipeInput;
	public static int slotRecipeYeast;
	public static int[] slotInventory;
	public static int tankInput;
	public static int tankOutput;
	private static List<IBreweryRecipe> recipes;

	public static boolean isValidGrain(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			for (final ItemStack ingredient : recipe.getGrains()) {
				if (ingredient.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidYeast(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			for (final ItemStack ingredient : recipe.getYeasts()) {
				if (ingredient.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static FluidStack getOutput(final FluidStack stack) {
		final BreweryCrafting crafting = new BreweryCrafting(stack, null, null, null);
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			if (recipe.getOutput(crafting) != null) {
				return recipe.getOutput(crafting);
			}
		}
		return null;
	}

	public static FluidStack getOutput(final BreweryCrafting crafting) {
		if (crafting.currentInput == null) {
			return null;
		}
		if (crafting.yeast == null) {
			return null;
		}
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			if (recipe.getOutput(crafting) != null) {
				return recipe.getOutput(crafting);
			}
		}
		return null;
	}

	public static boolean isValidIngredient(final ItemStack itemstack) {
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			for (final ItemStack ingr : recipe.getIngredient()) {
				if (ingr.isItemEqual(itemstack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			for (final FluidStack ingr : recipe.getInput()) {
				if (ingr.isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final IBreweryRecipe recipe : Brewery.recipes) {
			for (final FluidStack ingr : recipe.getOutput()) {
				if (ingr.isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output) {
		Brewery.recipes.add(new BreweryRecipe(input, output));
	}

	public static void addBeerAndMashRecipes() {
		Brewery.recipes.add(new BeerRecipe());
	}

	static {
		Brewery.slotRecipeGrains = new int[] { 0, 1, 2 };
		Brewery.slotRecipeInput = 3;
		Brewery.slotRecipeYeast = 4;
		Brewery.slotInventory = new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		Brewery.tankInput = 0;
		Brewery.tankOutput = 1;
		Brewery.recipes = new ArrayList<IBreweryRecipe>();
	}

	public static class PackageBrewery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation
	{
		public PackageBrewery() {
			super("brewery", ExtraTreeTexture.breweryTexture, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Brewery);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Brewery.slotRecipeGrains, "grain");
			for (final InventorySlot slot : inventory.getSlots(Brewery.slotRecipeGrains)) {
				slot.setValidator(new SlotValidatorBreweryGrain());
				slot.setType(InventorySlot.Type.Recipe);
			}
			inventory.addSlotArray(Brewery.slotInventory, "inventory");
			for (final InventorySlot slot : inventory.getSlots(Brewery.slotInventory)) {
				slot.forbidExtraction();
			}
			inventory.addSlot(Brewery.slotRecipeYeast, "yeast");
			inventory.getSlot(Brewery.slotRecipeYeast).setValidator(new SlotValidatorYeast());
			inventory.getSlot(Brewery.slotRecipeYeast).setType(InventorySlot.Type.Recipe);
			inventory.addSlot(Brewery.slotRecipeInput, "ingredient");
			inventory.getSlot(Brewery.slotRecipeInput).setValidator(new SlotValidatorBreweryIngredient());
			inventory.getSlot(Brewery.slotRecipeInput).setType(InventorySlot.Type.Recipe);
			final ComponentTankContainer tanks = new ComponentTankContainer(machine);
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

		@Override
		public void register() {
		}
	}

	public static class BreweryCrafting implements INBTTagable
	{
		public FluidStack currentInput;
		public ItemStack[] inputs;
		public ItemStack ingr;
		public ItemStack yeast;

		public BreweryCrafting(final FluidStack currentInput, final ItemStack ingr, final ItemStack[] inputs, final ItemStack yeast) {
			this.currentInput = currentInput;
			this.inputs = ((inputs == null) ? new ItemStack[3] : inputs);
			this.ingr = ingr;
			this.yeast = yeast;
		}

		public BreweryCrafting(final NBTTagCompound nbt) {
			this.readFromNBT(nbt);
		}

		@Override
		public void readFromNBT(final NBTTagCompound nbt) {
			this.currentInput = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluid"));
			this.ingr = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ingr"));
			(this.inputs = new ItemStack[3])[0] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in1"));
			this.inputs[1] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in2"));
			this.inputs[2] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in3"));
			this.yeast = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("yeast"));
		}

		@Override
		public void writeToNBT(final NBTTagCompound nbt) {
			final NBTTagCompound fluidTag = new NBTTagCompound();
			this.currentInput.writeToNBT(fluidTag);
			nbt.setTag("fluid", fluidTag);
			nbt.setTag("ingr", this.getNBT(this.ingr));
			nbt.setTag("in1", this.getNBT(this.inputs[0]));
			nbt.setTag("in2", this.getNBT(this.inputs[1]));
			nbt.setTag("in3", this.getNBT(this.inputs[2]));
			nbt.setTag("yeast", this.getNBT(this.yeast));
		}

		private NBTTagCompound getNBT(final ItemStack ingr) {
			if (ingr == null) {
				return new NBTTagCompound();
			}
			final NBTTagCompound nbt = new NBTTagCompound();
			ingr.writeToNBT(nbt);
			return nbt;
		}
	}

	public static class ComponentBreweryLogic extends ComponentProcessSetCost implements IProcess, INetwork.GuiNBT
	{
		public BreweryCrafting currentCrafting;

		public ComponentBreweryLogic(final Machine machine) {
			super(machine, 16000, 800);
			this.currentCrafting = null;
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isTankEmpty(Brewery.tankInput) && this.currentCrafting == null) {
				return new ErrorState.InsufficientLiquid("No Input Liquid", Brewery.tankInput);
			}
			if (Brewery.getOutput(this.getInputCrafting()) == null && this.currentCrafting == null) {
				return new ErrorState("No Recipe", "Brewing cannot occur with these ingredients");
			}
			if (!this.getUtil().hasIngredients(new int[] { 0, 1, 2, 3, 4 }, Brewery.slotInventory) && this.currentCrafting == null) {
				return new ErrorState("Insufficient Ingredients", "Not enough ingredients for Brewing");
			}
			return super.canWork();
		}

		private BreweryCrafting getInputCrafting() {
			return new BreweryCrafting(this.getUtil().getFluid(Brewery.tankInput), this.getUtil().getStack(Brewery.slotRecipeInput), this.getUtil().getStacks(Brewery.slotRecipeGrains), this.getUtil().getStack(Brewery.slotRecipeYeast));
		}

		@Override
		public ErrorState canProgress() {
			if (this.currentCrafting == null) {
				return new ErrorState("Brewery Empty", "No liquid in Brewery");
			}
			if (!this.getUtil().spaceInTank(Brewery.tankOutput, 1000)) {
				return new ErrorState.TankSpace("No Space for Fermented Liquid", Brewery.tankOutput);
			}
			if (this.getUtil().getFluid(Brewery.tankOutput) != null && !this.getUtil().getFluid(Brewery.tankOutput).isFluidEqual(Brewery.getOutput(this.currentCrafting))) {
				return new ErrorState.TankSpace("Different fluid in tank", Brewery.tankOutput);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			final FluidStack output = Brewery.getOutput(this.currentCrafting).copy();
			output.amount = 1000;
			this.getUtil().fillTank(Brewery.tankOutput, output);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			if (this.canWork() == null && this.currentCrafting == null && this.getUtil().getTank(Brewery.tankInput).getFluidAmount() >= 1000) {
				final FluidStack stack = this.getUtil().drainTank(Brewery.tankInput, 1000);
				this.currentCrafting = this.getInputCrafting();
				this.currentCrafting.currentInput = stack;
			}
		}

		@Override
		public void sendGuiNBT(final Map<String, NBTTagCompound> nbts) {
			final NBTTagCompound nbt = new NBTTagCompound();
			if (this.currentCrafting == null) {
				nbt.setBoolean("null", true);
			}
			else {
				this.currentCrafting.writeToNBT(nbt);
			}
			nbts.put("brewery-recipe", nbt);
		}

		@Override
		public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
			if (name.equals("brewery-recipe")) {
				if (nbt.getBoolean("null")) {
					this.currentCrafting = null;
				}
				else {
					this.currentCrafting = new BreweryCrafting(nbt);
				}
			}
		}

		@Override
		public String getTooltip() {
			if (this.currentCrafting == null) {
				return "Empty";
			}
			return "Creating " + Brewery.getOutput(this.currentCrafting).getFluid().getLocalizedName();
		}
	}

	public static class SlotValidatorBreweryGrain extends SlotValidator
	{
		public SlotValidatorBreweryGrain() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return Brewery.isValidGrain(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Brewing Grains";
		}
	}

	public static class SlotValidatorBreweryIngredient extends SlotValidator
	{
		public SlotValidatorBreweryIngredient() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return Brewery.isValidIngredient(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Brewing Ingredient";
		}
	}

	public static class SlotValidatorYeast extends SlotValidator
	{
		public SlotValidatorYeast() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return Brewery.isValidYeast(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Yeast";
		}
	}

	public static class TankValidatorFermentInput extends TankValidator
	{
		@Override
		public boolean isValid(final FluidStack liquid) {
			return Brewery.isValidInputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Fermentable Liquids";
		}
	}

	public static class TankValidatorFermentOutput extends TankValidator
	{
		@Override
		public boolean isValid(final FluidStack liquid) {
			return Brewery.isValidOutputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Fermented Liquids";
		}
	}

	public static class BreweryRecipe implements IBreweryRecipe
	{
		FluidStack input;
		ItemStack specificYeast;
		FluidStack output;

		public BreweryRecipe(final FluidStack input, final FluidStack output) {
			this(input, null, output);
		}

		public BreweryRecipe(final FluidStack input, final ItemStack specificYeast, final FluidStack output) {
			this.input = input;
			this.specificYeast = specificYeast;
			this.output = output;
		}

		@Override
		public FluidStack getOutput(final BreweryCrafting crafting) {
			if (this.specificYeast != null && !this.specificYeast.isItemEqual(crafting.yeast)) {
				return null;
			}
			if (this.input.isFluidEqual(crafting.currentInput)) {
				return this.output;
			}
			return null;
		}

		@Override
		public FluidStack[] getInput() {
			return new FluidStack[] { this.input };
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
			return new ItemStack[] { ExtraTreeItems.Yeast.get(1) };
		}
	}

	public static class BeerRecipe implements IBreweryRecipe
	{
		Map<ItemStack, String> grainCrops;
		List<FluidStack> outputs;
		static String barley;
		static String wheat;
		static String rye;
		static String corn;
		static String roasted;
		String[] grains;
		FluidStack water;
		ItemStack hops;

		public BeerRecipe() {
			this.grainCrops = new HashMap<ItemStack, String>();
			this.outputs = new ArrayList<FluidStack>();
			this.grains = new String[] { BeerRecipe.barley, BeerRecipe.wheat, BeerRecipe.rye, BeerRecipe.corn, BeerRecipe.roasted };
			for (final String grainType : this.grains) {
				for (final ItemStack stack : OreDictionary.getOres(grainType)) {
					this.grainCrops.put(stack, grainType);
				}
			}
			this.water = Binnie.Liquid.getLiquidStack("water", 5);
			this.hops = OreDictionary.getOres("cropHops").get(0);
			this.add(Alcohol.Ale);
			this.add(Alcohol.Lager);
			this.add(Alcohol.Stout);
			this.add(Alcohol.CornBeer);
			this.add(Alcohol.RyeBeer);
			this.add(Alcohol.WheatBeer);
			this.add(Alcohol.Barley);
			this.add(Alcohol.Corn);
			this.add(Alcohol.Rye);
			this.add(Alcohol.Wheat);
		}

		private void add(final IFluidType fluid) {
			this.outputs.add(fluid.get(1));
		}

		@Override
		public FluidStack getOutput(final BreweryCrafting crafting) {
			if (!crafting.currentInput.isFluidEqual(this.water)) {
				return null;
			}
			int roasted = 0;
			int barley = 0;
			int wheat = 0;
			int rye = 0;
			int corn = 0;
			for (final ItemStack stack : crafting.inputs) {
				if (stack == null) {
					return null;
				}
				String name = "";
				for (final Map.Entry<ItemStack, String> entry : this.grainCrops.entrySet()) {
					if (entry.getKey().isItemEqual(stack)) {
						name = entry.getValue();
						break;
					}
				}
				if (name.isEmpty()) {
					return null;
				}
				if (name.equals(BeerRecipe.roasted)) {
					++roasted;
				}
				if (name.equals(BeerRecipe.barley)) {
					++barley;
				}
				if (name.equals(BeerRecipe.wheat)) {
					++wheat;
				}
				if (name.equals(BeerRecipe.rye)) {
					++rye;
				}
				if (name.equals(BeerRecipe.corn)) {
					++corn;
				}
			}
			final boolean isBeer = crafting.ingr != null;
			final boolean isLager = crafting.yeast.isItemEqual(ExtraTreeItems.LagerYeast.get(1));
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
			return new FluidStack[] { this.water };
		}

		@Override
		public FluidStack[] getOutput() {
			return this.outputs.toArray(new FluidStack[0]);
		}

		@Override
		public ItemStack[] getGrains() {
			return this.grainCrops.keySet().toArray(new ItemStack[0]);
		}

		@Override
		public ItemStack[] getIngredient() {
			return new ItemStack[] { this.hops };
		}

		@Override
		public ItemStack[] getYeasts() {
			return new ItemStack[] { ExtraTreeItems.Yeast.get(1), ExtraTreeItems.LagerYeast.get(1) };
		}

		static {
			BeerRecipe.barley = "seedBarley";
			BeerRecipe.wheat = "seedWheat";
			BeerRecipe.rye = "seedRye";
			BeerRecipe.corn = "seedCorn";
			BeerRecipe.roasted = "seedRoasted";
		}
	}

	private interface IBreweryRecipe
	{
		FluidStack getOutput(final BreweryCrafting p0);

		FluidStack[] getInput();

		FluidStack[] getOutput();

		ItemStack[] getGrains();

		ItemStack[] getIngredient();

		ItemStack[] getYeasts();
	}
}
