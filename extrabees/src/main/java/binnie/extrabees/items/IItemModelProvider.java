package binnie.extrabees.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemModelProvider {
	@SideOnly(Side.CLIENT)
	void registerModel(Item item);
}
