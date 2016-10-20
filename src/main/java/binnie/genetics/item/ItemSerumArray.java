// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.item;

import binnie.Binnie;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IChromosomeType;
import binnie.genetics.genetics.IGeneItem;
import binnie.core.genetics.Gene;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.AlleleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import binnie.genetics.genetics.GeneArrayItem;
import forestry.api.genetics.ISpeciesRoot;
import binnie.genetics.api.IGene;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import binnie.genetics.api.IItemSerum;

public class ItemSerumArray extends ItemGene implements IItemSerum
{
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(final IIconRegister register) {
		this.icons[0] = Genetics.proxy.getIcon(register, "machines/genome.glass");
		this.icons[1] = Genetics.proxy.getIcon(register, "machines/genome.cap");
		this.icons[2] = Genetics.proxy.getIcon(register, "machines/genome.edges");
		this.icons[3] = Genetics.proxy.getIcon(register, "machines/genome.dna");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		final IGeneItem gene = this.getGeneItem(itemstack);
		return Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getDescriptor() + " Serum Array";
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
