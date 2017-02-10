package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneArrayItem;
import binnie.genetics.genetics.IGeneItem;
import com.google.common.base.Preconditions;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSerumArray extends ItemGene implements IItemSerum {
	public ItemSerumArray() {
		super("serum_array");
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
	public IGene[] getGenes(final ItemStack stack) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getGenes().toArray(new IGene[0]);
		}
		return new IGene[0];
	}

	@Override
	@Nullable
	public ISpeciesRoot getSpeciesRoot(final ItemStack stack) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getSpeciesRoot();
		}
		return null;
	}

	@Override
	@Nullable
	public IGene getGene(final ItemStack stack, final int chromosome) {
		GeneArrayItem geneItem = this.getGeneItem(stack);
		if (geneItem != null) {
			return geneItem.getGene(chromosome);
		} else {
			return null;
		}
	}

	@Override
	public GeneArrayItem getGeneItem(final ItemStack itemStack) {
		return new GeneArrayItem(itemStack);
	}

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> itemList) {
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
						item.addGene(new Gene(active, type, root));
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
		GeneArrayItem geneItem = getGeneItem(itemstack);
		if (geneItem != null) {
			ISpeciesRoot speciesRoot = geneItem.getSpeciesRoot();
			if (speciesRoot != null) {
				BreedingSystem system = Binnie.GENETICS.getSystem(speciesRoot);
				return system.getDescriptor() + " Serum Array";
			}
		}
		return "Corrupted Serum Array";
	}

	@Override
	public ItemStack addGene(final ItemStack stack, final IGene gene) {
		final IGeneItem geneItem = this.getGeneItem(stack);
		Preconditions.checkNotNull(geneItem, "Cannot add gene to itemStack that is not a valid serum array.");
		geneItem.addGene(gene);
		geneItem.writeToItem(stack);
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
