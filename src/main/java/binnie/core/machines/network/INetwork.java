package binnie.core.machines.network;

import cpw.mods.fml.relauncher.Side;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface INetwork {
    interface SendGuiNBT {
        void sendGuiNBT(Map<String, NBTTagCompound> nbts);
    }

    interface TilePacketSync {
        void syncToNBT(NBTTagCompound nbt);

        void syncFromNBT(NBTTagCompound nbt);
    }

    interface RecieveGuiNBT {
        void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt);
    }

    interface GuiNBT extends RecieveGuiNBT, SendGuiNBT {}
}
