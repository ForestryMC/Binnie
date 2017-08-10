package binnie.core.resource;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBinnieTexture {
	@SideOnly(Side.CLIENT)
	BinnieResource getTexture();
}
