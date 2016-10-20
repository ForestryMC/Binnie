package binnie.core.machines.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public interface INetwork {
    public interface SendGuiNBT {
        void sendGuiNBT(final Map<String, NBTTagCompound> p0);
    }

    public interface TilePacketSync {
        void syncToNBT(final NBTTagCompound p0);

        void syncFromNBT(final NBTTagCompound p0);
    }

    public interface RecieveGuiNBT {
        void recieveGuiNBT(final Side p0, final EntityPlayer p1, final String p2, final NBTTagCompound p3);
    }

    public interface GuiNBT extends RecieveGuiNBT, SendGuiNBT {
    }
}
