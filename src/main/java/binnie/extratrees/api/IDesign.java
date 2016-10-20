// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.api;

public interface IDesign
{
	String getName();

	ILayout getTopPattern();

	ILayout getBottomPattern();

	ILayout getNorthPattern();

	ILayout getEastPattern();

	ILayout getSouthPattern();

	ILayout getWestPattern();
}
