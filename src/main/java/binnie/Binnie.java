package binnie;

import binnie.core.*;
import binnie.core.genetics.*;
import binnie.core.item.*;
import binnie.core.language.*;
import binnie.core.liquid.*;
import binnie.core.machines.*;
import binnie.core.mod.config.*;
import binnie.core.resource.*;

import java.util.*;

public final class Binnie {
	public static List<ManagerBase> Managers = new ArrayList<>();
	public static ManagerLanguage Language = new ManagerLanguage();
	public static ManagerGenetics Genetics = new ManagerGenetics();
	public static ManagerConfig Configuration = new ManagerConfig();
	public static ManagerLiquid Liquid = new ManagerLiquid();
	public static ManagerMachine Machine = new ManagerMachine();
	public static ManagerItem Item = new ManagerItem();
	public static ManagerResource Resource = new ManagerResource();
}
