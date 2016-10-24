package binnie.core.proxy;

import binnie.core.IInitializable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IProxyCore extends IInitializable {
	
    Item registerItem(Item item);
    
    Block registerBlock(Block block);
}
