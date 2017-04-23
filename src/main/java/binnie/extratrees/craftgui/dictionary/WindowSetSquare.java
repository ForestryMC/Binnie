// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.dictionary;

import net.minecraft.world.World;
import binnie.extratrees.ExtraTrees;
import binnie.core.AbstractMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.Window;

public class WindowSetSquare extends Window
{
	public WindowSetSquare(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(150.0f, 150.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getName() {
		return null;
	}

	@Override
	public void initialiseClient() {
	}

	public static Window create(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
		return new WindowSetSquare(player, null, side);
	}
}
