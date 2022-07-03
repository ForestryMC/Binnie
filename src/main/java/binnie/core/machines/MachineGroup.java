package binnie.core.machines;

import binnie.Binnie;
import binnie.core.AbstractMod;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;

public class MachineGroup {
    private AbstractMod mod;
    private String blockName;
    private String uid;
    private Map<String, MachinePackage> packages;
    private Map<Integer, MachinePackage> packagesID;
    private BlockMachine block;
    public boolean customRenderer;
    private boolean renderedTileEntity;

    public MachineGroup(AbstractMod mod, String uid, String blockName, IMachineType[] types) {
        packages = new LinkedHashMap<>();
        packagesID = new LinkedHashMap<>();
        customRenderer = true;
        renderedTileEntity = true;
        this.mod = mod;
        this.uid = uid;
        this.blockName = blockName;

        for (IMachineType type : types) {
            if (type.getPackageClass() != null && type.isActive()) {
                try {
                    MachinePackage pack = type.getPackageClass().newInstance();
                    pack.assignMetadata(type.ordinal());
                    pack.setActive(type.isActive());
                    addPackage(pack);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create machine package " + type.toString(), e);
                }
            }
        }
        Binnie.Machine.registerMachineGroup(this);
        block = new BlockMachine(this, blockName);
        GameRegistry.registerBlock(block, ItemMachine.class, blockName);
    }

    private void addPackage(MachinePackage pack) {
        packages.put(pack.getUID(), pack);
        packagesID.put(pack.getMetadata(), pack);
        pack.setGroup(this);
    }

    public Collection<MachinePackage> getPackages() {
        return packages.values();
    }

    public BlockMachine getBlock() {
        return block;
    }

    public MachinePackage getPackage(int metadata) {
        return packagesID.get(metadata);
    }

    public MachinePackage getPackage(String name) {
        return packages.get(name);
    }

    public String getUID() {
        return mod.getModID() + "." + uid;
    }

    public String getShortUID() {
        return uid;
    }

    boolean isTileEntityRenderered() {
        return renderedTileEntity;
    }

    public void renderAsBlock() {
        renderedTileEntity = false;
    }

    public void setCreativeTab(CreativeTabs tab) {
        block.setCreativeTab(tab);
    }

    public AbstractMod getMod() {
        return mod;
    }
}
