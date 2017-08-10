package binnie.core.gui.resource.minecraft;

public enum CraftGUITexture {
	WINDOW("window"),
	PANEL_GRAY("panel.gray"),
	PANEL_BLACK("panel.black"),
	PANEL_TINTED("panel.tinted"),
	BUTTON_DISABLED("button.disabled"),
	BUTTON("button"),
	BUTTON_HIGHLIGHTED("button.highlighted"),
	SLOT("slot"),
	SLOT_BORDER("slot.border"),
	SLOT_OVERLAY("slot.overlay"),
	SLOT_CHARGE("slot.charge"),
	LIQUID_TANK("liquidtank"),
	LIQUID_TANK_OVERLAY("liquidtank.overlay"),
	STATE_ERROR("errorstate.error"),
	STATE_WARNING("errorstate.warning"),
	STATE_NONE("errorstate.none"),
	ENERGY_BAR_BACK("energybar.back"),
	ENERGY_BAR_GLOW("energybar.glow"),
	ENERGY_BAR_GLASS("energybar.glass"),
	TAB_DISABLED("tab.disabled"),
	TAB("tab"),
	TAB_HIGHLIGHTED("tab.highlighted"),
	TAB_OUTLINE("tab.outline"),
	TAB_SOLID("tab.solid"),
	SCROLL_DISABLED("scroll.disabled"),
	SCROLL("scroll"),
	SCROLL_HIGHLIGHTED("scroll.highlighted"),
	OUTLINE("outline"),
	HELP_BUTTON("button.help"),
	INFO_BUTTON("button.info"),
	USER_BUTTON("button.user"),
	POWER_BUTTON("button.power"),
	HORIZONTAL_LIQUID_TANK("horizontalliquidtank"),
	HORIZONTAL_LIQUID_TANK_OVERLAY("horizontalliquidtank.overlay"),
	SLIDE_UP("slide.up"),
	SLIDE_DOWN("slide.down"),
	SLIDE_LEFT("slide.left"),
	SLIDE_RIGHT("slide.right"),
	CHECKBOX("checkbox"),
	CHECKBOX_HIGHLIGHTED("checkbox.highlighted"),
	CHECKBOX_CHECKED("checkbox.checked"),
	CHECKBOX_CHECKED_HIGHLIGHTED("checkbox.checked.highlighted");

	String name;

	CraftGUITexture(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
