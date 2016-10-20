// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.triggers;

import binnie.core.machines.component.IBuildcraft;
import java.util.LinkedList;
import buildcraft.api.statements.IActionExternal;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Optional;
import buildcraft.api.statements.IActionInternal;
import java.util.Collection;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IActionProvider;

class ActionProvider implements IActionProvider
{
	@Override
	@Optional.Method(modid = "BuildCraft|Silicon")
	public Collection<IActionInternal> getInternalActions(final IStatementContainer container) {
		return null;
	}

	@Override
	@Optional.Method(modid = "BuildCraft|Silicon")
	public Collection<IActionExternal> getExternalActions(final ForgeDirection side, final TileEntity tile) {
		final LinkedList<IActionExternal> list = new LinkedList<IActionExternal>();
		if (tile instanceof IBuildcraft.ActionProvider) {
			((IBuildcraft.ActionProvider) tile).getActions(list);
		}
		final LinkedList<IActionExternal> list2 = new LinkedList<IActionExternal>();
		for (final IActionExternal action : list2) {
			if (action != null && action.getUniqueTag() != null) {
				list.add(action);
			}
		}
		return list2;
	}
}
