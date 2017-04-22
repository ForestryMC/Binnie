package binnie.core.resource;

import binnie.*;
import binnie.core.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class BinnieIcon extends BinnieResource {
	private int textureSheet;
	private IIcon icon;

	public BinnieIcon(AbstractMod mod, ResourceType type, String path) {
		super(mod, type, path);
		icon = null;
		textureSheet = ((type != ResourceType.Block) ? 1 : 0);
		Binnie.Resource.registerIcon(this);
	}

	public IIcon getIcon() {
		return this.icon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IIconRegister register) {
		registerIcon(register);
		return icon;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister register) {
		icon = BinnieCore.proxy.getIcon(register, mod, path);
	}

	public int getTextureSheet() {
		return textureSheet;
	}
}
