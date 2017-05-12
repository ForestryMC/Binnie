package binnie.extrabees.client;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 12-5-2017.
 */
public class GuiHack extends AbstractMod {

	public static final GuiHack INSTANCE;

	private GuiHack(){
		proxy = new IProxyCore(){

			@Override
			public void setMod(AbstractMod mod) {

			}

			@Override
			public Item registerItem(Item item) {
				return ExtraBees.proxy.registerItem(item);
			}

			@Override
			public Block registerBlock(Block block) {
				return ExtraBees.proxy.registerBlock(block);
			}

			@Override
			public <B extends Block> void registerBlock(B block, ItemBlock itemBlock) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void registerModels() {

			}

			@Override
			public void registerItemAndBlockColors() {

			}

			@Override
			public void preInit() {

			}

			@Override
			public void init() {

			}

			@Override
			public void postInit() {

			}
		};
	}

	private IProxyCore proxy;

	public void openGui(final IBinnieGUID ID, final EntityPlayer player, final BlockPos pos) {
		BinnieCore.getBinnieProxy().openGui(this, ID.ordinal(), player, pos);
	}


	@Override
	protected void registerModules() {
		addModule(new ModuleCore());
		addModule(new ModuleApiary());
	}

	@Override
	@Nonnull
	public Object getMod() {
		return ExtraBees.instance;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public String getChannel() {
		return "EB";
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraBeeGUID.values();
	}

	@Override
	public IProxyCore getProxy() {
		return proxy;
	}

	@Override
	public String getModID() {
		return ExtraBees.MODID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(INSTANCE);
		}
	}

	static {
		INSTANCE = new GuiHack();
	}

}
