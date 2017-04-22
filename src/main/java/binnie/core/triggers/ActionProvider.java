package binnie.core.triggers;

import binnie.core.machines.component.IBuildcraft;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionInternal;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;
import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.LinkedList;

class ActionProvider implements IActionProvider {
	@Override
	@Optional.Method(modid = "BuildCraft|Silicon")
	public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
		return null;
	}

	@Override
	@Optional.Method(modid = "BuildCraft|Silicon")
	public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
		LinkedList<IActionExternal> list = new LinkedList<>();
		if (tile instanceof IBuildcraft.ActionProvider) {
			((IBuildcraft.ActionProvider) tile).getActions(list);
		}

		LinkedList<IActionExternal> list2 = new LinkedList<>();
		for (IActionExternal action : list2) {
			if (action != null && action.getUniqueTag() != null) {
				list.add(action);
			}
		}
		return list2;
	}
}
