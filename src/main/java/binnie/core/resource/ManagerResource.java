package binnie.core.resource;

import binnie.core.AbstractMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

import java.util.ArrayList;
import java.util.List;

public class ManagerResource {
	private List<BinnieIcon> icons;

	public ManagerResource() {
		this.icons = new ArrayList<>();
	}

	public BinnieResource getPNG(final AbstractMod mod, final ResourceType type, final String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(final AbstractMod mod, final ResourceType type, final String path) {
		return new BinnieResource(mod, type, path);
	}

	public void registerIcon(final BinnieIcon binnieIcon) {
		this.icons.add(binnieIcon);
	}

	public BinnieIcon getItemIcon(final AbstractMod mod, final String iconFile) {
		return new BinnieIcon(mod, ResourceType.Item, iconFile);
	}

	public BinnieIcon getBlockIcon(final AbstractMod mod, final String iconFile) {
		return new BinnieIcon(mod, ResourceType.Block, iconFile);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register, final int type) {
		for (final BinnieIcon icon : this.icons) {
			if (icon.getTextureSheet() == type) {
				icon.registerIcon(register);
			}
		}
	}
}
