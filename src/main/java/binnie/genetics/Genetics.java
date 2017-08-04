package binnie.genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import forestry.api.recipes.RecipeManagers;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.item.ItemBeeDictionary;
import binnie.genetics.item.ItemPunnettSquare;
import binnie.genetics.item.ModuleItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.proxy.Proxy;

@Mod(
	modid = Constants.GENETICS_MOD_ID,
	name = "Binnie's Genetics",
	dependencies = "required-after:" + Constants.CORE_MOD_ID
)
public class Genetics extends AbstractMod {
	public static final String CHANNEL = "GEN";
	@Mod.Instance(Constants.GENETICS_MOD_ID)
	public static Genetics instance;
	@SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
	public static Proxy proxy;
	@Nullable
	private static ModuleItems items;
	@Nullable
	private static ModuleMachine machine;

	private static Item dictionaryBees;

	public static ModuleItems items() {
		Preconditions.checkState(items != null);
		return items;
	}

	public static ModuleMachine machine() {
		Preconditions.checkState(machine != null);
		return machine;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		dictionaryBees = proxy.registerItem(new ItemBeeDictionary());
		proxy.registerItem(new ItemPunnettSquare());
		preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		postInit();
		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000),
			ItemStack.EMPTY,
			new ItemStack(dictionaryBees),
			"X#X", "YEY", "RDR",
			'#', Blocks.GLASS_PANE,
			'X', Items.GOLD_INGOT,
			'Y', "ingotTin",
			'R', Items.REDSTONE,
			'D', Items.DIAMOND,
			'E', Items.EMERALD
		);
	}

	@Override
	protected void registerModules() {
		addModule(items = new ModuleItems());
		addModule(machine = new ModuleMachine());
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return GeneticsGUI.values();
	}

	@Override
	public IPacketID[] getPacketIDs() {
		return GeneticsPacket.values();
	}

	@Override
	public String getChannel() {
		return CHANNEL;
	}

	@Override
	public IProxyCore getProxy() {
		return Genetics.proxy;
	}

	@Override
	public String getModID() {
		return Constants.GENETICS_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isAvailable() {
		return BinnieCore.isGeneticsActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Genetics.instance);
		}
	}
}
