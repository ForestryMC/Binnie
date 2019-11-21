package binnie.extratrees.machines.fruitpress;

import binnie.core.machines.Machine;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.machines.fruitpress.recipes.FruitPressRecipeManager;
import net.minecraftforge.fluids.FluidStack;

public class FruitPressLogic extends ComponentProcessSetCost implements IProcess {
	public static final int PROCESS_ENERGY = 1000;
	public static final int PROCESS_LENGTH = 50;

	private int lastProgress;
	private boolean newItem;

	public FruitPressLogic(final Machine machine) {
		super(machine, PROCESS_ENERGY, PROCESS_LENGTH);
		this.lastProgress = 0;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(FruitPressMachine.SLOT_CURRENT)) {
			return new ErrorState(ExtraTreesErrorCode.FRUITPRESS_NO_FRUIT, FruitPressMachine.SLOT_CURRENT);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (!this.getUtil().spaceInTank(FruitPressMachine.TANK_OUTPUT, 5)) {
			return new ErrorState(CoreErrorCode.NO_SPACE_TANK, FruitPressMachine.TANK_OUTPUT);
		}
		FluidStack fluidOutputTank = this.getUtil().getFluid(FruitPressMachine.TANK_OUTPUT);
		FluidStack recipeOutput = FruitPressRecipeManager.getOutput(this.getUtil().getStack(FruitPressMachine.SLOT_CURRENT));
		if (fluidOutputTank != null && !fluidOutputTank.isFluidEqual(recipeOutput)) {
			return new ErrorState(CoreErrorCode.TANK_DIFFRENT_FLUID, FruitPressMachine.TANK_OUTPUT);
		}
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		this.getUtil().decreaseStack(FruitPressMachine.SLOT_CURRENT, 1);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		final FluidStack output = FruitPressRecipeManager.getOutput(this.getUtil().getStack(FruitPressMachine.SLOT_CURRENT));
		if (output == null) {
			return;
		}
		final int newProgress = (int) this.getProgress();
		if(this.newItem)
		{
			final FluidStack tank = new FluidStack(output, output.amount/25);
			this.getUtil().fillTank(FruitPressMachine.TANK_OUTPUT, tank);
			this.newItem = false;
		}
		while (this.lastProgress + 4 <= newProgress) {
			final int change = newProgress - this.lastProgress;
			final int amount = output.amount * change / 100;
			final FluidStack tank = new FluidStack(output, amount);
			this.getUtil().fillTank(FruitPressMachine.TANK_OUTPUT, tank);
			this.lastProgress += 4;
		}
		if (this.lastProgress > newProgress) {
			this.lastProgress = 0;
			this.newItem = true;
		}
	}

	@Override
	protected void onStartTask() {
		super.onStartTask();
		this.lastProgress = 0;
		this.newItem = true;
	}
}
