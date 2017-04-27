// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import net.minecraftforge.oredict.OreDictionary;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.ExtraTrees;
import binnie.core.item.ItemMisc;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.core.craftgui.minecraft.IMachineInformation;
import java.util.HashMap;
import java.util.List;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.item.crafting.CraftingManager;
import java.util.ArrayList;
import java.util.Collection;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.item.ItemStack;
import java.util.Map;

public class Lumbermill
{
	public static int slotWood;
	public static int slotPlanks;
	public static int slotBark;
	public static int slotSawdust;
	public static int tankWater;
	static Map<ItemStack, ItemStack> recipes;

	public static ItemStack getPlankProduct(final ItemStack item) {
		ItemStack stack = null;
		if (Lumbermill.recipes.isEmpty()) {
			calculateLumbermillProducts();
		}
		for (final Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
			if (entry.getKey().isItemEqual(item)) {
				stack = entry.getValue().copy();
				break;
			}
		}
		stack.stackSize = 6;
		return stack;
	}

	private static void calculateLumbermillProducts() {
		for (final IPlankType type : WoodManager.getAllPlankTypes()) {
			for (final ItemStack wood : getRecipeResult(type.getStack())) {
				Lumbermill.recipes.put(wood, type.getStack());
			}
		}
	}

	private static Collection<ItemStack> getRecipeResult(final ItemStack output) {
		final List<ItemStack> list = new ArrayList<ItemStack>();
		for (final Object recipeO : CraftingManager.getInstance().getRecipeList()) {
			if (recipeO instanceof ShapelessRecipes) {
				final ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
				if (recipe.recipeItems.size() != 1) {
					continue;
				}
				if (!(recipe.recipeItems.get(0) instanceof ItemStack)) {
					continue;
				}
				final ItemStack input = (ItemStack) recipe.recipeItems.get(0);
				if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
					list.add(input);
				}
			}
			if (recipeO instanceof ShapedRecipes) {
				final ShapedRecipes recipe2 = (ShapedRecipes) recipeO;
				if (recipe2.recipeItems.length != 1) {
					continue;
				}
				final ItemStack input = recipe2.recipeItems[0];
				if (recipe2.getRecipeOutput() != null && recipe2.getRecipeOutput().isItemEqual(output)) {
					list.add(input);
				}
			}
			if (recipeO instanceof ShapelessOreRecipe) {
				final ShapelessOreRecipe recipe3 = (ShapelessOreRecipe) recipeO;
				if (recipe3.getInput().size() != 1) {
					continue;
				}
				if (!(recipe3.getInput().get(0) instanceof ItemStack)) {
					continue;
				}
				final ItemStack input = (ItemStack) recipe3.getInput().get(0);
				if (recipe3.getRecipeOutput() == null || !recipe3.getRecipeOutput().isItemEqual(output)) {
					continue;
				}
				list.add(input);
			}
		}
		return list;
	}

	static {
		Lumbermill.slotWood = 0;
		Lumbermill.slotPlanks = 1;
		Lumbermill.slotBark = 2;
		Lumbermill.slotSawdust = 3;
		Lumbermill.tankWater = 0;
		Lumbermill.recipes = new HashMap<ItemStack, ItemStack>();
	}

	public static class PackageLumbermill extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation
	{
		public PackageLumbermill() {
			super("lumbermill", ExtraTreeTexture.lumbermillTexture, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Lumbermill.slotWood, "input");
			inventory.getSlot(Lumbermill.slotWood).setValidator(new SlotValidatorLog());
			inventory.getSlot(Lumbermill.slotWood).forbidExtraction();
			inventory.addSlot(Lumbermill.slotPlanks, "output");
			inventory.addSlot(Lumbermill.slotBark, "byproduct");
			inventory.addSlot(Lumbermill.slotSawdust, "byproduct");
			inventory.getSlot(Lumbermill.slotPlanks).setReadOnly();
			inventory.getSlot(Lumbermill.slotBark).setReadOnly();
			inventory.getSlot(Lumbermill.slotSawdust).setReadOnly();
			final ComponentTankContainer tanks = new ComponentTankContainer(machine);
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

	public static class ComponentLumbermillLogic extends ComponentProcessSetCost implements IProcess
	{
		public ComponentLumbermillLogic(final Machine machine) {
			super(machine, 900, 30);
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isSlotEmpty(Lumbermill.slotWood)) {
				return new ErrorState.NoItem("No Wood", Lumbermill.slotWood);
			}
			final ItemStack result = Lumbermill.getPlankProduct(this.getUtil().getStack(Lumbermill.slotWood));
			if (!this.getUtil().isSlotEmpty(Lumbermill.slotPlanks) && result != null) {
				final ItemStack currentPlank = this.getUtil().getStack(Lumbermill.slotPlanks);
				if (!result.isItemEqual(currentPlank) || result.stackSize + currentPlank.stackSize > currentPlank.getMaxStackSize()) {
					return new ErrorState.NoSpace("No room for new planks", new int[] { Lumbermill.slotPlanks });
				}
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!this.getUtil().liquidInTank(Lumbermill.tankWater, 5)) {
				return new ErrorState.InsufficientLiquid("Not Enough Water", Lumbermill.tankWater);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			final ItemStack result = Lumbermill.getPlankProduct(this.getUtil().getStack(Lumbermill.slotWood));
			if (result == null) {
				return;
			}
			this.getUtil().addStack(Lumbermill.slotPlanks, result);
			this.getUtil().addStack(Lumbermill.slotSawdust, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Sawdust, 1));
			this.getUtil().addStack(Lumbermill.slotBark, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Bark, 1));
			this.getUtil().decreaseStack(Lumbermill.slotWood, 1);
		}

		@Override
		protected void onTickTask() {
			this.getUtil().drainTank(Lumbermill.tankWater, 10);
		}
	}

	public static class SlotValidatorLog extends SlotValidator
	{
		public SlotValidatorLog() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			final String name = OreDictionary.getOreName(OreDictionary.getOreID(itemStack));
			return name.contains("logWood") && Lumbermill.getPlankProduct(itemStack) != null;
		}

		@Override
		public String getTooltip() {
			return "Logs";
		}
	}
}
