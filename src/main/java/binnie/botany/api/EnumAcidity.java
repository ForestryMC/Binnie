// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

public enum EnumAcidity
{
	Acid,
	Neutral,
	Alkaline;

	public String getID() {
		return this.name().toLowerCase();
	}
}
