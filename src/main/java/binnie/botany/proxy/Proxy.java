package binnie.botany.proxy;

import binnie.botany.Botany;
import binnie.core.proxy.BinnieModProxy;

public class Proxy extends BinnieModProxy implements IBotanyProxy {
    public Proxy() {
        super(Botany.instance);
    }
}
