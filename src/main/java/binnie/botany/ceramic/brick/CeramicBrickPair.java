package binnie.botany.ceramic.brick;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public class CeramicBrickPair {
	EnumFlowerColor colorFirst;
	EnumFlowerColor colorSecond;
	CeramicBrickType type;
	int ordinal;

	CeramicBrickPair(final EnumFlowerColor colorFirst, final EnumFlowerColor colorSecond, CeramicBrickType type) {
		this.colorFirst = colorFirst;
		this.colorSecond = colorSecond;
		this.type = type;
		this.ordinal = colorFirst.ordinal() + colorSecond.ordinal() * 256 + type.ordinal() * 256 * 256;
	}

	public boolean isTwoColors() {
		return this.type.canDouble() && this.colorSecond != this.colorFirst;
	}

	public CeramicBrickPair(ItemStack stack) {
		this(TileEntityMetadata.getItemDamage(stack));
	}

	public ItemStack getStack(int i) {
		return new ItemStack(Botany.ceramicBrick, i, this.ordinal());
	}

	public CeramicBrickPair(int id) {
		this.colorFirst = EnumFlowerColor.get(id & 0xFF);
		this.colorSecond = EnumFlowerColor.get(id >> 8 & 0xFF);
		this.type = CeramicBrickType.get(id >> 16 & 0xFF);
	}

	public String getName() {
		String name = this.colorFirst.getColourName();
		if (this.type.canDouble() && this.colorSecond != this.colorFirst) {
			name = name + " & " + this.colorSecond.getColourName();
		}
		return name + " " + this.type.name;
	}

	public int ordinal() {
		return ordinal;
	}

	public TextureAtlasSprite getSprite(int pass) {
		return this.type.sprites[pass];
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CeramicBrickPair)){
			return false;
		}
		return ((CeramicBrickPair)obj).ordinal() == ordinal();
	}
	
}
