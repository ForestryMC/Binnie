// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

import net.minecraft.util.IIcon;

public interface IFlowerType
{
	IIcon getStem(final EnumFlowerStage p0, final boolean p1, final int p2);

	IIcon getPetalIcon(final EnumFlowerStage p0, final boolean p1, final int p2);

	IIcon getVariantIcon(final EnumFlowerStage p0, final boolean p1, final int p2);

	int getID();

	int getSections();

	int ordinal();
}
