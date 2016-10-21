package binnie.extrabees.proxy;

import binnie.extrabees.genetics.ExtraBeesSpecies;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ExtraBeesProxyClient extends ExtraBeesProxy {
    @Override
	public void registerBeeModel(ExtraBeesSpecies type) {
        for (EnumBeeType t : EnumBeeType.VALUES)
            type.registerModels(BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.templateAsIndividual(type.getTemplate()), t).getItem(), null);
    }

    @Override
    public void registermodel(Item item, int meta) {
        registermodel(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void registermodel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
    }
}
