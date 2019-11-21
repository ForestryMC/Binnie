package binnie.extratrees.machines.distillery;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.machines.distillery.recipes.DistilleryRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Map;

public class DistilleryLogic extends ComponentProcessSetCost implements IProcess, INetwork.SendGuiNBT, INetwork.ReceiveGuiNBT {
	public static final int INPUT_FLUID_AMOUNT = Fluid.BUCKET_VOLUME;

	@Nullable
	private FluidStack currentFluid;
	private int level;

	public DistilleryLogic(final Machine machine) {
		super(machine, 16000, 800);
		this.currentFluid = null;
		this.level = 0;
	}

	@Override
	public float getEnergyPerTick() {
		return 2.0f;
	}

	@Override
	public int getProcessLength() {
		return 2000 + 800 * this.level;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.level = nbt.getByte("dlevel");

		NBTTagCompound fluidNbt = nbt.getCompoundTag("fluid");
		this.currentFluid = FluidStack.loadFluidStackFromNBT(fluidNbt);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt2) {
		NBTTagCompound nbt = super.writeToNBT(nbt2);
		nbt.setByte("dlevel", (byte) this.level);

		NBTTagCompound fluidNbt = new NBTTagCompound();
		if (this.currentFluid != null) {
			this.currentFluid.writeToNBT(fluidNbt);
		}
		nbt.setTag("fluid", fluidNbt);

		return nbt;
	}

	@Override
	@Nullable
	public ErrorState canWork() {
		if (this.getUtil().isTankEmpty(DistilleryMachine.TANK_INPUT) && this.currentFluid == null) {
			return new ErrorState(ExtraTreesErrorCode.DISTILLERY_INSUFFICIENT_LIQUID, DistilleryMachine.TANK_INPUT);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.currentFluid == null) {
			return new ErrorState(CoreErrorCode.TANK_EMPTY);
		}

		final MachineUtil util = this.getUtil();
		FluidStack fluidInOutputTank = util.getFluid(DistilleryMachine.TANK_OUTPUT);
		if (fluidInOutputTank != null) {
			FluidStack inputFluid = util.getFluid(DistilleryMachine.TANK_INPUT);
			FluidStack recipeOutput = DistilleryRecipeManager.getOutput(inputFluid, this.level);
			if (recipeOutput != null) {
				if (!recipeOutput.isFluidEqual(fluidInOutputTank)) {
					return new ErrorState(CoreErrorCode.TANK_DIFFRENT_FLUID, DistilleryMachine.TANK_OUTPUT);
				} else if (!util.spaceInTank(DistilleryMachine.TANK_OUTPUT, recipeOutput.amount)) {
					return new ErrorState(CoreErrorCode.NO_SPACE_TANK, DistilleryMachine.TANK_OUTPUT);
				}
			}
		}

		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		final FluidStack output = DistilleryRecipeManager.getOutput(this.currentFluid, this.level);
		if (output != null) {
			this.getUtil().fillTank(DistilleryMachine.TANK_OUTPUT, output);
		}
		this.currentFluid = null;
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {

	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("still-level")) {
			this.level = nbt.getByte("i");
			this.setProgress(0);
		}
		if (name.equals("still-recipe")) {
			if (nbt.getBoolean("null")) {
				this.currentFluid = null;
			} else {
				this.currentFluid = FluidStack.loadFluidStackFromNBT(nbt);
			}
		}
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> data) {
		final NBTTagCompound nbt = new NBTTagCompound();
		if (this.currentFluid == null) {
			nbt.setBoolean("null", true);
		} else {
			this.currentFluid.writeToNBT(nbt);
		}
		data.put("still-recipe", nbt);
		nbt.setByte("i", (byte) this.level);
		data.put("still-level", nbt);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.canWork() == null && this.currentFluid == null && this.getUtil().getTank(DistilleryMachine.TANK_INPUT).getFluidAmount() >= INPUT_FLUID_AMOUNT) {
			this.currentFluid = this.getUtil().drainTank(DistilleryMachine.TANK_INPUT, INPUT_FLUID_AMOUNT);
		}
	}

	@Override
	public String getTooltip() {
		if (this.currentFluid == null) {
			return I18N.localise("extratrees.machine.distillery.tooltips.empty");
		}
		FluidStack output = DistilleryRecipeManager.getOutput(this.currentFluid, this.level);
		if (output == null) {
			return I18N.localise("extratrees.machine.distillery.tooltips.empty");
		}
		return I18N.localise("extratrees.machine.distillery.tooltips.creating", output.getLocalizedName());
	}

	@Nullable
	public FluidStack getCurrentFluid() {
		return currentFluid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
