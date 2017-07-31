package binnie.core.gui.resource;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.geometry.Position;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public class Texture {
	private final Area area;
	private final Border padding;
	private final Border border;
	private final IBinnieTexture binnieTexture;

	public Texture(final Area area, final IBinnieTexture binnieTexture) {
		this(area, Border.ZERO, Border.ZERO, binnieTexture);
	}

	public Texture(final Area area, final Border padding, final IBinnieTexture binnieTexture) {
		this(area, padding, Border.ZERO, binnieTexture);
	}

	public Texture(final Area area, final Border padding, final Border border, final IBinnieTexture binnieTexture) {
		this.area = new Area(area);
		this.padding = new Border(padding);
		this.border = new Border(border);
		this.binnieTexture = binnieTexture;
	}

	public Area getArea() {
		return this.area;
	}

	public Border getPadding() {
		return this.padding;
	}

	public Border getBorder() {
		return this.border;
	}

	@SideOnly(Side.CLIENT)
	public BinnieResource getFilename() {
		return this.binnieTexture.getTexture();
	}

	public Border getTotalPadding() {
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
		return this.crop(new Border(anchor.opposite(), dist));
	}

	public Texture crop(final Border crop) {
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
