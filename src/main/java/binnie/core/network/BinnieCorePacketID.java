package binnie.core.network;

import binnie.core.*;
import binnie.core.block.*;
import binnie.core.machines.*;
import binnie.core.machines.network.*;
import binnie.core.network.packet.*;
import binnie.craftgui.minecraft.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.tileentity.*;

public enum BinnieCorePacketID implements IPacketID {
	NetworkEntityUpdate,
	TileMetadata,
	CraftGUIAction,
	TileDescriptionSync;

	@Override
	public void onMessage(MessageBinnie message, MessageContext context) {
		if (this == BinnieCorePacketID.NetworkEntityUpdate) {
			MessageUpdate packet = new MessageUpdate(message);
			TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());
			if (tile instanceof INetworkedEntity) {
				((INetworkedEntity) tile).readFromPacket(packet.payload);
			}
		} else if (this == BinnieCorePacketID.TileMetadata) {
			MessageMetadata packet2 = new MessageMetadata(message);
			TileEntity tile = packet2.getTileEntity(BinnieCore.proxy.getWorld());
			if (tile instanceof TileEntityMetadata) {
				((TileEntityMetadata) tile).setTileMetadata(packet2.meta, true);
			}
		} else if (this == BinnieCorePacketID.CraftGUIAction && context.side == Side.CLIENT) {
			MessageCraftGUI packet3 = new MessageCraftGUI(message);
			EntityPlayer player = BinnieCore.proxy.getPlayer();
			if (player.openContainer instanceof ContainerCraftGUI && packet3.getTagCompound() != null) {
				((ContainerCraftGUI) player.openContainer).recieveNBT(Side.CLIENT, player, packet3.getTagCompound());
			}
		} else if (this == BinnieCorePacketID.CraftGUIAction && context.side == Side.SERVER && context.netHandler instanceof NetHandlerPlayServer) {
			MessageCraftGUI packet3 = new MessageCraftGUI(message);
			EntityPlayer player = ((NetHandlerPlayServer) context.netHandler).playerEntity;
			if (player.openContainer instanceof ContainerCraftGUI && packet3.getTagCompound() != null) {
				((ContainerCraftGUI) player.openContainer).recieveNBT(Side.SERVER, player, packet3.getTagCompound());
			}
		} else if (this == BinnieCorePacketID.TileDescriptionSync && context.side == Side.CLIENT) {
			MessageTileNBT packet4 = new MessageTileNBT(message);
			TileEntity tile = packet4.getTarget(BinnieCore.proxy.getWorld());
			if (tile != null && packet4.getTagCompound() != null) {
				IMachine machine = Machine.getMachine(tile);
				if (machine != null && machine instanceof INetwork.TilePacketSync) {
					((INetwork.TilePacketSync) machine).syncFromNBT(packet4.getTagCompound());
				}
			}
		}
	}
}
