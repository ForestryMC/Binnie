package binnie.genetics.machine.incubator;

import javax.annotation.Nullable;
import java.util.Random;

import binnie.core.machines.transfer.TransferResult;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.machines.MachineUtil;
import binnie.core.machines.transfer.TransferRequest;

public class IncubatorRecipe implements IIncubatorRecipe {
	private final FluidStack input;
	@Nullable
	private final FluidStack output;
	private final ItemStack itemStack;
	private final float lossChance;
	private final float tickChance;
	private ItemStack outputStack;

	public IncubatorRecipe(ItemStack itemStack, final FluidStack input, @Nullable final FluidStack output, final float lossChance) {
		this(itemStack, input, output, lossChance, 1.0f);
	}

	public IncubatorRecipe(ItemStack itemStack, final FluidStack input, @Nullable final FluidStack output, final float lossChance, final float chance) {
		this.itemStack = itemStack;
		this.outputStack = ItemStack.EMPTY;
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
	public boolean isInputLiquid(@Nullable final FluidStack fluid) {
		return fluid != null && this.input.isFluidEqual(fluid);
	}

	@Override
	public boolean isInputLiquidSufficient(@Nullable final FluidStack fluid) {
		return fluid != null && fluid.amount >= this.input.amount;
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
		return itemStack;
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
		if (!this.outputStack.isEmpty()) {
			final ItemStack output = this.outputStack.copy();
			final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.SLOT_OUTPUT).ignoreValidation();
			product.transfer(null, true);
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
		final ItemStack outputStack = this.getOutputStack(machine);
		if (!outputStack.isEmpty()) {
			final ItemStack output = outputStack.copy();
			final TransferRequest product = new TransferRequest(output, machine.getInventory()).setTargetSlots(Incubator.SLOT_OUTPUT).ignoreValidation();
			TransferResult transferResult = product.transfer(null, false);
			return transferResult.isSuccess() && (transferResult.getRemaining().isEmpty() || transferResult.getRemaining().get(0).getItem() == Items.AIR);
		}
		return true;
	}
}
