package binnie.botany.network;

import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import forestry.api.genetics.AlleleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public enum PacketID implements IPacketID {
    Encylopedia,
    FlowerUpdate;

    @Override
    public void onMessage(final MessageBinnie message, final MessageContext context) {
        if (this == PacketID.Encylopedia && context.side == Side.CLIENT) {
            final MessageNBT packet = new MessageNBT(message);
            final NBTTagCompound data = packet.getTagCompound();
            final EntityPlayer player = BinnieCore.proxy.getPlayer();
            String info = "";
            if (data.hasNoTags()) {
                info += "Flower has not been discovered by you. Breed this flower yourself to discover.";
            } else {
                final IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species"));
                final IAlleleFlowerSpecies secondary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species2"));
                final float age = data.getFloat("Age");
                final EnumFlowerColor color1 = EnumFlowerColor.get(data.getShort("Colour"));
                final EnumFlowerColor color2 = EnumFlowerColor.get(data.getShort("Colour2"));
                if (primary == null || secondary == null) {
                    return;
                }
                info += "A";
                if (age == 0.0f) {
                    info += "";
                } else if (age < 0.25f) {
                    info += " Young";
                } else if (age < 0.75f) {
                    info += " Mature";
                } else {
                    info += " Old";
                }
                if (color1 == color2) {
                    info = info + " " + color1.getName();
                } else {
                    info = info + " " + color1.getName() + " & " + color2.getName();
                }
                if (primary == secondary) {
                    info = info + " " + primary.getName();
                } else {
                    info = info + " " + primary.getName() + "-" + secondary.getName() + " Hybrid";
                }
                if (age == 0.0f) {
                    info += " Germling";
                }
                if (data.getBoolean("Wilting")) {
                    info += ". Shame it is Wilting!";
                }
            }
            player.addChatMessage(new TextComponentString(info));
        } else if (this == PacketID.FlowerUpdate) {
            final MessageFlowerUpdate packet2 = new MessageFlowerUpdate(message);
            final TileEntity tile = packet2.getTileEntity(BinnieCore.proxy.getWorld());
            if (tile instanceof TileEntityFlower) {
                ((TileEntityFlower) tile).setRender(packet2.render);
            }
        }
    }
}
