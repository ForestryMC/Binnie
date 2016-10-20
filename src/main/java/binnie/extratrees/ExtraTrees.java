// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees;

import binnie.extrabees.ExtraBees;
import binnie.core.BinnieCore;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.core.gui.IBinnieGUID;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import binnie.extratrees.core.ModuleCore;
import binnie.extratrees.machines.ModuleMachine;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.genetics.ModuleGenetics;
import binnie.extratrees.alcohol.ModuleAlcohol;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.block.ModuleBlocks;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import binnie.extratrees.carpentry.BlockStainedDesign;
import binnie.extratrees.block.decor.BlockHedge;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.carpentry.BlockCarpentry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import cpw.mods.fml.common.SidedProxy;
import binnie.extratrees.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import binnie.core.AbstractMod;

@Mod(modid = "ExtraTrees", name = "Extra Trees", useMetadata = true, dependencies = "after:BinnieCore")
public class ExtraTrees extends AbstractMod
{
	@Mod.Instance("ExtraTrees")
	public static ExtraTrees instance;
	@SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
	public static Proxy proxy;
	public static Item itemDictionary;
	public static Item itemDictionaryLepi;
	public static Item itemMisc;
	public static Item itemFood;
	public static Block blockStairs;
	public static Block blockLog;
	public static BlockCarpentry blockCarpentry;
	public static Block blockPlanks;
	public static Block blockMachine;
	public static Block blockFence;
	public static BlockCarpentry blockPanel;
	public static Block blockKitchen;
	public static Block blockSlab;
	public static Block blockDoubleSlab;
	public static Item itemHammer;
	public static Item itemDurableHammer;
	public static Block blockGate;
	public static Block blockDoor;
	public static Block blockBranch;
	public static ItemDrink drink;
	public static BlockMultiFence blockMultiFence;
	public static Block blockDrink;
	public static BlockHedge blockHedge;
	public static BlockStainedDesign blockStained;
	public static int fruitPodRenderId;
	public static int doorRenderId;
	public static int branchRenderId;
	public static int fenceID;
	public static int stairsID;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		this.addModule(new ModuleBlocks());
		this.addModule(new ModuleItems());
		this.addModule(new ModuleAlcohol());
		this.addModule(new ModuleGenetics());
		this.addModule(new ModuleCarpentry());
		this.addModule(new ModuleMachine());
		this.addModule(new ModuleCore());
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

	public ExtraTrees() {
		ExtraTrees.instance = this;
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraTreesGUID.values();
	}

	@Override
	public Class[] getConfigs() {
		return new Class[] { ConfigurationMain.class };
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
	public String getModID() {
		return "extratrees";
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isExtraTreesActive();
	}

	public static class PacketHandler extends BinniePacketHandler
	{
		public PacketHandler() {
			super(ExtraBees.instance);
		}
	}
}
