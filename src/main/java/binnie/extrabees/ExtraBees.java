// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees;

import binnie.core.BinnieCore;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.config.ConfigurationMachines;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.core.gui.IBinnieGUID;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.worldgen.ModuleGeneration;
import binnie.extrabees.genetics.ModuleGenetics;
import binnie.extrabees.products.ModuleProducts;
import binnie.extrabees.core.ModuleCore;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

import cpw.mods.fml.common.SidedProxy;

import binnie.extrabees.proxy.ExtraBeesProxy;

import cpw.mods.fml.common.Mod;

import binnie.core.AbstractMod;

@Mod(modid = "ExtraBees", name = "Extra Bees", useMetadata = true, dependencies = "after:BinnieCore")
public class ExtraBees extends AbstractMod
{
	@Mod.Instance("ExtraBees")
	public static ExtraBees instance;
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
		this.addModule(new ModuleCore());
		this.addModule(new ModuleProducts());
		this.addModule(new ModuleGenetics());
		this.addModule(new ModuleGeneration());
		this.addModule(new ModuleLiquids());
		this.addModule(new ModuleApiary());
		this.preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	public ExtraBees() {
		ExtraBees.instance = this;
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraBeeGUID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[] { ConfigurationMain.class, ConfigurationMachines.class };
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
		return "extrabees";
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isExtraBeesActive();
	}

	public static class PacketHandler extends BinniePacketHandler
	{
		public PacketHandler() {
			super(ExtraBees.instance);
		}
	}
}
