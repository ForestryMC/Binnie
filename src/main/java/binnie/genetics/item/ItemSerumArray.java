package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneArrayItem;
import binnie.genetics.genetics.IGeneItem;
import forestry.api.genetics.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ItemSerumArray extends ItemGene implements IItemSerum {
    public ItemSerumArray() {
        super("serumArray");
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
    public IGene[] getGenes(final ItemStack stack) {
        return this.getGeneItem(stack).getGenes().toArray(new IGene[0]);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot(final ItemStack stack) {
        return this.getGeneItem(stack).getSpeciesRoot();
    }

    @Override
    public IGene getGene(final ItemStack stack, final int chromosome) {
        return this.getGeneItem(stack).getGene(chromosome);
    }

    @Override
    public GeneArrayItem getGeneItem(final ItemStack stack) {
        return new GeneArrayItem(stack);
    }

    @Override
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            for (final IIndividual template : root.getIndividualTemplates()) {
                if (template.getGenome().getPrimary().isSecret()) {
                    continue;
                }
                final IGeneItem item = new GeneArrayItem();
                for (final IChromosomeType type : root.getKaryotype()) {
                    final IChromosome c = template.getGenome().getChromosomes()[type.ordinal()];
                    if (c != null) {
                        final IAllele active = c.getActiveAllele();
                        if (active != null) {
                            item.addGene(new Gene(active, type, root));
                        }
                    }
                }
                final ItemStack array = new ItemStack(this);
                item.writeToItem(array);
                itemList.add(array);
            }
        }
    }

//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		this.icons[0] = Genetics.proxy.getIcon(register, "machines/genome.glass");
//		this.icons[1] = Genetics.proxy.getIcon(register, "machines/genome.cap");
//		this.icons[2] = Genetics.proxy.getIcon(register, "machines/genome.edges");
//		this.icons[3] = Genetics.proxy.getIcon(register, "machines/genome.dna");
//	}

    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        ISpeciesRoot speciesRoot = getGeneItem(itemstack).getSpeciesRoot();
        if(speciesRoot != null)
            return Binnie.Genetics.getSystem(getGeneItem(itemstack).getSpeciesRoot()).getDescriptor() + " Serum Array";
        return "Corrupted Serum Array";
    }

    @Override
    public ItemStack addGene(final ItemStack stack, final IGene gene) {
        final IGeneItem geneI = this.getGeneItem(stack);
        geneI.addGene(gene);
        geneI.writeToItem(stack);
        return stack;
    }

    public static ItemStack create(final IGene gene) {
        final ItemStack item = new ItemStack(Genetics.itemSerumArray);
        item.setItemDamage(item.getMaxDamage());
        final GeneArrayItem seq = new GeneArrayItem(gene);
        seq.writeToItem(item);
        return item;
    }
}
