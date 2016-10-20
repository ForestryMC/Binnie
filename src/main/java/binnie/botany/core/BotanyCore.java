package binnie.botany.core;

import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.FlowerHelper;

public class BotanyCore {
    public static final int CHANCE_INTERPOLLINATION = 20;
    public static IFlowerRoot speciesRoot;

    public static IFlowerRoot getFlowerRoot() {
        return BotanyCore.speciesRoot;
    }

    static {
        BotanyCore.speciesRoot = new FlowerHelper();
    }
}
