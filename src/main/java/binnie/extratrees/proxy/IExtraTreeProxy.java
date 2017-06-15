package binnie.extratrees.proxy;

import net.minecraft.block.Block;

import binnie.core.proxy.IProxyCore;

public interface IExtraTreeProxy extends IProxyCore {
	void setCustomStateMapper(String name, Block block);
}
