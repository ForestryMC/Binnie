// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.component;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionReceptor;
import binnie.core.triggers.TriggerData;
import java.util.List;

public interface IBuildcraft
{
	public interface TriggerProvider
	{
		void getTriggers(final List<TriggerData> p0);
	}

	public interface ActionProvider extends IActionReceptor
	{
		void getActions(final List<IActionExternal> p0);
	}
}
