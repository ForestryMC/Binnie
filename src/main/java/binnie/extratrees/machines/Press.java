package binnie.extratrees.machines;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class Press {
	public static int slotFruit = 0;
	public static int slotCurrent = 1;
	public static int tankWater = 0;
	private static Map<ItemStack, FluidStack> pressRecipes = new HashMap<>();

	public static boolean isInput(ItemStack itemstack) {
		return getOutput(itemstack) != null;
	}

	public static FluidStack getOutput(ItemStack itemstack) {
		if (itemstack == null) {
			return null;
		}

		for (Map.Entry<ItemStack, FluidStack> entry : Press.pressRecipes.entrySet()) {
			if (itemstack.isItemEqual(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	public static void addRecipe(ItemStack stack, FluidStack fluid) {
		if (getOutput(stack) != null) {
			return;
		}
		Press.pressRecipes.put(stack, fluid);
	}

	public static class PackagePress extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackagePress() {
			super("press", ExtraTreeTexture.pressTexture, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Press);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Press.slotFruit, "input");
			inventory.getSlot(Press.slotFruit).setValidator(new SlotValidatorSqueezable());
			inventory.getSlot(Press.slotFruit).forbidExtraction();
			inventory.addSlot(Press.slotCurrent, "process");
			inventory.getSlot(Press.slotCurrent).setValidator(new SlotValidatorSqueezable());
			inventory.getSlot(Press.slotCurrent).forbidInteraction();
			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(Press.tankWater, "output", 5000);
			new ComponentPowerReceptor(machine);
			new ComponentInventoryTransfer(machine).addRestock(new int[]{Press.slotFruit}, Press.slotCurrent, 1);
			new ComponentFruitPressLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentFruitPressLogic extends ComponentProcessSetCost implements IProcess {
		protected int lastProgress;

		public ComponentFruitPressLogic(Machine machine) {
			super(machine, 1000, 50);
			lastProgress = 0;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(Press.slotCurrent)) {
				return new ErrorState.NoItem("No Fruit", Press.slotCurrent);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!getUtil().spaceInTank(Press.tankWater, 5)) {
				return new ErrorState.TankSpace("No room in tank", Press.tankWater);
			}
			if (getUtil().getFluid(Press.tankWater) != null && !getUtil().getFluid(Press.tankWater).isFluidEqual(Press.getOutput(getUtil().getStack(Press.slotCurrent)))) {
				return new ErrorState.TankSpace("Different fluid in tank", Press.tankWater);
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			getUtil().decreaseStack(Press.slotCurrent, 1);
		}

		@Override
		protected void onTickTask() {
			// ignored
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			FluidStack output = Press.getOutput(getUtil().getStack(Press.slotCurrent));
			if (output == null) {
				return;
			}

			int newProgress = (int) getProgress();
			while (lastProgress + 4 <= newProgress) {
				int change = newProgress - lastProgress;
				int amount = output.amount * change / 100;
				FluidStack tank = new FluidStack(output, amount);
				getUtil().fillTank(Press.tankWater, tank);
				lastProgress += 4;
			}

			if (lastProgress > newProgress) {
				lastProgress = 0;
			}
		}

		@Override
		protected void onStartTask() {
			super.onStartTask();
			lastProgress = 0;
		}
	}

	public static class SlotValidatorSqueezable extends SlotValidator {
		public SlotValidatorSqueezable() {
			super(SlotValidator.IconBlock);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Press.isInput(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Fruit";
		}
	}
}
