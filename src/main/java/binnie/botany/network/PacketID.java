package binnie.botany.network;

import binnie.Binnie;
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
    FIELDKIT;

    @Override
    public void onMessage(MessageBinnie message, MessageContext context) {
        if (this == PacketID.FIELDKIT && context.side == Side.CLIENT) {
            MessageNBT packet = new MessageNBT(message);
            NBTTagCompound data = packet.getTagCompound();
            EntityPlayer player = BinnieCore.proxy.getPlayer();
            String info = "";
            if (data.hasNoTags()) {
                info += Binnie.Language.localise("botany.flowers.species.not.discover");
            } else {
                IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species"));
                IAlleleFlowerSpecies secondary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species2"));
                float age = data.getFloat("Age");
                EnumFlowerColor color1 = EnumFlowerColor.get(data.getShort("Colour"));
                EnumFlowerColor color2 = EnumFlowerColor.get(data.getShort("Colour2"));
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
                    info = info + " " + primary.getName() + "-" + secondary.getName() + " " + Binnie.Language.localise("botany.flowers.species.hybrid");
                }
                if (age == 0.0f) {
                    info += " " + Binnie.Language.localise("botany.flowers.species.germling");
                }
                if (data.getBoolean("Wilting")) {
                    info += ". " + Binnie.Language.localise("botany.flowers.species.wilting");
                }
            }
            player.addChatMessage(new TextComponentString(info));
        }
    }
}
