package binnie.core.api.gui;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITexture {
	IArea getArea();

	IBorder getBorder();

	@SideOnly(Side.CLIENT)
	ResourceLocation getResourceLocation();

	IBorder getTotalPadding();

	int width();

	int height();

	ITexture crop(Alignment anchor, int dist);

	ITexture crop(IBorder crop);
}
