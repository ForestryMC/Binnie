package binnie.extrabees.genetics;

import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import java.util.LinkedHashSet;
import java.util.Set;

public enum ExtraBeesBranch implements IClassification {
	BARREN("Vacapis"),
	HOSTILE("Infenapis"),
	ROCKY("Monapis"),
	METALLIC("Lamminapis"),
	METALLIC2("Metalapis"),
	ALLOY("Allapis"),
	PRECIOUS("Pluriapis"),
	MINERAL("Niphapis"),
	GEMSTONE("Gemmapis"),
	NUCLEAR("Levapis"),
	HISTORIC("Priscapis"),
	FOSSILIZED("Fosiapis"),
	REFINED("Petrapis"),
	AQUATIC("Aquapis"),
	SACCHARINE("Sacchapis"),
	CLASSICAL("Grecapis"),
	VOLCANIC("Irrapis"),
	VIRULENT("Virapis"),
	VISCOUS("Viscapis"),
	CAUSTIC("Morbapis"),
	ENERGETIC("Incitapis"),
	FARMING("Agriapis"),
	SHADOW("Pullapis"),
	PRIMARY("Primapis"),
	SECONDARY("Secapis"),
	TERTIARY("Tertiapis"),
	FTB("Eftebeapis"),
	QUANTUM("Quantapis"),
	BOTANIA("Botaniapis");

	protected IClassification parent;

	private String uid;
	private String scientific;
	private Set<IAlleleBeeSpecies> speciesSet;

	ExtraBeesBranch(String scientific) {
		this.scientific = scientific;
		speciesSet = new LinkedHashSet<>();
		uid = toString().toLowerCase();
	}

