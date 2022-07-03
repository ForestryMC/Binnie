package binnie.extratrees.block.decor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FenceType {
    public int size;
    public boolean solid;
    public boolean embossed;

    public FenceType(int size, boolean solid, boolean embedded) {
        this.size = size;
        this.solid = solid;
        embossed = embedded;
    }

    public FenceType(int meta) {
        size = (meta & 0x3);
        solid = ((meta >> 2 & 0x1) > 0);
        embossed = ((meta >> 3 & 0x1) > 0);
    }

    public static Collection<FenceType> values() {
        List<FenceType> list = new ArrayList<>();
        for (int size = 0; size < 3; ++size) {
            for (boolean solid : new boolean[] {false, true}) {
                for (boolean embedded : new boolean[] {false, true}) {
                    list.add(new FenceType(size, solid, embedded));
                }
            }
        }
        return list;
    }

    public String getPrefix() {
        return ((size == 0) ? "" : ((size == 1) ? "Full " : "Low "))
                + (solid ? "Solid " : "")
                + (embossed ? "Embedded " : "");
    }

    public int ordinal() {
        return size + ((solid ? 1 : 0) << 2) + ((embossed ? 1 : 0) << 3);
    }

    public boolean isPlain() {
        return size == 0 && !embossed && !solid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FenceType) {
            FenceType o = (FenceType) obj;
            return o.size == size && o.embossed == embossed && o.solid == solid;
        }
        return super.equals(obj);
    }
}
