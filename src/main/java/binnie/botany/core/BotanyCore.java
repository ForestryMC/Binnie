package binnie.botany.core;

import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.FlowerHelper;

public class BotanyCore {
    public static IFlowerRoot speciesRoot = new FlowerHelper();

    public static IFlowerRoot getFlowerRoot() {
        return BotanyCore.speciesRoot;
    }
}
