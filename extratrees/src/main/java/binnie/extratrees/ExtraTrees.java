package binnie.extratrees;

import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.lepidopterology.ButterflyManager;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.features.IFeatureItem;
import binnie.core.gui.IBinnieGUID;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.machines.errors.ErrorStateRegistry;
import binnie.core.modules.ModuleProvider;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.genetics.MothBreedingSystem;
import binnie.extratrees.genetics.gui.analyst.ButterflyAnalystPagePlugin;
import binnie.extratrees.genetics.gui.analyst.TreeAnalystPagePlugin;
import binnie.extratrees.genetics.gui.analyst.TreeProducePlugin;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.modules.ModuleWood;
import binnie.extratrees.proxy.Proxy;
import binnie.genetics.api.GeneticsApi;
import binnie.genetics.api.analyst.IAnalystManager;

@Mod(
	modid = Constants.EXTRA_TREES_MOD_ID,
	name = "Binnie's Extra Trees",
	version = "@VERSION@",
	acceptedMinecraftVersions = Constants.ACCEPTED_MINECRAFT_VERSIONS,
	dependencies = "required-after:" + Constants.CORE_MOD_ID + ';'
		+ "after:" + Constants.DESIGN_MOD_ID + ';'
)
public class ExtraTrees extends ModuleProvider {

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.EXTRA_TREES_MOD_ID)
	public static ExtraTrees instance;

	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
	public static Proxy proxy;
	public static IBreedingSystem mothBreedingSystem;

	public ExtraTrees() {
		super();
		MinecraftForge.EVENT_BUS.register(ModuleWood.class);
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		container.registerConfigHandler(new ConfigurationMain(container));
		super.preInit(evt);

		if (ButterflyManager.butterflyRoot != null) {
			mothBreedingSystem = new MothBreedingSystem();
			Binnie.GENETICS.registerBreedingSystem(mothBreedingSystem);
		}

		IAnalystManager analystManager = GeneticsApi.analystManager;
		if (analystManager != null) {
			analystManager.registerAnalystPagePlugin(new TreeAnalystPagePlugin());
			analystManager.registerAnalystPagePlugin(new ButterflyAnalystPagePlugin());
			analystManager.registerProducePlugin(new TreeProducePlugin());
		}

		for (ExtraTreesErrorCode errorCode : ExtraTreesErrorCode.values()) {
			ErrorStateRegistry.registerErrorState(errorCode);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onRegisterItem(RegistryEvent.Register<Item> event) {
		IFeatureItem glassFeature = FluidContainerType.GLASS.getFeature();
		if (glassFeature.hasItem()) {
			ItemFluidContainer container = (ItemFluidContainer) glassFeature.item();
			for (Juice juice : Juice.values()) {
				OreDictionary.registerOre(juice.getOreDict(), container.getContainer(juice.getType()));
			}
		}
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		super.init(evt);
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		super.postInit(evt);
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraTreesGUID.values();
	}

	@Override
	public String getChannel() {
		return "ET";
	}

	@Override
	public IProxyCore getProxy() {
		return ExtraTrees.proxy;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(ExtraTrees.instance);
		}
	}
}
