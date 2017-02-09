package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IAlleleButterflySpecies;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ButterflySpecies implements IAlleleButterflySpecies {
	WhiteAdmiral("White Admiral", "Limenitis camilla", 16448250),
	PurpleEmperor("Purple Emperor", "Apatura iris", 4338374),
	RedAdmiral("Red Admiral", "Vanessa atalanta", 15101764),
	PaintedLady("Painted Lady", "Vanessa cardui", 15573064),
	SmallTortoiseshell("Small Tortoiseshell", "Aglais urticae", 15365387),
	CamberwellBeauty("Camberwell Beauty", "Aglais antiopa", 9806540),
	Peacock("Peacock", "Inachis io", 13842434),
	Wall("Wall", "Lasiommata megera", 15707678),
	CrimsonRose("Crimson Rose", "Atrophaneura hector", 16736891),
	KaiserIHind("Kaiser-i-Hind", "Teinopalpus imperialis", 7839808),
	GoldenBirdwing("Golden Birdwing", "Troides aeacus", 16374814),
	MarshFritillary("Marsh Fritillary", "Euphydryas aurinia", 16747520),
	PearlBorderedFritillary("Pearl-bordered Fritillary", "Boloria euphrosyne", 16747267),
	QueenOfSpainFritillary("Queen of Spain Fritillary", "Issoria lathonia", 16765247),
	SpeckledWood("Speckled Wood", "Pararge aegeria", 16119949),
	ScotchAngus("Scotch Angus", "Erebia aethiops", 12735523),
	Gatekeeper("Gatekeeper", "Pyronia tithonus", 16433962),
	MeadowBrown("Meadow Brown", "Maniola jurtina", 14914841),
	SmallHeath("Small Heath", "Coenonympha pamphilus", 16754226),
	Ringlet("Ringlet", "Aphantopus hyperantus", 9919799),
	Monarch("Monarch", "Danaus plexippus", 16757254),
	MarbledWhite("Marbled White", "Melanargia galathea", 15527148);

	private String name;
	private String branchName;
	private String scientific;
	private BinnieResource texture;
	private IClassification branch;
	private int colour;
	private Map<ItemStack, Float> butterflyLoot;
	private Map<ItemStack, Float> caterpillarLoot;

	ButterflySpecies(final String name, final String scientific, final int colour) {
		this.butterflyLoot = new HashMap<>();
		this.caterpillarLoot = new HashMap<>();
		this.name = name;
		this.branchName = scientific.split(" ")[0].toLowerCase();
		this.scientific = scientific.split(" ")[1];
		this.texture = Binnie.RESOURCE.getPNG(ExtraTrees.instance, ResourceType.Entity, this.toString());
		this.colour = colour;

		final String uid = "trees." + branchName.toLowerCase();
		IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
		if (branch == null) {
			branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
		}
		this.branch = branch;

//		final String scientific = species2.branchName.substring(0, 1).toUpperCase() + species2.branchName.substring(1).toLowerCase();
//		final String uid = "trees." + species2.branchName.toLowerCase();
//		IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
//		if (branch == null) {
//			branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
//		}
//		(species2.branch = branch).addMemberSpecies(species2);

	}


	@Override
	public String getName() {
		return this.name;
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
		return this.scientific;
	}

	@Override
	public String getAuthority() {
		return "Binnie";
	}

	@Override
	public IClassification getBranch() {
		Preconditions.checkState(this.branch != null, "branch has not been initialized yet");
		return this.branch;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIconProvider getIconProvider() {
//		return null;
//	}

	@Override
	public int getSpriteColour(int renderPass) {
		return (renderPass == 0) ? 0xFFFFFF : this.colour;
	}

	@Override
	public String getItemTexture() {
		return "";
	}

	@Override
	public void registerSprites() {

	}

	@Override
	public String getModID() {
		return "extratrees";
	}


	/////////

	@Override
	public String getUID() {
		return "extrabutterflies.species." + this.toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public String getEntityTexture() {
		return this.texture.getResourceLocation().toString();
	}

	public IAllele[] getTemplate() {
		final IAllele[] def = this.getRoot().getDefaultTemplate().clone();
		def[0] = this;
		return def;
	}

	@Override
	public IButterflyRoot getRoot() {
		return Binnie.GENETICS.getButterflyRoot();
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
	public float getResearchSuitability(final ItemStack itemstack) {
		if (itemstack.isEmpty()) {
			return 0.0f;
		}
		if (itemstack.getItem() == Items.GLASS_BOTTLE) {
			return 0.9f;
		}
		for (final ItemStack stack : this.butterflyLoot.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}
		for (final ItemStack stack : this.caterpillarLoot.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}
		if (itemstack.getItem() == Mods.Forestry.item("honey_drop")) {
			return 0.5f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
			return 0.7f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("bee_comb")) {
			return 0.4f;
		}
		if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
			return 1.0f;
		}
		for (final Map.Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
			if (entry.getKey().isItemEqual(itemstack)) {
				return entry.getValue();
			}
		}
		return 0.0f;
	}

	@Override
	public NonNullList<ItemStack> getResearchBounty(final World world, final GameProfile researcher, final IIndividual individual, final int bountyLevel) {
		NonNullList<ItemStack> bounty = NonNullList.create();
		ItemStack serum = this.getRoot().getMemberStack(individual.copy(), EnumFlutterType.SERUM);
		bounty.add(serum);
		return bounty;
	}

	@Override
	public Set<BiomeDictionary.Type> getSpawnBiomes() {
		return Collections.emptySet();
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
		return this.getUID();
	}
}
