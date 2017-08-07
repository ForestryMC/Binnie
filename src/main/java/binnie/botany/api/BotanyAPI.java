package binnie.botany.api;

import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.api.genetics.IFlowerFactory;
import binnie.botany.api.genetics.IFlowerRoot;

public class BotanyAPI {
	/**
	 * Convenient access to AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers")
	 */
	public static IFlowerRoot flowerRoot;

	/**
	 * Used to create new flowers.
	 */
	public static IFlowerFactory flowerFactory;

	/**
	 * Used to manager soil.
	 */
	public static IGardeningManager gardening;
}
