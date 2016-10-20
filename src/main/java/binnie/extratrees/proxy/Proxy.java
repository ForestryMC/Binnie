package binnie.extratrees.proxy;

import binnie.core.proxy.BinnieModProxy;
import binnie.extratrees.ExtraTrees;
import net.minecraft.block.Block;

public  class Proxy extends BinnieModProxy implements IExtraTreeProxy {
    public Proxy() {
        super(ExtraTrees.instance);
    }

    @Override
    public void setCustomStateMapper(String name, Block block) {

    }
}
