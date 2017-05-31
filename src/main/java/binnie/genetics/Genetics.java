package binnie.genetics;

import binnie.Binnie;
import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
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
import com.google.common.base.Preconditions;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;

@Mod(modid = Constants.GENETICS_MOD_ID, name = "Binnie's Genetics", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
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
	public void preInit(final FMLPreInitializationEvent evt) {
		proxy.registerItem(dictionaryBees = new ItemBeeDictionary());
		proxy.registerItem(new ItemPunnettSquare());
		this.preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("water", 2000), ItemStack.EMPTY, new ItemStack(dictionaryBees), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotTin", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	@Override
	protected void registerModules() {
		this.addModule(items = new ModuleItems());
		this.addModule(machine = new ModuleMachine());
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
	public boolean isActive() {
		return BinnieCore.isGeneticsActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Genetics.instance);
		}
	}
}
