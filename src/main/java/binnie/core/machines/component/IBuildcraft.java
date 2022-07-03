package binnie.core.machines.component;

import binnie.core.triggers.TriggerData;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionReceptor;
import java.util.List;

public interface IBuildcraft {
    interface TriggerProvider {
        void getTriggers(List<TriggerData> p0);
    }

    interface ActionProvider extends IActionReceptor {
        void getActions(List<IActionExternal> p0);
    }
}
