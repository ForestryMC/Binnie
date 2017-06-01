package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlowerType;
import binnie.core.resource.BinnieSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class FlowerSpriteManager {
	private static final Map<IFlowerType, FlowerSprites> flowerSprites = new HashMap<>();

	public static void initSprites(IFlowerType type) {
		if (flowerSprites.containsKey(type)) {
			return;
		}
		flowerSprites.put(type, new FlowerSprites(type));
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getStem(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getStem(type, section, flowered);
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getPetal(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getPetal(type, section, flowered);
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite getVariant(IFlowerType type, int section, boolean flowered) {
		FlowerSprites flower = flowerSprites.get(type);
		if (flower == null) {
			return null;
		}
		return flower.getVariant(type, section, flowered);
	}

	private static class FlowerSprites {
		private BinnieSprite[] stem;
		private BinnieSprite[] variant;
		private BinnieSprite[] petal;
		private BinnieSprite[] unflowered;
		private int sections;

		public FlowerSprites(IFlowerType type) {
			this.sections = type.getSections();
			this.stem = new BinnieSprite[sections];
			this.petal = new BinnieSprite[sections];
			this.variant = new BinnieSprite[sections];
			this.unflowered = new BinnieSprite[sections];
			for (int section = 0; section < sections; ++section) {
				final String suf = (section == 0) ? "" : ("" + (section + 1));
				final String pre = (sections == 1) ? "" : "double/";
				this.stem[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".0");
				this.petal[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".1");
				this.variant[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".2");
				this.unflowered[section] = Binnie.RESOURCE.getBlockSprite(Botany.instance, "flowers/" + pre + type.toString().toLowerCase() + suf + ".3");
			}
		}

		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getStem(IFlowerType type, int section, boolean flowered) {
			return stem[section % sections].getSprite();
		}

		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getPetal(IFlowerType type, int section, boolean flowered) {
			return (flowered ? petal[section % this.sections] : this.unflowered[section % this.sections]).getSprite();
		}

		@Nullable
		@SideOnly(Side.CLIENT)
		public TextureAtlasSprite getVariant(IFlowerType type, int section, boolean flowered) {
			return flowered ? variant[section % this.sections].getSprite() : null;
		}
	}
}
