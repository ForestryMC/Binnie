package binnie.botany.core;

import binnie.botany.GardeningManager;
import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.genetics.FlowerRoot;

public class BotanyCore {
	public static final int CHANCE_INTERPOLLINATION = 20;

	public static IFlowerRoot getFlowerRoot() {
		if (BotanyAPI.flowerRoot == null) {
			BotanyAPI.flowerRoot = new FlowerRoot();
		}
		return BotanyAPI.flowerRoot;
	}

	public static IGardeningManager getGardening() {
		if (BotanyAPI.gardening == null) {
			BotanyAPI.gardening = new GardeningManager();
		}
		return BotanyAPI.gardening;
	}
}
