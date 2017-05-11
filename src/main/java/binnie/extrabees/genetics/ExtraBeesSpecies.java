package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.ForestryAllele;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.products.EnumHoneyComb;
import binnie.extrabees.products.ItemHoneyComb;
import binnie.genetics.genetics.AlleleHelper;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.core.genetics.alleles.EnumAllele;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

// TODO drop this and replace for ExtraBeeDefinition
public enum ExtraBeesSpecies implements IAlleleBeeSpecies, IIconProvider {
	ARID("aridus", 0xbee854),
	BARREN("infelix", 0xe0d263),
	DESOLATE("desolo", 0xd1b890),
	GNAWING("apica", 0xe874b0),
	ROTTEN("caries", 0xbfe0b6),
	BONE("os", 0xe9ede8),
	CREEPER("erepo", 0x2ce615),
	DECOMPOSING("aegrus", 0x523711),
	ROCK("saxum", 0xa8a8a8),
	STONE("lapis", 0x757575),
	GRANITE("granum", 0x695555),
	MINERAL("minerale", 0x6e757d),
	COPPER("cuprous", 0xd16308),
	TIN("stannus", 0xbdb1bd),
	IRON("ferrous", 0xa87058),
	LEAD("plumbous", 0xad8bab),
	ZINC("spelta", 0xedebff),
	TITANIUM("titania", 0xb0aae3),
	BRONZE,
	BRASS,
	STEEL,
	TUNGSTATE("wolfram", 1249812),
	GOLD("aureus", 15125515),
	SILVER("argentus", 14408667),
	ELECTRUM,
	PLATINUM("platina", 14408667),
	LAPIS("lazuli", 4009179),
	SODALITE,
	PYRITE,
	BAUXITE,
	CINNABAR,
	SPHALERITE,
	EMERALD("emerala", 1900291),
	RUBY("ruba", 14024704),
	SAPPHIRE("saphhira", 673791),
	OLIVINE,
	DIAMOND("diama", 8371706),
	UNSTABLE("levis", 4099124),
	NUCLEAR("nucleus", 4312111),
	RADIOACTIVE("fervens", 2031360),
	ANCIENT("antiquus", 15915919),
	PRIMEVAL("priscus", 11773563),
	PREHISTORIC("pristinus", 7232064),
	RELIC("sapiens", 5062166),
	COAL("carbo", 8025672),
	RESIN("lacrima", 10908443),
	OIL("lubricus", 5719920),
	PEAT,
	DISTILLED("distilli", 3498838),
	FUEL("refina", 16760835),
	CREOSOTE("creosota", 9936403),
	LATEX("latex", 4803134),
	WATER("aqua", 9741055),
	RIVER("flumen", 8631252),
	OCEAN("mare", 1912493),
	INK("atramentum", 922695),
	GROWING("tyrelli", 6024152),
	THRIVING("thriva", 3466109),
	BLOOMING("blooma", 704308),
	SWEET("mellitus", 16536049),
	SUGAR("dulcis", 15127520),
	RIPENING("ripa", 11716445),
	FRUIT("pomum", 14375030),
	ALCOHOL("vinum", 15239777),
	FARM("ager", 7723872),
	MILK("lacteus", 14936296),
	COFFEE("arabica", 9199152),
	CITRUS,
	MINT,
	SWAMP("paludis", 3500339),
	BOGGY("lama", 7887913),
	FUNGAL("boletus", 13722112),
	MARBLE,
	ROMAN,
	GREEK,
	CLASSICAL,
	BASALT("aceri", 9202025),
	TEMPERED("iratus", 9062472),
	ANGRY,
	VOLCANIC("volcano", 5049356),
	MALICIOUS("acerbus", 7875191),
	INFECTIOUS("contagio", 12070581),
	VIRULENT("morbus", 15733740),
	VISCOUS("liquidus", 608014),
	GLUTINOUS("glutina", 1936423),
	STICKY("lentesco", 1565480),
	CORROSIVE("corrumpo", 4873227),
	CAUSTIC("torrens", 8691997),
	ACIDIC("acidus", 12644374),
	EXCITED("excita", 16729413),
	ENERGETIC("energia", 15218119),
	ECSTATIC("ecstatica", 11482600),
	ARTIC("artica", 11395296),
	FREEZING("glacia", 8119267),
	SHADOW("shadowa", 5855577),
	DARKENED("darka", 3354163),
	ABYSS("abyssba", 2164769),
	RED("rubra", 16711680),
	YELLOW("fulvus", 16768256),
	BLUE("caeruleus", 8959),
	GREEN("prasinus", 39168),
	BLACK("niger", 5723991),
	WHITE("albus", 16777215),
	BROWN("fuscus", 6042895),
	ORANGE("flammeus", 16751872),
	CYAN("cyana", 65509),
	PURPLE("purpureus", 11403519),
	GRAY("ravus", 12237498),
	LIGHTBLUE("aqua", 40447),
	PINK("rosaceus", 16744671),
	LIMEGREEN("lima", 65288),
	MAGENTA("fuchsia", 16711884),
	LIGHTGRAY("canus", 13224393),
	CELEBRATORY("celeba", 16386666),
	JADED("jadeca", 16386666),
	GLOWSTONE("glowia", 14730779),
	HAZARDOUS("infensus", 11562024),
	NICKEL("claro", 16768764),
	INVAR,
	QUANTUM("quanta", 3655131),
	SPATIAL("spatia", 4987872),
	UNUSUAL("daniella", 5874874),
	YELLORIUM("yellori", 14019840),
	CYANITE("cyanita", 34541),
	BLUTONIUM("caruthus", 1769702),
	MYSTICAL("mystica", 4630306);

