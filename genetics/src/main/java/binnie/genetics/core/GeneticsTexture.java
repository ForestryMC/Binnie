package binnie.genetics.core;

import binnie.core.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.BinnieSprite;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public enum GeneticsTexture implements IBinnieTexture {
	GUI_PROCESS(ResourceType.GUI, "process"),
	GUI_PROCESS_2(ResourceType.GUI, "process2"),
	GUI_PROCESS_3(ResourceType.GUI, "process3"),
	;

	public static BinnieSprite dnaIcon;

	private final String texture;
	private final ResourceType type;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

	GeneticsTexture(ResourceType base, String texture) {
		this.texture = texture;
		type = base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		if (resource == null) {
			resource = Binnie.RESOURCE.getPNG(Genetics.instance, type, texture);
		}
		return resource;
	}

}
