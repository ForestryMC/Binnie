package binnie.core.resource;


import binnie.Binnie;
import binnie.core.AbstractMod;
import com.google.common.base.Preconditions;
import forestry.core.render.TextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BinnieSprite extends BinnieResource {
	@Nullable
	private TextureAtlasSprite sprite;

	public BinnieSprite(final AbstractMod mod, final ResourceType type, final String path) {
		super(mod, type, path);
		this.sprite = null;
		Binnie.RESOURCE.registerSprite(this);
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite() {
		Preconditions.checkState(this.sprite != null, "Tried to get sprite before it is registered.");
		return this.sprite;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		sprite = TextureManager.registerSprite(getResourceLocation());
	}
}
