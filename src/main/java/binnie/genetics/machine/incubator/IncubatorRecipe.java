package binnie.genetics.machine.incubator;

import binnie.core.machines.MachineUtil;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.api.IIncubatorRecipe;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class IncubatorRecipe implements IIncubatorRecipe {

    private final FluidStack input;

    @Nullable
    private final FluidStack output;

    private final float lossChance;
    private final float tickChance;
    private final ItemStack inputStack;

    @Nullable
    private ItemStack outputStack;

    public IncubatorRecipe(
            ItemStack inputStack, final FluidStack input, @Nullable final FluidStack output, final float lossChance) {
        this(inputStack, input, output, lossChance, 1.0f);
    }

    public IncubatorRecipe(
            ItemStack inputStack,
            final FluidStack input,
            @Nullable final FluidStack output,
            final float lossChance,
            final float chance) {
        this.inputStack = inputStack;
        this.outputStack = null;
        this.input = input;
        this.output = output;
        this.lossChance = lossChance;
        this.tickChance = chance;
    }

    @Override
    public float getChance() {
        return this.tickChance;
    }

    @Override
    public float getLossChance() {
        return lossChance;
    }

    @Override
    public boolean isInputLiquid(final FluidStack fluid) {
        return fluid != null && this.input.isFluidEqual(fluid);
    }

    @Override
    public boolean isInputLiquidSufficient(final FluidStack fluid) {
        return fluid != null && fluid.amount >= this.input.amount;
    }

    @Override
    public boolean isItemStack(ItemStack stack) {
        return false;
    }

    @Override
    public FluidStack getInput() {
        return input;
    }

    @Override
    @Nullable
    public FluidStack getOutput() {
        return output;
    }

    @Override
    public ItemStack getInputStack() {
        return inputStack;
    }

    @Override
    public ItemStack getExpectedOutput() {
        return outputStack;
    }

    @Override
    public void doTask(final MachineUtil machine) {
        machine.drainTank(Incubator.TANK_INPUT, this.input.amount);
        if (this.output != null) {
            machine.fillTank(Incubator.TANK_OUTPUT, this.output);
        }
        this.outputStack = this.getOutputStack(machine);
        if (this.outputStack != null) {
            final ItemStack output = this.outputStack.copy();
            final TransferRequest product = new TransferRequest(output, machine.getInventory())
                    .setTargetSlots(Incubator.SLOT_OUTPUT)
                    .ignoreValidation();
            product.transfer(true);
        }
        final Random rand = machine.getRandom();
        if (rand.nextFloat() < this.lossChance) {
            machine.decreaseStack(Incubator.SLOT_INCUBATOR, 1);
        }
    }

    public IncubatorRecipe setOutputStack(final ItemStack stack) {
        this.outputStack = stack;
        return this;
    }

    protected ItemStack getOutputStack(final MachineUtil util) {
        return getExpectedOutput();
    }

    @Override
    public boolean roomForOutput(final MachineUtil machine) {
        if (this.output != null && !machine.isTankEmpty(1)) {
            if (!this.output.isFluidEqual(machine.getFluid(Incubator.TANK_OUTPUT))) {
                return false;
            }
            if (!machine.spaceInTank(Incubator.TANK_OUTPUT, this.output.amount)) {
                return false;
            }
        }

        ItemStack outputStack = getOutputStack(machine);
        if (outputStack == null) {
            return true;
        }

        ItemStack output = outputStack.copy();
        TransferRequest product = new TransferRequest(output, machine.getInventory())
                .setTargetSlots(Incubator.SLOT_OUTPUT)
                .ignoreValidation();
        return product.transfer(false) == null;
    }
}
