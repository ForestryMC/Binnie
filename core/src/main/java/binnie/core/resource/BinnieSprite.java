package binnie.core.resource;

import binnie.core.AbstractMod;
import binnie.core.Binnie;
import binnie.core.api.gui.IBinnieSprite;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BinnieSprite extends BinnieResource implements IBinnieSprite {

	@Nullable
	private TextureAtlasSprite sprite;

	public BinnieSprite(final AbstractMod mod, final ResourceType type, final String path) {
		this(mod.getModId(), type, path);
	}

	public BinnieSprite(final String modid, final ResourceType type, final String path) {
		super(modid, type, path);
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
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		sprite = textureMap.registerSprite(getResourceLocation());
	}
}
