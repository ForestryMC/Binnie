package binnie.genetics.machine.incubator;

import javax.annotation.Nullable;
import java.util.Random;

import binnie.core.machines.transfer.TransferResult;
import net.minecraft.item.ItemStack;

import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import binnie.core.machines.Machine;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.machine.GeneticsErrorCode;

public class IncubatorLogic extends ComponentProcessIndefinate implements IProcess {
	@Nullable
	private IIncubatorRecipe recipe;
	private final Random rand;
	private boolean roomForOutput;

	public IncubatorLogic(final Machine machine) {
		super(machine, 2.0f);
		this.recipe = null;
		this.rand = new Random();
		this.roomForOutput = true;
	}

	private static boolean isStackValid(ItemStack stack, IIncubatorRecipe recipe) {
		return OreDictionary.itemMatches(recipe.getInputStack(),stack,false);
	}

	@Override
	public ErrorState canWork() {
		if (this.recipe == null) {
			return new ErrorState(CoreErrorCode.NO_RECIPE);
		}
		return super.canWork();
	}

	@Override
	public ErrorState canProgress() {
		if (this.recipe != null) {
			if (!this.recipe.isInputLiquidSufficient(this.getUtil().getFluid(Incubator.TANK_INPUT))) {
				return new ErrorState(GeneticsErrorCode.INCUBATOR_INSUFFICIENT_LIQUID, Incubator.TANK_INPUT);
			}
			if (!this.roomForOutput) {
				return new ErrorState(CoreErrorCode.NO_SPACE_TANK, Incubator.TANK_OUTPUT);
			}
		}
		return super.canProgress();
	}

	@Override
	protected void onTickTask() {
		if (this.rand.nextInt(20) == 0 && this.recipe != null && this.rand.nextFloat() < this.recipe.getChance()) {
			this.recipe.doTask(this.getUtil());
		}
	}

	@Override
	public boolean inProgress() {
		return this.recipe != null;
	}

	@Nullable
	private IIncubatorRecipe getRecipe(final ItemStack stack, final FluidStack liquid) {
		for (final IIncubatorRecipe recipe : Incubator.getRecipes()) {
			final boolean rightLiquid = recipe.isInputLiquid(liquid);
			final boolean rightItem = isStackValid(stack, recipe);
			if (rightLiquid && rightItem) {
				return recipe;
			}
		}
		return null;
	}

	@Override
	public void onInventoryUpdate() {
		super.onInventoryUpdate();
		if (!this.getUtil().isServer()) {
			return;
		}
		final FluidStack liquid = this.getUtil().getFluid(Incubator.TANK_INPUT);
		final ItemStack incubator = this.getUtil().getStack(Incubator.SLOT_INCUBATOR);
		checkAvailability(liquid, incubator);
		if (this.recipe == null) {
			if (liquid == null) {
				return;
			}
			if (!incubator.isEmpty()) {
				final IIncubatorRecipe recipe = this.getRecipe(incubator, liquid);
				if (recipe != null) {
					this.recipe = recipe;
					return;
				}
			}
			IIncubatorRecipe potential = null;
			int potentialSlot = 0;
			for (final int slot : Incubator.SLOT_QUEUE) {
				final ItemStack stack = this.getUtil().getStack(slot);
				if (stack.isEmpty()) continue;
				for (final IIncubatorRecipe recipe2 : Incubator.getRecipes()) {
					final boolean rightLiquid = recipe2.isInputLiquid(liquid);
					final boolean rightItem = isStackValid(stack, recipe2);
					if (rightLiquid && rightItem) {
						potential = recipe2;
						potentialSlot = slot;
						break;
					}
				}
				if (potential != null) {
					break;
				}
			}
			if (potential != null) {
				final TransferRequest removal = new TransferRequest(incubator, this.getInventory()).setTargetSlots(Incubator.SLOT_OUTPUT).ignoreValidation();
				TransferResult transferResult = removal.transfer(null, false);
				if (transferResult.isSuccess() && transferResult.getRemaining().isEmpty()) {
					this.recipe = potential;
				}
				removal.transfer(null, true);
				final ItemStack stack2 = this.getUtil().getStack(potentialSlot);
				this.getUtil().setStack(potentialSlot, ItemStack.EMPTY);
				this.getUtil().setStack(Incubator.SLOT_INCUBATOR, stack2);
			}
		}
		if (this.recipe != null) {
			this.roomForOutput = this.recipe.roomForOutput(this.getUtil());
		}
	}

	private void checkAvailability(@Nullable FluidStack liquid, ItemStack incubator) {
		if (this.recipe != null && (incubator.isEmpty() || liquid == null || !this.recipe.isInputLiquid(liquid) || !isStackValid(incubator, recipe))) {
			this.recipe = null;
			TransferRequest transferRequest = new TransferRequest(incubator, this.getInventory()).setTargetSlots(Incubator.SLOT_OUTPUT).ignoreValidation();
			TransferResult transferResult = transferRequest.transfer(null, true);
			if (transferResult.isSuccess()) {
				NonNullList<ItemStack> results = transferResult.getRemaining();
				if (results.size() == 1) {
					final ItemStack leftover = results.get(0);
					this.getUtil().setStack(Incubator.SLOT_INCUBATOR, leftover);
				}
			}
		}
	}
}
