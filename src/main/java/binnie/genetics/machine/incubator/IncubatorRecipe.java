package binnie.genetics.machine.incubator;

import binnie.core.machines.MachineUtil;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.api.IIncubatorRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public abstract class IncubatorRecipe implements IIncubatorRecipe {
	protected FluidStack input;
	protected FluidStack output;
	protected float lossChance;
	protected ItemStack outputStack;
	protected float tickChance;

	public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance) {
		this(input, output, lossChance, 1.0f);
	}

	public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance, float chance) {
		this.input = input;
		this.output = output;
		this.lossChance = lossChance;
		tickChance = chance;
	}

	@Override
	public float getChance() {
		return tickChance;
	}

	@Override
	public boolean isInputLiquid(FluidStack liquid) {
		return liquid != null && input.isFluidEqual(liquid);
	}

	@Override
	public boolean isInputLiquidSufficient(FluidStack liquid) {
		return liquid != null && liquid.amount >= 500;
	}

	@Override
	public void doTask(MachineUtil machine) {
		machine.drainTank(Incubator.TANK_INPUT, input.amount);
		if (output != null) {
			machine.fillTank(Incubator.TANK_OUTPUT, output);
		}

		outputStack = getOutputStack(machine);
		if (outputStack != null) {
			ItemStack output = outputStack.copy();
			TransferRequest product = new TransferRequest(output, machine.getInventory())
				.setTargetSlots(Incubator.SLOT_OUTPUT)
				.ignoreValidation();
			product.transfer(true);
		}

		Random rand = machine.getRandom();
		if (rand.nextFloat() < lossChance) {
			machine.decreaseStack(Incubator.SLOT_INCUBATOR, 1);
		}
	}

	public IncubatorRecipe setOutputStack(ItemStack stack) {
		outputStack = stack;
		return this;
	}

	protected ItemStack getOutputStack(MachineUtil util) {
		return outputStack;
	}

	@Override
	public boolean roomForOutput(MachineUtil machine) {
		if (output != null && !machine.isTankEmpty(Incubator.TANK_OUTPUT)) {
			if (!machine.getFluid(Incubator.TANK_OUTPUT).isFluidEqual(output)) {
				return false;
			}
			if (!machine.spaceInTank(Incubator.TANK_OUTPUT, output.amount)) {
				return false;
			}
		}

		ItemStack outputStack = getOutputStack(machine);
		if (outputStack != null) {
			ItemStack output = outputStack.copy();
			TransferRequest product = new TransferRequest(output, machine.getInventory())
				.setTargetSlots(Incubator.SLOT_OUTPUT)
				.ignoreValidation();
			ItemStack leftover = product.transfer(false);
			return leftover == null;
		}
		return true;
	}
}