	public static void doInit() {
		IClassification frozenBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.frozen");
		if (frozenBranch != null) {
			frozenBranch.addMemberSpecies(ExtraBeesSpecies.ARTIC);
			ExtraBeesSpecies.ARTIC.setBranch(frozenBranch);
			frozenBranch.addMemberSpecies(ExtraBeesSpecies.FREEZING);
			ExtraBeesSpecies.FREEZING.setBranch(frozenBranch);
		}

		IClassification agrarianBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.agrarian");
		if (agrarianBranch != null) {
			agrarianBranch.addMemberSpecies(ExtraBeesSpecies.FARM);
			ExtraBeesSpecies.FARM.setBranch(agrarianBranch);
			ExtraBeesSpecies.GROWING.setBranch(agrarianBranch);
			ExtraBeesSpecies.THRIVING.setBranch(agrarianBranch);
			ExtraBeesSpecies.BLOOMING.setBranch(agrarianBranch);
		}

		IClassification boggyBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.boggy");
		if (boggyBranch != null) {
			boggyBranch.addMemberSpecies(ExtraBeesSpecies.SWAMP);
			boggyBranch.addMemberSpecies(ExtraBeesSpecies.BOGGY);
			boggyBranch.addMemberSpecies(ExtraBeesSpecies.FUNGAL);
			ExtraBeesSpecies.SWAMP.setBranch(boggyBranch);
			ExtraBeesSpecies.BOGGY.setBranch(boggyBranch);
			ExtraBeesSpecies.FUNGAL.setBranch(boggyBranch);
		}

		IClassification festiveBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.festive");
		if (festiveBranch != null) {
			festiveBranch.addMemberSpecies(ExtraBeesSpecies.CELEBRATORY);
			ExtraBeesSpecies.CELEBRATORY.setBranch(festiveBranch);
		}

		IClassification austereBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.austere");
		if (austereBranch != null) {
			austereBranch.addMemberSpecies(ExtraBeesSpecies.HAZARDOUS);
			ExtraBeesSpecies.HAZARDOUS.setBranch(austereBranch);
		}

		ExtraBeesBranch.FARMING.addMemberSpecies(ExtraBeesSpecies.MILK);
		ExtraBeesBranch.FARMING.addMemberSpecies(ExtraBeesSpecies.COFFEE);
		ExtraBeesBranch.FARMING.addMemberSpecies(ExtraBeesSpecies.CITRUS);
		ExtraBeesBranch.FARMING.addMemberSpecies(ExtraBeesSpecies.MINT);
		ExtraBeesBranch.FARMING.register();

		ExtraBeesBranch.BARREN.addMemberSpecies(ExtraBeesSpecies.ARID);
		ExtraBeesBranch.BARREN.addMemberSpecies(ExtraBeesSpecies.BARREN);
		ExtraBeesBranch.BARREN.addMemberSpecies(ExtraBeesSpecies.DESOLATE);
		ExtraBeesBranch.BARREN.addMemberSpecies(ExtraBeesSpecies.DECOMPOSING);
		ExtraBeesBranch.BARREN.addMemberSpecies(ExtraBeesSpecies.GNAWING);
		ExtraBeesBranch.BARREN.register();

		ExtraBeesBranch.HOSTILE.addMemberSpecies(ExtraBeesSpecies.ROTTEN);
		ExtraBeesBranch.HOSTILE.addMemberSpecies(ExtraBeesSpecies.BONE);
		ExtraBeesBranch.HOSTILE.addMemberSpecies(ExtraBeesSpecies.CREEPER);
		ExtraBeesBranch.HOSTILE.register();

		ExtraBeesBranch.ROCKY.addMemberSpecies(ExtraBeesSpecies.ROCK);
		ExtraBeesBranch.ROCKY.addMemberSpecies(ExtraBeesSpecies.STONE);
		ExtraBeesBranch.ROCKY.addMemberSpecies(ExtraBeesSpecies.GRANITE);
		ExtraBeesBranch.ROCKY.addMemberSpecies(ExtraBeesSpecies.MINERAL);
		ExtraBeesBranch.ROCKY.register();

		ExtraBeesBranch.METALLIC.addMemberSpecies(ExtraBeesSpecies.IRON);
		ExtraBeesBranch.METALLIC.addMemberSpecies(ExtraBeesSpecies.COPPER);
		ExtraBeesBranch.METALLIC.addMemberSpecies(ExtraBeesSpecies.TIN);
		ExtraBeesBranch.METALLIC.addMemberSpecies(ExtraBeesSpecies.LEAD);
		ExtraBeesBranch.METALLIC.register();

		ExtraBeesBranch.METALLIC2.addMemberSpecies(ExtraBeesSpecies.NICKEL);
		ExtraBeesBranch.METALLIC2.addMemberSpecies(ExtraBeesSpecies.ZINC);
		ExtraBeesBranch.METALLIC2.addMemberSpecies(ExtraBeesSpecies.TUNGSTATE);
		ExtraBeesBranch.METALLIC2.addMemberSpecies(ExtraBeesSpecies.TITANIUM);
		ExtraBeesBranch.METALLIC2.register();

		ExtraBeesBranch.ALLOY.addMemberSpecies(ExtraBeesSpecies.BRONZE);
		ExtraBeesBranch.ALLOY.addMemberSpecies(ExtraBeesSpecies.BRASS);
		ExtraBeesBranch.ALLOY.addMemberSpecies(ExtraBeesSpecies.STEEL);
		ExtraBeesBranch.ALLOY.addMemberSpecies(ExtraBeesSpecies.INVAR);
		ExtraBeesBranch.ALLOY.register();

		ExtraBeesBranch.PRECIOUS.addMemberSpecies(ExtraBeesSpecies.SILVER);
		ExtraBeesBranch.PRECIOUS.addMemberSpecies(ExtraBeesSpecies.GOLD);
		ExtraBeesBranch.PRECIOUS.addMemberSpecies(ExtraBeesSpecies.ELECTRUM);
		ExtraBeesBranch.PRECIOUS.addMemberSpecies(ExtraBeesSpecies.PLATINUM);
		ExtraBeesBranch.PRECIOUS.register();

		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.LAPIS);
		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.SODALITE);
		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.PYRITE);
		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.BAUXITE);
		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.CINNABAR);
		ExtraBeesBranch.MINERAL.addMemberSpecies(ExtraBeesSpecies.SPHALERITE);
		ExtraBeesBranch.MINERAL.register();

		ExtraBeesBranch.GEMSTONE.addMemberSpecies(ExtraBeesSpecies.EMERALD);
		ExtraBeesBranch.GEMSTONE.addMemberSpecies(ExtraBeesSpecies.RUBY);
		ExtraBeesBranch.GEMSTONE.addMemberSpecies(ExtraBeesSpecies.SAPPHIRE);
		ExtraBeesBranch.GEMSTONE.addMemberSpecies(ExtraBeesSpecies.OLIVINE);
		ExtraBeesBranch.GEMSTONE.addMemberSpecies(ExtraBeesSpecies.DIAMOND);
		ExtraBeesBranch.GEMSTONE.register();

		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.UNSTABLE);
		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.NUCLEAR);
		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.RADIOACTIVE);
		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.YELLORIUM);
		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.CYANITE);
		ExtraBeesBranch.NUCLEAR.addMemberSpecies(ExtraBeesSpecies.BLUTONIUM);
		ExtraBeesBranch.NUCLEAR.register();

		ExtraBeesBranch.HISTORIC.addMemberSpecies(ExtraBeesSpecies.ANCIENT);
		ExtraBeesBranch.HISTORIC.addMemberSpecies(ExtraBeesSpecies.PRIMEVAL);
		ExtraBeesBranch.HISTORIC.addMemberSpecies(ExtraBeesSpecies.PREHISTORIC);
		ExtraBeesBranch.HISTORIC.addMemberSpecies(ExtraBeesSpecies.RELIC);
		ExtraBeesBranch.HISTORIC.register();

		ExtraBeesBranch.FOSSILIZED.addMemberSpecies(ExtraBeesSpecies.COAL);
		ExtraBeesBranch.FOSSILIZED.addMemberSpecies(ExtraBeesSpecies.RESIN);
		ExtraBeesBranch.FOSSILIZED.addMemberSpecies(ExtraBeesSpecies.OIL);
		ExtraBeesBranch.FOSSILIZED.addMemberSpecies(ExtraBeesSpecies.PEAT);
		ExtraBeesBranch.FOSSILIZED.register();

		ExtraBeesBranch.REFINED.addMemberSpecies(ExtraBeesSpecies.DISTILLED);
		ExtraBeesBranch.REFINED.addMemberSpecies(ExtraBeesSpecies.FUEL);
		ExtraBeesBranch.REFINED.addMemberSpecies(ExtraBeesSpecies.CREOSOTE);
		ExtraBeesBranch.REFINED.addMemberSpecies(ExtraBeesSpecies.LATEX);
		ExtraBeesBranch.REFINED.register();

		ExtraBeesBranch.AQUATIC.addMemberSpecies(ExtraBeesSpecies.WATER);
		ExtraBeesBranch.AQUATIC.addMemberSpecies(ExtraBeesSpecies.RIVER);
		ExtraBeesBranch.AQUATIC.addMemberSpecies(ExtraBeesSpecies.OCEAN);
		ExtraBeesBranch.AQUATIC.addMemberSpecies(ExtraBeesSpecies.INK);
		ExtraBeesBranch.AQUATIC.register();

		ExtraBeesBranch.SACCHARINE.addMemberSpecies(ExtraBeesSpecies.SWEET);
		ExtraBeesBranch.SACCHARINE.addMemberSpecies(ExtraBeesSpecies.SUGAR);
		ExtraBeesBranch.SACCHARINE.addMemberSpecies(ExtraBeesSpecies.FRUIT);
		ExtraBeesBranch.SACCHARINE.addMemberSpecies(ExtraBeesSpecies.RIPENING);
		ExtraBeesBranch.SACCHARINE.register();

		ExtraBeesBranch.CLASSICAL.addMemberSpecies(ExtraBeesSpecies.MARBLE);
		ExtraBeesBranch.CLASSICAL.addMemberSpecies(ExtraBeesSpecies.ROMAN);
		ExtraBeesBranch.CLASSICAL.addMemberSpecies(ExtraBeesSpecies.GREEK);
		ExtraBeesBranch.CLASSICAL.addMemberSpecies(ExtraBeesSpecies.CLASSICAL);
		ExtraBeesBranch.CLASSICAL.register();

		ExtraBeesBranch.VOLCANIC.addMemberSpecies(ExtraBeesSpecies.BASALT);
		ExtraBeesBranch.VOLCANIC.addMemberSpecies(ExtraBeesSpecies.TEMPERED);
		ExtraBeesBranch.VOLCANIC.addMemberSpecies(ExtraBeesSpecies.ANGRY);
		ExtraBeesBranch.VOLCANIC.addMemberSpecies(ExtraBeesSpecies.VOLCANIC);
		ExtraBeesBranch.VOLCANIC.addMemberSpecies(ExtraBeesSpecies.GLOWSTONE);
		ExtraBeesBranch.VOLCANIC.register();

		ExtraBeesBranch.VISCOUS.addMemberSpecies(ExtraBeesSpecies.VISCOUS);
		ExtraBeesBranch.VISCOUS.addMemberSpecies(ExtraBeesSpecies.GLUTINOUS);
		ExtraBeesBranch.VISCOUS.addMemberSpecies(ExtraBeesSpecies.STICKY);
		ExtraBeesBranch.VISCOUS.register();

		ExtraBeesBranch.VIRULENT.addMemberSpecies(ExtraBeesSpecies.MALICIOUS);
		ExtraBeesBranch.VIRULENT.addMemberSpecies(ExtraBeesSpecies.INFECTIOUS);
		ExtraBeesBranch.VIRULENT.addMemberSpecies(ExtraBeesSpecies.VIRULENT);
		ExtraBeesBranch.VIRULENT.register();

		ExtraBeesBranch.CAUSTIC.addMemberSpecies(ExtraBeesSpecies.CORROSIVE);
		ExtraBeesBranch.CAUSTIC.addMemberSpecies(ExtraBeesSpecies.CAUSTIC);
		ExtraBeesBranch.CAUSTIC.addMemberSpecies(ExtraBeesSpecies.ACIDIC);
		ExtraBeesBranch.CAUSTIC.register();

		ExtraBeesBranch.ENERGETIC.addMemberSpecies(ExtraBeesSpecies.EXCITED);
		ExtraBeesBranch.ENERGETIC.addMemberSpecies(ExtraBeesSpecies.ENERGETIC);
		ExtraBeesBranch.ENERGETIC.addMemberSpecies(ExtraBeesSpecies.ECSTATIC);
		ExtraBeesBranch.ENERGETIC.register();

		ExtraBeesBranch.SHADOW.addMemberSpecies(ExtraBeesSpecies.SHADOW);
		ExtraBeesBranch.SHADOW.addMemberSpecies(ExtraBeesSpecies.DARKENED);
		ExtraBeesBranch.SHADOW.addMemberSpecies(ExtraBeesSpecies.ABYSS);
		ExtraBeesBranch.SHADOW.register();

		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.RED);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.YELLOW);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.BLUE);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.GREEN);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.BLACK);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.WHITE);
		ExtraBeesBranch.PRIMARY.addMemberSpecies(ExtraBeesSpecies.BROWN);
		ExtraBeesBranch.PRIMARY.register();

		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.ORANGE);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.CYAN);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.PURPLE);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.GRAY);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.LIGHTBLUE);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.PINK);
		ExtraBeesBranch.SECONDARY.addMemberSpecies(ExtraBeesSpecies.LIMEGREEN);
		ExtraBeesBranch.SECONDARY.register();

		ExtraBeesBranch.TERTIARY.addMemberSpecies(ExtraBeesSpecies.MAGENTA);
		ExtraBeesBranch.TERTIARY.addMemberSpecies(ExtraBeesSpecies.LIGHTGRAY);
		ExtraBeesBranch.TERTIARY.register();

		ExtraBeesBranch.FTB.addMemberSpecies(ExtraBeesSpecies.JADED);
		ExtraBeesBranch.FTB.register();

		ExtraBeesBranch.QUANTUM.addMemberSpecies(ExtraBeesSpecies.UNUSUAL);
		ExtraBeesBranch.QUANTUM.addMemberSpecies(ExtraBeesSpecies.SPATIAL);
		ExtraBeesBranch.QUANTUM.addMemberSpecies(ExtraBeesSpecies.QUANTUM);
		ExtraBeesBranch.QUANTUM.register();

		ExtraBeesBranch.BOTANIA.addMemberSpecies(ExtraBeesSpecies.MYSTICAL);
		ExtraBeesBranch.BOTANIA.register();
	}

	@Override
	public String getUID() {
		return "extrabees.genus." + uid;
	}

	@Override
	public String getName() {
		return ExtraBees.proxy.localise("branch." + toString().toLowerCase() + ".name");
	}

	@Override
	public String getScientific() {
		return scientific;
	}

	@Override
	public String getDescription() {
		return ExtraBees.proxy.localiseOrBlank("branch." + toString().toLowerCase() + ".desc");
	}

	public void register() {
		if (speciesSet.isEmpty()) {
			return;
		}

		AlleleManager.alleleRegistry.registerClassification(this);
		IClassification parent = AlleleManager.alleleRegistry.getClassification("family.apidae");
		if (parent != null) {
			parent.addMemberGroup(this);
			setParent(parent);
		}
	}

	@Override
	public IClassification.EnumClassLevel getLevel() {
		return IClassification.EnumClassLevel.GENUS;
	}

	@Override
	public IClassification[] getMemberGroups() {
		return null;
	}

	@Override
	public void addMemberGroup(IClassification group) {
	}

	@Override
	public IAlleleSpecies[] getMemberSpecies() {
		return speciesSet.toArray(new IAlleleSpecies[0]);
	}

	@Override
	public void addMemberSpecies(IAlleleSpecies species) {
		speciesSet.add((IAlleleBeeSpecies) species);
		if (species instanceof ExtraBeesSpecies) {
			((ExtraBeesSpecies) species).setBranch(this);
		}
	}

	@Override
	public IClassification getParent() {
		return parent;
	}

	@Override
	public void setParent(IClassification parent) {
		this.parent = parent;
	}
}
