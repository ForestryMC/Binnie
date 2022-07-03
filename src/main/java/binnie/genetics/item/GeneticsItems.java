package binnie.genetics.item;

import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum GeneticsItems implements IItemMisc {
    LaboratoryCasing("casingIron"),
    DNADye("dnaDye"),
    FluorescentDye("dyeFluor"),
    Enzyme("enzyme"),
    GrowthMedium("growthMedium"),
    EmptySequencer("sequencerEmpty"),
    EmptySerum("serumEmpty"),
    EmptyGenome("genomeEmpty"),
    Cylinder("cylinderEmpty"),
    IntegratedCircuit("integratedCircuit"),
    IntegratedCPU("integratedCPU"),
    IntegratedCasing("casingCircuit");

    protected IIcon icon;
    protected String name;
    protected String iconPath;

    GeneticsItems(String name) {
        this.name = name;
        this.iconPath = name;
    }

    @Override
    public IIcon getIcon(ItemStack itemStack) {
        return icon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = Genetics.proxy.getIcon(register, iconPath);
    }

    @Override
    public void addInformation(List data) {
        // ignored
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("genetics.item." + name + ".name");
    }

    @Override
    public ItemStack get(int count) {
        if (Genetics.itemGenetics == null) {
            return null;
        }
        return new ItemStack(Genetics.itemGenetics, count, ordinal());
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
