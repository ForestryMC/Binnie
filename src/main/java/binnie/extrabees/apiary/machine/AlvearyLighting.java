// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary.machine;

import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.core.machines.Machine;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.core.craftgui.minecraft.IMachineInformation;

public class AlvearyLighting
{
	public static class PackageAlvearyLighting extends AlvearyMachine.AlvearyPackage implements IMachineInformation
	{
		public PackageAlvearyLighting() {
			super("lighting", ExtraBeeTexture.AlvearyLighting.getTexture(), false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentLighting(machine);
		}
	}

	public static class ComponentLighting extends ComponentBeeModifier implements IBeeModifier, IBeeListener
	{
		public ComponentLighting(final Machine machine) {
			super(machine);
		}

		@Override
		public boolean isSelfLighted() {
			return true;
		}
	}
}
