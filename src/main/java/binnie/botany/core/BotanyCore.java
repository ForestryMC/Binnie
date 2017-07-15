package binnie.botany.core;

import binnie.botany.api.FlowerManager;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.api.IGardeningManager;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.FlowerRoot;

public class BotanyCore {
	public static final int CHANCE_INTERPOLLINATION = 20;

	public static IFlowerRoot getFlowerRoot() {
		if (FlowerManager.flowerRoot == null) {
			FlowerManager.flowerRoot = new FlowerRoot();
		}
		return FlowerManager.flowerRoot;
	}

	public static IGardeningManager getGardening() {
		if (FlowerManager.gardening == null) {
			FlowerManager.gardening = new Gardening();
		}
		return FlowerManager.gardening;
	}
}
