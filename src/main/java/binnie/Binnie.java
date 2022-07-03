package binnie;

import binnie.core.ManagerBase;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.item.ManagerItem;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.machines.ManagerMachine;
import binnie.core.mod.config.ManagerConfig;
import binnie.core.resource.ManagerResource;
import java.util.ArrayList;
import java.util.List;

public final class Binnie {
    public static List<ManagerBase> Managers = new ArrayList<>();
    public static ManagerGenetics Genetics = new ManagerGenetics();
    public static ManagerConfig Configuration = new ManagerConfig();
    public static ManagerLiquid Liquid = new ManagerLiquid();
    public static ManagerMachine Machine = new ManagerMachine();
    public static ManagerItem Item = new ManagerItem();
    public static ManagerResource Resource = new ManagerResource();
}
