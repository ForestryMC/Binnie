// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import binnie.botany.flower.TileEntityFlower;
import net.minecraft.util.ChatComponentText;
import binnie.botany.genetics.EnumFlowerColor;
import forestry.api.genetics.AlleleManager;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.core.BinnieCore;
import binnie.core.network.packet.MessageNBT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.IPacketID;

public enum PacketID implements IPacketID
{
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
			}
			else {
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
				}
				else if (age < 0.25f) {
					info += " Young";
				}
				else if (age < 0.75f) {
					info += " Mature";
				}
				else {
					info += " Old";
				}
				if (color1 == color2) {
					info = info + " " + color1.getName();
				}
				else {
					info = info + " " + color1.getName() + " & " + color2.getName();
				}
				if (primary == secondary) {
					info = info + " " + primary.getName();
				}
				else {
					info = info + " " + primary.getName() + "-" + secondary.getName() + " Hybrid";
				}
				if (age == 0.0f) {
					info += " Germling";
				}
				if (data.getBoolean("Wilting")) {
					info += ". Shame it is Wilting!";
				}
			}
			final IChatComponent chat = new ChatComponentText(info);
			player.addChatMessage(chat);
		}
		else if (this == PacketID.FlowerUpdate) {
			final MessageFlowerUpdate packet2 = new MessageFlowerUpdate(message);
			final TileEntity tile = packet2.getTileEntity(BinnieCore.proxy.getWorld());
			if (tile instanceof TileEntityFlower) {
				((TileEntityFlower) tile).setRender(packet2.render);
			}
		}
	}
}
