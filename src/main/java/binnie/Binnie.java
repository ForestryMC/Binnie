// 
// Decompiled by Procyon v0.5.30
// 

package binnie;

import java.util.ArrayList;
import binnie.core.resource.ManagerResource;
import binnie.core.item.ManagerItem;
import binnie.core.machines.ManagerMachine;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.mod.config.ManagerConfig;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.language.ManagerLanguage;
import binnie.core.ManagerBase;
import java.util.List;

public final class Binnie
{
	public static final List<ManagerBase> Managers;
	public static final ManagerLanguage Language;
	public static final ManagerGenetics Genetics;
	public static final ManagerConfig Configuration;
	public static final ManagerLiquid Liquid;
	public static final ManagerMachine Machine;
	public static final ManagerItem Item;
	public static final ManagerResource Resource;

	static {
		Managers = new ArrayList<ManagerBase>();
		Language = new ManagerLanguage();
		Genetics = new ManagerGenetics();
		Configuration = new ManagerConfig();
		Liquid = new ManagerLiquid();
		Machine = new ManagerMachine();
		Item = new ManagerItem();
		Resource = new ManagerResource();
	}
}
