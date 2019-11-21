package binnie.extratrees.machines.infuser;

import binnie.core.machines.Machine;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class InfuserLogic extends ComponentProcessSetCost implements IProcess {
	private ItemStack infusing;

	public InfuserLogic(final Machine machine) {
		super(machine, 16000, 800);
	}

	@Override
	public int getProcessEnergy() {
		return this.getProcessLength() * 2;
	}

	@Override
	public int getProcessLength() {
		return 20;
	}

	@Override
	public ErrorState canWork() {
		if (this.getUtil().isTankEmpty(Infuser.TANK_INPUT)) {
			return new ErrorState(CoreErrorCode.TANK_EMPTY, Infuser.TANK_INPUT);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		FluidStack outputTankFluid = this.getUtil().getFluid(Infuser.TANK_OUTPUT);
		if (!this.getUtil().isTankEmpty(Infuser.TANK_OUTPUT) && this.getOutput() != null && !this.getOutput().isFluidEqual(outputTankFluid)) {
			return new ErrorState(CoreErrorCode.NO_SPACE_TANK, new int[]{Infuser.TANK_OUTPUT});
		}
		if (outputTankFluid != null && !outputTankFluid.isFluidEqual(this.getOutput())) {
			return new ErrorState(CoreErrorCode.TANK_DIFFRENT_FLUID, Infuser.TANK_OUTPUT);
		}
		return super.canProgress();
	}

	@Nullable
	private FluidStack getOutput() {
		FluidStack fluid = this.getUtil().getFluid(Infuser.TANK_INPUT);
		if (fluid == null) {
			return null;
		}
		return InfuserRecipes.getOutput(fluid, this.infusing);
	}

	@Override
	protected void onFinishTask() {
		final FluidStack output = this.getOutput();
		if (output != null) {
			this.getUtil().fillTank(Infuser.TANK_OUTPUT, output.copy());
		}
	}

	@Override
	public String getTooltip() {
		return "Infusing";
	}
}
