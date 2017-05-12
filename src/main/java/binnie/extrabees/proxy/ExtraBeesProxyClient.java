package binnie.extrabees.proxy;

import binnie.core.models.ModelManager;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExtraBeesProxyClient extends ExtraBeesProxy {

	public static ModelManager modelManager = new ModelManager(ExtraBees.MODID);

	@Override
	public void registerBeeModel(ExtraBeesSpecies type) {
		for (EnumBeeType t : EnumBeeType.VALUES)
			type.registerModels(BeeManager.beeRoot.getMemberStack(BeeManager.beeRoot.templateAsIndividual(type.getTemplate()), t).getItem(), null);
	}

	@Override
	public void registerModel(Item item, int meta) {
		registerModel(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@Override
	public void registerModel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	public static ModelManager getModelManager() {
		return modelManager;
	}

	@Override
	public Item registerItem(Item item) {
		getModelManager().registerItemClient(item);
		return super.registerItem(item);
	}

	@Override
	public Block registerBlock(Block block) {
		getModelManager().registerBlockClient(block);
		return super.registerBlock(block);
	}

	@Override
	public void registerModels() {
		getModelManager().registerModels();
	}

	@Override
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
	}
}
