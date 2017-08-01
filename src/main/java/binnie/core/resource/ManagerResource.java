package binnie.core.resource;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;

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

	public BinnieResource getPNG(final String mod, final ResourceType type, final String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(final String mod, final ResourceType type, final String path) {
		return new BinnieResource(mod, type, path);
	}

	public void registerSprite(final BinnieSprite binnieIcon) {
		this.sprites.add(binnieIcon);
	}

	public BinnieSprite getItemSprite(final AbstractMod mod, final String iconFile) {
		return new BinnieSprite(mod, ResourceType.ITEM, iconFile);
	}

	public BinnieSprite getItemSprite(final String mod, final String iconFile) {
		return new BinnieSprite(mod, ResourceType.ITEM, iconFile);
	}

	public BinnieSprite getBlockSprite(final AbstractMod mod, final String iconFile) {
		return new BinnieSprite(mod, ResourceType.Block, iconFile);
	}

	//Todo: move asset to binnie core assets
	public BinnieSprite getUndiscoveredBeeSprite() {
		return getItemSprite("extrabees", "icon/undiscovered_bee");
	}

	//Todo: move asset to binnie core assets
	public BinnieSprite getDiscoveredBeeSprite() {
		return getItemSprite("extrabees", "icon/discovered_bee");
	}

	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		for (BinnieSprite sprite : this.sprites) {
			sprite.registerSprites();
		}
		BinnieCore.getBinnieProxy().reloadSprites();
	}
}
