package binnie.genetics.item;

import com.google.common.base.Preconditions;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.core.item.ItemCore;
import binnie.core.util.I18N;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemAnalysable;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.SequencerItem;

public class ItemSequence extends ItemCore implements IItemAnalysable, IItemChargeable {
	public ItemSequence() {
		super("sequence");
		this.setMaxStackSize(1);
		this.setMaxDamage(5);
		this.setCreativeTab(CreativeTabGenetics.instance);
	}

	public static ItemStack create(final IGene gene) {
		return create(gene, false);
	}

	public static ItemStack create(final IGene gene, final boolean sequenced) {
		final ItemStack item = new ItemStack(Genetics.items().itemSequencer);
		item.setItemDamage(sequenced ? 0 : item.getMaxDamage());
		final SequencerItem seq = new SequencerItem(gene);
		seq.writeToItem(item);
		return item;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GeneItem gene = GeneItem.create(itemstack);
		if (gene == null) {
			return I18N.localise("genetics.item.sequence.corrupted");
		} else {
			return gene.getBreedingSystem().getDescriptor() + " " + I18N.localise("genetics.item.sequence.name");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> subItems, boolean advanced) {
		subItems.add(I18N.localise("genetics.item.sequence." + (5 - itemStack.getItemDamage() % 6)));
		SequencerItem gene = SequencerItem.create(itemStack);
		if (gene != null) {
			if (gene.analysed) {
				gene.getInfo(subItems);
			} else {
				subItems.add("<" + I18N.localise("genetics.item.sequence.unknown") + ">");
			}
			int seq = gene.sequenced;
			if (seq == 0) {
				subItems.add(I18N.localise("genetics.item.sequence.unsequenced"));
			} else if (seq < 100) {
				subItems.add(I18N.localise("genetics.genetics.item.sequence.partially", seq));
			} else {
				subItems.add(I18N.localise("genetics.item.sequence.sequenced"));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		final IAlleleBeeSpecies species = (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesMeadows");
		Preconditions.checkNotNull(species);
		subItems.add(create(new Gene(species, EnumBeeChromosome.SPECIES, Binnie.GENETICS.getBeeRoot()), false));
	}

	@Override
	public boolean isAnalysed(final ItemStack stack) {
		final SequencerItem seq = SequencerItem.create(stack);
		return seq != null && seq.analysed;
	}

	@Override
	public ItemStack analyse(final ItemStack stack) {
		final SequencerItem seq = SequencerItem.create(stack);
		Preconditions.checkNotNull(seq, "Cannot analyze itemstack that is not a valid item sequence");
		seq.analysed = true;
		seq.writeToItem(stack);
		return stack;
	}

	@Override
	public float getAnalyseTimeMult(final ItemStack stack) {
		return 1.0f;
	}

	@Override
	public int getCharges(final ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	public int getMaxCharges(ItemStack stack) {
		return stack.getMaxDamage();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
		final SequencerItem seq = SequencerItem.create(stack);
		Preconditions.checkNotNull(seq, "Cannot getSpeciesRoot from itemstack that is not a valid item sequence");
		return seq.getSpeciesRoot();
	}
}
