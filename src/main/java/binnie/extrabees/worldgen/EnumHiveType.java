// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import java.util.ArrayList;
import forestry.api.apiculture.IHiveDrop;
import java.util.List;

public enum EnumHiveType
{
	Water,
	Rock,
	Nether,
	Marble;

	public List<IHiveDrop> drops;

	private EnumHiveType() {
		this.drops = new ArrayList<IHiveDrop>();
	}
}
