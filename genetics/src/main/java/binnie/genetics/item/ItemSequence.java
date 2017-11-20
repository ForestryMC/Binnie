package binnie.genetics.item;

import javax.annotation.Nullable;

import binnie.core.ModId;
import com.google.common.base.Preconditions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import forestry.api.apiculture.BeeManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.genetics.Gene;
import binnie.core.item.ItemCore;
import binnie.core.util.I18N;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.core.api.genetics.IGene;
import binnie.core.api.genetics.IItemAnalysable;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.SequencerItem;

public class ItemSequence extends ItemCore implements IItemAnalysable, IItemChargeable {
	private final NumberFormat percentFormat = DecimalFormat.getPercentInstance(I18N.getLocale());

	public ItemSequence() {
		super("sequence");
		this.setMaxStackSize(1);
		this.setMaxDamage(5);
		this.setCreativeTab(CreativeTabGenetics.INSTANCE);
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
	public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18N.localise("genetics.item.sequence." + (5 - itemStack.getItemDamage() % 6)));
		SequencerItem gene = SequencerItem.create(itemStack);
		if (gene != null) {
			if (gene.isAnalysed()) {
				gene.getInfo(tooltip);
			} else {
				tooltip.add("<" + I18N.localise(ModId.GENETICS, "item.sequence.unknown") + ">");
			}
			int seq = gene.getSequenced();
			if (seq == 0) {
				tooltip.add(I18N.localise(ModId.GENETICS, "item.sequence.unsequenced"));
			} else if (seq < 100) {
				tooltip.add(I18N.localise(ModId.GENETICS, "item.sequence.partially", percentFormat.format(seq / 100.0)));
			} else {
				tooltip.add(I18N.localise(ModId.GENETICS, "item.sequence.sequenced"));
			}
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			final IAlleleBeeSpecies species = (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesMeadows");
			Preconditions.checkNotNull(species);
			items.add(create(new Gene(species, EnumBeeChromosome.SPECIES, BeeManager.beeRoot), false));
		}
	}

	@Override
	public boolean isAnalysed(final ItemStack stack) {
		final SequencerItem seq = SequencerItem.create(stack);
		return seq != null && seq.isAnalysed();
	}

	@Override
	public ItemStack analyse(final ItemStack stack) {
		final SequencerItem seq = SequencerItem.create(stack);
		Preconditions.checkNotNull(seq, "Cannot analyze itemstack that is not a valid item sequence");
		seq.setAnalysed(true);
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
