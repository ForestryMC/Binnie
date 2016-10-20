// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.mod.config;

import java.io.File;
import binnie.core.BinnieCore;
import binnie.core.AbstractMod;
import net.minecraftforge.common.config.Configuration;

class BinnieConfiguration extends Configuration
{
	public AbstractMod mod;
	private String filename;

	public BinnieConfiguration(final String filename, final AbstractMod mod) {
		super(new File(BinnieCore.proxy.getDirectory(), filename));
		this.mod = mod;
		this.filename = filename;
	}
}
