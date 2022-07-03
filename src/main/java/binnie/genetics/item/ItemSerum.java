package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSerum extends ItemGene implements IItemSerum {
    public ItemSerum() {
        super("serum");
        setMaxDamage(16);
    }

    public static ItemStack create(IGene gene) {
        ItemStack item = new ItemStack(Genetics.itemSerum);
        item.setItemDamage(item.getMaxDamage());
        GeneItem seq = new GeneItem(gene);
        seq.writeToItem(item);
        return item;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public int getCharges(ItemStack stack) {
        return stack.getItem().getMaxDamage() - stack.getItemDamage();
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            Map<IChromosomeType, List<IAllele>> chromosomeMap = Binnie.Genetics.getChromosomeMap(root);
            for (Map.Entry<IChromosomeType, List<IAllele>> entry : chromosomeMap.entrySet()) {
                IChromosomeType chromosome = entry.getKey();
                for (IAllele allele : entry.getValue()) {
                    Gene gene = Gene.create(allele, chromosome, root);
                    if (gene == null) {
                        continue;
                    }

                    IGeneItem geneItem = new GeneItem(gene);
                    ItemStack stack = new ItemStack(this);
                    geneItem.writeToItem(stack);
                    list.add(stack);
                }
            }
        }
    }

    @Override
    public IGene[] getGenes(ItemStack stack) {
        return new IGene[] {getGeneItem(stack).getGene()};
    }

    @Override
    public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
        return getGeneItem(stack).getSpeciesRoot();
    }

    @Override
    public IGene getGene(ItemStack stack, int chromosome) {
        return getGeneItem(stack).getGene();
    }

    @Override
    public GeneItem getGeneItem(ItemStack stack) {
        return new GeneItem(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IGeneItem gene = getGeneItem(stack);
        BreedingSystem breedingSystem = Binnie.Genetics.getSystem(gene.getSpeciesRoot());
        if (breedingSystem != null) {
            return I18N.localise("genetics.item.serum.name", breedingSystem.getDescriptor());
        }
        return I18N.localise("genetics.item.serumEmpty.name");
    }

    @Override
    public ItemStack addGene(ItemStack stack, IGene gene) {
        IGeneItem geneItem = getGeneItem(stack);
        geneItem.addGene(gene);
        geneItem.writeToItem(stack);
        return stack;
    }
}
