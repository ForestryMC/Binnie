package binnie.extratrees.gui.database;

import binnie.core.api.gui.IPoint;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.database.PageAbstract;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.util.I18N;
import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.blocks.decor.FenceType;

@SideOnly(Side.CLIENT)
public class PagePlanksOverview extends PageAbstract<ItemStack> {
	public PagePlanksOverview(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		IPoint size = getSize();
		new ControlText(this, new Area(0, 0, size.xPos(), 24), species.getDisplayName(), TextJustification.MIDDLE_CENTER);
		new ControlText(this, new Area(12, 24, size.xPos() - 24, 24), I18N.localise(DatabaseConstants.EXTRATREES_KEY + ".planks.use"), TextJustification.MIDDLE_LEFT);
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
		final ControlText controlDescription = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), "", TextJustification.MIDDLE_CENTER);
		final ControlText controlSignature = new ControlText(this, new Area(8, 84, this.getSize().xPos() - 16, 0), "", TextJustification.BOTTOM_RIGHT);
		String desc = "";
		if (type != null) {
			desc = type.getDescription();
		}
		StringBuilder descBody = new StringBuilder("§o");
		String descSig = "";
		if (desc == null || desc.length() == 0) {
			descBody.append(I18N.localise(DatabaseConstants.KEY + ".nodescription"));
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
		final int descHeight = CraftGUI.RENDER.textHeight(controlDescription.getValue(), controlDescription.getSize().xPos());
		controlSignature.setPosition(new Point(controlSignature.getPosition().xPos(), controlDescription.getPosition().yPos() + descHeight + 10));
	}
}
