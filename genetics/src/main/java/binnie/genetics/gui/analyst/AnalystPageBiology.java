package binnie.genetics.gui.analyst;

import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IIndividual;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.util.I18N;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.api.analyst.IBiologyPlugin;

@SideOnly(Side.CLIENT)
public class AnalystPageBiology<T extends IIndividual> extends Control implements ITitledWidget {

	public AnalystPageBiology(IWidget parent, IArea area, T ind, IBiologyPlugin<T> plugin, IAnalystManager analystManager) {
		super(parent, area);
		setColor(0x006666);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		y = plugin.addBiologyPages(ind, this, y, analystManager);
		setSize(new Point(getWidth(), y));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.BIOLOGY_KEY + "");
	}
}
