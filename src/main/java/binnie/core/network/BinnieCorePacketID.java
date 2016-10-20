// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.network;

import binnie.core.machines.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.Machine;
import binnie.core.network.packet.MessageTileNBT;
import net.minecraft.network.NetHandlerPlayServer;
import binnie.craftgui.minecraft.ContainerCraftGUI;
import binnie.core.network.packet.MessageCraftGUI;
import cpw.mods.fml.relauncher.Side;
import binnie.core.block.TileEntityMetadata;
import binnie.core.network.packet.MessageMetadata;
import binnie.core.BinnieCore;
import binnie.core.network.packet.MessageUpdate;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import binnie.core.network.packet.MessageBinnie;

public enum BinnieCorePacketID implements IPacketID
{
	NetworkEntityUpdate,
	TileMetadata,
	CraftGUIAction,
	TileDescriptionSync;

	@Override
	public void onMessage(final MessageBinnie message, final MessageContext context) {
		if (this == BinnieCorePacketID.NetworkEntityUpdate) {
			final MessageUpdate packet = new MessageUpdate(message);
			final TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());
			if (tile instanceof INetworkedEntity) {
				((INetworkedEntity) tile).readFromPacket(packet.payload);
			}
		}
		else if (this == BinnieCorePacketID.TileMetadata) {
			final MessageMetadata packet2 = new MessageMetadata(message);
			final TileEntity tile = packet2.getTileEntity(BinnieCore.proxy.getWorld());
			if (tile instanceof TileEntityMetadata) {
				((TileEntityMetadata) tile).setTileMetadata(packet2.meta, true);
			}
		}
		else if (this == BinnieCorePacketID.CraftGUIAction && context.side == Side.CLIENT) {
			final MessageCraftGUI packet3 = new MessageCraftGUI(message);
			final EntityPlayer player = BinnieCore.proxy.getPlayer();
			if (player.openContainer instanceof ContainerCraftGUI && packet3.getTagCompound() != null) {
				((ContainerCraftGUI) player.openContainer).recieveNBT(Side.CLIENT, player, packet3.getTagCompound());
			}
		}
		else if (this == BinnieCorePacketID.CraftGUIAction && context.side == Side.SERVER && context.netHandler instanceof NetHandlerPlayServer) {
			final MessageCraftGUI packet3 = new MessageCraftGUI(message);
			final EntityPlayer player = ((NetHandlerPlayServer) context.netHandler).playerEntity;
			if (player.openContainer instanceof ContainerCraftGUI && packet3.getTagCompound() != null) {
				((ContainerCraftGUI) player.openContainer).recieveNBT(Side.SERVER, player, packet3.getTagCompound());
			}
		}
		else if (this == BinnieCorePacketID.TileDescriptionSync && context.side == Side.CLIENT) {
			final MessageTileNBT packet4 = new MessageTileNBT(message);
			final TileEntity tile = packet4.getTarget(BinnieCore.proxy.getWorld());
			if (tile != null && packet4.getTagCompound() != null) {
				final IMachine machine = Machine.getMachine(tile);
				if (machine != null && machine instanceof INetwork.TilePacketSync) {
					((INetwork.TilePacketSync) machine).syncFromNBT(packet4.getTagCompound());
				}
			}
		}
	}
}
