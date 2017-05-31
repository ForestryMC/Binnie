package binnie.core.machines.component;

import binnie.core.triggers.TriggerData;

import java.util.List;

public interface IBuildcraft {
	interface TriggerProvider {
		void getTriggers(final List<TriggerData> p0);
	}

	interface ActionProvider //extends IActionReceptor
	{
		//		void getActions(final List<IActionExternal> p0);
	}
}
