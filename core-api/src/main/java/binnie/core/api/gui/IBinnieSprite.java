package binnie.core.api.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBinnieSprite {
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite();
}
