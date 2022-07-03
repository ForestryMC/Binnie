package binnie.extratrees.carpentry;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GlassType implements IDesignMaterial {
    protected static Map<Integer, GlassType> types = new LinkedHashMap<>();

    static {
        for (StandardColor c : StandardColor.values()) {
            GlassType.types.put(c.ordinal(), new GlassType(c.ordinal(), c.name, c.color));
        }
        for (EnumFlowerColor c2 : EnumFlowerColor.values()) {
            GlassType.types.put(
                    128 + c2.ordinal(), new GlassType(128 + c2.ordinal(), c2.getName(), c2.getColor(false)));
        }
    }

    protected String name;
    protected int color;
    protected int id;

    private GlassType(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static int getIndex(IDesignMaterial id) {
        for (Map.Entry<Integer, GlassType> entry : GlassType.types.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public static IDesignMaterial get(int id) {
        return GlassType.types.get(id);
    }

    public static IDesignMaterial get(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        for (Map.Entry<Integer, GlassType> entry : GlassType.types.entrySet()) {
            if (stack.isItemEqual(entry.getValue().getStack())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public ItemStack getStack() {
        if (id < 128) {
            return new ItemStack(Blocks.stained_glass, 1, id);
        }
        return TileEntityMetadata.getItemStack(Botany.stained, id - 128);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    private enum StandardColor {
        White("White", new Color(0xffffff)),
        Orange("Orange", new Color(0xd87f33)),
        Magenta("Magenta", new Color(0xb24cd8)),
        LightBlue("Light Blue", new Color(0x6699d8)),
        Yellow("Yellow", new Color(0xe5e533)),
        Lime("Lime", new Color(0x7fcc19)),
        Pink("Pink", new Color(0xf27fa5)),
        Gray("Gray", new Color(0x4c4c4c)),
        LightGray("Light Gray", new Color(0x999999)),
        Cyan("Cyan", new Color(0x4c7f99)),
        Purple("Purple", new Color(0x7f3fb2)),
        Blue("Blue", new Color(0x334cb2)),
        Brown("Brown", new Color(0x664c33)),
        Green("Green", new Color(0x667f33)),
        Red("Red", new Color(0x993333)),
        Black("Black", new Color(0x191919));

        protected String name;
        protected int color;

        StandardColor(String name, Color color) {
            this.name = name;
            this.color = color.getRGB();
        }
    }
}
