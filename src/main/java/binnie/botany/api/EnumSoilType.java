// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

public enum EnumSoilType
{
	SOIL,
	LOAM,
	FLOWERBED;

	public String getID() {
		return this.name().toLowerCase();
	}
}
