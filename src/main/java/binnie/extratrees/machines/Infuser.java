package binnie.extratrees.machines;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class Infuser {
	public static int tankInput = 0;
	public static int tankOutput = 1;
	static Map<Fluid, FluidStack> recipes = new HashMap<>();

	public static FluidStack getOutput(FluidStack fluid, ItemStack stack) {
		if (fluid == null) {
			return null;
		}
		return Infuser.recipes.get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(FluidStack fluid) {
		return Infuser.recipes.containsKey(fluid.getFluid());
	}

	public static boolean isValidOutputLiquid(FluidStack fluid) {
		for (Map.Entry<Fluid, FluidStack> entry : Infuser.recipes.entrySet()) {
			if (entry.getValue().isFluidEqual(fluid)) {
				return true;
			}
		}
		return false;
	}

	public static void addRecipe(FluidStack input, FluidStack output) {
		Infuser.recipes.put(input.getFluid(), output);
	}

	// TODO unused class?
	public static class PackageInfuser extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackageInfuser() {
			super("infuser", ExtraTreeTexture.infuserTexture, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Infuser);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(Infuser.tankInput, "input", 5000);
			tanks.getTankSlot(Infuser.tankInput).setValidator(new TankValidatorInfuserInput());
			tanks.getTankSlot(Infuser.tankInput).setOutputSides(MachineSide.TopAndBottom);
			tanks.addTank(Infuser.tankOutput, "output", 5000);
			tanks.getTankSlot(Infuser.tankOutput).setValidator(new TankValidatorInfuserOutput());
			tanks.getTankSlot(Infuser.tankOutput).setReadOnly();
			tanks.getTankSlot(Infuser.tankOutput).setOutputSides(MachineSide.Sides);
			new ComponentPowerReceptor(machine);
			new ComponentInfuserLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentInfuserLogic extends ComponentProcessSetCost implements IProcess {
		protected ItemStack infusing;

		public ComponentInfuserLogic(Machine machine) {
			super(machine, 16000, 800);
		}

		@Override
		public int getProcessEnergy() {
			return getProcessLength() * 2;
		}

		@Override
		public int getProcessLength() {
			return 20;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isTankEmpty(Infuser.tankInput)) {
				return new ErrorState.InsufficientLiquid("No Input Liquid", Infuser.tankInput);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!getUtil().isTankEmpty(Infuser.tankOutput) && getOutput() != null && !getOutput().isFluidEqual(getUtil().getFluid(Infuser.tankOutput))) {
				return new ErrorState.Tank("No Room", "No room for liquid", new int[]{Infuser.tankOutput});
			}
			if (getUtil().getFluid(Infuser.tankOutput) != null && !getUtil().getFluid(Infuser.tankOutput).isFluidEqual(getOutput())) {
				return new ErrorState.TankSpace("Different fluid in tank", Infuser.tankOutput);
			}
			return super.canProgress();
		}

		private FluidStack getOutput() {
			return Infuser.getOutput(getUtil().getFluid(Infuser.tankInput), infusing);
		}

		@Override
		protected void onFinishTask() {
			FluidStack output = getOutput().copy();
			getUtil().fillTank(Infuser.tankOutput, output);
		}

		@Override
		protected void onTickTask() {
			// ignored
		}

		@Override
		public String getTooltip() {
			return "Infusing";
		}
	}

	public static class TankValidatorInfuserInput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Infuser.isValidInputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Infusable Liquids";
		}
	}

	public static class TankValidatorInfuserOutput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Infuser.isValidOutputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Infused Liquids";
		}
	}
}
