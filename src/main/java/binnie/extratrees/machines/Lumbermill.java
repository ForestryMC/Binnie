package binnie.extratrees.machines;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.item.ItemMisc;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lumbermill {
	public static int slotWood = 0;
	public static int slotPlanks = 1;
	public static int slotBark = 2;
	public static int slotSawdust = 3;
	public static int tankWater = 0;
	static Map<ItemStack, ItemStack> recipes = new HashMap<>();

	public static ItemStack getPlankProduct(ItemStack item) {
		ItemStack stack = null;
		if (Lumbermill.recipes.isEmpty()) {
			calculateLumbermillProducts();
		}

		for (Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
			if (entry.getKey().isItemEqual(item)) {
				stack = entry.getValue().copy();
				break;
			}
		}
		stack.stackSize = 6;
		return stack;
	}

	private static void calculateLumbermillProducts() {
		for (IPlankType type : WoodManager.getAllPlankTypes()) {
			for (ItemStack wood : getRecipeResult(type.getStack())) {
				Lumbermill.recipes.put(wood, type.getStack());
			}
		}
	}

	private static Collection<ItemStack> getRecipeResult(ItemStack output) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (Object recipeO : CraftingManager.getInstance().getRecipeList()) {
			if (recipeO instanceof ShapelessRecipes) {
				ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
				if (recipe.recipeItems.size() != 1 || !(recipe.recipeItems.get(0) instanceof ItemStack)) {
					continue;
				}

				ItemStack input = (ItemStack) recipe.recipeItems.get(0);
				if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
					list.add(input);
				}
			}

			if (recipeO instanceof ShapedRecipes) {
				ShapedRecipes recipe2 = (ShapedRecipes) recipeO;
				if (recipe2.recipeItems.length != 1) {
					continue;
				}

				ItemStack input = recipe2.recipeItems[0];
				if (recipe2.getRecipeOutput() != null && recipe2.getRecipeOutput().isItemEqual(output)) {
					list.add(input);
				}
			}

			if (recipeO instanceof ShapelessOreRecipe) {
				ShapelessOreRecipe recipe3 = (ShapelessOreRecipe) recipeO;
				if (recipe3.getInput().size() != 1 || !(recipe3.getInput().get(0) instanceof ItemStack)) {
					continue;
				}

				ItemStack input = (ItemStack) recipe3.getInput().get(0);
				if (recipe3.getRecipeOutput() == null || !recipe3.getRecipeOutput().isItemEqual(output)) {
					continue;
				}
				list.add(input);
			}
		}
		return list;
	}

	public static class PackageLumbermill extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackageLumbermill() {
			super("lumbermill", ExtraTreeTexture.lumbermillTexture, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Lumbermill.slotWood, "input");
			inventory.getSlot(Lumbermill.slotWood).setValidator(new SlotValidatorLog());
			inventory.getSlot(Lumbermill.slotWood).forbidExtraction();
			inventory.addSlot(Lumbermill.slotPlanks, "output");
			inventory.addSlot(Lumbermill.slotBark, "byproduct");
			inventory.addSlot(Lumbermill.slotSawdust, "byproduct");
			inventory.getSlot(Lumbermill.slotPlanks).setReadOnly();
			inventory.getSlot(Lumbermill.slotBark).setReadOnly();
			inventory.getSlot(Lumbermill.slotSawdust).setReadOnly();
			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(Lumbermill.tankWater, "input", 10000);
			tanks.getTankSlot(Lumbermill.tankWater).setValidator(new TankValidator.Basic("water"));
			new ComponentPowerReceptor(machine);
			new ComponentLumbermillLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentLumbermillLogic extends ComponentProcessSetCost implements IProcess {
		public ComponentLumbermillLogic(Machine machine) {
			super(machine, 900, 30);
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(Lumbermill.slotWood)) {
				return new ErrorState.NoItem("No Wood", Lumbermill.slotWood);
			}

			ItemStack result = Lumbermill.getPlankProduct(getUtil().getStack(Lumbermill.slotWood));
			if (!getUtil().isSlotEmpty(Lumbermill.slotPlanks) && result != null) {
				ItemStack currentPlank = getUtil().getStack(Lumbermill.slotPlanks);
				if (!result.isItemEqual(currentPlank) || result.stackSize + currentPlank.stackSize > currentPlank.getMaxStackSize()) {
					return new ErrorState.NoSpace("No room for new planks", new int[]{Lumbermill.slotPlanks});
				}
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!getUtil().liquidInTank(Lumbermill.tankWater, 5)) {
				return new ErrorState.InsufficientLiquid("Not Enough Water", Lumbermill.tankWater);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			ItemStack result = Lumbermill.getPlankProduct(getUtil().getStack(Lumbermill.slotWood));
			if (result == null) {
				return;
			}

			getUtil().addStack(Lumbermill.slotPlanks, result);
			getUtil().addStack(Lumbermill.slotSawdust, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Sawdust, 1));
			getUtil().addStack(Lumbermill.slotBark, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Bark, 1));
			getUtil().decreaseStack(Lumbermill.slotWood, 1);
		}

		@Override
		protected void onTickTask() {
			getUtil().drainTank(Lumbermill.tankWater, 10);
		}
	}

	public static class SlotValidatorLog extends SlotValidator {
		public SlotValidatorLog() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			String name = OreDictionary.getOreName(OreDictionary.getOreID(itemStack));
			return name.contains("logWood") && Lumbermill.getPlankProduct(itemStack) != null;
		}

		@Override
		public String getTooltip() {
			return "Logs";
		}
	}
}
