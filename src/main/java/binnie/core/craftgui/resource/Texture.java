package binnie.core.craftgui.resource;

import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.geometry.Position;
import binnie.core.resource.BinnieResource;

public class Texture {
    public static Texture NULL = null;
    IArea area;
    IBorder padding;
    IBorder border;
    BinnieResource filename;

    public Texture(IArea area, BinnieResource filename) {
        this(area, IBorder.ZERO, IBorder.ZERO, filename);
    }

    public Texture(IArea area, IBorder padding, BinnieResource filename) {
        this(area, padding, IBorder.ZERO, filename);
    }

    public Texture(IArea area, IBorder padding, IBorder border, BinnieResource filename) {
        this.padding = IBorder.ZERO;
        this.border = IBorder.ZERO;
        this.area = new IArea(area);
        this.padding = new IBorder(padding);
        this.border = new IBorder(border);
        this.filename = filename;
    }

    public IArea getArea() {
        return area;
    }

    public IBorder getPadding() {
        return padding;
    }

    public IBorder getBorder() {
        return border;
    }

    public BinnieResource getFilename() {
        return filename;
    }

    public IBorder getTotalPadding() {
        return padding.add(border);
    }

    public float w() {
        return getArea().w();
    }

    public float h() {
        return getArea().h();
    }

    public float u() {
        return getArea().x();
    }

    public float v() {
        return getArea().y();
    }

    public Texture crop(Position anchor, float dist) {
        return crop(new IBorder(anchor.opposite(), dist));
    }

    public Texture crop(IBorder crop) {
        Texture copy = new Texture(area, padding, border, filename);
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
        out += area.toString();
        if (!padding.isNonZero()) {
            out = out + " padding:" + padding.toString();
        }
        if (!border.isNonZero()) {
            out = out + " border:" + border.toString();
        }
        return out + "]";
    }
}
