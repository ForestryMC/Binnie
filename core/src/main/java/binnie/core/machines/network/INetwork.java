package binnie.core.machines.network;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface INetwork {
	interface SendGuiNBT {
		void sendGuiNBTToClient(Map<String, NBTTagCompound> data);
	}

	interface TilePacketSync {
		void syncToNBT(NBTTagCompound nbt);

		void syncFromNBT(NBTTagCompound nbt);
	}

	interface ReceiveGuiNBT {
		void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt);

		@SideOnly(Side.CLIENT)
		void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt);
	}

	interface GuiNBT extends ReceiveGuiNBT, SendGuiNBT {
	}
}
