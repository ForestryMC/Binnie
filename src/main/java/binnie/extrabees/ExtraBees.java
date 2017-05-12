package binnie.extrabees;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.config.ConfigurationMachines;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import binnie.extrabees.genetics.ModuleGenetics;
import binnie.extrabees.init.ItemRegister;
import binnie.extrabees.init.RecipeRegister;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.products.ItemHoneyComb;
import binnie.extrabees.proxy.ExtraBeesProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.EXTRA_BEES_MOD_ID, name = "Binnie's Extra Bees", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class ExtraBees extends AbstractMod {

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.EXTRA_BEES_MOD_ID)
	public static ExtraBees instance;

	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.extrabees.proxy.ExtraBeesProxyClient", serverSide = "binnie.extrabees.proxy.ExtraBeesProxyServer")
	public static ExtraBeesProxy proxy;

	public static Block hive;
	public static Material materialBeehive;
	public static Block ectoplasm;
	public static Block apiaristMachine;
	public static Block geneticMachine;
	public static Block advGeneticMachine;
	public static Item comb;
	public static Item propolis;
	public static Item honeyDrop;
	public static Item honeyCrystal;
	public static Item honeyCrystalEmpty;
	public static Item dictionary;
	public static Item itemMisc;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		this.preInit();
		ItemRegister.preInitItems();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
		ItemHoneyComb.addSubtypes();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
		RecipeRegister.postInitRecipes();
	}

	@Override
	protected void registerModules() {
		this.addModule(new ModuleCore());
		this.addModule(new ModuleGenetics());
		//this.addModule(new ModuleGeneration());
		this.addModule(new ModuleLiquids());
		this.addModule(new ModuleApiary());
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraBeeGUID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[]{ConfigurationMain.class, ConfigurationMachines.class};
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
