package binnie.core.machines.transfer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidActionResult;

import java.util.Collections;

/**
 * Modeled after {@link FluidActionResult} but for item transfers.
 */
public class TransferResult {
	public static final TransferResult FAILURE = new TransferResult(false, ItemStack.EMPTY);

	private final NonNullList<ItemStack> remaining;
	private final boolean success;

	public TransferResult(ItemStack... results) {
		this(true, results);
	}

	private TransferResult(boolean success, ItemStack... remaining) {
		this.success = success;
		this.remaining = NonNullList.create();
		Collections.addAll(this.remaining, remaining);
	}

	public boolean isSuccess() {
		return success;
	}

	public NonNullList<ItemStack> getRemaining() {
		return remaining;
	}
}
