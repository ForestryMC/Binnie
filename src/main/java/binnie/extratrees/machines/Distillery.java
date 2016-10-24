package binnie.extratrees.machines;

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
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Distillery {
    public static int tankInput = 0;
    public static int tankOutput = 1;
    static List<Map<Fluid, FluidStack>> recipes;

    public static FluidStack getOutput(final FluidStack fluid, final int level) {
        if (fluid == null) {
            return null;
        }
        return Distillery.recipes.get(level).get(fluid.getFluid());
    }

    public static boolean isValidInputLiquid(final FluidStack fluid) {
        for (int i = 0; i < 3; ++i) {
            if (Distillery.recipes.get(i).containsKey(fluid.getFluid())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidOutputLiquid(final FluidStack fluid) {
        for (int i = 0; i < 3; ++i) {
            for (final Map.Entry<Fluid, FluidStack> entry : Distillery.recipes.get(i).entrySet()) {
                if (entry.getValue().isFluidEqual(fluid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addRecipe(final FluidStack input, final FluidStack output, final int level) {
        Distillery.recipes.get(level).put(input.getFluid(), output);
    }

    static {
        (Distillery.recipes = new ArrayList<>()).add(new HashMap<>());
        Distillery.recipes.add(new HashMap<>());
        Distillery.recipes.add(new HashMap<>());
    }

    public static class PackageDistillery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackageDistillery() {
            super("distillery", ExtraTreeTexture.distilleryTexture, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Distillery);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            final ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(Distillery.tankInput, "input", 5000);
            tanks.getTankSlot(Distillery.tankInput).setValidator(new TankValidatorDistilleryInput());
            tanks.getTankSlot(Distillery.tankInput).setOutputSides(MachineSide.TopAndBottom);
            tanks.addTank(Distillery.tankOutput, "output", 5000);
            tanks.getTankSlot(Distillery.tankOutput).setValidator(new TankValidatorDistilleryOutput());
            tanks.getTankSlot(Distillery.tankOutput).setReadOnly();
            tanks.getTankSlot(Distillery.tankOutput).setOutputSides(MachineSide.Sides);
            new ComponentPowerReceptor(machine);
            new ComponentDistilleryLogic(machine);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentDistilleryLogic extends ComponentProcessSetCost implements IProcess, INetwork.SendGuiNBT, INetwork.RecieveGuiNBT {
        public FluidStack currentFluid;
        public int level;
        int guiLevel;

        public ComponentDistilleryLogic(final Machine machine) {
            super(machine, 16000, 800);
            this.currentFluid = null;
            this.level = 0;
            this.guiLevel = machine.getUniqueProgressBarID();
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
        }

        @Override
        public NBTTagCompound writeToNBT(final NBTTagCompound nbt2) {
            NBTTagCompound nbt = super.writeToNBT(nbt2);
            nbt.setByte("dlevel", (byte) this.level);
            return nbt;
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().isTankEmpty(Distillery.tankInput) && this.currentFluid == null) {
                return new ErrorState.InsufficientLiquid("No Input Liquid", Distillery.tankInput);
            }
            return super.canWork();
        }

        @Override
        public ErrorState canProgress() {
            if (this.currentFluid == null) {
                return new ErrorState("Distillery Empty", "No liquid in Distillery");
            }
            if (!this.getUtil().isTankEmpty(Distillery.tankOutput) && this.getOutput() != null && !this.getOutput().isFluidEqual(this.getUtil().getFluid(Distillery.tankOutput))) {
                return new ErrorState.Tank("No Room", "No room for liquid", new int[]{Distillery.tankOutput});
            }
            if (this.getUtil().getFluid(Distillery.tankOutput) != null && !this.getUtil().getFluid(Distillery.tankOutput).isFluidEqual(Distillery.getOutput(this.getUtil().getFluid(Distillery.tankInput), this.level))) {
                return new ErrorState.TankSpace("Different fluid in tank", Distillery.tankOutput);
            }
            return super.canProgress();
        }

        private FluidStack getOutput() {
            return Distillery.getOutput(this.getUtil().getFluid(Distillery.tankInput), this.level);
        }

        @Override
        protected void onFinishTask() {
            final FluidStack output = Distillery.getOutput(this.currentFluid, this.level).copy();
            output.amount = 1000;
            this.getUtil().fillTank(Distillery.tankOutput, output);
        }

        @Override
        protected void onTickTask() {
        }

        @Override
        public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
            if (name.equals("still-level")) {
                this.level = nbt.getByte("i");
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
        public void sendGuiNBT(final Map<String, NBTTagCompound> data) {
            final NBTTagCompound nbt = new NBTTagCompound();
            if (this.currentFluid == null) {
                nbt.setBoolean("null", true);
            } else {
                this.currentFluid.writeToNBT(nbt);
            }
            data.put("still-recipe", nbt);
            final NBTTagCompound nbt2 = new NBTTagCompound();
            nbt.setByte("i", (byte) this.level);
            data.put("still-level", nbt);
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            if (this.canWork() == null && this.currentFluid == null && this.getUtil().getTank(Distillery.tankInput).getFluidAmount() >= 1000) {
                final FluidStack stack = this.getUtil().drainTank(Distillery.tankInput, 1000);
                this.currentFluid = stack;
            }
        }

        @Override
        public String getTooltip() {
            if (this.currentFluid == null) {
                return "Empty";
            }
            return "Creating " + Distillery.getOutput(this.currentFluid, this.level).getFluid().getLocalizedName(this.currentFluid);
        }
    }

    public static class TankValidatorDistilleryInput extends TankValidator {
        @Override
        public boolean isValid(final FluidStack itemStack) {
            return Distillery.isValidInputLiquid(itemStack);
        }

        @Override
        public String getTooltip() {
            return "Distillable Liquids";
        }
    }

    public static class TankValidatorDistilleryOutput extends TankValidator {
        @Override
        public boolean isValid(final FluidStack itemStack) {
            return Distillery.isValidOutputLiquid(itemStack);
        }

        @Override
        public String getTooltip() {
            return "Distilled Liquids";
        }
    }
}
