package binnie.core.machines.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public interface INetwork {
	interface SendGuiNBT {
		void sendGuiNBTToClient(final Map<String, NBTTagCompound> data);
	}

	interface TilePacketSync {
		void syncToNBT(NBTTagCompound nbt);

		void syncFromNBT(NBTTagCompound nbt);
	}

	interface ReceiveGuiNBT {
		void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt);

		@SideOnly(Side.CLIENT)
		void receiveGuiNBTOnClient(final EntityPlayer player, final String name, final NBTTagCompound nbt);
	}

	interface GuiNBT extends ReceiveGuiNBT, SendGuiNBT {
	}
}