	public HashMap<ItemStack, Float> allProducts = new LinkedHashMap<>();
	public HashMap<ItemStack, Float> allSpecialties = new LinkedHashMap<>();
	public EnumBeeSpeciesState state;

	protected boolean nocturnal = false;

	private int primaryColor;
	private int secondaryColor = 0xffdc16;
	private EnumTemperature temperature = EnumTemperature.NORMAL;
	private EnumHumidity humidity = EnumHumidity.NORMAL;
	private boolean hasEffect = false;
	private boolean isSecret = true;
	private boolean isCounted = true;
	private String binomial;
	private IClassification branch = null;
	private String uid;
	private Achievement achievement = null;
	private boolean dominant = true;
	private HashMap<ItemStack, Float> products = new LinkedHashMap<>();
	private HashMap<ItemStack, Float> specialties = new LinkedHashMap<>();
	private IAllele[] template;

	@SideOnly(Side.CLIENT)
	private IIcon[][] icons;

	ExtraBeesSpecies(String binomial, int color) {
		this.binomial = binomial;
		primaryColor = color;
		state = EnumBeeSpeciesState.ACTIVE;
		uid = toString().toLowerCase();
	}

	ExtraBeesSpecies() {
		binomial = "";
		primaryColor = 0xffffff;
		state = EnumBeeSpeciesState.DEPRECATED;
		uid = "";
	}

	public static IAllele[] getDefaultTemplate() {
		return Binnie.Genetics.getBeeRoot().getDefaultTemplate();
	}

	public static void doInit() {
		for (ExtraBeesSpecies species : values()) {
			species.template = getDefaultTemplate();
		}

		int aridBody = 0xcbe374;
		int rockBody = 0x999999;

		for (int i = 0; i < 16; ++i) {
			ExtraBeesSpecies species3 = values()[ExtraBeesSpecies.RED.ordinal() + i];
			EnumHoneyComb comb = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
			species3.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.75f);
			species3.addSpecialty(comb, 0.25f);
			species3.setSecondaryColor(0x8cff00);
		}

