package binnie.botany.ceramic.brick;

import com.google.common.base.Preconditions;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;

public class CeramicBrickPair {
	public final EnumFlowerColor colorFirst;
	public final EnumFlowerColor colorSecond;

	public CeramicBrickType type;
	public int ordinal;

	CeramicBrickPair(EnumFlowerColor colorFirst, EnumFlowerColor colorSecond, CeramicBrickType type) {
		this.colorFirst = colorFirst;
		this.colorSecond = colorSecond;
		this.type = type;
		ordinal = colorFirst.ordinal() + colorSecond.ordinal() * 256 + type.ordinal() * 256 * 256;
	}

	public CeramicBrickPair(ItemStack stack) {
		this(TileEntityMetadata.getItemDamage(stack));
	}

	public CeramicBrickPair(int id) {
		colorFirst = EnumFlowerColor.get(id & 0xFF);
		colorSecond = EnumFlowerColor.get(id >> 8 & 0xFF);
		type = CeramicBrickType.get(id >> 16 & 0xFF);
	}

	public boolean isTwoColors() {
		return type.canDouble() && colorSecond != colorFirst;
	}

	public ItemStack getStack(int i) {
		return new ItemStack(Botany.ceramicBrick, i, ordinal());
	}

	public String getName() {
		String name = colorFirst.getFlowerColorAllele().getColorName();
		if (type.canDouble() && colorSecond != colorFirst) {
			name = name + " & " + colorSecond.getFlowerColorAllele().getColorName();
		}
		return I18N.localise("botany.ceramic.type." + type.id + ".name", name);
	}

	public int ordinal() {
		return ordinal;
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(int pass) {
		TextureAtlasSprite[] sprites = type.sprites;
		Preconditions.checkState(sprites != null, "Sprites have not been registered.");
		return sprites[pass];
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(ordinal());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CeramicBrickPair
				&& ((CeramicBrickPair) obj).ordinal() == ordinal();
	}
}
