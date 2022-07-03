package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.effect.BlockEctoplasm;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.genetics.items.ItemDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.IGenome;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModuleGenetics implements IInitializable {
    public static IGenome getGenome(IAlleleBeeSpecies allele0) {
        return Binnie.Genetics.getBeeRoot()
                .templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(allele0.getUID()));
    }

    @Override
    public void preInit() {
        ExtraBees.dictionary = new ItemDictionary();

        ExtraBees.ectoplasm = new BlockEctoplasm();
        GameRegistry.registerBlock(ExtraBees.ectoplasm, "ectoplasm");
    }

    @Override
    public void init() {
        ExtraBeesEffect.doInit();
        ExtraBeesFlowers.doInit();
        ExtraBeeDefinition.initBees();
    }

    @Override
    public void postInit() {
        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(ExtraBees.dictionary),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.gold_ingot,
                'Y',
                "ingotTin",
                'R',
                Items.redstone,
                'D',
                Items.diamond,
                'E',
                Items.emerald);
    }
}
