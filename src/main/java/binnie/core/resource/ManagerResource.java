package binnie.core.resource;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ManagerResource {
	private List<BinnieSprite> sprites;

	public ManagerResource() {
		this.sprites = new ArrayList<>();
	}

	public BinnieResource getPNG(final AbstractMod mod, final ResourceType type, final String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(final AbstractMod mod, final ResourceType type, final String path) {
		return new BinnieResource(mod, type, path);
	}

	public void registerSprite(final BinnieSprite binnieIcon) {
		this.sprites.add(binnieIcon);
	}

	public BinnieSprite getItemSprite(final AbstractMod mod, final String iconFile) {
		return new BinnieSprite(mod, ResourceType.Item, iconFile);
	}

	public BinnieSprite getBlockSprite(final AbstractMod mod, final String iconFile) {
		return new BinnieSprite(mod, ResourceType.Block, iconFile);
	}

	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		for (BinnieSprite sprite : this.sprites) {
			sprite.registerSprites();
		}
		BinnieCore.proxy.reloadSprites();
	}


}
