package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IAlleleButterflySpecies;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ButterflySpecies implements IAlleleButterflySpecies {
	WhiteAdmiral("White Admiral", "Limenitis camilla", 0xfafafa),
	PurpleEmperor("Purple Emperor", "Apatura iris", 0x4232c6),
	RedAdmiral("Red Admiral", "Vanessa atalanta", 0xe66f44),
	PaintedLady("Painted Lady", "Vanessa cardui", 0xeda048),
	SmallTortoiseshell("Small Tortoiseshell", "Aglais urticae", 0xea750b),
	CamberwellBeauty("Camberwell Beauty", "Aglais antiopa", 0x95a2cc),
	Peacock("Peacock", "Inachis io", 0xd33802),
	Wall("Wall", "Lasiommata megera", 0xefae1e),
	CrimsonRose("Crimson Rose", "Atrophaneura hector", 0xff627b),
	KaiserIHind("Kaiser-i-Hind", "Teinopalpus imperialis", 0x77a040),
	GoldenBirdwing("Golden Birdwing", "Troides aeacus", 0xf9dc1e),
	MarshFritillary("Marsh Fritillary", "Euphydryas aurinia", 0xff8c00),
	PearlBorderedFritillary("Pearl-bordered Fritillary", "Boloria euphrosyne", 0xff8b03),
	QueenOfSpainFritillary("Queen of Spain Fritillary", "Issoria lathonia", 0xffd13f),
	SpeckledWood("Speckled Wood", "Pararge aegeria", 0xf5f88d),
	ScotchAngus("Scotch Angus", "Erebia aethiops", 0xc25423),
	Gatekeeper("Gatekeeper", "Pyronia tithonus", 0xfac32a),
	MeadowBrown("Meadow Brown", "Maniola jurtina", 0xe39519),
	SmallHeath("Small Heath", "Coenonympha pamphilus", 0xffa632),
	Ringlet("Ringlet", "Aphantopus hyperantus", 0x975d37),
	Monarch("Monarch", "Danaus plexippus", 0xffb206),
	MarbledWhite("Marbled White", "Melanargia galathea", 0xececec);

	public IClassification branch;

	protected String name;
	protected String branchName;
	protected String scientific;
	protected BinnieResource texture;
	protected int color;

	private Map<ItemStack, Float> butterflyLoot;
	private Map<ItemStack, Float> caterpillarLoot;

	ButterflySpecies(String name, String scientific, int color) {
		this.name = name;
		this.scientific = scientific.split(" ")[1];
		this.color = color;
		butterflyLoot = new HashMap<>();
		caterpillarLoot = new HashMap<>();
		branchName = scientific.split(" ")[0].toLowerCase();
		texture = Binnie.Resource.getPNG(ExtraTrees.instance, ResourceType.Entity, toString());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	@Override
	public boolean hasEffect() {
		return false;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public boolean isCounted() {
		return true;
	}

	@Override
	public String getBinomial() {
		return scientific;
	}

	@Override
	public String getAuthority() {
		return "Binnie";
	}

	@Override
	public IClassification getBranch() {
		return branch;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() {
		return null;
	}

	@Override
	public String getUID() {
		return "extrabutterflies.species." + toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getEntityTexture() {
		return texture.getResourceLocation().toString();
	}

	public IAllele[] getTemplate() {
		IAllele[] def = getRoot().getDefaultTemplate().clone();
		def[0] = this;
		return def;
	}

	@Override
	public IButterflyRoot getRoot() {
		return Binnie.Genetics.getButterflyRoot();
	}

	@Override
	public float getRarity() {
		return 0.5f;
	}

	@Override
	public boolean isNocturnal() {
		return false;
	}

	@Override
	public int getIconColour(int renderPass) {
		return (renderPass > 0) ? 16777215 : color;
	}

	@Override
	public Map<ItemStack, Float> getButterflyLoot() {
		return new HashMap<>();
	}

	@Override
	public Map<ItemStack, Float> getCaterpillarLoot() {
		return new HashMap<>();
	}

	@Override
	public int getComplexity() {
		return 4;
	}

	@Override
	public float getResearchSuitability(ItemStack itemstack) {
		if (itemstack == null) {
			return 0.0f;
		}
		if (itemstack.getItem() == Items.glass_bottle) {
			return 0.9f;
		}

		for (ItemStack stack : butterflyLoot.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		for (ItemStack stack : caterpillarLoot.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		if (itemstack.getItem() == Mods.forestry.item("honeyDrop")) {
			return 0.5f;
		}
		if (itemstack.getItem() == Mods.forestry.item("honeydew")) {
			return 0.7f;
		}
		if (itemstack.getItem() == Mods.forestry.item("beeComb")) {
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
		return new ItemStack[]{getRoot().getMemberStack(individual.copy(), EnumFlutterType.SERUM.ordinal())};
	}

	@Override
	public EnumSet<BiomeDictionary.Type> getSpawnBiomes() {
		return EnumSet.noneOf(BiomeDictionary.Type.class);
	}

	@Override
	public boolean strictSpawnMatch() {
		return false;
	}

	@Override
	public float getFlightDistance() {
		return 5.0f;
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}
}
