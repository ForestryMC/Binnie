package binnie.genetics.gui.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Point;
import binnie.core.util.I18N;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnalystPageBiology<T extends IIndividual> extends ControlAnalystPage {

	public interface IBiologyPlugin<T extends IIndividual> {
		@SideOnly(Side.CLIENT)
		int addBiologyPages(T individual, IWidget parent, int y);
	}

	public AnalystPageBiology(IWidget parent, IArea area, T ind, IBiologyPlugin<T> plugin) {
		super(parent, area);
		setColor(0x006666);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		y = plugin.addBiologyPages(ind, this, y);
		setSize(new Point(getWidth(), y));
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.BIOLOGY_KEY + "");
	}
}
