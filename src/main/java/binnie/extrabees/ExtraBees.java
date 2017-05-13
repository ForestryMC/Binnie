package binnie.extrabees;

import binnie.extrabees.client.GuiHack;
import binnie.extrabees.genetics.ExtraBeeMutation;
import binnie.extrabees.genetics.ExtraBeesBranch;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.init.BlockRegister;
import binnie.extrabees.init.ItemRegister;
import binnie.extrabees.init.RecipeRegister;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.proxy.ExtraBeesCommonProxy;
import binnie.extrabees.utils.MaterialBeehive;
import binnie.extrabees.utils.config.ConfigHandler;
import binnie.extrabees.utils.config.ConfigurationMain;
import binnie.extrabees.worldgen.ExtraBeesWorldGenerator;
import forestry.api.genetics.AlleleManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExtraBees.MODID, name = "Binnie's Extra Bees", useMetadata = true)
public class ExtraBees {

	public static final String MODID = "extrabees";

	@Mod.Instance(MODID)
	public static ExtraBees instance;

	@SidedProxy(clientSide = "binnie.extrabees.proxy.ExtraBeesClientProxy", serverSide = "binnie.extrabees.proxy.ExtraBeesCommonProxy")
	public static ExtraBeesCommonProxy proxy;
	public static ConfigHandler configHandler;
	public static NetworkHandler networkHandler;

	public static Block hive;
	public static Material materialBeehive;
	public static Block ectoplasm;
	public static Item comb;
	public static Item propolis;
	public static Item honeyDrop;
	public static Item honeyCrystal;
	public static Item honeyCrystalEmpty;
	public static Item dictionary;
	public static Item itemMisc;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		GuiHack.INSTANCE.preInit();

		networkHandler = new NetworkHandler(MODID);
		materialBeehive = new MaterialBeehive();
		configHandler = new ConfigHandler(event.getSuggestedConfigurationFile());
		configHandler.addConfigurable(new ConfigurationMain());
		BlockRegister.preInitBlocks();
		ItemRegister.preInitItems();

		ExtraBeesBranch.setSpeciesBranches();
		// Register species
		for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
			AlleleManager.alleleRegistry.registerAllele(species);
		}
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		GuiHack.INSTANCE.init();

		configHandler.reload(true);
		ItemHoneyComb.addSubtypes();
		GameRegistry.registerWorldGenerator(new ExtraBeesWorldGenerator(), 0);
		ExtraBeesEffect.doInit();
		ExtraBeesFlowers.doInit();
		ExtraBeesSpecies.doInit();
		ExtraBeeMutation.doInit();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		GuiHack.INSTANCE.postInit();

		BlockRegister.postInitBlocks();
		RecipeRegister.postInitRecipes();
	}

	private static class NetworkHandler {

		private NetworkHandler(String channel){
			this.networkWrapper = new SimpleNetworkWrapper(channel);
		}

		private final SimpleNetworkWrapper networkWrapper;

	}

}
