package binnie.core.triggers;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.common.Optional;

import binnie.core.Constants;
import binnie.core.machines.component.IBuildcraft;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionInternal;
import buildcraft.api.statements.IActionInternalSided;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;

class ActionProvider implements IActionProvider {

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public void addInternalActions(Collection<IActionInternal> actions, IStatementContainer container) {
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public void addInternalSidedActions(Collection<IActionInternalSided> actions, IStatementContainer container, @Nonnull EnumFacing side) {
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public void addExternalActions(Collection<IActionExternal> actions, @Nonnull EnumFacing side, TileEntity tile) {
		List<IActionExternal> tileActions = new LinkedList<>();
		if (tile instanceof IBuildcraft.ActionProvider) {
			((IBuildcraft.ActionProvider) tile).getActions(tileActions);
		}
		for (IActionExternal action : tileActions) {
			if (action != null && action.getUniqueTag() != null) {
				actions.add(action);
			}
		}
	}
}
