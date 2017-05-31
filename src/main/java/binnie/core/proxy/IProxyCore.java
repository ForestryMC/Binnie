package binnie.core.proxy;

import binnie.core.AbstractMod;
import binnie.core.IInitializable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface IProxyCore extends IInitializable {

	void setMod(AbstractMod mod);

	Item registerItem(Item item);

	Block registerBlock(Block block);

	<B extends Block> void registerBlock(B block, ItemBlock itemBlock);

	void registerModels();

	void registerItemAndBlockColors();
}
