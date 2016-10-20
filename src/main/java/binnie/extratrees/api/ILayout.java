// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.api;

import net.minecraft.util.IIcon;

public interface ILayout
{
	IPattern getPattern();

	boolean isInverted();

	ILayout rotateRight();

	ILayout rotateLeft();

	ILayout flipHorizontal();

	ILayout flipVertical();

	ILayout invert();

	IIcon getPrimaryIcon(final IDesignSystem p0);

	IIcon getSecondaryIcon(final IDesignSystem p0);
}
