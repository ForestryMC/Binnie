package binnie.botany.tile;

import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerColor;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.genetics.EnumFlowerType;
import forestry.api.core.INbtWritable;
import net.minecraft.nbt.NBTTagCompound;

public class FlowerRenderInfo implements INbtWritable {
	private final IFlowerColor primary;
	private final IFlowerColor secondary;
	private final IFlowerColor stem;
	private final IFlowerType type;
	private final byte age;
	private final boolean wilted;
	private final boolean flowered;
	private final byte section;

	public FlowerRenderInfo(IFlower flower, TileEntityFlower tile) {
		this.section = (byte) tile.getSection();
		this.primary = flower.getGenome().getPrimaryColor();
		this.secondary = flower.getGenome().getSecondaryColor();
		this.stem = flower.getGenome().getStemColor();
		this.age = (byte) flower.getAge();
		this.wilted = flower.isWilted();
		this.flowered = flower.hasFlowered();
		this.type = flower.getGenome().getType();
	}

	public FlowerRenderInfo(NBTTagCompound nbt) {
		this.primary = EnumFlowerColor.values()[nbt.getByte("primary")].getFlowerColorAllele();
		this.secondary = EnumFlowerColor.values()[nbt.getByte("secondary")].getFlowerColorAllele();
		this.stem = EnumFlowerColor.values()[nbt.getByte("stem")].getFlowerColorAllele();
		this.type = EnumFlowerType.values()[nbt.getByte("type")];
		this.age = nbt.getByte("age");
		this.section = nbt.getByte("section");
		this.wilted = nbt.getBoolean("wilted");
		this.flowered = nbt.getBoolean("flowered");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FlowerRenderInfo) {
			FlowerRenderInfo o = (FlowerRenderInfo) obj;
			return o.age == age && o.wilted == wilted && o.flowered == flowered && o.primary == primary && o.secondary == secondary && o.stem == stem && o.type == type;
		}
		return super.equals(obj);
	}

	public IFlowerColor getPrimary() {
		return primary;
	}

	public IFlowerColor getSecondary() {
		return secondary;
	}

	public IFlowerColor getStem() {
		return stem;
	}

	public IFlowerType getType() {
		return type;
	}

	public byte getAge() {
		return age;
	}

	public boolean isWilted() {
		return wilted;
	}

	public boolean isFlowered() {
		return flowered;
	}

	public byte getSection() {
		return section;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setByte("primary", (byte) primary.getID());
		nbt.setByte("secondary", (byte) secondary.getID());
		nbt.setByte("stem", (byte) stem.getID());
		nbt.setByte("type", (byte) type.ordinal());
		nbt.setByte("age", age);
		nbt.setByte("section", section);
		nbt.setBoolean("wilted", wilted);
		nbt.setBoolean("flowered", flowered);
		return nbt;
	}
}
