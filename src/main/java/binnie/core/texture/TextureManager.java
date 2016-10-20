// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.texture;

import java.util.ArrayList;
import javax.swing.Icon;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureManager
{
	static List<Icon> textures;

	public static void init() {
	}

	static {
		TextureManager.textures = new ArrayList<Icon>();
	}
}
