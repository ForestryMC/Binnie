package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.IGeneItem;
import com.google.common.base.Preconditions;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemSerum extends ItemGene implements IItemSerum {
	public ItemSerum() {
		super("serum");
		this.setMaxDamage(16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List<String> list, final boolean par4) {
		super.addInformation(itemstack, entityPlayer, list, par4);
	}

	@Override
	public int getCharges(final ItemStack itemStack) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage();
	}

	@Override
	public int getMaxCharges(ItemStack itemStack) {
		return itemStack.getMaxDamage();
	}

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
			final Map<IChromosomeType, List<IAllele>> chromosomeMap = Binnie.GENETICS.getChromosomeMap(root);
			for (final Map.Entry<IChromosomeType, List<IAllele>> entry : chromosomeMap.entrySet()) {
				final IChromosomeType chromosome = entry.getKey();
				for (final IAllele allele : entry.getValue()) {
					final Gene gene = Gene.create(allele, chromosome, root);
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
		GeneItem geneItem = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneItem, "Cannot get genes from itemStack that is not a valid serum.");
		return new IGene[]{geneItem.getGene()};
	}

	@Override
	public ISpeciesRoot getSpeciesRoot(final ItemStack stack) {
		GeneItem geneItem = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneItem, "Cannot get species root from itemStack that is not a valid serum.");
		return geneItem.getSpeciesRoot();
	}

	@Override
	public IGene getGene(final ItemStack stack, final int chromosome) {
		GeneItem geneItem = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneItem, "Cannot get gene from itemStack that is not a valid serum.");
		return geneItem.getGene();
	}

	@Override
	@Nullable
	public GeneItem getGeneItem(final ItemStack itemStack) {
		return GeneItem.create(itemStack);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		final GeneItem gene = this.getGeneItem(itemstack);
		if (gene != null) {
			BreedingSystem system = Binnie.GENETICS.getSystem(gene.getSpeciesRoot());
			return system.getDescriptor() + " " + Binnie.LANGUAGE.localise("genetic.item.gene.serum");
		} else {
			return Binnie.LANGUAGE.localise("genetic.item.gene.corrupted.serum");
		}
	}

	@Override
	public ItemStack addGene(final ItemStack stack, final IGene gene) {
		final IGeneItem geneI = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneI, "Cannot add gene to itemStack that is not a valid serum.");
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
