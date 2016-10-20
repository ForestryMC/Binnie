package binnie.core.mod.config;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

class BinnieConfiguration extends Configuration {
    public AbstractMod mod;
    private String filename;

    public BinnieConfiguration(final String filename, final AbstractMod mod) {
        super(new File(BinnieCore.proxy.getDirectory(), filename));
        this.mod = mod;
        this.filename = filename;
    }
}
