package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
		this.setTitle("Mutator");
		new ControlPlayerInventory(this);
		new ControlSlot.Builder(this, 79, 30).assign(0);
		new ControlText(this, new IArea(0, 52, this.w(), 16), "Possible Mutagens:", TextJustification.MiddleCenter).setColour(5592405);
		final int size = AlvearyMutator.getMutagens().size();
		final int w = size * 18;
		NumberFormat numberFormat = NumberFormat.getNumberInstance(MinecraftForgeClient.getLocale());
		if (size > 0) {
			int x = (this.w() - w) / 2;
			for (final Pair<ItemStack, Float> mutagen : AlvearyMutator.getMutagens()) {
				final ControlItemDisplay display = new ControlItemDisplay(this, x, 66);
				display.setItemStack(mutagen.getKey());
				display.hastooltip = true;
				Float multiplier = mutagen.getValue();

				String format = numberFormat.format(multiplier);
				display.addTooltip(TextFormatting.GRAY + "Multiplier: " + format + "x");
				x += 18;
			}
		}
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "AlvearyMutator";
	}
}
