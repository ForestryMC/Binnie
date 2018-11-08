package binnie.core.machines.component;

import java.util.List;

import binnie.core.triggers.TriggerData;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionReceptor;

public interface IBuildcraft {
	interface TriggerProvider {
		void getTriggers(final List<TriggerData> p0);
	}

	interface ActionProvider extends IActionReceptor
	{
		void getActions(final List<IActionExternal> p0);
	}
}
