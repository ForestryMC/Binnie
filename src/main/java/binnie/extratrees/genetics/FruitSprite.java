package binnie.extratrees.genetics;

import binnie.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public enum FruitSprite {
	TINY,
	SMALL,
	AVERAGE,
	LARGE,
	LARGER,
	PEAR;

	public static final FruitSprite[] VALUES = values();
	private final ResourceLocation location;

	FruitSprite() {
		location = new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/fruit/" + name().toLowerCase(Locale.ENGLISH));
	}

	public ResourceLocation getLocation() {
		return location;
	}

	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		map.registerSprite(location);
	}
}
