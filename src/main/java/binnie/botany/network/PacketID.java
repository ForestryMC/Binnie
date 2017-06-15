package binnie.botany.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import forestry.api.genetics.AlleleManager;

import binnie.Binnie;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;

public enum PacketID implements IPacketID {
	FIELDKIT;

	@Override
	public void onMessage(MessageBinnie message, MessageContext context) {
		if (this == PacketID.FIELDKIT && context.side == Side.CLIENT) {
			MessageNBT packet = new MessageNBT(message);
			onUseFieldKit(packet.getTagCompound());
		}
	}

	public void onUseFieldKit(NBTTagCompound data) {
		EntityPlayer player = BinnieCore.getBinnieProxy().getPlayer();
		String info = "";
		if (data == null || data.hasNoTags()) {
			info += Binnie.LANGUAGE.localise("botany.flowers.species.not.discover");
		} else {
			IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species"));
			IAlleleFlowerSpecies secondary = (IAlleleFlowerSpecies) AlleleManager.alleleRegistry.getAllele(data.getString("Species2"));
			float age = data.getFloat("Age");
			EnumFlowerColor color1 = EnumFlowerColor.get(data.getShort("Colour"));
			EnumFlowerColor color2 = EnumFlowerColor.get(data.getShort("Colour2"));
			if (primary == null || secondary == null) {
				return;
			}
			info += Binnie.LANGUAGE.localise("botany.flowers.fielkit.a");
			if (age == 0.0f) {
				info += "";
			} else if (age < 0.25f) {
				info += " " + Binnie.LANGUAGE.localise("botany.flowers.fielkit.young");
			} else if (age < 0.75f) {
				info += " " + Binnie.LANGUAGE.localise("botany.flowers.fielkit.mature");
			} else {
				info += " " + Binnie.LANGUAGE.localise("botany.flowers.fielkit.old");
			}
			if (color1 == color2) {
				info = info + " " + color1.getName();
			} else {
				info = info + " " + color1.getName() + " & " + color2.getName();
			}
			if (primary == secondary) {
				info = info + " " + primary.getName();
			} else {
				info = info + " " + primary.getName() + "-" + secondary.getName() + " " + Binnie.LANGUAGE.localise("botany.flowers.species.hybrid");
			}
			if (age == 0.0f) {
				info += " " + Binnie.LANGUAGE.localise("botany.flowers.species.germling");
			}
			if (data.getBoolean("Wilting")) {
				info += ". " + Binnie.LANGUAGE.localise("botany.flowers.species.wilting");
			}
		}
		player.sendStatusMessage(new TextComponentString(info), false);
	}
}
