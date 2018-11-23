package binnie.extratrees.gui;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.gui.minecraft.Window;
import binnie.extratrees.ExtraTrees;

public class WindowSetSquare extends Window {
	public WindowSetSquare(EntityPlayer player, @Nullable IInventory inventory, Side side) {
		super(150, 150, player, inventory, side);
	}

	public static Window create(EntityPlayer player, World world, int x, int y, int z, Side side) {
		return new WindowSetSquare(player, null, side);
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		// TODO background should not be null
		return null;
	}

	@Override
	public void initialiseClient() {
	}
}
