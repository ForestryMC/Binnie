package binnie.genetics.machine;

import binnie.core.machines.MachineUtil;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.api.IIncubatorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class IncubatorRecipe implements IIncubatorRecipe {
	private final FluidStack input;
	private final FluidStack output;
	private final ItemStack itemStack;
	private final float lossChance;
	private final float tickChance;
	private ItemStack outputStack;

	public IncubatorRecipe(ItemStack itemStack, final FluidStack input, final FluidStack output, final float lossChance) {
		this(itemStack, input, output, lossChance, 1.0f);
	}

	public IncubatorRecipe(ItemStack itemStack, final FluidStack input, final FluidStack output, final float lossChance, final float chance) {
		this.itemStack = itemStack;
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
		return fluid != null && fluid.amount >= 500;
	}

	@Override
	public FluidStack getInput() {
		return input;
	}

	@Override
	public FluidStack getOutput() {
		return output;
	}

	@Override
	public ItemStack getInputStack() {
		return itemStack;
	}

	@Override
	public ItemStack getExpectedOutput() {
		return outputStack;
	}

	@Override
	public void doTask(final MachineUtil machine) {
		machine.drainTank(0, this.input.amount);
		if (this.output != null) {
			machine.fillTank(1, this.output);
		}
		this.outputStack = this.getOutputStack(machine);
		if (this.outputStack != null) {
			final ItemStack output = this.outputStack.copy();
			final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
			product.transfer(true);
		}
		final Random rand = machine.getRandom();
		if (rand.nextFloat() < this.lossChance) {
			machine.decreaseStack(3, 1);
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
			if (!machine.getFluid(1).isFluidEqual(this.output)) {
				return false;
			}
			if (!machine.spaceInTank(1, this.output.amount)) {
				return false;
			}
		}
		final ItemStack outputStack = this.getOutputStack(machine);
		if (outputStack != null) {
			final ItemStack output = outputStack.copy();
			final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.slotOutput).ignoreValidation();
			final ItemStack leftover = product.transfer(false);
			return leftover == null;
		}
		return true;
	}
}
