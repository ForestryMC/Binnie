package binnie.extratrees.machines;

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
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class Infuser {
    public static int tankInput;
    public static int tankOutput;
    static Map<Fluid, FluidStack> recipes;

    public static FluidStack getOutput(final FluidStack fluid, final ItemStack stack) {
        if (fluid == null) {
            return null;
        }
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

    static {
        Infuser.tankInput = 0;
        Infuser.tankOutput = 1;
        Infuser.recipes = new HashMap<Fluid, FluidStack>();
    }

    public static class PackageInfuser extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackageInfuser() {
            super("infuser", ExtraTreeTexture.infuserTexture, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Infuser);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            final ComponentTankContainer tanks = new ComponentTankContainer(machine);
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
            if (!this.getUtil().isTankEmpty(Infuser.tankOutput) && this.getOutput() != null && !this.getOutput().isFluidEqual(this.getUtil().getFluid(Infuser.tankOutput))) {
                return new ErrorState.Tank("No Room", "No room for liquid", new int[]{Infuser.tankOutput});
            }
            if (this.getUtil().getFluid(Infuser.tankOutput) != null && !this.getUtil().getFluid(Infuser.tankOutput).isFluidEqual(this.getOutput())) {
                return new ErrorState.TankSpace("Different fluid in tank", Infuser.tankOutput);
            }
            return super.canProgress();
        }

        private FluidStack getOutput() {
            return Infuser.getOutput(this.getUtil().getFluid(Infuser.tankInput), this.infusing);
        }

        @Override
        protected void onFinishTask() {
            final FluidStack output = this.getOutput().copy();
            this.getUtil().fillTank(Infuser.tankOutput, output);
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
