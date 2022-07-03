package binnie.extratrees.nei;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.nbt.NBTTagCompound;

public class IMCForNEI {
    public static void IMCSender() {
        sendHandler("binnie.extratrees.nei.NEIHandlerLumbermill", "ExtraTrees:machine:0");

        sendCatalyst("extratrees.lumbermill", "ExtraTrees:machine:0");
    }

    private static void sendHandler(String aName, String aBlock) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", "Extra Trees");
        aNBT.setString("modId", "ExtraTrees");
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
