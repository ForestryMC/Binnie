package binnie.extrabees.machines.mutator.window;

import javax.annotation.Nullable;
import java.text.NumberFormat;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.client.MinecraftForgeClient;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.Constants;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.extrabees.utils.AlvearyMutationHandler;

public class WindowAlvearyMutator extends Window {
	public WindowAlvearyMutator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(176, 176, player, inventory, side);
	}

	@Nullable
	public static Window create(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		if (inventory == null) {
			return null;
		}
		return new WindowAlvearyMutator(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
		new ControlPlayerInventory(this);
		new ControlSlot.Builder(this, 79, 30).assign(0);
		new ControlText(this, new Area(0, 52, getWidth(), 16), "Possible Mutagens:", TextJustification.MIDDLE_CENTER).setColor(5592405);
		final int size = AlvearyMutationHandler.getMutagens().size();
		final int w = size * 18;
		NumberFormat numberFormat = NumberFormat.getNumberInstance(MinecraftForgeClient.getLocale());
		if (size > 0) {
			int x = (getWidth() - w) / 2;
			for (final Pair<ItemStack, Float> mutagen : AlvearyMutationHandler.getMutagens()) {
				final ControlItemDisplay display = new ControlItemDisplay(this, x, 66);
				display.setItemStack(mutagen.getKey());
				display.setTooltip();
				Float multiplier = mutagen.getValue();

				String format = numberFormat.format(multiplier);
				display.addTooltip(TextFormatting.GRAY + "Multiplier: " + format + "x");
				x += 18;
			}
		}
	}

	@Override
	protected String getModId() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyMutator";
	}
}
