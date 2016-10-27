package binnie.extratrees.proxy;

import binnie.core.proxy.IProxyCore;
import net.minecraft.block.Block;

public interface IExtraTreeProxy extends IProxyCore {
	void setCustomStateMapper(String name, Block block);
}
