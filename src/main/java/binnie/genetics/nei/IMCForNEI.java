package binnie.genetics.nei;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.nbt.NBTTagCompound;

public class IMCForNEI {
    public static void IMCSender() {
        sendHandler("binnie.genetics.nei.IsolatorRecipeHandler", "Genetics:machine:0");
        sendHandler("binnie.genetics.nei.SequencerRecipeHandler", "Genetics:machine:1");
        sendHandler("binnie.genetics.nei.PolymeriserRecipeHandler", "Genetics:machine:2");
        sendHandler("binnie.genetics.nei.InoculatorRecipeHandler", "Genetics:machine:3");
        sendHandler("binnie.genetics.nei.AnalyserRecipeHandler", "Genetics:labMachine:1");
        sendHandler("binnie.genetics.nei.IncubatorRecipeHandler", "Genetics:labMachine:2");
        sendHandler("binnie.genetics.nei.GenepoolRecipeHandler", "Genetics:labMachine:3");
        sendHandler("binnie.genetics.nei.AcclimatiserRecipeHandler", "Genetics:labMachine:4");
        sendHandler("binnie.genetics.nei.SplicerRecipeHandler", "Genetics:advMachine:0");
        sendHandler("binnie.genetics.nei.DatabaseRecipeHandler", "Genetics:database:0");

        sendCatalyst("genetics.isolator", "Genetics:machine:0");
        sendCatalyst("genetics.sequencer", "Genetics:machine:1");
        sendCatalyst("genetics.polymeriser", "Genetics:machine:2");
        sendCatalyst("genetics.inoculator", "Genetics:machine:3");
        sendCatalyst("genetics.database", "Genetics:database:0");
        sendCatalyst("genetics.database", "Genetics:database:1");
        sendCatalyst("genetics.database", "Genetics:labMachine:0");
        sendCatalyst("genetics.analyser", "Genetics:labMachine:1");
        sendCatalyst("genetics.incubator", "Genetics:labMachine:2");
        sendCatalyst("genetics.genepool", "Genetics:labMachine:3");
        sendCatalyst("genetics.acclimatiser", "Genetics:labMachine:4");
        sendCatalyst("genetics.splicer", "Genetics:advMachine:0");
    }

    private static void sendHandler(String aName, String aBlock) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", "Genetics");
        aNBT.setString("modId", "Genetics");
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 76);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", 4);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