		for (ExtraBeesSpecies species2 : values()) {
			species2.registerTemplate();
		}
	}

	@Override
	public String getName() {
		return ExtraBees.proxy.localise("species." + name().toLowerCase() + ".name");
	}

	@Override
	public String getDescription() {
		return ExtraBees.proxy.localiseOrBlank("species." + name().toLowerCase() + ".desc");
	}

	@Override
	public EnumTemperature getTemperature() {
		return temperature;
	}

	private void setTemperature(EnumTemperature temperature) {
		this.temperature = temperature;
	}

	@Override
	public EnumHumidity getHumidity() {
		return humidity;
	}

	private ExtraBeesSpecies setHumidity(EnumHumidity humidity) {
		this.humidity = humidity;
		return this;
	}

	@Override
	public boolean hasEffect() {
		return hasEffect;
	}

	@Override
	public boolean isSecret() {
		return isSecret;
	}

	@Override
	public boolean isCounted() {
		return isCounted;
	}

	@Override
	public String getBinomial() {
		return binomial;
	}

	@Override
	public String getAuthority() {
		return "Binnie";
	}

	@Override
	public IClassification getBranch() {
		return branch;
	}

	void setBranch(IClassification branch) {
		this.branch = branch;
	}

	@Override
	public String getUID() {
		return "extrabees.species." + uid;
	}

	@Override
	public boolean isDominant() {
		return dominant;
	}

	public HashMap<ItemStack, Float> getProducts() {
		return products;
	}

	public HashMap<ItemStack, Float> getSpecialty() {
		return specialties;
	}

	private void setState(EnumBeeSpeciesState state) {
		this.state = state;
	}

	public void registerTemplate() {
		Binnie.Genetics.getBeeRoot().registerTemplate(getTemplate());
		if (state != EnumBeeSpeciesState.ACTIVE) {
			AlleleManager.alleleRegistry.blacklistAllele(getUID());
		}
	}

	public void addProduct(ItemStack product, Float chance) {
		if (product == null) {
			setState(EnumBeeSpeciesState.INACTIVE);
		} else {
			products.put(product, chance);
			allProducts.put(product, chance);
		}
	}

	public ExtraBeesSpecies addProduct(IItemEnum product, Float chance) {
		if (product.isActive()) {
			addProduct(product.get(1), chance);
		} else {
			allProducts.put(product.get(1), chance);
			setState(EnumBeeSpeciesState.INACTIVE);
		}
		return this;
	}

	public ExtraBeesSpecies addSpecialty(ItemStack product, Float chance) {
		if (product == null) {
			setState(EnumBeeSpeciesState.INACTIVE);
		} else {
			specialties.put(product, chance);
			allSpecialties.put(product, chance);
		}
		return this;
	}

	private ExtraBeesSpecies addSpecialty(IItemEnum product, Float chance) {
		if (product.isActive()) {
			addSpecialty(product.get(1), chance);
		} else {
			setState(EnumBeeSpeciesState.INACTIVE);
			allSpecialties.put(product.get(1), chance);
		}
		return this;
	}

	public IAllele[] getTemplate() {
		template[EnumBeeChromosome.SPECIES.ordinal()] = this;
		return template;
	}

	public ExtraBeesSpecies importTemplate(ForestryAllele.BeeSpecies species) {
		importTemplate(species.getTemplate());
		return this;
	}

	public ExtraBeesSpecies importTemplate(ExtraBeesSpecies species) {
		importTemplate(species.getTemplate());
		return this;
	}

	public void importTemplate(IAllele[] template) {
		this.template = template.clone();
		setHumidity(((IAlleleSpecies) template[0]).getHumidity());
		setTemperature(((IAlleleSpecies) template[0]).getTemperature());
		setSecondaryColor(((IAlleleSpecies) template[0]).getIconColour(1));
		this.template[EnumBeeChromosome.SPECIES.ordinal()] = this;
	}

	public ExtraBeesSpecies recessive() {
		dominant = false;
		return this;
	}

	public ExtraBeesSpecies setIsSecret(boolean secret) {
		isSecret = secret;
		return this;
	}

	public ExtraBeesSpecies setHasEffect(boolean effect) {
		hasEffect = effect;
		return this;
	}

	public ExtraBeesSpecies setSecondaryColor(int colour) {
		secondaryColor = colour;
		return this;
	}

	@Override
	public boolean isJubilant(IBeeGenome genome, IBeeHousing housing) {
		return true;
	}

	@Override
	public int getIconColour(int renderPass) {
		if (renderPass == 0) {
			return primaryColor;
		}
		if (renderPass == 1) {
			return secondaryColor;
		}
		return 0xffffff;
	}

	@Override
	public IIconProvider getIconProvider() {
		return this;
	}

	@Override
	public IIcon getIcon(short texUID) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		String iconType = "default";
		String mod = "forestry";
		if (this == ExtraBeesSpecies.JADED) {
			iconType = "jaded";
			mod = "extrabees";
		}

		icons = new IIcon[EnumBeeType.values().length][3];
		IIcon body1 = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/body1");
		for (int i = 0; i < EnumBeeType.values().length; ++i) {
			if (EnumBeeType.values()[i] != EnumBeeType.NONE) {
				icons[i][0] = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".outline");
				icons[i][1] = ((EnumBeeType.values()[i] != EnumBeeType.LARVAE) ? body1 : BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body"));
				icons[i][2] = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body2");
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(EnumBeeType type, int renderPass) {
		if (icons == null) {
			return ExtraBeesSpecies.ARID.getIcon(type, renderPass);
		}
		return icons[type.ordinal()][renderPass];
	}

	@Override
	public IBeeRoot getRoot() {
		return Binnie.Genetics.getBeeRoot();
	}

	@Override
	public boolean isNocturnal() {
		return nocturnal;
	}

	public ExtraBeesSpecies setNocturnal() {
		nocturnal = true;
		return this;
	}

	public ExtraBeesSpecies setAllDay() {
		return setAllDay(true);
	}

	public ExtraBeesSpecies setAllDay(boolean allDay) {
		template[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleHelper.getAllele(allDay);
		return this;
	}

	public ExtraBeesSpecies setCaveDwelling() {
		template[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleHelper.getAllele(true);
		return this;
	}

	public ExtraBeesSpecies setTolerantFlyer() {
		template[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleHelper.getAllele(true);
		return this;
	}

	public ExtraBeesSpecies setFlowerProvider(String uid) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
		if (allele instanceof IAlleleFlowers) {
			template[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = allele;
		}
		return this;
	}

	public ExtraBeesSpecies setEffect(String uid) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
		if (allele instanceof IAlleleBeeEffect) {
			template[EnumBeeChromosome.EFFECT.ordinal()] = allele;
		}
		return this;
	}

	private ExtraBeesSpecies setFertility(EnumAllele.Fertility fertility) {
		template[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleHelper.getAllele(fertility);
		return this;
	}

	private ExtraBeesSpecies setLifespan(EnumAllele.Lifespan lifespan) {
		template[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleHelper.getAllele(lifespan);
		return this;
	}

	private ExtraBeesSpecies setSpeed(EnumAllele.Speed speed) {
		template[EnumBeeChromosome.SPEED.ordinal()] = AlleleHelper.getAllele(speed);
		return this;
	}

	private ExtraBeesSpecies setTerritory(EnumAllele.Territory territory) {
		template[EnumBeeChromosome.TERRITORY.ordinal()] = AlleleHelper.getAllele(territory);
		return this;
	}

	private ExtraBeesSpecies setFlowering(EnumAllele.Flowering flowering) {
		template[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleHelper.getAllele(flowering);
		return this;
	}

	private ExtraBeesSpecies setHumidityTolerance(EnumTolerance humidityTolerance) {
		template[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleHelper.getAllele(humidityTolerance);
		return this;
	}

	private ExtraBeesSpecies setTemperatureTolerance(EnumTolerance temperatureTolerance) {
		template[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleHelper.getAllele(temperatureTolerance);
		return this;
	}

	@Override
	public float getResearchSuitability(ItemStack itemstack) {
		if (itemstack == null) {
			return 0.0f;
		}

		for (ItemStack stack : products.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		for (ItemStack stack : specialties.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		if (itemstack.getItem() == Items.glass_bottle) {
			return 0.9f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
			return 0.5f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
			return 0.7f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("beeComb")) {
			return 0.4f;
		}
		if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
			return 1.0f;
		}

		for (Map.Entry<ItemStack, Float> entry : getRoot().getResearchCatalysts().entrySet()) {
			if (entry.getKey().isItemEqual(itemstack)) {
				return entry.getValue();
			}
		}
		return 0.0f;
	}

	@Override
	public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
		ArrayList<ItemStack> bounty = new ArrayList<ItemStack>();
		ItemStack research = null;
		if (world.rand.nextFloat() < 10.0f / bountyLevel) {
			Collection<? extends IMutation> combinations = getRoot().getCombinations(this);
			if (combinations.size() > 0) {
				IMutation[] candidates = combinations.toArray(new IMutation[0]);
				research = AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]);
			}
		}

		if (research != null) {
			bounty.add(research);
		}

		if (bountyLevel > 10) {
			for (ItemStack stack : specialties.keySet()) {
				ItemStack stack2 = stack.copy();
				stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
				bounty.add(stack2);
			}
		}

		for (ItemStack stack : products.keySet()) {
			ItemStack stack2 = stack.copy();
			stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
			bounty.add(stack2);
		}
		return bounty.toArray(new ItemStack[0]);
	}

	@Override
	public String getEntityTexture() {
		return "/gfx/forestry/entities/bees/honeyBee.png";
	}

	@Override
	public int getComplexity() {
		return 1 + getGeneticAdvancement(this, new ArrayList<>());
	}

	private int getGeneticAdvancement(IAllele species, ArrayList<IAllele> exclude) {
		int own = 1;
		int highest = 0;
		exclude.add(species);

		for (IMutation mutation : getRoot().getPaths(species, EnumBeeChromosome.SPECIES)) {
			if (!exclude.contains(mutation.getAllele0())) {
				int otherAdvance = getGeneticAdvancement(mutation.getAllele0(), exclude);
				if (otherAdvance > highest) {
					highest = otherAdvance;
				}
			}
			if (!exclude.contains(mutation.getAllele1())) {
				int otherAdvance = getGeneticAdvancement(mutation.getAllele1(), exclude);
				if (otherAdvance <= highest) {
					continue;
				}
				highest = otherAdvance;
			}
		}
		return own + ((highest < 0) ? 0 : highest);
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}

	@Override
	public Map<ItemStack, Float> getProductChances() {
		return getProducts();
	}

	@Override
	public Map<ItemStack, Float> getSpecialtyChances() {
		return getSpecialty();
	}
}
