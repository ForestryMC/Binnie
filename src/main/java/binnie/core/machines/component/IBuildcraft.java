package binnie.core.machines.component;

import binnie.core.triggers.*;
import buildcraft.api.statements.*;

import java.util.*;

public interface IBuildcraft {
	interface TriggerProvider {
		void getTriggers(List<TriggerData> p0);
	}

	interface ActionProvider extends IActionReceptor {
		void getActions(List<IActionExternal> p0);
	}
}
