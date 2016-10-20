// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.item;

import binnie.genetics.Genetics;
import binnie.genetics.genetics.IGeneItem;
import binnie.genetics.api.IGene;
import binnie.genetics.genetics.GeneItem;
import binnie.core.genetics.Gene;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import java.util.Map;
import binnie.Binnie;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.AlleleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import binnie.genetics.api.IItemSerum;

public class ItemSerum extends ItemGene implements IItemSerum
{
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
		return new IGene[] { this.getGeneItem(stack).getGene() };
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
		final IGeneItem gene = this.getGeneItem(itemstack);
		return Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getDescriptor() + " Serum";
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
