package binnie.design;

import binnie.design.api.IDesignSystem;
import binnie.design.api.ILayout;
import binnie.design.api.IPattern;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Layout implements ILayout {
	IPattern pattern;
	boolean inverted;

	private Layout(final IPattern pattern, final boolean inverted) {
		this.pattern = pattern;
		this.inverted = inverted;
	}

	public static ILayout get(final IPattern pattern, final boolean inverted) {
		return new Layout(pattern, inverted);
	}

	public static ILayout get(final IPattern pattern) {
		return new Layout(pattern, false);
	}

	@Override
	public IPattern getPattern() {
		return this.pattern;
	}

	@Override
	public boolean isInverted() {
		return this.inverted;
	}

	ILayout newLayout(final ILayout newLayout) {
		return new Layout(newLayout.getPattern(), this.inverted ^ newLayout.isInverted());
	}

	@Override
	public ILayout rotateRight() {
		return this.rotateLeft().rotateLeft().rotateLeft();
	}

	@Override
	public ILayout rotateLeft() {
		return this.newLayout(this.pattern.getRotation());
	}

	@Override
	public ILayout flipHorizontal() {
		return this.newLayout(this.pattern.getHorizontalFlip());
	}

	@Override
	public ILayout flipVertical() {
		return this.newLayout(this.pattern.getHorizontalFlip().rotateLeft().rotateLeft());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getPrimarySprite(IDesignSystem system) {
		return this.inverted ? this.pattern.getSecondarySprite(system) : this.pattern.getPrimarySprite(system);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getSecondarySprite(IDesignSystem system) {
		return this.inverted ? this.pattern.getPrimarySprite(system) : this.pattern.getSecondarySprite(system);
	}

	@Override
	public ILayout invert() {
		return new Layout(this.pattern, !this.inverted);
	}
}
