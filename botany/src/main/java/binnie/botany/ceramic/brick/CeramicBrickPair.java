package binnie.botany.ceramic.brick;

import com.google.common.base.Preconditions;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.util.I18N;

public class CeramicBrickPair {
	public static final CeramicBrickPair EMPTY = new CeramicBrickPair(EnumFlowerColor.DarkSeaGreen, EnumFlowerColor.DarkSeaGreen, CeramicBrickType.TILE);

	private final EnumFlowerColor colorFirst;
	private final EnumFlowerColor colorSecond;

	private CeramicBrickType type;
	private int ordinal;

	public CeramicBrickPair(EnumFlowerColor colorFirst, EnumFlowerColor colorSecond, CeramicBrickType type) {
		this.colorFirst = colorFirst;
		this.colorSecond = colorSecond;
		this.type = type;
		ordinal = colorFirst.ordinal() + colorSecond.ordinal() * 256 + type.ordinal() * 256 * 256;
	}

	public CeramicBrickPair(ItemStack stack) {
		this(getId(stack));
	}

	public CeramicBrickPair(int id) {
		colorFirst = EnumFlowerColor.get(id & 0xFF);
		colorSecond = EnumFlowerColor.get(id >> 8 & 0xFF);
		type = CeramicBrickType.get(id >> 16 & 0xFF);
	}

	public boolean isTwoColors() {
		return type.canDouble() && colorSecond != colorFirst;
	}

	public static int getId(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return EMPTY.ordinal();
		}
		NBTTagCompound nbtTagCompound = stack.getTagCompound();
		return nbtTagCompound.getInteger("id");
	}

	public ItemStack getStack(int i) {
		ItemStack stack = new ItemStack(ModuleCeramic.ceramicBrick, i);
		stack.setTagInfo("id", new NBTTagInt(ordinal()));
		return stack;
	}

	public String getName() {
		String name = colorFirst.getFlowerColorAllele().getColorName();
		if (type.canDouble() && colorSecond != colorFirst) {
			name = name + " & " + colorSecond.getFlowerColorAllele().getColorName();
		}
		return I18N.localise("botany.ceramic.type." + type.getId() + ".name", name);
	}

	public int ordinal() {
		return ordinal;
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(int pass) {
		TextureAtlasSprite[] sprites = type.getSprites();
		Preconditions.checkState(sprites != null, "Sprites have not been registered.");
		return sprites[pass];
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(type.ordinal());
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CeramicBrickPair
			&& ((CeramicBrickPair) obj).type == type;
	}

	public EnumFlowerColor getColorFirst() {
		return colorFirst;
	}

	public EnumFlowerColor getColorSecond() {
		return colorSecond;
	}

	public CeramicBrickType getType() {
		return type;
	}

	public void setType(CeramicBrickType type) {
		this.type = type;
	}
}
