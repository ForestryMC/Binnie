package binnie.extratrees.machines;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;

public class Infuser {
	public static final int tankInput = 0;
	public static final int tankOutput = 1;
	static Map<Fluid, FluidStack> recipes = new HashMap<>();

	@Nullable
	public static FluidStack getOutput(final FluidStack fluid, final ItemStack stack) {
		return Infuser.recipes.get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(final FluidStack fluid) {
		return Infuser.recipes.containsKey(fluid.getFluid());
	}

	public static boolean isValidOutputLiquid(final FluidStack fluid) {
		for (final Map.Entry<Fluid, FluidStack> entry : Infuser.recipes.entrySet()) {
			if (entry.getValue().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(final FluidStack input, final FluidStack output) {
		Infuser.recipes.put(input.getFluid(), output);
	}

	public static class PackageInfuser extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackageInfuser() {
			super("infuser", ExtraTreeTexture.Infuser, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Infuser);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			final ComponentTankContainer tanks = new ComponentTankContainer(machine);

			final TankSlot input = tanks.addTank(Infuser.tankInput, "input", 5000);
			input.setValidator(new TankValidatorInfuserInput());
			input.setOutputSides(MachineSide.TopAndBottom);

			final TankSlot output = tanks.addTank(Infuser.tankOutput, "output", 5000);
			output.setValidator(new TankValidatorInfuserOutput());
			output.setReadOnly();
			output.setOutputSides(MachineSide.Sides);

			new ComponentPowerReceptor(machine);
			new ComponentInfuserLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class ComponentInfuserLogic extends ComponentProcessSetCost implements IProcess {
		ItemStack infusing;

		public ComponentInfuserLogic(final Machine machine) {
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
			if (this.getUtil().isTankEmpty(Infuser.tankInput)) {
				return new ErrorState.InsufficientLiquid("No Input Liquid", Infuser.tankInput);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			FluidStack outputTankFluid = this.getUtil().getFluid(Infuser.tankOutput);
			if (!this.getUtil().isTankEmpty(Infuser.tankOutput) && this.getOutput() != null && !this.getOutput().isFluidEqual(outputTankFluid)) {
				return new ErrorState.Tank("No Room", "No room for liquid", new int[]{Infuser.tankOutput});
			}
			if (outputTankFluid != null && !outputTankFluid.isFluidEqual(this.getOutput())) {
				return new ErrorState.TankSpace("Different fluid in tank", Infuser.tankOutput);
			}
			return super.canProgress();
		}

		@Nullable
		private FluidStack getOutput() {
			FluidStack fluid = this.getUtil().getFluid(Infuser.tankInput);
			if (fluid == null) {
				return null;
			}
			return Infuser.getOutput(fluid, this.infusing);
		}

		@Override
		protected void onFinishTask() {
			final FluidStack output = this.getOutput();
			if (output != null) {
				this.getUtil().fillTank(Infuser.tankOutput, output.copy());
			}
		}

		@Override
		protected void onTickTask() {
		}

		@Override
		public String getTooltip() {
			return "Infusing";
		}
	}

	public static class TankValidatorInfuserInput extends TankValidator {
		@Override
		public boolean isValid(final FluidStack itemStack) {
			return Infuser.isValidInputLiquid(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Infusable Liquids";
		}
	}

	public static class TankValidatorInfuserOutput extends TankValidator {
		@Override
		public boolean isValid(final FluidStack itemStack) {
			return Infuser.isValidOutputLiquid(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Infused Liquids";
		}
	}
}
