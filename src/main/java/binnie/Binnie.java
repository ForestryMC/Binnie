package binnie;

import binnie.core.ManagerBase;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.language.ManagerLanguage;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.machines.ManagerMachine;
import binnie.core.mod.config.ManagerConfig;
import binnie.core.resource.ManagerResource;

import java.util.ArrayList;
import java.util.List;

public final class Binnie {
	public static final List<ManagerBase> MANAGERS = new ArrayList<>();
	public static final ManagerLanguage LANGUAGE = new ManagerLanguage();
	public static final ManagerGenetics GENETICS = new ManagerGenetics();
	public static final ManagerConfig CONFIGURATION = new ManagerConfig();
	public static final ManagerLiquid LIQUID = new ManagerLiquid();
	public static final ManagerMachine MCHINE = new ManagerMachine();
	public static final ManagerResource RESOURCE = new ManagerResource();
}
