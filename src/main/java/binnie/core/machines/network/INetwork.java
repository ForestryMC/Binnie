package binnie.core.machines.network;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface INetwork {
	interface SendGuiNBT {
		void sendGuiNBTToClient(final Map<String, NBTTagCompound> p0);
	}

	interface TilePacketSync {
		void syncToNBT(final NBTTagCompound p0);

		void syncFromNBT(final NBTTagCompound p0);
	}

	interface ReceiveGuiNBT {
		void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt);

		@SideOnly(Side.CLIENT)
		void receiveGuiNBTOnClient(final EntityPlayer player, final String name, final NBTTagCompound nbt);
	}

	interface GuiNBT extends ReceiveGuiNBT, SendGuiNBT {
	}
}
