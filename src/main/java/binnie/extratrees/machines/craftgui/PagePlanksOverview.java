package binnie.extratrees.machines.craftgui;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceType;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.PageAbstract;
import binnie.genetics.gui.database.WindowAbstractDatabase;

@SideOnly(Side.CLIENT)
public class PagePlanksOverview extends PageAbstract<ItemStack> {
	public PagePlanksOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new Area(0, 0, this.size().x(), 24), species.getDisplayName(), TextJustification.MIDDLE_CENTER);
		new ControlText(this, new Area(12, 24, this.size().x() - 24, 24), I18N.localise("extratrees.gui.database.planks.use"), TextJustification.MIDDLE_LEFT);
		final IPlankType type = WoodManager.getPlankType(species);
		int x = 12;
		if (type != null) {
			final ItemStack fence = WoodManager.getFence(type, new FenceType(0), 1);
			final ItemStack gate = WoodManager.getGate(type);
			final ItemStack door = WoodManager.getDoor(type);
			if (!fence.isEmpty()) {
				new ControlItemDisplay(this, x, 48).setItemStack(fence);
				x += 22;
			}
			if (!gate.isEmpty()) {
				new ControlItemDisplay(this, x, 48).setItemStack(gate);
				x += 22;
			}
			if (!door.isEmpty()) {
				new ControlItemDisplay(this, x, 48).setItemStack(door);
				x += 22;
			}
		}
		final ControlText controlDescription = new ControlText(this, new Area(8, 84, this.getSize().x() - 16, 0), "", TextJustification.MIDDLE_CENTER);
		final ControlText controlSignature = new ControlText(this, new Area(8, 84, this.getSize().x() - 16, 0), "", TextJustification.BOTTOM_RIGHT);
		String desc = "";
		if (type != null) {
			desc = type.getDescription();
		}
		StringBuilder descBody = new StringBuilder("§o");
		String descSig = "";
		if (desc == null || desc.length() == 0) {
			descBody.append(I18N.localise("binniecore.gui.database.nodescription"));
		} else {
			final String[] descStrings = desc.split("\\|");
			descBody.append(descStrings[0]);
			for (int i = 1; i < descStrings.length - 1; ++i) {
				descBody.append(" ").append(descStrings[i]);
			}
			if (descStrings.length > 1) {
				descSig += descStrings[descStrings.length - 1];
			}
		}
		controlDescription.setValue(descBody + "§r");
		controlSignature.setValue(descSig + "§r");
		final int descHeight = CraftGUI.render.textHeight(controlDescription.getValue(), controlDescription.getSize().x());
		controlSignature.setPosition(new Point(controlSignature.pos().x(), controlDescription.getPosition().y() + descHeight + 10));
	}
}
