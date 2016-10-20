// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.gui;

import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.network.IOrdinaled;

public interface IBinnieGUID extends IOrdinaled
{
	Window getWindow(final EntityPlayer p0, final World p1, final int p2, final int p3, final int p4, final Side p5);
}
