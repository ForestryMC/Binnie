package binnie.core.triggers;

import binnie.core.machines.component.*;
import buildcraft.api.statements.*;
import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.util.*;

import java.util.*;

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
