package binnie.genetics.gui.analyst;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnalystPageBehaviour<T extends IIndividual> extends ControlAnalystPage {
	public interface IBehaviourPlugin<T extends IIndividual> {
		int addBehaviourPages(T individual, IWidget parent, int y);
	}

	public AnalystPageBehaviour(IWidget parent, IArea area, T ind, IBehaviourPlugin<T> behaviourPlugin) {
		super(parent, area);
		setColor(0x660033);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 12;
		y = behaviourPlugin.addBehaviourPages(ind, this, y);
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.BEHAVIOUR_KEY + "");
	}
}
