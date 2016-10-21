package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.effect.BlockEctoplasm;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.genetics.items.ItemDictionary;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IGenome;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleGenetics implements IInitializable {
    @Override
    public void preInit() {
        for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele(species);
        }
        ExtraBees.dictionary = new ItemDictionary();
        GameRegistry.register(ExtraBees.ectoplasm = new BlockEctoplasm());
        GameRegistry.register(new ItemBlock(ExtraBees.ectoplasm).setRegistryName(ExtraBees.ectoplasm.getRegistryName()));
    }

    @Override
    public void init() {
        ExtraBeesEffect.doInit();
        ExtraBeesFlowers.doInit();
        ExtraBeesSpecies.doInit();
        ExtraBeeMutation.doInit();
        ExtraBeesBranch.doInit();
    }

    @Override
    public void postInit() {
        int ebSpeciesCount = 0;
        int ebTotalSpeciesCount = 0;
        for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
            ++ebTotalSpeciesCount;
            if (!AlleleManager.alleleRegistry.isBlacklisted(species.getUID())) {
                ++ebSpeciesCount;
            }
        }
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), null, new ItemStack(ExtraBees.dictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotTin", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
    }

    public static IGenome getGenome(final IAlleleBeeSpecies allele0) {
        return Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(allele0.getUID()));
    }

//	public static ItemStack getBeeIcon(final IAlleleBeeSpecies species) {
//		if (species == null) {
//			return null;
//		}
//		final IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
//		if (template == null) {
//			return null;
//		}
//		final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
//		final IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
//		final ItemStack item = Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.PRINCESS);
//		return item;
//	}
}
