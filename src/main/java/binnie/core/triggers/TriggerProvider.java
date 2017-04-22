package binnie.core.triggers;

import binnie.core.machines.component.*;
import buildcraft.api.statements.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.util.*;

import java.util.*;

class TriggerProvider implements ITriggerProvider {
	static TriggerProvider instance = new TriggerProvider();
	public static List<BinnieTrigger> triggers = new ArrayList<>();

	@Override
	public Collection<ITriggerExternal> getExternalTriggers(ForgeDirection side, TileEntity tile) {
		LinkedList<TriggerData> list = new LinkedList<TriggerData>();
		LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
		if (tile instanceof IBuildcraft.TriggerProvider) {
			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
		}
		for (TriggerData data : list) {
			if (data.getKey() != null && data.getKey().getUniqueTag() != null) {
				triggerData.add(data.getKey());
			}
		}
		return triggerData;
	}

	// TODO unusing method?
	public static boolean isTriggerActive(ITriggerExternal trigger, TileEntity tile) {
		LinkedList<TriggerData> list = new LinkedList<TriggerData>();
		LinkedList<ITriggerExternal> triggerData = new LinkedList<ITriggerExternal>();
		if (tile instanceof IBuildcraft.TriggerProvider) {
			((IBuildcraft.TriggerProvider) tile).getTriggers(list);
		}
		for (TriggerData data : list) {
			if (data.getKey() == trigger) {
				return data.getValue();
			}
		}
		return false;
	}

	@Override
	public Collection<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
		return new ArrayList<>();
	}
}
