package binnie.extratrees.nei;

import binnie.core.BinnieCore;
import binnie.core.nei.RecipeHandlerBase;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIExtraTreesConfig implements IConfigureNEI {
    protected static void registerHandler(RecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    @Override
    public void loadConfig() {
        registerHandler(new NEIHandlerLumbermill());
    }

    @Override
    public String getName() {
        return "Extra Trees NEI";
    }

    @Override
    public String getVersion() {
        return BinnieCore.VERSION;
    }
}
