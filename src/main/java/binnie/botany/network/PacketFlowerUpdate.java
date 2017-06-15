package binnie.botany.network;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;

import binnie.botany.flower.TileEntityFlower;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;

public class PacketFlowerUpdate extends SPacketUpdateTileEntity {
	public TileEntityFlower.RenderInfo render;

	public PacketFlowerUpdate(BlockPos blockPos, int metadata, NBTTagCompound compound, final TileEntityFlower.RenderInfo render) {
		super(blockPos, metadata, compound);
		this.render = render;
	}

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		super.readPacketData(buf);
		this.render = new TileEntityFlower.RenderInfo();
		this.render.primary = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		this.render.secondary = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		this.render.stem = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		this.render.type = EnumFlowerType.values()[buf.readByte()];
		this.render.age = buf.readByte();
		this.render.section = buf.readByte();
		this.render.wilted = buf.readBoolean();
		this.render.flowered = buf.readBoolean();
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		super.writePacketData(buf);
		buf.writeByte(this.render.primary.getID());
		buf.writeByte(this.render.secondary.getID());
		buf.writeByte(this.render.stem.getID());
		buf.writeByte(this.render.type.ordinal());
		buf.writeByte(this.render.age);
		buf.writeByte(this.render.section);
		buf.writeBoolean(this.render.wilted);
		buf.writeBoolean(this.render.flowered);
	}
}
