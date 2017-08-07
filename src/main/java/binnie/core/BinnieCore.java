package binnie.core;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.plugins.PluginManager;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.block.TileEntityMetadata;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.gui.BinnieGUIHandler;
import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.ModuleCraftGUI;
import binnie.core.integration.extrabees.ExtraBeesIntegration;
import binnie.core.item.ItemFieldKit;
import binnie.core.item.ItemGenesis;
import binnie.core.item.ModuleItems;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.storage.ModuleStorage;
import binnie.core.mod.config.ConfigurationMain;
import binnie.core.mod.config.ConfigurationMods;
import binnie.core.models.ModelManager;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.BinnieProxy;
import binnie.core.proxy.IBinnieProxy;
import binnie.core.triggers.ModuleTrigger;
import binnie.modules.ModuleManager;

@Mod(
	modid = Constants.CORE_MOD_ID,
	name = "Binnie Core",
	version = "@VERSION@",
	dependencies = "required-after:forge@[13.20.0.2279,);" +
		"required-after:forestry@[5.3.4.102,);" +
		"after:jei@[4.3.1,);"
)
public final class BinnieCore extends AbstractMod {

	private static final List<AbstractMod> modList = new ArrayList<>();
	public static int multipassRenderID;
	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.CORE_MOD_ID)
	private static BinnieCore instance;
	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.core.proxy.BinnieProxyClient", serverSide = "binnie.core.proxy.BinnieProxyServer")
	private static BinnieProxy proxy;
	@Nullable
	private static MachineGroup packageCompartment;
	@Nullable
	private static ItemGenesis genesis;
	@Nullable
	private static ItemFieldKit fieldKit;

	public BinnieCore() {
		FluidRegistry.enableUniversalBucket();
	}

	public static MachineGroup getPackageCompartment() {
		Preconditions.checkState(packageCompartment != null, "packageCompartment has not been init");
		return packageCompartment;
	}

	public static void setPackageCompartment(MachineGroup packageCompartment) {
		BinnieCore.packageCompartment = packageCompartment;
	}

	public static ItemGenesis getGenesis() {
		Preconditions.checkState(genesis != null, "genesis has not been init");
		return genesis;
	}

	public static void setGenesis(ItemGenesis genesis) {
		BinnieCore.genesis = genesis;
	}

	public static ItemFieldKit getFieldKit() {
		Preconditions.checkState(fieldKit != null, "fieldKit has not been init");
		return fieldKit;
	}

	public static void setFieldKit(ItemFieldKit fieldKit) {
		BinnieCore.fieldKit = fieldKit;
	}

	public static BinnieCore getInstance() {
		return instance;
	}

	public static BinnieProxy getBinnieProxy() {
		return proxy;
	}

	public static boolean isLepidopteryActive() {
		return PluginManager.configDisabledPlugins.stream().noneMatch(e -> e instanceof forestry.lepidopterology.PluginLepidopterology);
	}

	public static boolean isApicultureActive() {
		return PluginManager.configDisabledPlugins.stream().noneMatch(e -> e instanceof forestry.arboriculture.PluginArboriculture);
	}

	public static boolean isArboricultureActive() {
		return PluginManager.configDisabledPlugins.stream().noneMatch(e -> e instanceof forestry.arboriculture.PluginArboriculture);
	}

	public static boolean isBotanyActive() {
		return ConfigurationMods.botany;
	}

	public static boolean isGeneticsActive() {
		return ConfigurationMods.genetics;
	}

	public static boolean isExtraBeesActive() {
		return ExtraBeesIntegration.isLoaded() && isApicultureActive();
	}

	public static boolean isExtraTreesActive() {
		return ConfigurationMods.extraTrees && isArboricultureActive();
	}

	static void registerMod(final AbstractMod mod) {
		BinnieCore.modList.add(mod);
	}

	private static List<AbstractMod> getActiveMods() {
		final List<AbstractMod> list = new ArrayList<>();
		for (final AbstractMod mod : BinnieCore.modList) {
			if (mod.isAvailable()) {
				list.add(mod);
			}
		}
		return list;
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		ModuleManager.loadModules(evt);
		MinecraftForge.EVENT_BUS.register(Binnie.LIQUID);
		Binnie.CONFIGURATION.registerConfiguration(ConfigurationMods.class, this);
		for (FluidContainerType container : FluidContainerType.getBinnieContainers()) {
			Item item = new ItemFluidContainer(container);
			getProxy().registerItem(item);
		}
		this.preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
		for (final AbstractMod mod : getActiveMods()) {
			NetworkRegistry.INSTANCE.registerGuiHandler(mod.getMod(), new BinnieGUIHandler(mod));
		}
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "binnie.tile.metadata");
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	@Override
	protected void registerModules() {
		for (final ManagerBase baseManager : Binnie.MANAGERS) {
			this.addModule(baseManager);
		}
		this.addModule(new ModuleCraftGUI());
		this.addModule(new ModuleStorage());
		this.addModule(new ModuleItems());
		if (Loader.isModLoaded("BuildCraft|Silicon")) {
			this.addModule(new ModuleTrigger());
		}
		if (ExtraBeesIntegration.isLoaded()) {
			this.addModule(new ExtraBeesIntegration());
		}
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return BinnieCoreGUI.values();
	}

	@Override
	public String getChannel() {
		return "BIN";
	}

	@Override
	public IBinnieProxy getProxy() {
		return proxy;
	}

	//Module handling

	@Override
	public String getModID() {
		return Constants.CORE_MOD_ID;
	}

	@Override
	public IPacketID[] getPacketIDs() {
		return BinnieCorePacketID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[]{ConfigurationMain.class};
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	//TODO RENDERING
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleTextureRemap(final TextureStitchEvent.Pre event) {
		/*if (event.map.getTextureType() == 0) {
			Binnie.Liquid.reloadIcons(event.map);
		}*/
		Binnie.RESOURCE.registerSprites();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleModelBake(ModelBakeEvent event) {
		ModelManager.registerCustomModels(event);
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(BinnieCore.getInstance());
		}
	}
}
