package binnie.extratrees.machines;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Distillery {
	public static int tankInput = 0;
	public static int tankOutput = 1;

	protected static List<Map<Fluid, FluidStack>> recipes = new ArrayList<>();

	static {
		Distillery.recipes.add(new HashMap<>());
		Distillery.recipes.add(new HashMap<>());
		Distillery.recipes.add(new HashMap<>());
	}

	public static FluidStack getOutput(FluidStack fluid, int level) {
		if (fluid == null) {
			return null;
		}
		return Distillery.recipes.get(level).get(fluid.getFluid());
	}

	public static boolean isValidInputLiquid(FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			if (Distillery.recipes.get(i).containsKey(fluid.getFluid())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutputLiquid(FluidStack fluid) {
		for (int i = 0; i < 3; ++i) {
			for (Map.Entry<Fluid, FluidStack> entry : Distillery.recipes.get(i).entrySet()) {
				if (entry.getValue().isFluidEqual(fluid)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addRecipe(FluidStack input, FluidStack output, int level) {
		Distillery.recipes.get(level).put(input.getFluid(), output);
	}

	public static class PackageDistillery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		public PackageDistillery() {
			super("distillery", ExtraTreeTexture.distilleryTexture, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Distillery);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			ComponentTankContainer tanks = new ComponentTankContainer(machine);
			tanks.addTank(Distillery.tankInput, "input", 5000);
			tanks.getTankSlot(Distillery.tankInput).setValidator(new TankValidatorDistilleryInput());
			tanks.getTankSlot(Distillery.tankInput).setOutputSides(MachineSide.TOP_AND_BOTTOM);
			tanks.addTank(Distillery.tankOutput, "output", 5000);
			tanks.getTankSlot(Distillery.tankOutput).setValidator(new TankValidatorDistilleryOutput());
			tanks.getTankSlot(Distillery.tankOutput).setReadOnly();
			tanks.getTankSlot(Distillery.tankOutput).setOutputSides(MachineSide.SIDES);
			new ComponentPowerReceptor(machine);
			new ComponentDistilleryLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentDistilleryLogic extends ComponentProcessSetCost implements
		IProcess,
		INetwork.SendGuiNBT,
		INetwork.RecieveGuiNBT {
		public FluidStack currentFluid;
		public int level;

		protected int guiLevel;

		public ComponentDistilleryLogic(Machine machine) {
			super(machine, 16000, 800);
			currentFluid = null;
			level = 0;
			guiLevel = machine.getUniqueProgressBarID();
		}

		@Override
		public float getEnergyPerTick() {
			return 2.0f;
		}

		@Override
		public int getProcessLength() {
			return 2000 + 800 * level;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			level = nbt.getByte("dlevel");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setByte("dlevel", (byte) level);
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isTankEmpty(Distillery.tankInput) && currentFluid == null) {
				return new ErrorState.InsufficientLiquid("No Input Liquid", Distillery.tankInput);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (currentFluid == null) {
				return new ErrorState("Distillery Empty", "No liquid in Distillery");
			}
			if (!getUtil().isTankEmpty(Distillery.tankOutput) && getOutput() != null && !getOutput().isFluidEqual(getUtil().getFluid(Distillery.tankOutput))) {
				return new ErrorState.Tank("No Room", "No room for liquid", new int[]{Distillery.tankOutput});
			}
			if (getUtil().getFluid(Distillery.tankOutput) != null && !getUtil().getFluid(Distillery.tankOutput).isFluidEqual(Distillery.getOutput(getUtil().getFluid(Distillery.tankInput), level))) {
				return new ErrorState.TankSpace("Different fluid in tank", Distillery.tankOutput);
			}
			return super.canProgress();
		}

		private FluidStack getOutput() {
			return Distillery.getOutput(getUtil().getFluid(Distillery.tankInput), level);
		}

		@Override
		protected void onFinishTask() {
			FluidStack output = Distillery.getOutput(currentFluid, level).copy();
			output.amount = 1000;
			getUtil().fillTank(Distillery.tankOutput, output);
		}

		@Override
		protected void onTickTask() {
			// ignored
		}

		@Override
		public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
			if (name.equals("still-level")) {
				level = nbt.getByte("i");
			}

			if (name.equals("still-recipe")) {
				if (nbt.getBoolean("null")) {
					currentFluid = null;
				} else {
					currentFluid = FluidStack.loadFluidStackFromNBT(nbt);
				}
			}
		}

		@Override
		public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
			NBTTagCompound nbt = new NBTTagCompound();
			if (currentFluid == null) {
				nbt.setBoolean("null", true);
			} else {
				currentFluid.writeToNBT(nbt);
			}

			nbts.put("still-recipe", nbt);
			nbt.setByte("i", (byte) level);
			nbts.put("still-level", nbt);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			if (canWork() == null && currentFluid == null && getUtil().getTank(Distillery.tankInput).getFluidAmount() >= 1000) {
				FluidStack stack = getUtil().drainTank(Distillery.tankInput, 1000);
				currentFluid = stack;
			}
		}

		@Override
		public String getTooltip() {
			if (currentFluid == null) {
				return "Empty";
			}
			// TODO remove deprecated
			return "Creating " + Distillery.getOutput(currentFluid, level).getFluid().getLocalizedName();
		}
	}

	public static class TankValidatorDistilleryInput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Distillery.isValidInputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Distillable Liquids";
		}
	}

	public static class TankValidatorDistilleryOutput extends TankValidator {
		@Override
		public boolean isValid(FluidStack liquid) {
			return Distillery.isValidOutputLiquid(liquid);
		}

		@Override
		public String getTooltip() {
			return "Distilled Liquids";
		}
	}
}
