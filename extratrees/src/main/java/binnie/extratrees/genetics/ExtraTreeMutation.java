package binnie.extratrees.genetics;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import forestry.api.arboriculture.TreeManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;

public class ExtraTreeMutation implements ITreeMutation {
	private final int chance;
	private boolean isSecret;
	private final IAlleleTreeSpecies allele0;
	private final IAlleleTreeSpecies allele1;
	private final IAllele[] template;
	private float minTemperature;
	private float maxTemperature;
	private float minRainfall;
	private float maxRainfall;
	private float height;

	public ExtraTreeMutation(final IAlleleSpecies allele0, final IAlleleSpecies allele1, final IAlleleSpecies result, final int chance) {
		this(allele0, allele1, TreeManager.treeRoot.getTemplate(result), chance);
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
		TreeManager.treeRoot.registerMutation(this);
	}

	public static void init() {
		final IAlleleTreeSpecies lemon = (IAlleleTreeSpecies) getVanilla("Lemon");
		new ExtraTreeMutation(getVanilla("Cherry"), lemon, ETTreeDefinition.KeyLime.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.KeyLime.getSpecies(), lemon, ETTreeDefinition.FingerLime.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), lemon, ETTreeDefinition.Pomelo.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Pomelo.getSpecies(), getVanilla("Cherry"), ETTreeDefinition.Manderin.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Pomelo.getSpecies(), lemon, ETTreeDefinition.Citron.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Manderin.getSpecies(), getVanilla("Cherry"), ETTreeDefinition.Kumquat.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Pomelo.getSpecies(), ETTreeDefinition.Manderin.getSpecies(), ETTreeDefinition.Orange.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Citron.getSpecies(), ETTreeDefinition.Manderin.getSpecies(), ETTreeDefinition.BuddhaHand.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Kumquat.getSpecies(), ETTreeDefinition.Manderin.getSpecies(), ETTreeDefinition.Tangerine.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Kumquat.getSpecies(), ETTreeDefinition.Manderin.getSpecies(), ETTreeDefinition.Satsuma.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Pomelo.getSpecies(), ETTreeDefinition.Orange.getSpecies(), ETTreeDefinition.Grapefruit.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Pomelo.getSpecies(), ETTreeDefinition.KeyLime.getSpecies(), ETTreeDefinition.Lime.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Oak"), getVanilla("Cherry"), ETTreeDefinition.OrchardApple.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.OrchardApple.getSpecies(), getVanilla("Maple"), ETTreeDefinition.SweetCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.OrchardApple.getSpecies(), ETTreeDefinition.SweetCrabapple.getSpecies(), ETTreeDefinition.FloweringCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.OrchardApple.getSpecies(), getVanilla("Birch"), ETTreeDefinition.PrairieCrabapple.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ETTreeDefinition.OrchardApple.getSpecies(), ETTreeDefinition.Blackthorn.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Cherry"), ETTreeDefinition.CherryPlum.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Chestnut"), ETTreeDefinition.Peach.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ETTreeDefinition.Peach.getSpecies(), ETTreeDefinition.Nectarine.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), ETTreeDefinition.Peach.getSpecies(), ETTreeDefinition.Apricot.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Walnut"), ETTreeDefinition.Almond.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Cherry"), ETTreeDefinition.WildCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Willow"), getVanilla("Cherry"), ETTreeDefinition.SourCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Cherry"), ETTreeDefinition.BlackCherry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ETTreeDefinition.Banana.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Banana.getSpecies(), getVanilla("Kapok"), ETTreeDefinition.RedBanana.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Banana.getSpecies(), getVanilla("Teak"), ETTreeDefinition.Plantain.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Birch"), getVanilla("Oak"), ETTreeDefinition.Beech.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Birch"), ETTreeDefinition.Beech.getSpecies(), ETTreeDefinition.Alder.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Beech.getSpecies(), ETTreeDefinition.Aspen.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Aspen.getSpecies(), ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Rowan.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Beech.getSpecies(), ETTreeDefinition.Aspen.getSpecies(), ETTreeDefinition.Hazel.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Beech.getSpecies(), ETTreeDefinition.Rowan.getSpecies(), ETTreeDefinition.Hawthorn.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Aspen.getSpecies(), ETTreeDefinition.Elder.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Rowan.getSpecies(), ETTreeDefinition.Holly.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Willow"), ETTreeDefinition.Aspen.getSpecies(), ETTreeDefinition.Sallow.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Beech.getSpecies(), getVanilla("Birch"), ETTreeDefinition.Pecan.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Beech.getSpecies(), getVanilla("Spruce"), ETTreeDefinition.CopperBeech.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Spruce"), ETTreeDefinition.Ash.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Ash.getSpecies(), getVanilla("Birch"), ETTreeDefinition.Whitebeam.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Ash.getSpecies(), getVanilla("Pine"), ETTreeDefinition.Elm.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Ash.getSpecies(), getVanilla("Larch"), ETTreeDefinition.Hornbeam.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Ash.getSpecies(), getVanilla("Maple"), ETTreeDefinition.Sycamore.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Larch"), getVanilla("Spruce"), ETTreeDefinition.Yew.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Larch"), ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.BalsamFir.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), ETTreeDefinition.BalsamFir.getSpecies(), ETTreeDefinition.Fir.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), ETTreeDefinition.Fir.getSpecies(), ETTreeDefinition.Hemlock.getSpecies(), 10).setHeight(80);
		new ExtraTreeMutation(ETTreeDefinition.Fir.getSpecies(), getVanilla("Larch"), ETTreeDefinition.Cedar.getSpecies(), 10).setHeight(60);
		new ExtraTreeMutation(ETTreeDefinition.Fir.getSpecies(), getVanilla("Spruce"), ETTreeDefinition.DouglasFir.getSpecies(), 10).setHeight(60);
		new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ETTreeDefinition.Cypress.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), ETTreeDefinition.LoblollyPine.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Cherry"), ETTreeDefinition.Butternut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Oak"), ETTreeDefinition.AcornOak.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Olive.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Lime"), ETTreeDefinition.RedMaple.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Larch"), ETTreeDefinition.Sweetgum.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Lime"), ETTreeDefinition.Locust.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Teak"), ETTreeDefinition.Iroko.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.OrchardApple.getSpecies(), getVanilla("Birch"), ETTreeDefinition.Pear.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ETTreeDefinition.OldFustic.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.OldFustic.getSpecies(), getVanilla("Kapok"), ETTreeDefinition.OsangeOrange.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ETTreeDefinition.Brazilwood.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Brazilwood.getSpecies(), getVanilla("Kapok"), ETTreeDefinition.Purpleheart.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), ETTreeDefinition.Rosewood.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Rosewood.getSpecies(), getVanilla("Kapok"), ETTreeDefinition.Logwood.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Lime"), ETTreeDefinition.Gingko.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Beech.getSpecies(), getVanilla("Jungle"), ETTreeDefinition.Brazilnut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), ETTreeDefinition.RoseGum.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.RoseGum.getSpecies(), getVanilla("Mahogony"), ETTreeDefinition.SwampGum.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Coffee.getSpecies(), getVanilla("Teak"), ETTreeDefinition.Clove.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), getVanilla("Jungle"), ETTreeDefinition.Coffee.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Holly.getSpecies(), ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.Box.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Hemlock.getSpecies(), getVanilla("Jungle"), ETTreeDefinition.MonkeyPuzzle.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.RoseGum.getSpecies(), getVanilla("Balsa"), ETTreeDefinition.RainbowGum.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.RoseGum.getSpecies(), ETTreeDefinition.Brazilwood.getSpecies(), ETTreeDefinition.PinkIvory.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ETTreeDefinition.Elder.getSpecies(), ETTreeDefinition.Raspberry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Cherry"), ETTreeDefinition.Elder.getSpecies(), ETTreeDefinition.Redcurrant.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.BlackCherry.getSpecies(), ETTreeDefinition.Redcurrant.getSpecies(), ETTreeDefinition.Blackcurrant.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.BlackCherry.getSpecies(), ETTreeDefinition.Raspberry.getSpecies(), ETTreeDefinition.Blackberry.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Blackberry.getSpecies(), ETTreeDefinition.Raspberry.getSpecies(), ETTreeDefinition.Blueberry.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Blackberry.getSpecies(), ETTreeDefinition.CherryPlum.getSpecies(), ETTreeDefinition.Cranberry.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Raspberry.getSpecies(), ETTreeDefinition.Fir.getSpecies(), ETTreeDefinition.Juniper.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Raspberry.getSpecies(), ETTreeDefinition.Lime.getSpecies(), ETTreeDefinition.Gooseberry.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Raspberry.getSpecies(), ETTreeDefinition.Orange.getSpecies(), ETTreeDefinition.GoldenRaspberry.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ETTreeDefinition.Rosewood.getSpecies(), ETTreeDefinition.Cinnamon.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Balsa"), ETTreeDefinition.Brazilnut.getSpecies(), ETTreeDefinition.Coconut.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), getVanilla("Oak"), ETTreeDefinition.Cashew.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Oak"), ETTreeDefinition.Avocado.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ETTreeDefinition.Clove.getSpecies(), ETTreeDefinition.Nutmeg.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Teak"), ETTreeDefinition.Clove.getSpecies(), ETTreeDefinition.Allspice.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Allspice.getSpecies(), ETTreeDefinition.Clove.getSpecies(), ETTreeDefinition.StarAnise.getSpecies(), 10);
		new ExtraTreeMutation(getVanilla("Jungle"), ETTreeDefinition.Orange.getSpecies(), ETTreeDefinition.Mango.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.StarAnise.getSpecies(), ETTreeDefinition.Mango.getSpecies(), ETTreeDefinition.Starfruit.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Hazel.getSpecies(), ETTreeDefinition.Gingko.getSpecies(), ETTreeDefinition.Candlenut.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Hazel.getSpecies(), ETTreeDefinition.Gingko.getSpecies(), ETTreeDefinition.Chilli.getSpecies(), 10);
		new ExtraTreeMutation(ETTreeDefinition.Hazel.getSpecies(), ETTreeDefinition.Alder.getSpecies(), ETTreeDefinition.DwarfHazel.getSpecies(), 10);
	}

	public static IAlleleSpecies getVanilla(final String uid) {
		String forestryUid = "forestry.tree" + uid;
		final IAllele allele = AlleleManager.alleleRegistry.getAllele(forestryUid);
		Preconditions.checkArgument(allele != null);
		Preconditions.checkArgument(allele instanceof IAlleleSpecies, "No Forestry species with uid: %s", forestryUid);
		return (IAlleleSpecies) allele;
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
		return TreeManager.treeRoot;
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
