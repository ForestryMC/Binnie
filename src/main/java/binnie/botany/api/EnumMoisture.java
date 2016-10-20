// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

public enum EnumMoisture
{
	Dry,
	Normal,
	Damp;

	public String getID() {
		return this.name().toLowerCase();
	}
}
