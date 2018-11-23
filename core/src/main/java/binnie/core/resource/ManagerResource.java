package binnie.core.resource;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;

public class ManagerResource {
	private final List<BinnieSprite> sprites;

	public ManagerResource() {
		this.sprites = new ArrayList<>();
	}

	public BinnieResource getPNG(AbstractMod mod, ResourceType type, String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(AbstractMod mod, ResourceType type, String path) {
		return new BinnieResource(mod, type, path);
	}

	public BinnieResource getPNG(String mod, ResourceType type, String path) {
		return this.getFile(mod, type, path + ".png");
	}

	public BinnieResource getFile(String mod, ResourceType type, String path) {
		return new BinnieResource(mod, type, path);
	}

	public void registerSprite(BinnieSprite binnieIcon) {
		this.sprites.add(binnieIcon);
	}

	public BinnieSprite getItemSprite(AbstractMod mod, String iconFile) {
		return new BinnieSprite(mod, ResourceType.ITEM, iconFile);
	}

	public BinnieSprite getItemSprite(String modId, String iconFile) {
		return new BinnieSprite(modId, ResourceType.ITEM, iconFile);
	}

	public BinnieSprite getBlockSprite(AbstractMod mod, String iconFile) {
		return new BinnieSprite(mod, ResourceType.Block, iconFile);
	}

	//Todo: move asset to binnie core assets
	public BinnieSprite getUndiscoveredBeeSprite() {
		return new BinnieSprite("extrabees", ResourceType.ITEM, "icon/undiscovered_bee");
	}

	//Todo: move asset to binnie core assets
	public BinnieSprite getDiscoveredBeeSprite() {
		return new BinnieSprite("extrabees", ResourceType.ITEM, "icon/discovered_bee");
	}

	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		for (BinnieSprite sprite : this.sprites) {
			sprite.registerSprites();
		}
		BinnieCore.getBinnieProxy().reloadSprites();
	}
}
