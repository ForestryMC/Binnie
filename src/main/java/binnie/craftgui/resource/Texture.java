package binnie.craftgui.resource;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.Position;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Texture {
	private final IArea area;
	private final IBorder padding;
	private final IBorder border;
	private final IBinnieTexture binnieTexture;

	public Texture(final IArea area, final IBinnieTexture binnieTexture) {
		this(area, IBorder.ZERO, IBorder.ZERO, binnieTexture);
	}

	public Texture(final IArea area, final IBorder padding, final IBinnieTexture binnieTexture) {
		this(area, padding, IBorder.ZERO, binnieTexture);
	}

	public Texture(final IArea area, final IBorder padding, final IBorder border, final IBinnieTexture binnieTexture) {
		this.area = new IArea(area);
		this.padding = new IBorder(padding);
		this.border = new IBorder(border);
		this.binnieTexture = binnieTexture;
	}

	public IArea getArea() {
		return this.area;
	}

	public IBorder getPadding() {
		return this.padding;
	}

	public IBorder getBorder() {
		return this.border;
	}

	@SideOnly(Side.CLIENT)
	public BinnieResource getFilename() {
		return this.binnieTexture.getTexture();
	}

	public IBorder getTotalPadding() {
		return this.padding.add(this.border);
	}

	public int width() {
		return this.getArea().width();
	}

	public int height() {
		return this.getArea().height();
	}

	public int u() {
		return this.getArea().xPos();
	}

	public int v() {
		return this.getArea().yPos();
	}

	public Texture crop(final Position anchor, final int dist) {
		return this.crop(new IBorder(anchor.opposite(), dist));
	}

	public Texture crop(final IBorder crop) {
		final Texture copy = new Texture(this.area, this.padding, this.border, this.binnieTexture);
		if (crop.b() > 0) {
			copy.border.b(0);
			copy.padding.b(copy.padding.b() - Math.min(crop.b(), copy.padding.b()));
			copy.area.setHeight(copy.area.height() - crop.b());
		}
		if (crop.t() > 0) {
			copy.border.t(0);
			copy.padding.t(copy.padding.t() - Math.min(crop.t(), copy.padding.t()));
			copy.area.setHeight(copy.area.height() - crop.t());
			copy.area.setYPos(copy.area.yPos() + crop.t());
		}
		if (crop.r() > 0) {
			copy.border.r(0);
			copy.padding.r(copy.padding.r() - Math.min(crop.r(), copy.padding.r()));
			copy.area.setWidth(copy.area.width() - crop.r());
		}
		if (crop.l() > 0) {
			copy.border.l(0);
			copy.padding.l(copy.padding.l() - Math.min(crop.l(), copy.padding.l()));
			copy.area.setWidth(copy.area.width() - crop.l());
			copy.area.setXPos(copy.area.xPos() + crop.l());
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
		return out + "]";
	}
}
