package binnie.genetics.nei;

import binnie.core.BinnieCore;
import binnie.core.nei.RecipeHandlerBase;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIGeneticsConfig implements IConfigureNEI {
    protected static void registerHandler(RecipeHandlerBase handler) {
        handler.prepare();
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

    @Override
    public void loadConfig() {
        registerHandler(new IsolatorRecipeHandler());
        registerHandler(new SequencerRecipeHandler());
        registerHandler(new PolymeriserRecipeHandler());
        registerHandler(new InoculatorRecipeHandler());
        registerHandler(new AnalyserRecipeHandler());
        registerHandler(new IncubatorRecipeHandler());
        registerHandler(new GenepoolRecipeHandler());
        registerHandler(new AcclimatiserRecipeHandler());
        registerHandler(new SplicerRecipeHandler());
        registerHandler(new DatabaseRecipeHandler());
    }

    @Override
    public String getName() {
        return "Genetics NEI";
    }

    @Override
    public String getVersion() {
        return BinnieCore.VERSION;
    }
}
