package binnie.extratrees.genetics;

import binnie.Binnie;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtraTreeMutation implements ITreeMutation {
	int chance;
	boolean isSecret;
	IAlleleTreeSpecies allele0;
	IAlleleTreeSpecies allele1;
	IAllele[] template;
	private float minTemperature;
	private float maxTemperature;
	private float minRainfall;
	private float maxRainfall;
	private float height;

	public static void init() {
		final IAlleleTreeSpecies lemon = (IAlleleTreeSpecies) getVanilla("Lemon");
		new ExtraTreeMutation(getVanilla("Cherry"), lemon, ExtraTreeSpecies.KeyLime.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.KeyLime.getSpecies(), lemon, ExtraTreeSpecies.FingerLime.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), lemon, ExtraTreeSpecies.Pomelo.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Pomelo.getSpecies(), getVanilla("Cherry"), ExtraTreeSpecies.Manderin.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Pomelo.getSpecies(), lemon, ExtraTreeSpecies.Citron.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Manderin.getSpecies(), getVanilla("Cherry"), ExtraTreeSpecies.Kumquat.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Pomelo.getSpecies(), ExtraTreeSpecies.Manderin.getSpecies(), ExtraTreeSpecies.Orange.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Citron.getSpecies(), ExtraTreeSpecies.Manderin.getSpecies(), ExtraTreeSpecies.BuddhaHand.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Kumquat.getSpecies(), ExtraTreeSpecies.Manderin.getSpecies(), ExtraTreeSpecies.Tangerine.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Kumquat.getSpecies(), ExtraTreeSpecies.Manderin.getSpecies(), ExtraTreeSpecies.Satsuma.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Pomelo.getSpecies(), ExtraTreeSpecies.Orange.getSpecies(), ExtraTreeSpecies.Grapefruit.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Pomelo.getSpecies(), ExtraTreeSpecies.KeyLime.getSpecies(), ExtraTreeSpecies.Lime.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Oak"), getVanilla("Cherry"), ExtraTreeSpecies.OrchardApple.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple.getSpecies(), getVanilla("Maple"), ExtraTreeSpecies.SweetCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple.getSpecies(), ExtraTreeSpecies.SweetCrabapple.getSpecies(), ExtraTreeSpecies.FloweringCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple.getSpecies(), getVanilla("Birch"), ExtraTreeSpecies.PrairieCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.OrchardApple.getSpecies(), ExtraTreeSpecies.Blackthorn.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Cherry"), ExtraTreeSpecies.CherryPlum.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Chestnut"), ExtraTreeSpecies.Peach.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.Peach.getSpecies(), ExtraTreeSpecies.Nectarine.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ExtraTreeSpecies.Peach.getSpecies(), ExtraTreeSpecies.Apricot.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Walnut"), ExtraTreeSpecies.Almond.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Cherry"), ExtraTreeSpecies.WildCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Willow"), getVanilla("Cherry"), ExtraTreeSpecies.SourCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Cherry"), ExtraTreeSpecies.BlackCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ExtraTreeSpecies.Banana.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Banana.getSpecies(), getVanilla("Kapok"), ExtraTreeSpecies.RedBanana.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Banana.getSpecies(), getVanilla("Teak"), ExtraTreeSpecies.Plantain.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Birch"), getVanilla("Oak"), ExtraTreeSpecies.Beech.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Birch"), ExtraTreeSpecies.Beech.getSpecies(), ExtraTreeSpecies.Alder.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Beech.getSpecies(), ExtraTreeSpecies.Aspen.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Aspen.getSpecies(), ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Rowan.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Beech.getSpecies(), ExtraTreeSpecies.Aspen.getSpecies(), ExtraTreeSpecies.Hazel.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Beech.getSpecies(), ExtraTreeSpecies.Rowan.getSpecies(), ExtraTreeSpecies.Hawthorn.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Aspen.getSpecies(), ExtraTreeSpecies.Elder.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Rowan.getSpecies(), ExtraTreeSpecies.Holly.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Willow"), ExtraTreeSpecies.Aspen.getSpecies(), ExtraTreeSpecies.Sallow.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Beech.getSpecies(), getVanilla("Birch"), ExtraTreeSpecies.Pecan.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Beech.getSpecies(), getVanilla("Spruce"), ExtraTreeSpecies.CopperBeech.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Spruce"), ExtraTreeSpecies.Ash.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Ash.getSpecies(), getVanilla("Birch"), ExtraTreeSpecies.Whitebeam.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Ash.getSpecies(), getVanilla("Pine"), ExtraTreeSpecies.Elm.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Ash.getSpecies(), getVanilla("Larch"), ExtraTreeSpecies.Hornbeam.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Ash.getSpecies(), getVanilla("Maple"), ExtraTreeSpecies.Sycamore.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Larch"), getVanilla("Spruce"), ExtraTreeSpecies.Yew.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Larch"), ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.BalsamFir.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), ExtraTreeSpecies.BalsamFir.getSpecies(), ExtraTreeSpecies.Fir.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), ExtraTreeSpecies.Fir.getSpecies(), ExtraTreeSpecies.Hemlock.getSpecies(), 10).setHeight(80);
		new ExtraTreeMutation(ExtraTreeSpecies.Fir.getSpecies(), getVanilla("Larch"), ExtraTreeSpecies.Cedar.getSpecies(), 10).setHeight(60);
		new ExtraTreeMutation(ExtraTreeSpecies.Fir.getSpecies(), getVanilla("Spruce"), ExtraTreeSpecies.DouglasFir.getSpecies(), 10).setHeight(60);
		new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ExtraTreeSpecies.Cypress.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ExtraTreeSpecies.LoblollyPine.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Cherry"), ExtraTreeSpecies.Butternut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Oak"), ExtraTreeSpecies.AcornOak.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Olive.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Lime"), ExtraTreeSpecies.RedMaple.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Larch"), ExtraTreeSpecies.Sweetgum.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Lime"), ExtraTreeSpecies.Locust.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Teak"), ExtraTreeSpecies.Iroko.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.OrchardApple.getSpecies(), getVanilla("Birch"), ExtraTreeSpecies.Pear.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.OldFustic.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.OldFustic.getSpecies(), getVanilla("Kapok"), ExtraTreeSpecies.OsangeOsange.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.Brazilwood.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Brazilwood.getSpecies(), getVanilla("Kapok"), ExtraTreeSpecies.Purpleheart.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ExtraTreeSpecies.Rosewood.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Rosewood.getSpecies(), getVanilla("Kapok"), ExtraTreeSpecies.Logwood.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Lime"), ExtraTreeSpecies.Gingko.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Beech.getSpecies(), getVanilla("Jungle"), ExtraTreeSpecies.Brazilnut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ExtraTreeSpecies.RoseGum.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.RoseGum.getSpecies(), getVanilla("Mahogony"), ExtraTreeSpecies.SwampGum.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Coffee.getSpecies(), getVanilla("Teak"), ExtraTreeSpecies.Clove.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), getVanilla("Jungle"), ExtraTreeSpecies.Coffee.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Holly.getSpecies(), ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.Box.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Hemlock.getSpecies(), getVanilla("Jungle"), ExtraTreeSpecies.MonkeyPuzzle.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.RoseGum.getSpecies(), getVanilla("Balsa"), ExtraTreeSpecies.RainbowGum.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.RoseGum.getSpecies(), ExtraTreeSpecies.Brazilwood.getSpecies(), ExtraTreeSpecies.PinkIvory.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Elder.getSpecies(), ExtraTreeSpecies.Raspberry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ExtraTreeSpecies.Elder.getSpecies(), ExtraTreeSpecies.Redcurrant.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.BlackCherry.getSpecies(), ExtraTreeSpecies.Redcurrant.getSpecies(), ExtraTreeSpecies.Blackcurrant.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.BlackCherry.getSpecies(), ExtraTreeSpecies.Raspberry.getSpecies(), ExtraTreeSpecies.Blackberry.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Blackberry.getSpecies(), ExtraTreeSpecies.Raspberry.getSpecies(), ExtraTreeSpecies.Blueberry.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Blackberry.getSpecies(), ExtraTreeSpecies.CherryPlum.getSpecies(), ExtraTreeSpecies.Cranberry.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Raspberry.getSpecies(), ExtraTreeSpecies.Fir.getSpecies(), ExtraTreeSpecies.Juniper.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Raspberry.getSpecies(), ExtraTreeSpecies.Lime.getSpecies(), ExtraTreeSpecies.Gooseberry.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Raspberry.getSpecies(), ExtraTreeSpecies.Orange.getSpecies(), ExtraTreeSpecies.GoldenRaspberry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Rosewood.getSpecies(), ExtraTreeSpecies.Cinnamon.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), ExtraTreeSpecies.Brazilnut.getSpecies(), ExtraTreeSpecies.Coconut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), getVanilla("Oak"), ExtraTreeSpecies.Cashew.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Oak"), ExtraTreeSpecies.Avacado.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Clove.getSpecies(), ExtraTreeSpecies.Nutmeg.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ExtraTreeSpecies.Clove.getSpecies(), ExtraTreeSpecies.Allspice.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Allspice.getSpecies(), ExtraTreeSpecies.Clove.getSpecies(), ExtraTreeSpecies.StarAnise.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Jungle"), ExtraTreeSpecies.Orange.getSpecies(), ExtraTreeSpecies.Mango.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.StarAnise.getSpecies(), ExtraTreeSpecies.Mango.getSpecies(), ExtraTreeSpecies.Starfruit.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Hazel.getSpecies(), ExtraTreeSpecies.Gingko.getSpecies(), ExtraTreeSpecies.Candlenut.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Hazel.getSpecies(), ExtraTreeSpecies.Gingko.getSpecies(), ExtraTreeSpecies.Chilli.getSpecies(), 10);
		new ExtraTreeMutation(ExtraTreeSpecies.Hazel.getSpecies(), ExtraTreeSpecies.Alder.getSpecies(), ExtraTreeSpecies.DwarfHazel.getSpecies(), 10);
	}

	public static IAllele getVanilla(final String uid) {
		final IAllele allele = AlleleManager.alleleRegistry.getAllele("forestry.tree" + uid);
		if (allele == null) {
			throw new RuntimeException("No forestry species with id " + uid);
		}
		return allele;
	}

	public ExtraTreeMutation(final IAllele allele0, final IAllele allele1, final IAllele result, final int chance) {
		this(allele0, allele1, Binnie.Genetics.getTreeRoot().getTemplate(result.getUID()), chance);
	}

	public ExtraTreeMutation(final IAllele allele0, final IAllele allele1, final IAllele[] template, final int chance) {
		this.isSecret = false;
		this.minTemperature = 0.0f;
		this.maxTemperature = 2.0f;
		this.minRainfall = 0.0f;
		this.maxRainfall = 2.0f;
		this.height = -1.0f;
		this.allele0 = (IAlleleTreeSpecies) allele0;
		this.allele1 = (IAlleleTreeSpecies) allele1;
		this.template = template;
		this.chance = chance;
		Binnie.Genetics.getTreeRoot().registerMutation(this);
	}

	public ExtraTreeMutation setIsSecret() {
		this.isSecret = true;
		return this;
	}

	public ExtraTreeMutation setTemperature(final float minTemperature, final float maxTemperature) {
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		return this;
	}

	public ExtraTreeMutation setRainfall(final float minRainfall, final float maxRainfall) {
		this.minRainfall = minRainfall;
		this.maxRainfall = maxRainfall;
		return this;
	}

	public ExtraTreeMutation setTemperatureRainfall(final float minTemperature, final float maxTemperature, final float minRainfall, final float maxRainfall) {
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.minRainfall = minRainfall;
		this.maxRainfall = maxRainfall;
		return this;
	}

	public ExtraTreeMutation setHeight(final int minHeight) {
		this.height = minHeight;
		return this;
	}

	@Override
	public IAlleleTreeSpecies getAllele0() {
		return this.allele0;
	}

	@Override
	public IAlleleTreeSpecies getAllele1() {
		return this.allele1;
	}

	@Override
	public float getBaseChance() {
		return this.chance;
	}

	@Override
	public IAllele[] getTemplate() {
		return this.template;
	}

	@Override
	public boolean isPartner(final IAllele allele) {
		return this.allele0.getUID().equals(allele.getUID()) || this.allele1.getUID().equals(allele.getUID());
	}

	@Override
	public IAllele getPartner(final IAllele allele) {
		if (this.allele0.getUID().equals(allele.getUID())) {
			return this.allele1;
		}
		return this.allele0;
	}

	@Override
	public boolean isSecret() {
		return this.isSecret;
	}

	@Override
	public Collection<String> getSpecialConditions() {
		final List<String> conditions = new ArrayList<>();
		if (this.height > 0.0f) {
			conditions.add("Minimum height from bedrock of " + this.height);
		}
		return conditions;
	}

	@Override
	public ITreeRoot getRoot() {
		return Binnie.Genetics.getTreeRoot();
	}

	@Override
	public float getChance(World world, BlockPos pos, IAlleleTreeSpecies allele0, IAlleleTreeSpecies allele1, ITreeGenome genome0, ITreeGenome genome1) {
		final int processedChance = this.chance;
		final Biome biome = world.getBiome(pos);//WorldChunkManager().getBiomeGenAt(x, z);
		if (biome.getTemperature() < this.minTemperature || biome.getTemperature() > this.maxTemperature) {
			return 0.0f;
		}
		if (biome.getRainfall() < this.minRainfall || biome.getRainfall() > this.maxRainfall) {
			return 0.0f;
		}
		if (this.height > 0.0f && pos.getY() < this.height) {
			return 0.0f;
		}
		if (this.allele0.getUID().equals(allele0.getUID()) && this.allele1.getUID().equals(allele1.getUID())) {
			return processedChance;
		}
		if (this.allele1.getUID().equals(allele0.getUID()) && this.allele0.getUID().equals(allele1.getUID())) {
			return processedChance;
		}
		return 0.0f;
	}


}
