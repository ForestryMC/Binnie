package binnie.extratrees.block.decor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FenceType {
    public int size;
    public boolean solid;
    public boolean embossed;

    public FenceType(final int size, final boolean solid, final boolean embedded) {
        this.size = size;
        this.solid = solid;
        this.embossed = embedded;
    }

    public String getPrefix() {
        return ((this.size == 0) ? "" : ((this.size == 1) ? "Full " : "Low ")) + (this.solid ? "Solid " : "") + (this.embossed ? "Embedded " : "");
    }

    public int ordinal() {
        return this.size + ((this.solid ? 1 : 0) << 2) + ((this.embossed ? 1 : 0) << 3);
    }

    public FenceType(final int meta) {
        this.size = (meta & 0x3);
        this.solid = ((meta >> 2 & 0x1) > 0);
        this.embossed = ((meta >> 3 & 0x1) > 0);
    }

    public static Collection<FenceType> values() {
        final List<FenceType> list = new ArrayList<FenceType>();
        for (int size = 0; size < 3; ++size) {
            for (final boolean solid : new boolean[]{false, true}) {
                for (final boolean embedded : new boolean[]{false, true}) {
                    list.add(new FenceType(size, solid, embedded));
                }
            }
        }
        return list;
    }

    public boolean isPlain() {
        return this.size == 0 && !this.embossed && !this.solid;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FenceType) {
            final FenceType o = (FenceType) obj;
            return o.size == this.size && o.embossed == this.embossed && o.solid == this.solid;
        }
        return super.equals(obj);
    }
}
