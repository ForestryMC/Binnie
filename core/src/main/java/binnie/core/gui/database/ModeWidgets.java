package binnie.core.gui.database;

import binnie.core.api.gui.IArea;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.controls.page.ControlPage;
import binnie.core.gui.controls.page.ControlPages;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;

public final class ModeWidgets {
	public ControlPage<IDatabaseMode> getModePage() {
		return modePage;
	}

	public ControlListBox getListBox() {
		return listBox;
	}

	@FunctionalInterface
	public interface IListBoxCreator {
		ControlListBox createListBox(IArea area, ControlPage<IDatabaseMode> modePage);
	}

	private final ControlPage<IDatabaseMode> modePage;
	private final ControlListBox listBox;
	private final ControlPages<DatabaseTab> infoPages;
	private ControlTabBar<DatabaseTab> infoTabs;

	public ModeWidgets(IDatabaseMode mode, WindowAbstractDatabase database, IListBoxCreator listBoxCreator) {
		this.modePage = new ControlPage<>(database.getModePages(), 0, 0, database.getSize().xPos(), database.getSize().yPos(), mode);
		final IArea listBoxArea = database.getPanelSearch().getArea().inset(2);
		this.listBox = listBoxCreator.createListBox(listBoxArea, this.modePage);
		CraftGUIUtil.alignToWidget(this.listBox, database.getPanelSearch());
		CraftGUIUtil.moveWidget(this.listBox, new Point(2, 2));
		CraftGUIUtil.alignToWidget(this.infoPages = new ControlPages<>(this.modePage, 0, 0, 144, 176), database.getPanelInformation());
	}

	public ControlPages<DatabaseTab> getInfoPages() {
		return infoPages;
	}

	public ControlTabBar<DatabaseTab> getInfoTabs() {
		return infoTabs;
	}

	public void setInfoTabs(ControlTabBar<DatabaseTab> infoTabs) {
		this.infoTabs = infoTabs;
	}
}
