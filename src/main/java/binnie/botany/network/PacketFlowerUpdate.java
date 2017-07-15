package binnie.botany.network;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;

import binnie.botany.flower.TileEntityFlower;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;

// TODO unused class?
public class PacketFlowerUpdate extends SPacketUpdateTileEntity {
	public TileEntityFlower.RenderInfo render;

	public PacketFlowerUpdate(BlockPos blockPos, int metadata, NBTTagCompound compound, TileEntityFlower.RenderInfo render) {
		super(blockPos, metadata, compound);
		this.render = render;
	}

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		super.readPacketData(buf);
		render = new TileEntityFlower.RenderInfo();
		render.primary = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		render.secondary = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		render.stem = EnumFlowerColor.values()[buf.readByte()].getFlowerColorAllele();
		render.type = EnumFlowerType.values()[buf.readByte()];
		render.age = buf.readByte();
		render.section = buf.readByte();
		render.wilted = buf.readBoolean();
		render.flowered = buf.readBoolean();
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		super.writePacketData(buf);
		buf.writeByte(render.primary.getID());
		buf.writeByte(render.secondary.getID());
		buf.writeByte(render.stem.getID());
		buf.writeByte(render.type.ordinal());
		buf.writeByte(render.age);
		buf.writeByte(render.section);
		buf.writeBoolean(render.wilted);
		buf.writeBoolean(render.flowered);
	}
}
