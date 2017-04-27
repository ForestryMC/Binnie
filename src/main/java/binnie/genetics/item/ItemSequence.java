package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemAnalysable;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.SequencerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSequence extends Item implements IItemAnalysable, IItemChargable {
	public ItemSequence() {
		setMaxStackSize(1);
		setMaxDamage(5);
		setUnlocalizedName("sequence");
		setCreativeTab(CreativeTabGenetics.instance);
	}

	public static ItemStack create(IGene gene) {
		return create(gene, false);
	}

	public static ItemStack create(IGene gene, boolean sequenced) {
		ItemStack item = new ItemStack(Genetics.itemSequencer);
		item.setItemDamage(sequenced ? 0 : item.getMaxDamage());
		SequencerItem seq = new SequencerItem(gene);
		seq.writeToItem(item);
		return item;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		GeneItem gene = new GeneItem(itemstack);
		if (gene.isCorrupted()) {
			return "Corrupted Sequence";
		}
		return gene.getBreedingSystem().getDescriptor() + " DNA Sequence";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
		super.addInformation(itemstack, entityPlayer, list, par4);
		list.add(Genetics.proxy.localise("item.sequence." + (5 - itemstack.getItemDamage() % 6)));
		SequencerItem gene = new SequencerItem(itemstack);
		if (gene.isCorrupted()) {
			return;
		}

		if (gene.analysed) {
			gene.getInfo(list);
		} else {
			list.add("<Unknown>");
		}

		int seq = gene.sequenced;
		if (seq == 0) {
			list.add("Unsequenced");
		} else if (seq < 100) {
			list.add("Partially Sequenced (" + seq + "%)");
		} else {
			list.add("Fully Sequenced");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = Genetics.proxy.getIcon(register, "sequencer");
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
		IAlleleBeeSpecies species = (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesMeadows");
		itemList.add(create(new Gene(species, EnumBeeChromosome.SPECIES, Binnie.Genetics.getBeeRoot()), false));
	}

	@Override
	public boolean isAnalysed(ItemStack stack) {
		SequencerItem seq = new SequencerItem(stack);
		return seq.isCorrupted() || seq.analysed;
	}

	@Override
	public ItemStack analyse(ItemStack stack) {
		SequencerItem seq = new SequencerItem(stack);
		seq.analysed = true;
		seq.writeToItem(stack);
		return stack;
	}

	@Override
	public float getAnalyseTimeMult(ItemStack stack) {
		return 1.0f;
	}

	@Override
	public int getCharges(ItemStack stack) {
		return stack.getItem().getMaxDamage() - stack.getItemDamage();
	}
}
