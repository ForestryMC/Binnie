package binnie.extrabees;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import binnie.extrabees.genetics.ExtraBeeMutation;
import binnie.extrabees.genetics.ExtraBeesBranch;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.init.BlockRegister;
import binnie.extrabees.init.ItemRegister;
import binnie.extrabees.init.RecipeRegister;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.proxy.ExtraBeesProxy;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.EXTRA_BEES_MOD_ID, name = "Binnie's Extra Bees", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class ExtraBees extends AbstractMod {

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.EXTRA_BEES_MOD_ID)
	public static ExtraBees instance;

	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.extrabees.proxy.ExtraBeesProxyClient", serverSide = "binnie.extrabees.proxy.ExtraBeesProxyServer")
	public static ExtraBeesProxy proxy;
	public static ConfigHandler configHandler;

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
		this.preInit();
		materialBeehive = new MaterialBeehive();
		configHandler = new ConfigHandler(event.getModConfigurationDirectory());
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
		this.init();
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
		this.postInit();
		BlockRegister.postInitBlocks();
		RecipeRegister.postInitRecipes();
	}

	@Override
	protected void registerModules() {
		this.addModule(new ModuleCore());
		this.addModule(new ModuleLiquids());
		this.addModule(new ModuleApiary());
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraBeeGUID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[0];
	}

	@Override
	public IProxyCore getProxy() {
		return ExtraBees.proxy;
	}

	@Override
	public String getChannel() {
		return "EB";
	}

	@Override
	public String getModID() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isExtraBeesActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(ExtraBees.instance);
		}
	}

}
