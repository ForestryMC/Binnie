// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.resource;

import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.Binnie;
import binnie.core.AbstractMod;
import net.minecraft.util.IIcon;

public class BinnieIcon extends BinnieResource
{
	private int textureSheet;
	private IIcon icon;

	public BinnieIcon(final AbstractMod mod, final ResourceType type, final String path) {
		super(mod, type, path);
		this.textureSheet = 0;
		this.icon = null;
		this.textureSheet = ((type != ResourceType.Block) ? 1 : 0);
		Binnie.Resource.registerIcon(this);
	}

	public IIcon getIcon() {
		return this.icon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final IIconRegister register) {
		this.registerIcon(register);
		return this.icon;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcon(final IIconRegister register) {
		this.icon = BinnieCore.proxy.getIcon(register, this.mod, this.path);
	}

	public int getTextureSheet() {
		return this.textureSheet;
	}
}
