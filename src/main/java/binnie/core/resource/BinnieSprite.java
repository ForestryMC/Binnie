package binnie.core.resource;


import binnie.Binnie;
import binnie.core.AbstractMod;
import forestry.core.render.TextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BinnieSprite extends BinnieResource {
	private TextureAtlasSprite sprite;

	public BinnieSprite(final AbstractMod mod, final ResourceType type, final String path) {
		super(mod, type, path);
		this.sprite = null;
		Binnie.RESOURCE.registerSprite(this);
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite() {
		return this.sprite;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		sprite = TextureManager.registerSprite(getResourceLocation());
	}
}
