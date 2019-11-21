package binnie.extratrees.genetics;

import binnie.core.Constants;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IAlleleButterflySpecies;
import forestry.api.lepidopterology.IAlleleButterflySpeciesBuilder;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyGenome;
import forestry.api.lepidopterology.IButterflyMutationBuilder;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.lepidopterology.genetics.Butterfly;
import forestry.lepidopterology.genetics.ButterflyDefinition;
import forestry.lepidopterology.genetics.IButterflyDefinition;
import forestry.lepidopterology.genetics.MothDefinition;
import net.minecraft.item.ItemStack;

import java.awt.Color;
import java.util.Arrays;

public enum ButterflySpecies implements IButterflyDefinition {
	White_Admiral("White Admiral", "Limenitis camilla", 16448250),
	Purple_Emperor("Purple Emperor", "Apatura iris", 4338374),
	Red_Admiral("Red Admiral", "Vanessa atalanta", 15101764),
	Painted_Lady("Painted Lady", "Vanessa cardui", 15573064),
	Small_Tortoiseshell("Small Tortoiseshell", "Aglais urticae", 15365387),
	Camberwell_Beauty("Camberwell Beauty", "Aglais antiopa", 9806540),
	Peacock("Peacock", "Inachis io", 13842434),
	Wall("Wall", "Lasiommata megera", 15707678),
	Crimson_Rose("Crimson Rose", "Atrophaneura hector", 16736891),
	Kaiser_I_Hind("Kaiser-i-Hind", "Teinopalpus imperialis", 7839808),
	Golden_Birdwing("Golden Birdwing", "Troides aeacus", 16374814),
	Marsh_Fritillary("Marsh Fritillary", "Euphydryas aurinia", 16747520),
	Pearl_Bordered_Fritillary("Pearl-bordered Fritillary", "Boloria euphrosyne", 16747267),
	Queen_Of_Spain_Fritillary("Queen of Spain Fritillary", "Issoria lathonia", 16765247),
	Speckled_Wood("Speckled Wood", "Pararge aegeria", 16119949),
	Scotch_Angus("Scotch Angus", "Erebia aethiops", 12735523),
	Gatekeeper("Gatekeeper", "Pyronia tithonus", 16433962),
	Meadow_Brown("Meadow Brown", "Maniola jurtina", 14914841),
	Small_Heath("Small Heath", "Coenonympha pamphilus", 16754226),
	Ringlet("Ringlet", "Aphantopus hyperantus", 9919799),
	Monarch("Monarch", "Danaus plexippus", 16757254),
	Marbled_White("Marbled White", "Melanargia galathea", 15527148);

	public static final ButterflySpecies[] VALUES = values();

	private final IAlleleButterflySpecies species;
	private final IClassification branch;
	private IAllele[] template;
	private IButterflyGenome genome;

	ButterflySpecies(String name, String scientific, int colour) {
		String branchName = scientific.split(" ")[0].toLowerCase();
		String binomial = scientific.split(" ")[1];

		final String branchUid = "trees." + branchName.toLowerCase();
		IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + branchUid);
		if (branch == null) {
			branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, branchUid, scientific);
		}
		this.branch = branch;

		String uid = "extrabutterflies.species." + this.toString().toLowerCase().replace("_", "");
		String unlocalizedName = uid + ".name";
		IClassification parent = branch.getParent();
		String unlocalizedDescription = "for.description." + uid;
		String texture = "butterflies/" + toString().toLowerCase();

		IAlleleButterflySpeciesBuilder speciesBuilder = ButterflyManager.butterflyFactory.createSpecies(uid, unlocalizedName, "Binnie", unlocalizedDescription, Constants.EXTRA_TREES_MOD_ID, texture, true, branch, binomial, new Color(colour));
		speciesBuilder.setRarity(0.5F);
		setSpeciesProperties(speciesBuilder);
		species = speciesBuilder.build();
	}

	public static void initButterflies() {
		for (ButterflySpecies butterfly : VALUES) {
			butterfly.init();
		}
		for (ButterflySpecies butterfly : VALUES) {
			butterfly.registerMutations();
		}
	}

	public static void preInit() {

	}

	private void init() {
		template = ButterflyManager.butterflyRoot.getDefaultTemplate();
		AlleleHelper.getInstance().set(template, EnumButterflyChromosome.SPECIES, species);
		setAlleles(template);

		genome = ButterflyManager.butterflyRoot.templateAsGenome(template);

		ButterflyManager.butterflyRoot.registerTemplate(template);
	}

	protected void setSpeciesProperties(IAlleleButterflySpeciesBuilder species) {

	}

	protected void setAlleles(IAllele[] alleles) {

	}

	protected void registerMutations() {

	}

	protected final IButterflyMutationBuilder registerMutation(IButterflyDefinition parent1, IButterflyDefinition parent2, int chance) {
		IAlleleButterflySpecies species1;
		IAlleleButterflySpecies species2;

		if (parent1 instanceof ButterflySpecies) {
			species1 = ((ButterflySpecies) parent1).species;
		} else if (parent1 instanceof ButterflyDefinition) {
			species1 = ((ButterflyDefinition) parent1).getSpecies();
		} else if (parent1 instanceof MothDefinition) {
			species1 = ((MothDefinition) parent1).getSpecies();
		} else {
			throw new IllegalArgumentException("Unknown parent1: " + parent1);
		}

		if (parent2 instanceof ButterflySpecies) {
			species2 = ((ButterflySpecies) parent2).species;
		} else if (parent2 instanceof ButterflyDefinition) {
			species2 = ((ButterflyDefinition) parent2).getSpecies();
		} else if (parent2 instanceof MothDefinition) {
			species2 = ((MothDefinition) parent2).getSpecies();
		} else {
			throw new IllegalArgumentException("Unknown parent2: " + parent2);
		}

		return ButterflyManager.butterflyMutationFactory.createMutation(species1, species2, getTemplate(), chance);
	}

	@Override
	public final IAllele[] getTemplate() {
		return Arrays.copyOf(template, template.length);
	}

	@Override
	public final IButterflyGenome getGenome() {
		return genome;
	}

	@Override
	public final IButterfly getIndividual() {
		return new Butterfly(genome);
	}

	@Override
	public final ItemStack getMemberStack(EnumFlutterType flutterType) {
		IButterfly butterfly = getIndividual();
		return ButterflyManager.butterflyRoot.getMemberStack(butterfly, flutterType);
	}

	public IAlleleButterflySpecies getSpecies() {
		return species;
	}
}
