package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.IInitializable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IProxyCore extends IInitializable {

	void setMod(AbstractMod mod);

	Item registerItem(Item item);

	Block registerBlock(Block block);

	public void registerModels();

	public void registerItemAndBlockColors();

}
