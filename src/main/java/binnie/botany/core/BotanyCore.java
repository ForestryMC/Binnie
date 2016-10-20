// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.core;

import binnie.botany.genetics.FlowerHelper;
import binnie.botany.api.IFlowerRoot;

public class BotanyCore
{
	public static final int CHANCE_INTERPOLLINATION = 20;
	public static IFlowerRoot speciesRoot;

	public static IFlowerRoot getFlowerRoot() {
		return BotanyCore.speciesRoot;
	}

	static {
		BotanyCore.speciesRoot = new FlowerHelper();
	}
}
