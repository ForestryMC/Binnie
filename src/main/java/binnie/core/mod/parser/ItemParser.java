package binnie.core.mod.parser;

import binnie.core.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;

import java.lang.reflect.*;

public class ItemParser extends FieldParser {
	@Override
	public boolean isHandled(Field field, AbstractMod mod) {
		return Item.class.isAssignableFrom(field.getType());
	}

	@Override
	public void preInit(Field field, AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
		Item item = (Item) field.get(null);
		if (item != null) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
	}
}
