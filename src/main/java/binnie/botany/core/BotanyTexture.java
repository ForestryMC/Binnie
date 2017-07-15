package binnie.botany.core;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

// TODO unused enum?
public enum BotanyTexture implements IBinnieTexture {
	;
	String texture;
	ResourceType type;
	
	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;
	
	BotanyTexture(ResourceType base, String texture) {
		this.texture = texture;
		type = base;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(Botany.instance, type, texture);
		}
		return resource;
	}
}
