package binnie.botany.integration.jei;

import binnie.botany.api.genetics.IFlower;
import binnie.botany.genetics.Flower;
import binnie.botany.modules.ModuleFlowers;
import binnie.core.Constants;
import binnie.core.modules.BotanyModuleUIDs;
import forestry.api.core.ForestryAPI;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@JEIPlugin
public class BotanyJeiPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (ForestryAPI.moduleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.FLOWERS)) {
            subtypeRegistry.registerSubtypeInterpreter(ModuleFlowers.flowerItem, (ItemStack itemStack) -> {
                NBTTagCompound tagCompound = itemStack.getTagCompound();
                if (tagCompound == null) {
                    return ModuleFlowers.flowerItem.getTranslationKey();
                }
                IFlower flower = new Flower(tagCompound);
                return flower.getGenome().getPrimary().getAlleleName();
            });
        }
    }
}
