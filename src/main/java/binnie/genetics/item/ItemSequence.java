package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
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
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSequence extends Item implements IItemAnalysable, IItemChargable {
    public ItemSequence() {
        setMaxStackSize(1);
        setMaxDamage(5);
        setUnlocalizedName("sequence");
        setCreativeTab(GeneticsCreativeTab.instance);
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
    public String getItemStackDisplayName(ItemStack stack) {
        GeneItem gene = new GeneItem(stack);
        if (gene.isCorrupted()) {
            return I18N.localise("genetics.sequence.corrupted");
        }
        return I18N.localise(
                "genetics.sequence.descriptor", gene.getBreedingSystem().getDescriptor());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18N.localise("genetics.item.sequence." + (5 - stack.getItemDamage() % 6)));
        SequencerItem gene = new SequencerItem(stack);
        if (gene.isCorrupted()) {
            return;
        }

        if (gene.analysed) {
            gene.getInfo(tooltip);
        } else {
            tooltip.add(I18N.localise("genetics.sequence.unknown"));
        }

        int seq = gene.sequenced;
        if (seq == 0) {
            tooltip.add(I18N.localise("genetics.sequence.unsequenced"));
        } else if (seq < 100) {
            tooltip.add(I18N.localise("genetics.sequence.partially", seq));
        } else {
            tooltip.add(I18N.localise("genetics.sequence.fully"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Genetics.proxy.getIcon(register, "sequencer");
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        IAlleleBeeSpecies species =
                (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesMeadows");
        list.add(create(new Gene(species, EnumBeeChromosome.SPECIES, Binnie.Genetics.getBeeRoot()), false));
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
