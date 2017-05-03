package binnie.genetics.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlMachineProgress;

// TODO unused class?
public class ControlProcessTemporary extends ControlMachineProgress {
	public ControlProcessTemporary(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, null, null, null);
		setSize(new IPoint(width, height));
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.solid(getArea(), -4868683);
		float w = getSize().y() * progress / 100.0f;
		CraftGUI.Render.solid(new IArea(getArea().x(), getArea().y(), w, getArea().h()), -65536);
	}
}
