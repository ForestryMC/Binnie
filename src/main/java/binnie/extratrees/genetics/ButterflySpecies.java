package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.core.util.I18N;
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
import java.awt.Color;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

public enum ButterflySpecies implements IAlleleButterflySpecies {
    WhiteAdmiral("whiteAdmiral", "Limenitis camilla", new Color(0xfafafa)),
    PurpleEmperor("purpleEmperor", "Apatura iris", new Color(0x4232c6)),
    RedAdmiral("redAdmiral", "Vanessa atalanta", new Color(0xe66f44)),
    PaintedLady("paintedLady", "Vanessa cardui", new Color(0xeda048)),
    SmallTortoiseshell("smallTortoiseshell", "Aglais urticae", new Color(0xea750b)),
    CamberwellBeauty("camberwellBeauty", "Aglais antiopa", new Color(0x95a2cc)),
    Peacock("peacock", "Inachis io", new Color(0xd33802)),
    Wall("wall", "Lasiommata megera", new Color(0xefae1e)),
    CrimsonRose("crimsonRose", "Atrophaneura hector", new Color(0xff627b)),
    KaiserIHind("kaiserIHind", "Teinopalpus imperialis", new Color(0x77a040)),
    GoldenBirdwing("goldenBirdwing", "Troides aeacus", new Color(0xf9dc1e)),
    MarshFritillary("marshFritillary", "Euphydryas aurinia", new Color(0xff8c00)),
    PearlBorderedFritillary("pearlBorderedFritillary", "Boloria euphrosyne", new Color(0xff8b03)),
    QueenOfSpainFritillary("queenOfSpainFritillary", "Issoria lathonia", new Color(0xffd13f)),
    SpeckledWood("speckledWood", "Pararge aegeria", new Color(0xf5f88d)),
    ScotchAngus("scotchAngus", "Erebia aethiops", new Color(0xc25423)),
    Gatekeeper("gatekeeper", "Pyronia tithonus", new Color(0xfac32a)),
    MeadowBrown("meadowBrown", "Maniola jurtina", new Color(0xe39519)),
    SmallHeath("smallHeath", "Coenonympha pamphilus", new Color(0xffa632)),
    Ringlet("ringlet", "Aphantopus hyperantus", new Color(0x975d37)),
    Monarch("monarch", "Danaus plexippus", new Color(0xffb206)),
    MarbledWhite("marbledWhite", "Melanargia galathea", new Color(0xececec));

    public IClassification branch;

    protected String name;
    protected String branchName;
    protected String scientific;
    protected BinnieResource texture;
    protected int color;

    private Map<ItemStack, Float> butterflyLoot;
    private Map<ItemStack, Float> caterpillarLoot;

    ButterflySpecies(String name, String scientific, Color color) {
        this.name = name;
        this.scientific = scientific.split(" ")[1];
        this.color = color.getRGB();
        butterflyLoot = new HashMap<>();
        caterpillarLoot = new HashMap<>();
        branchName = scientific.split(" ")[0].toLowerCase();
        texture = Binnie.Resource.getPNG(ExtraTrees.instance, ResourceType.Entity, toString());
    }

    @Override
    public String getName() {
        return I18N.localise("extratrees.butterflies.species." + name);
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
        return (renderPass > 0) ? 0xffffff : color;
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
        if (itemstack.getItem() == Mods.forestry.item("beeCombs")) {
            return 0.4f;
        }
        if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
            return 1.0f;
        }

        for (Map.Entry<ItemStack, Float> entry :
                getRoot().getResearchCatalysts().entrySet()) {
            if (entry.getKey().isItemEqual(itemstack)) {
                return entry.getValue();
            }
        }
        return 0.0f;
    }

    @Override
    public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
        return new ItemStack[] {getRoot().getMemberStack(individual.copy(), EnumFlutterType.SERUM.ordinal())};
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
