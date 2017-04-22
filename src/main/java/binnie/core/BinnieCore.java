package binnie.core;

import binnie.*;
import binnie.core.block.*;
import binnie.core.gui.*;
import binnie.core.item.*;
import binnie.core.liquid.*;
import binnie.core.machines.*;
import binnie.core.machines.storage.*;
import binnie.core.mod.config.*;
import binnie.core.mod.parser.*;
import binnie.core.network.*;
import binnie.core.proxy.*;
import binnie.core.triggers.*;
import binnie.craftgui.minecraft.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.*;
import forestry.api.core.*;
import forestry.plugins.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraftforge.client.event.*;

import java.util.*;

@Mod(modid = "BinnieCore", name = "Binnie Core", useMetadata = true)
public final class BinnieCore extends AbstractMod {
	@Mod.Instance("BinnieCore")
	public static BinnieCore instance;
	@SidedProxy(clientSide = "binnie.core.proxy.BinnieProxyClient", serverSide = "binnie.core.proxy.BinnieProxyServer")
	public static BinnieProxy proxy;
	public static int multipassRenderID;
	private static List<AbstractMod> modList = new ArrayList<>();
	public static MachineGroup packageCompartment;
	public static ItemGenesis genesis;
	public static ItemFieldKit fieldKit;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		Binnie.Configuration.registerConfiguration(ConfigurationMods.class, this);
		for (ManagerBase baseManager : Binnie.Managers) {
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
	public void init(FMLInitializationEvent evt) {
		init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		postInit();
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return BinnieCoreGUI.values();
	}

	@Override
	public void preInit() {
		BinnieCore.instance = this;
		for (FluidContainer container : FluidContainer.values()) {
			Item item = new ItemFluidContainer(container);
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
		FieldParser.parsers.add(new ItemParser());
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
		for (AbstractMod mod : getActiveMods()) {
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

	static void registerMod(AbstractMod mod) {
		BinnieCore.modList.add(mod);
	}

	private static List<AbstractMod> getActiveMods() {
		List<AbstractMod> list = new ArrayList<>();
		for (AbstractMod mod : BinnieCore.modList) {
			if (mod.isActive()) {
				list.add(mod);
			}
		}
		return list;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleSpeciesDiscovered(ForestryEvent.SpeciesDiscovered event) {
		try {
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.username.getName());
			if (player == null) {
				return;
			}
			event.tracker.synchToPlayer(player);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("species", event.species.getUID());
		} catch (Exception ex) {
			// ignored
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
	public void handleTextureRemap(TextureStitchEvent.Pre event) {
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

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(BinnieCore.instance);
		}
	}
}
