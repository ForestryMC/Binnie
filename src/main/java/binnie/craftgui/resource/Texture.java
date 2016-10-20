package binnie.craftgui.resource;

import binnie.core.resource.BinnieResource;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.Position;

public class Texture {
    public static final Texture NULL;
    IArea area;
    IBorder padding;
    IBorder border;
    BinnieResource filename;

    public Texture(final IArea area, final BinnieResource filename) {
        this(area, IBorder.ZERO, IBorder.ZERO, filename);
    }

    public Texture(final IArea area, final IBorder padding, final BinnieResource filename) {
        this(area, padding, IBorder.ZERO, filename);
    }

    public Texture(final IArea area, final IBorder padding, final IBorder border, final BinnieResource filename) {
        this.padding = IBorder.ZERO;
        this.border = IBorder.ZERO;
        this.area = new IArea(area);
        this.padding = new IBorder(padding);
        this.border = new IBorder(border);
        this.filename = filename;
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

    public BinnieResource getFilename() {
        return this.filename;
    }

    public IBorder getTotalPadding() {
        return this.padding.add(this.border);
    }

    public float w() {
        return this.getArea().w();
    }

    public float h() {
        return this.getArea().h();
    }

    public float u() {
        return this.getArea().x();
    }

    public float v() {
        return this.getArea().y();
    }

    public Texture crop(final Position anchor, final float dist) {
        return this.crop(new IBorder(anchor.opposite(), dist));
    }

    public Texture crop(final IBorder crop) {
        final Texture copy = new Texture(this.area, this.padding, this.border, this.filename);
        if (crop.b() > 0.0f) {
            copy.border.b(0.0f);
            copy.padding.b(copy.padding.b() - Math.min(crop.b(), copy.padding.b()));
            copy.area.h(copy.area.h() - crop.b());
        }
        if (crop.t() > 0.0f) {
            copy.border.t(0.0f);
            copy.padding.t(copy.padding.t() - Math.min(crop.t(), copy.padding.t()));
            copy.area.h(copy.area.h() - crop.t());
            copy.area.y(copy.area.y() + crop.t());
        }
        if (crop.r() > 0.0f) {
            copy.border.r(0.0f);
            copy.padding.r(copy.padding.r() - Math.min(crop.r(), copy.padding.r()));
            copy.area.w(copy.area.w() - crop.r());
        }
        if (crop.l() > 0.0f) {
            copy.border.l(0.0f);
            copy.padding.l(copy.padding.l() - Math.min(crop.l(), copy.padding.l()));
            copy.area.w(copy.area.w() - crop.l());
            copy.area.x(copy.area.x() + crop.l());
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

    static {
        NULL = null;
    }
}
