package binnie.core.gui.resource.textures;

import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IBorder;
import binnie.core.api.gui.ITexture;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Texture implements ITexture {
	private final Area area;
	private final Border padding;
	private final Border border;
	private final IBinnieTexture binnieTexture;

	public Texture(final Area area, final Border padding, final Border border, final IBinnieTexture binnieTexture) {
		this.area = new Area(area);
		this.padding = new Border(padding);
		this.border = new Border(border);
		this.binnieTexture = binnieTexture;
	}

	@Override
	public Area getArea() {
		return this.area;
	}

	@Override
	public IBorder getBorder() {
		return this.border;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getResourceLocation() {
		return this.binnieTexture.getTexture().getResourceLocation();
	}

	@Override
	public IBorder getTotalPadding() {
		return this.padding.add(this.border);
	}

	@Override
	public int width() {
		return this.getArea().width();
	}

	@Override
	public int height() {
		return this.getArea().height();
	}

	@Override
	public ITexture crop(final Alignment anchor, final int dist) {
		return this.crop(new Border(anchor.opposite(), dist));
	}

	@Override
	public ITexture crop(final IBorder crop) {
		final Texture copy = new Texture(this.area, this.padding, this.border, this.binnieTexture);
		if (crop.getBottom() > 0) {
			copy.border.setBottom(0);
			copy.padding.setBottom(copy.padding.getBottom() - Math.min(crop.getBottom(), copy.padding.getBottom()));
			copy.area.setHeight(copy.area.height() - crop.getBottom());
		}
		if (crop.getTop() > 0) {
			copy.border.setTop(0);
			copy.padding.setTop(copy.padding.getTop() - Math.min(crop.getTop(), copy.padding.getTop()));
			copy.area.setHeight(copy.area.height() - crop.getTop());
			copy.area.setYPos(copy.area.yPos() + crop.getTop());
		}
		if (crop.getRight() > 0) {
			copy.border.setRight(0);
			copy.padding.setRight(copy.padding.getRight() - Math.min(crop.getRight(), copy.padding.getRight()));
			copy.area.setWidth(copy.area.width() - crop.getRight());
		}
		if (crop.getLeft() > 0) {
			copy.border.setLeft(0);
			copy.padding.setLeft(copy.padding.getLeft() - Math.min(crop.getLeft(), copy.padding.getLeft()));
			copy.area.setWidth(copy.area.width() - crop.getLeft());
			copy.area.setXPos(copy.area.xPos() + crop.getLeft());
		}
		return copy;
	}

	@Override
	public String toString() {
		String out = "Texture[";
		out += this.area.toString();
		if (!this.padding.isNonZero()) {
			out = out + " padding:" + this.padding.toString();
		}
		if (!this.border.isNonZero()) {
			out = out + " border:" + this.border.toString();
		}
		return out + ']';
	}
}
