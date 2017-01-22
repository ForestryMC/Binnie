package binnie.extratrees.genetics;

import java.util.Locale;

import binnie.Constants;
import forestry.core.proxy.Proxies;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum FruitSprite{
	TINY,
	SMALL,
	AVERAGE,
	LARGE,
	LARGER,
	PEAR;

	public static final FruitSprite[] VALUES = values();
	private final ResourceLocation location;

	private FruitSprite() {
		location = new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "blocks/fruit/" + name().toLowerCase(Locale.ENGLISH));
	}
	
	public ResourceLocation getLocation() {
		return location;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		TextureMap map = Proxies.common.getClientInstance().getTextureMapBlocks();
		map.registerSprite(location);
	}
}
