package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.util.I18N;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ExtraTreeMutation implements ITreeMutation {
    protected int chance;
    protected boolean isSecret;
    protected IAlleleTreeSpecies allele0;
    protected IAlleleTreeSpecies allele1;
    protected IAllele[] template;

    private float minTemperature;
    private float maxTemperature;
    private float minRainfall;
    private float maxRainfall;
    private float height;

    public ExtraTreeMutation(IAllele allele0, IAllele allele1, IAllele result, int chance) {
        this(allele0, allele1, Binnie.Genetics.getTreeRoot().getTemplate(result.getUID()), chance);
    }

    public ExtraTreeMutation(IAllele allele0, IAllele allele1, IAllele[] template, int chance) {
        isSecret = false;
        minTemperature = 0.0f;
        maxTemperature = 2.0f;
        minRainfall = 0.0f;
        maxRainfall = 2.0f;
        height = -1.0f;
        this.allele0 = (IAlleleTreeSpecies) allele0;
        this.allele1 = (IAlleleTreeSpecies) allele1;
        this.template = template;
        this.chance = chance;
        Binnie.Genetics.getTreeRoot().registerMutation(this);
    }

    public static void init() {
        IAlleleTreeSpecies lemon = (IAlleleTreeSpecies) getVanilla("Lemon");
        new ExtraTreeMutation(getVanilla("Cherry"), lemon, ExtraTreeSpecies.KeyLime, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.KeyLime, lemon, ExtraTreeSpecies.FingerLime, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), lemon, ExtraTreeSpecies.Pomelo, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Pomelo, getVanilla("Cherry"), ExtraTreeSpecies.Manderin, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Pomelo, lemon, ExtraTreeSpecies.Citron, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Manderin, getVanilla("Cherry"), ExtraTreeSpecies.Kumquat, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Pomelo, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Orange, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Citron, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.BuddhaHand, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Kumquat, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Tangerine, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Kumquat, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Satsuma, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Pomelo, ExtraTreeSpecies.Orange, ExtraTreeSpecies.Grapefruit, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Pomelo, ExtraTreeSpecies.KeyLime, ExtraTreeSpecies.Lime, 10);
        new ExtraTreeMutation(getVanilla("Oak"), getVanilla("Cherry"), ExtraTreeSpecies.OrchardApple, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple, getVanilla("Maple"), ExtraTreeSpecies.SweetCrabapple, 10);
        new ExtraTreeMutation(
                ExtraTreeSpecies.OrchardApple,
                ExtraTreeSpecies.SweetCrabapple,
                ExtraTreeSpecies.FloweringCrabapple,
                10);
        new ExtraTreeMutation(
                ExtraTreeSpecies.OrchardApple, getVanilla("Birch"), ExtraTreeSpecies.PrairieCrabapple, 10);
        new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.OrchardApple, ExtraTreeSpecies.Blackthorn, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Cherry"), ExtraTreeSpecies.CherryPlum, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Chestnut"), ExtraTreeSpecies.Peach, 10);
        new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.Peach, ExtraTreeSpecies.Nectarine, 10);
        new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.Peach, ExtraTreeSpecies.Apricot, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Walnut"), ExtraTreeSpecies.Almond, 10);
        new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Cherry"), ExtraTreeSpecies.WildCherry, 10);
        new ExtraTreeMutation(getVanilla("Willow"), getVanilla("Cherry"), ExtraTreeSpecies.SourCherry, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Cherry"), ExtraTreeSpecies.BlackCherry, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ExtraTreeSpecies.Banana, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Banana, getVanilla("Kapok"), ExtraTreeSpecies.RedBanana, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Banana, getVanilla("Teak"), ExtraTreeSpecies.Plantain, 10);
        new ExtraTreeMutation(getVanilla("Birch"), getVanilla("Oak"), ExtraTreeSpecies.Beech, 10);
        new ExtraTreeMutation(getVanilla("Birch"), ExtraTreeSpecies.Beech, ExtraTreeSpecies.Alder, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Alder, ExtraTreeSpecies.Beech, ExtraTreeSpecies.Aspen, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Aspen, ExtraTreeSpecies.Alder, ExtraTreeSpecies.Rowan, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Beech, ExtraTreeSpecies.Aspen, ExtraTreeSpecies.Hazel, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Beech, ExtraTreeSpecies.Rowan, ExtraTreeSpecies.Hawthorn, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Alder, ExtraTreeSpecies.Aspen, ExtraTreeSpecies.Elder, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Alder, ExtraTreeSpecies.Rowan, ExtraTreeSpecies.Holly, 10);
        new ExtraTreeMutation(getVanilla("Willow"), ExtraTreeSpecies.Aspen, ExtraTreeSpecies.Sallow, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Beech, getVanilla("Birch"), ExtraTreeSpecies.Pecan, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Beech, getVanilla("Spruce"), ExtraTreeSpecies.CopperBeech, 10);
        new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Spruce"), ExtraTreeSpecies.Ash, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Ash, getVanilla("Birch"), ExtraTreeSpecies.Whitebeam, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Ash, getVanilla("Pine"), ExtraTreeSpecies.Elm, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Ash, getVanilla("Larch"), ExtraTreeSpecies.Hornbeam, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Ash, getVanilla("Maple"), ExtraTreeSpecies.Sycamore, 10);
        new ExtraTreeMutation(getVanilla("Larch"), getVanilla("Spruce"), ExtraTreeSpecies.Yew, 10);
        new ExtraTreeMutation(getVanilla("Larch"), ExtraTreeSpecies.Alder, ExtraTreeSpecies.BalsamFir, 10);
        new ExtraTreeMutation(getVanilla("Pine"), ExtraTreeSpecies.BalsamFir, ExtraTreeSpecies.Fir, 10);
        new ExtraTreeMutation(getVanilla("Pine"), ExtraTreeSpecies.Fir, ExtraTreeSpecies.Hemlock, 10).setHeight(80);
        new ExtraTreeMutation(ExtraTreeSpecies.Fir, getVanilla("Larch"), ExtraTreeSpecies.Cedar, 10).setHeight(60);
        new ExtraTreeMutation(ExtraTreeSpecies.Fir, getVanilla("Spruce"), ExtraTreeSpecies.DouglasFir, 10)
                .setHeight(60);
        new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ExtraTreeSpecies.Cypress, 10);
        new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ExtraTreeSpecies.LoblollyPine, 10);
        new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Cherry"), ExtraTreeSpecies.Butternut, 10);
        new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Oak"), ExtraTreeSpecies.AcornOak, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Alder, ExtraTreeSpecies.Olive, 10);
        new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Lime"), ExtraTreeSpecies.RedMaple, 10);
        new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Larch"), ExtraTreeSpecies.Sweetgum, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Lime"), ExtraTreeSpecies.Locust, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Teak"), ExtraTreeSpecies.Iroko, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple, getVanilla("Birch"), ExtraTreeSpecies.Pear, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.OldFustic, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.OldFustic, getVanilla("Kapok"), ExtraTreeSpecies.OsangeOsange, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.Brazilwood, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Brazilwood, getVanilla("Kapok"), ExtraTreeSpecies.Purpleheart, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.Rosewood, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Rosewood, getVanilla("Kapok"), ExtraTreeSpecies.Logwood, 10);
        new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Lime"), ExtraTreeSpecies.Gingko, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Beech, getVanilla("Jungle"), ExtraTreeSpecies.Brazilnut, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ExtraTreeSpecies.RoseGum, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.RoseGum, getVanilla("Mahogony"), ExtraTreeSpecies.SwampGum, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Coffee, getVanilla("Teak"), ExtraTreeSpecies.Clove, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), getVanilla("Jungle"), ExtraTreeSpecies.Coffee, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Holly, ExtraTreeSpecies.Alder, ExtraTreeSpecies.Box, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Hemlock, getVanilla("Jungle"), ExtraTreeSpecies.MonkeyPuzzle, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.RoseGum, getVanilla("Balsa"), ExtraTreeSpecies.RainbowGum, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.RoseGum, ExtraTreeSpecies.Brazilwood, ExtraTreeSpecies.PinkIvory, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Elder, ExtraTreeSpecies.Raspberry, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Elder, ExtraTreeSpecies.Redcurrant, 10);
        new ExtraTreeMutation(
                ExtraTreeSpecies.BlackCherry, ExtraTreeSpecies.Redcurrant, ExtraTreeSpecies.Blackcurrant, 10);
        new ExtraTreeMutation(
                ExtraTreeSpecies.BlackCherry, ExtraTreeSpecies.Raspberry, ExtraTreeSpecies.Blackberry, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Blackberry, ExtraTreeSpecies.Raspberry, ExtraTreeSpecies.Blueberry, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Blackberry, ExtraTreeSpecies.CherryPlum, ExtraTreeSpecies.Cranberry, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Raspberry, ExtraTreeSpecies.Fir, ExtraTreeSpecies.Juniper, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Raspberry, ExtraTreeSpecies.Lime, ExtraTreeSpecies.Gooseberry, 10);
        new ExtraTreeMutation(
                ExtraTreeSpecies.Raspberry, ExtraTreeSpecies.Orange, ExtraTreeSpecies.GoldenRaspberry, 10);
        new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Rosewood, ExtraTreeSpecies.Cinnamon, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), ExtraTreeSpecies.Brazilnut, ExtraTreeSpecies.Coconut, 10);
        new ExtraTreeMutation(getVanilla("Teak"), getVanilla("Oak"), ExtraTreeSpecies.Cashew, 10);
        new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Oak"), ExtraTreeSpecies.Avacado, 10);
        new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Clove, ExtraTreeSpecies.Nutmeg, 10);
        new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Clove, ExtraTreeSpecies.Allspice, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Allspice, ExtraTreeSpecies.Clove, ExtraTreeSpecies.StarAnise, 10);
        new ExtraTreeMutation(getVanilla("Jungle"), ExtraTreeSpecies.Orange, ExtraTreeSpecies.Mango, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.StarAnise, ExtraTreeSpecies.Mango, ExtraTreeSpecies.Starfruit, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Hazel, ExtraTreeSpecies.Gingko, ExtraTreeSpecies.Candlenut, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Hazel, ExtraTreeSpecies.Gingko, ExtraTreeSpecies.Chilli, 10);
        new ExtraTreeMutation(ExtraTreeSpecies.Hazel, ExtraTreeSpecies.Alder, ExtraTreeSpecies.DwarfHazel, 10);
    }

    public static IAllele getVanilla(String uid) {
        IAllele allele = AlleleManager.alleleRegistry.getAllele("forestry.tree" + uid);
        if (allele == null) {
            throw new RuntimeException("No forestry species with id " + uid);
        }
        return allele;
    }

    public ExtraTreeMutation setHeight(int minHeight) {
        height = minHeight;
        return this;
    }

    @Override
    public IAlleleTreeSpecies getAllele0() {
        return allele0;
    }

    @Override
    public IAlleleTreeSpecies getAllele1() {
        return allele1;
    }

    @Override
    public float getBaseChance() {
        return chance;
    }

    @Override
    public IAllele[] getTemplate() {
        return template;
    }

    @Override
    public boolean isPartner(IAllele allele) {
        return allele0.getUID().equals(allele.getUID()) || allele1.getUID().equals(allele.getUID());
    }

    @Override
    public IAllele getPartner(IAllele allele) {
        if (allele0.getUID().equals(allele.getUID())) {
            return allele1;
        }
        return allele0;
    }

    @Override
    public boolean isSecret() {
        return isSecret;
    }

    @Override
    public float getChance(
            World world,
            int x,
            int y,
            int z,
            IAlleleTreeSpecies allele0,
            IAlleleTreeSpecies allele1,
            ITreeGenome genome0,
            ITreeGenome genome1) {
        int processedChance = chance;
        BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
        if (biome.temperature < minTemperature || biome.temperature > maxTemperature) {
            return 0.0f;
        }
        if (biome.rainfall < minRainfall || biome.rainfall > maxRainfall) {
            return 0.0f;
        }
        if (height > 0.0f && y < height) {
            return 0.0f;
        }
        if (this.allele0.getUID().equals(allele0.getUID())
                && this.allele1.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        if (this.allele1.getUID().equals(allele0.getUID())
                && this.allele0.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        return 0.0f;
    }

    @Override
    public Collection<String> getSpecialConditions() {
        List<String> conditions = new ArrayList<>();
        if (height > 0.0f) {
            conditions.add(I18N.localise("extratrees.genetics.requirements.height", height));
        }
        return conditions;
    }

    @Override
    public ITreeRoot getRoot() {
        return Binnie.Genetics.getTreeRoot();
    }
}
