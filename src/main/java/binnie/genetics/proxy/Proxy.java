package binnie.genetics.proxy;

import binnie.core.proxy.BinnieModProxy;
import binnie.genetics.Genetics;

public class Proxy extends BinnieModProxy implements IGeneticsProxy {
    public Proxy() {
        super(Genetics.instance);
    }
}
