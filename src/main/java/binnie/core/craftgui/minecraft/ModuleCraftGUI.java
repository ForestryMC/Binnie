package binnie.core.craftgui.minecraft;

import binnie.core.IInitializable;

public class ModuleCraftGUI implements IInitializable {
	@Override
	public void preInit() {
		for (final GUIIcon icon : GUIIcon.values()) {
			icon.register();
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {

	}
}
