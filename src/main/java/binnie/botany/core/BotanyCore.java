package binnie.botany.core;

import binnie.botany.api.FlowerManager;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.FlowerRoot;
import forestry.api.genetics.AlleleManager;

public class BotanyCore {
    public static final int CHANCE_INTERPOLLINATION = 20;

    public static IFlowerRoot getFlowerRoot() {
    	if(FlowerManager.flowerRoot == null){
    		FlowerManager.flowerRoot = new FlowerRoot();
    	}
        return FlowerManager.flowerRoot;
    }
}
