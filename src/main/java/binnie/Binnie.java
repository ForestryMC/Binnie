package binnie;

import binnie.core.ManagerBase;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.item.ManagerItem;
import binnie.core.language.ManagerLanguage;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.machines.ManagerMachine;
import binnie.core.mod.config.ManagerConfig;
import binnie.core.resource.ManagerResource;

import java.util.ArrayList;
import java.util.List;

public final class Binnie {
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
