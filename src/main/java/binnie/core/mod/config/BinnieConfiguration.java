package binnie.core.mod.config;

import binnie.core.*;
import net.minecraftforge.common.config.*;

import java.io.*;

class BinnieConfiguration extends Configuration {
	public AbstractMod mod;
	private String filename;

	public BinnieConfiguration(String filename, AbstractMod mod) {
		super(new File(BinnieCore.proxy.getDirectory(), filename));
		this.mod = mod;
		this.filename = filename;
	}
}
