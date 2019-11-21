package binnie.core.config;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

class BinnieConfiguration extends Configuration {
	public AbstractMod mod;
	private final String filename;

	public BinnieConfiguration(final String filename, final AbstractMod mod) {
		super(new File(BinnieCore.getBinnieProxy().getDirectory(), filename));
		this.mod = mod;
		this.filename = filename;
	}
}
