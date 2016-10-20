package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.IGeneItem;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemSerum extends ItemGene implements IItemSerum {
    public ItemSerum() {
        super("serum");
        this.setMaxDamage(16);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List list, final boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
    }

    @Override
    public int getCharges(final ItemStack stack) {
        return stack.getItem().getMaxDamage() - stack.getItemDamage();
    }

    @Override
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            final Map<IChromosomeType, List<IAllele>> chromosomeMap = Binnie.Genetics.getChromosomeMap(root);
            for (final Map.Entry<IChromosomeType, List<IAllele>> entry : chromosomeMap.entrySet()) {
                final IChromosomeType chromosome = entry.getKey();
                for (final IAllele allele : entry.getValue()) {
                    final Gene gene = Gene.create(allele, chromosome, root);
                    if (gene == null) {
                        continue;
                    }
                    final IGeneItem item = new GeneItem(gene);
                    final ItemStack stack = new ItemStack(this);
                    item.writeToItem(stack);
                    itemList.add(stack);
                }
            }
        }
    }

    @Override
    public IGene[] getGenes(final ItemStack stack) {
        return new IGene[]{this.getGeneItem(stack).getGene()};
    }

    @Override
    public ISpeciesRoot getSpeciesRoot(final ItemStack stack) {
        return this.getGeneItem(stack).getSpeciesRoot();
    }

    @Override
    public IGene getGene(final ItemStack stack, final int chromosome) {
        return this.getGeneItem(stack).getGene();
    }

    @Override
    public GeneItem getGeneItem(final ItemStack stack) {
        return new GeneItem(stack);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        //TODO
        //final IGeneItem gene = this.getGeneItem(itemstack);
        //return Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getDescriptor() + " Serum";
        return "";
    }

    @Override
    public ItemStack addGene(final ItemStack stack, final IGene gene) {
        final IGeneItem geneI = this.getGeneItem(stack);
        geneI.addGene(gene);
        geneI.writeToItem(stack);
        return stack;
    }

    public static ItemStack create(final IGene gene) {
        final ItemStack item = new ItemStack(Genetics.itemSerum);
        item.setItemDamage(item.getMaxDamage());
        final GeneItem seq = new GeneItem(gene);
        seq.writeToItem(item);
        return item;
    }
}
