// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core;

import binnie.Binnie;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.gui.BinnieGUIHandler;
import binnie.core.gui.IBinnieGUID;
import binnie.core.item.ItemFieldKit;
import binnie.core.item.ItemGenesis;
import binnie.core.item.ModuleItems;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.storage.ModuleStorage;
import binnie.core.mod.config.ConfigurationMain;
import binnie.core.mod.config.ConfigurationMods;
import binnie.core.mod.parser.FieldParser;
import binnie.core.mod.parser.ItemParser;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.BinnieProxy;
import binnie.core.proxy.IBinnieProxy;
import binnie.core.triggers.ModuleTrigger;
import binnie.craftgui.minecraft.ModuleCraftGUI;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.ForestryEvent;
import forestry.plugins.PluginManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = "BinnieCore", name = "Binnie Core", useMetadata = true)
public final class BinnieCore extends AbstractMod {
	@Mod.Instance("BinnieCore")
	public static BinnieCore instance;
	@SidedProxy(clientSide = "binnie.core.proxy.BinnieProxyClient", serverSide = "binnie.core.proxy.BinnieProxyServer")
	public static BinnieProxy proxy;
	public static int multipassRenderID;
	private static List<AbstractMod> modList;
	public static MachineGroup packageCompartment;
	public static ItemGenesis genesis;
	public static ItemFieldKit fieldKit;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		Binnie.Configuration.registerConfiguration(ConfigurationMods.class, this);
		for (final ManagerBase baseManager : Binnie.Managers) {
			addModule(baseManager);
		}

		addModule(new ModuleCraftGUI());
		addModule(new ModuleStorage());
		addModule(new ModuleItems());
		if (Loader.isModLoaded("BuildCraft|Silicon")) {
			addModule(new ModuleTrigger());
		}
		preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return BinnieCoreGUI.values();
	}

	@Override
	public void preInit() {
		BinnieCore.instance = this;
		for (final FluidContainer container : FluidContainer.values()) {
			final Item item = new ItemFluidContainer(container);
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
		FieldParser.parsers.add(new ItemParser());
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
		for (final AbstractMod mod : getActiveMods()) {
			NetworkRegistry.INSTANCE.registerGuiHandler(mod, new BinnieGUIHandler(mod));
		}

		BinnieCore.multipassRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new MultipassBlockRenderer());
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "binnie.tile.metadata");
	}

	public static boolean isLepidopteryActive() {
		return PluginManager.Module.LEPIDOPTEROLOGY.isEnabled();
	}

	public static boolean isApicultureActive() {
		return PluginManager.Module.APICULTURE.isEnabled();
	}

	public static boolean isArboricultureActive() {
		return PluginManager.Module.ARBORICULTURE.isEnabled();
	}

	public static boolean isBotanyActive() {
		return ConfigurationMods.botany;
	}

	public static boolean isGeneticsActive() {
		return ConfigurationMods.genetics;
	}

	public static boolean isExtraBeesActive() {
		return ConfigurationMods.extraBees && isApicultureActive();
	}

	public static boolean isExtraTreesActive() {
		return ConfigurationMods.extraTrees && isArboricultureActive();
	}

	@Override
	public void postInit() {
		super.postInit();
	}

	static void registerMod(final AbstractMod mod) {
		BinnieCore.modList.add(mod);
	}

	private static List<AbstractMod> getActiveMods() {
		final List<AbstractMod> list = new ArrayList<AbstractMod>();
		for (final AbstractMod mod : BinnieCore.modList) {
			if (mod.isActive()) {
				list.add(mod);
			}
		}
		return list;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleSpeciesDiscovered(final ForestryEvent.SpeciesDiscovered event) {
		try {
			final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.username.getName());
			if (player == null) {
				return;
			}
			event.tracker.synchToPlayer(player);
			final NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("species", event.species.getUID());
		} catch (Exception ex) {
		}
	}

	@Override
	public String getChannel() {
		return "BIN";
	}

	@Override
	public IBinnieProxy getProxy() {
		return BinnieCore.proxy;
	}

	@Override
	public String getModID() {
		return "binniecore";
	}

	@Override
	public IPacketID[] getPacketIDs() {
		return BinnieCorePacketID.values();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleTextureRemap(final TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0) {
			Binnie.Liquid.reloadIcons(event.map);
		}
		Binnie.Resource.registerIcons(event.map, event.map.getTextureType());
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
	public boolean isActive() {
		return true;
	}

	static {
		BinnieCore.modList = new ArrayList<AbstractMod>();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(BinnieCore.instance);
		}
	}
}
