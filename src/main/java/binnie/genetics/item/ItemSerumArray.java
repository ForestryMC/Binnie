package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneArrayItem;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSerumArray extends ItemGene implements IItemSerum {
    public ItemSerumArray() {
        super("serumArray");
        setMaxDamage(16);
    }

    public static ItemStack create(IGene gene) {
        ItemStack item = new ItemStack(Genetics.itemSerumArray);
        item.setItemDamage(item.getMaxDamage());
        GeneArrayItem seq = new GeneArrayItem(gene);
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
    public IGene[] getGenes(ItemStack stack) {
        return getGeneItem(stack).getGenes().toArray(new IGene[0]);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
        return getGeneItem(stack).getSpeciesRoot();
    }

    @Override
    public IGene getGene(ItemStack stack, int chromosome) {
        return getGeneItem(stack).getGene(chromosome);
    }

    @Override
    public GeneArrayItem getGeneItem(ItemStack stack) {
        return new GeneArrayItem(stack);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            for (IIndividual template : root.getIndividualTemplates()) {
                if (template.getGenome().getPrimary().isSecret()) {
                    continue;
                }

                IGeneItem geneItem = new GeneArrayItem();
                for (IChromosomeType type : root.getKaryotype()) {
                    IChromosome c = template.getGenome().getChromosomes()[type.ordinal()];
                    if (c == null) {
                        continue;
                    }

                    IAllele active = c.getActiveAllele();
                    if (active != null) {
                        geneItem.addGene(new Gene(active, type, root));
                    }
                }

                ItemStack array = new ItemStack(this);
                geneItem.writeToItem(array);
                list.add(array);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        icons[0] = Genetics.proxy.getIcon(register, "machines/genome.glass");
        icons[1] = Genetics.proxy.getIcon(register, "machines/genome.cap");
        icons[2] = Genetics.proxy.getIcon(register, "machines/genome.edges");
        icons[3] = Genetics.proxy.getIcon(register, "machines/genome.dna");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IGeneItem gene = getGeneItem(stack);
        BreedingSystem breedingSystem = Binnie.Genetics.getSystem(gene.getSpeciesRoot());
        if (breedingSystem != null) {
            return I18N.localise("genetics.item.serumArray.name", breedingSystem.getDescriptor());
        }
        return I18N.localise("genetics.item.genomeEmpty.name");
    }

    @Override
    public ItemStack addGene(ItemStack stack, IGene gene) {
        IGeneItem geneI = getGeneItem(stack);
        geneI.addGene(gene);
        geneI.writeToItem(stack);
        return stack;
    }
}
