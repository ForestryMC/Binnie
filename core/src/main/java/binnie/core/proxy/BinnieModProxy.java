package binnie.core.proxy;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;

public class BinnieModProxy implements IBinnieModProxy {
	@Nullable
	private AbstractMod mod;

	public BinnieModProxy() {
	}

	private AbstractMod getMod() {
		Preconditions.checkState(mod != null, "Mod has not been set");
		return mod;
	}

	@Override
	public void setMod(final AbstractMod mod) {
		this.mod = mod;
	}

	@Override
	public Item registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	@Override
	public Block registerBlock(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		return block;
	}

	@Override
	public <T extends Block> void registerBlock(T block, ItemBlock itemBlock) {
		registerBlock(block);

		if (itemBlock.getRegistryName() == null) {
			itemBlock.setRegistryName(block.getRegistryName());
		}
		registerItem(itemBlock);
	}

	@Override
	public void registerModels() {
	}

	@Override
	public void registerItemAndBlockColors() {
	}

	@Override
	public void openGui(final IBinnieGUID ID, final EntityPlayer player, final BlockPos pos) {
		BinnieCore.getBinnieProxy().openGui(this.getMod(), ID.ordinal(), player, pos);
	}

	@Override
	public void sendToAll(final MessageBase packet) {
		this.getMod().getNetworkWrapper().sendToAll(packet.GetMessage());
	}

	@Override
	public void sendToPlayer(final MessageBase packet, final EntityPlayer entityplayer) {
		if (entityplayer instanceof EntityPlayerMP) {
			this.getMod().getNetworkWrapper().sendTo(packet.GetMessage(), (EntityPlayerMP) entityplayer);
		}
	}

	@Override
	public void sendToServer(final MessageBase packet) {
		this.getMod().getNetworkWrapper().sendToServer(packet.GetMessage());
	}

	public void registerModel(Item item, int meta, ModelResourceLocation modelResourceLocation) {

	}

	/*@Override
	public IIcon getIcon(final IIconRegister register, final String string) {
		return BinnieCore.proxy.getIcon(register, this.mod.getModID(), string);
	}*/

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
