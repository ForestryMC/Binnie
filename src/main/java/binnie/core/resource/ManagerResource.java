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

	public BinnieResource getPNG(AbstractMod mod, ResourceType type, String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(AbstractMod mod, ResourceType type, String path) {
		return new BinnieResource(mod, type, path);
	}

	public void registerIcon(BinnieIcon binnieIcon) {
		this.icons.add(binnieIcon);
	}

	public BinnieIcon getItemIcon(AbstractMod mod, String iconFile) {
		return new BinnieIcon(mod, ResourceType.Item, iconFile);
	}

	public BinnieIcon getBlockIcon(AbstractMod mod, String iconFile) {
		return new BinnieIcon(mod, ResourceType.Block, iconFile);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register, int type) {
		for (BinnieIcon icon : this.icons) {
			if (icon.getTextureSheet() == type) {
				icon.registerIcon(register);
			}
		}
	}
}
