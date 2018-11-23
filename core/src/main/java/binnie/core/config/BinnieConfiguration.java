package binnie.core.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;

class BinnieConfiguration extends Configuration {
	public AbstractMod mod;
	private final String filename;

	public BinnieConfiguration(String filename, AbstractMod mod) {
		super(new File(BinnieCore.getBinnieProxy().getDirectory(), filename));
		this.mod = mod;
		this.filename = filename;
	}
}
