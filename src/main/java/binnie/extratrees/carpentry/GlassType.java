package binnie.extratrees.carpentry;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlassType implements IDesignMaterial {
	protected static Map<Integer, GlassType> types;

	static {
		GlassType.types = new LinkedHashMap<>();
		for (StandardColor c : StandardColor.values()) {
			GlassType.types.put(c.ordinal(), new GlassType(c.ordinal(), c.name, c.colour));
		}
		for (EnumFlowerColor c2 : EnumFlowerColor.values()) {
			GlassType.types.put(128 + c2.ordinal(), new GlassType(128 + c2.ordinal(), c2.getName(), c2.getColor(false)));
		}
	}

	protected String name;
	protected int colour;
	protected int id;

	private GlassType(int id, String name, int colour) {
		this.id = id;
		this.name = name;
		this.colour = colour;
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
	public int getColour() {
		return colour;
	}

	private enum StandardColor {
		White("White", 16777215),
		Orange("Orange", 14188339),
		Magenta("Magenta", 11685080),
		LightBlue("Light Blue", 6724056),
		Yellow("Yellow", 15066419),
		Lime("Lime", 8375321),
		Pink("Pink", 15892389),
		Gray("Gray", 5000268),
		LightGray("Light Gray", 10066329),
		Cyan("Cyan", 5013401),
		Purple("Purple", 8339378),
		Blue("Blue", 3361970),
		Brown("Brown", 6704179),
		Green("Green", 6717235),
		Red("Red", 10040115),
		Black("Black", 1644825);

		protected String name;
		protected int colour;

		StandardColor(String name, int colour) {
			this.name = name;
			this.colour = colour;
		}
	}
}
