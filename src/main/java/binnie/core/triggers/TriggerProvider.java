package binnie.core.triggers;

import binnie.core.machines.component.IBuildcraft;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.ITriggerInternal;
import buildcraft.api.statements.ITriggerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class TriggerProvider implements ITriggerProvider {
	static TriggerProvider instance = new TriggerProvider();
	public static List<BinnieTrigger> triggers = new ArrayList<>();

	@Override
	public Collection<ITriggerExternal> getExternalTriggers(final ForgeDirection side, final TileEntity tile) {
		final LinkedList<TriggerData> list = new LinkedList<TriggerData>();
		final LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
		if (tile instanceof IBuildcraft.TriggerProvider) {
			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
		}
		for (final TriggerData data : list) {
			if (data.getKey() != null && data.getKey().getUniqueTag() != null) {
				triggerData.add(data.getKey());
			}
		}
		return triggerData;
	}

	// TODO unusing method?
	public static boolean isTriggerActive(final ITriggerExternal trigger, final TileEntity tile) {
		final LinkedList<TriggerData> list = new LinkedList<TriggerData>();
		final LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
		if (tile instanceof IBuildcraft.TriggerProvider) {
			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
		}
		for (final TriggerData data : list) {
			if (data.getKey() == trigger) {
				return data.getValue();
			}
		}
		return false;
	}

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(final IStatementContainer container) {
		return new ArrayList<>();
	}
}
