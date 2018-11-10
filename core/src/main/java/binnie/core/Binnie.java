package binnie.core;

import java.util.ArrayList;
import java.util.List;

import binnie.core.config.ManagerConfig;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.machines.ManagerMachine;
import binnie.core.resource.ManagerResource;

public final class Binnie {
	public static final List<ManagerBase> MANAGERS = new ArrayList<>();
	public static final ManagerGenetics GENETICS = new ManagerGenetics();
	public static final ManagerConfig CONFIGURATION = new ManagerConfig();
	public static final ManagerLiquid LIQUID = new ManagerLiquid();
	public static final ManagerMachine MACHINE = new ManagerMachine();
	public static final ManagerResource RESOURCE = new ManagerResource();
}
