package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.FruitPod;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.item.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.genetics.alleles.AlleleTreeSpecies;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public enum ExtraTreeFruitGene implements IAlleleFruit, IFruitProvider {
    Blackthorn(10, 0x6d8f1e, 0xde2f69, FruitSprite.Small),
    CherryPlum(10, 0x6d8f1e, 0xe81c4b, FruitSprite.Small),
    Peach(10, 0x6d8f1e, 0xfaa023, FruitSprite.Average),
    Nectarine(10, 0x6d8f1e, 0xfa5523, FruitSprite.Average),
    Apricot(10, 0x6d8f1e, 0xfacf23, FruitSprite.Average),
    Almond(10, 0x6d8f1e, 0x8ee376, FruitSprite.Small),
    WildCherry(10, 0x6d8f1e, 0xff0000, FruitSprite.Tiny),
    SourCherry(10, 0x6d8f1e, 0x9c092b, FruitSprite.Tiny),
    BlackCherry(10, 0x6d8f1e, 0x4a0a19, FruitSprite.Tiny),
    Orange(10, 0x37f043, 0xff940a, FruitSprite.Average),
    Manderin(10, 0x37f043, 0xff940a, FruitSprite.Average),
    Tangerine(10, 0x37f043, 0xff940a, FruitSprite.Average),
    Satsuma(10, 0x37f043, 0xff940a, FruitSprite.Average),
    KeyLime(10, 0x37f043, 0x9bff44, FruitSprite.Small),
    Lime(10, 0x37f043, 0x9bff44, FruitSprite.Average),
    FingerLime(10, 0x37f043, 0xaa3b38, FruitSprite.Small),
    Pomelo(10, 0x37f043, 0x5cd34a, FruitSprite.Larger),
    Grapefruit(10, 0x37f043, 0xff940a, FruitSprite.Large),
    Kumquat(10, 0x37f043, 0xff940a, FruitSprite.Small),
    Citron(10, 0x37f043, 0xffec60, FruitSprite.Large),
    BuddhaHand(10, 0x37f043, 0xffec60, FruitSprite.Large),
    Apple(10, 0x78c953, 0xf71616, FruitSprite.Average),
    Crabapple(10, 0x78c953, 0xffbd4c, FruitSprite.Average),
    Banana("Banana", FruitPod.Banana),
    RedBanana("Red Banana", FruitPod.RedBanana),
    Plantain("Platain", FruitPod.Plantain),
    Hazelnut(7, 0x7d791e, 0xdcb276, FruitSprite.Small),
    Butternut(7, 0xb2b750, 0xf5b462, FruitSprite.Small),
    Beechnut(8, 0xdbbe7c, 0x5f3e35, FruitSprite.Tiny),
    Pecan(8, 0xa2ac4c, 0xf0cf89, FruitSprite.Small),
    BrazilNut(10, 0x59a769, 0x965530, FruitSprite.Large),
    Fig(9, 0xd8b162, 0x6c3f46, FruitSprite.Small),
    Acorn(6, 0x72b226, 0xad6a1d, FruitSprite.Tiny),
    Elderberry(9, 0x71975d, 0x515b43, FruitSprite.Tiny),
    Olive(9, 0x879e35, 0x62572a, FruitSprite.Small),
    GingkoNut(7, 0x8c975b, 0xe5daad, FruitSprite.Tiny),
    Coffee(8, 0x716d1d, 0xf84f66, FruitSprite.Tiny),
    Pear(10, 0x9f8f51, 0x9fd551, FruitSprite.Pear),
    OsangeOsange(10, 0x979752, 0xa2bf27, FruitSprite.Larger),
    Clove(9, 0x687c2c, 0xab4445, FruitSprite.Tiny),
    Coconut("Coconut", FruitPod.Coconut),
    Cashew(8, 0xc4851c, 0xe94b17, FruitSprite.Average),
    Avacado(10, 0x9cbe72, 0x211f10, FruitSprite.Pear),
    Nutmeg(9, 0xe2c32d, 0xac8355, FruitSprite.Tiny),
    Allspice(9, 0xe7a47a, 0x714636, FruitSprite.Tiny),
    Chilli(10, 0x716265, 0xe71832, FruitSprite.Small),
    StarAnise(8, 0x85442e, 0xd45c05, FruitSprite.Tiny),
    Mango(10, 0x658c15, 0xf2a636, FruitSprite.Average),
    Starfruit(10, 0x95c20d, 0xe5d22e, FruitSprite.Average),
    Candlenut(8, 0x7da873, 0xdecab2, FruitSprite.Small),
    Papayimar("Papayimar", FruitPod.Papayimar),
    Blackcurrant(8, 0x8f8c53, 0x4b4e53, FruitSprite.Tiny),
    Redcurrant(8, 0xc6800e, 0xe61e0e, FruitSprite.Tiny),
    Blackberry(8, 0x8f6d71, 0x494371, FruitSprite.Tiny),
    Raspberry(8, 0xecd1c5, 0xdd6971, FruitSprite.Tiny),
    Blueberry(8, 0x9bb297, 0x6093be, FruitSprite.Tiny),
    Cranberry(8, 0xbaa730, 0xde1a30, FruitSprite.Tiny),
    Juniper(8, 0x9b8c72, 0x606372, FruitSprite.Tiny),
    Gooseberry(8, 0xb99f50, 0xb9cf50, FruitSprite.Tiny),
    GoldenRaspberry(8, 0xbeb03b, 0xf3b03b, FruitSprite.Tiny);

    protected IFruitFamily family;
    protected boolean isRipening;
    protected int diffR;
    protected int diffG;
    protected int diffB;
    protected FruitPod pod;
    protected int ripeningPeriod;
    protected int colorUnripe;
    protected int color;
    protected FruitSprite index;
    protected HashMap<ItemStack, Float> products;

    ExtraTreeFruitGene(int time, int unripe, int color, FruitSprite index) {
        this.color = color;
        this.index = index;
        isRipening = false;
        diffB = 0;
        pod = null;
        ripeningPeriod = 0;
        products = new HashMap<>();
        setRipening(time, unripe);
    }

    ExtraTreeFruitGene(String name, FruitPod pod) {
        this.pod = pod;
        isRipening = false;
        diffB = 0;
        products = new HashMap<>();
        ripeningPeriod = 2;
    }

    public static void init() {
        IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
        IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;
        AlleleManager.alleleRegistry.registerFruitFamily(familyBerry);
        AlleleManager.alleleRegistry.registerFruitFamily(familyCitrus);
        ExtraTreeFruitGene.Apple.addProduct(new ItemStack(Items.apple), 1.0f);
        ExtraTreeFruitGene.Apple.setFamily(familyPome);
        ExtraTreeFruitGene.Crabapple.addProduct(Food.Crabapple.get(1), 1.0f);
        ExtraTreeFruitGene.Crabapple.setFamily(familyPome);
        ExtraTreeFruitGene.Orange.addProduct(Food.Orange.get(1), 1.0f);
        ExtraTreeFruitGene.Orange.setFamily(familyCitrus);
        ExtraTreeFruitGene.Manderin.addProduct(Food.Manderin.get(1), 1.0f);
        ExtraTreeFruitGene.Manderin.setFamily(familyCitrus);
        ExtraTreeFruitGene.Tangerine.addProduct(Food.Tangerine.get(1), 1.0f);
        ExtraTreeFruitGene.Tangerine.setFamily(familyCitrus);
        ExtraTreeFruitGene.Satsuma.addProduct(Food.Satsuma.get(1), 1.0f);
        ExtraTreeFruitGene.Satsuma.setFamily(familyCitrus);
        ExtraTreeFruitGene.KeyLime.addProduct(Food.KeyLime.get(1), 1.0f);
        ExtraTreeFruitGene.KeyLime.setFamily(familyCitrus);
        ExtraTreeFruitGene.Lime.addProduct(Food.Lime.get(1), 1.0f);
        ExtraTreeFruitGene.Lime.setFamily(familyCitrus);
        ExtraTreeFruitGene.FingerLime.addProduct(Food.FingerLime.get(1), 1.0f);
        ExtraTreeFruitGene.FingerLime.setFamily(familyCitrus);
        ExtraTreeFruitGene.Pomelo.addProduct(Food.Pomelo.get(1), 1.0f);
        ExtraTreeFruitGene.Pomelo.setFamily(familyCitrus);
        ExtraTreeFruitGene.Grapefruit.addProduct(Food.Grapefruit.get(1), 1.0f);
        ExtraTreeFruitGene.Grapefruit.setFamily(familyCitrus);
        ExtraTreeFruitGene.Kumquat.addProduct(Food.Kumquat.get(1), 1.0f);
        ExtraTreeFruitGene.Kumquat.setFamily(familyCitrus);
        ExtraTreeFruitGene.Citron.addProduct(Food.Citron.get(1), 1.0f);
        ExtraTreeFruitGene.Citron.setFamily(familyCitrus);
        ExtraTreeFruitGene.BuddhaHand.addProduct(Food.BuddhaHand.get(1), 1.0f);
        ExtraTreeFruitGene.BuddhaHand.setFamily(familyCitrus);
        ExtraTreeFruitGene.Blackthorn.addProduct(Food.Blackthorn.get(1), 1.0f);
        ExtraTreeFruitGene.Blackthorn.setFamily(familyPrune);
        ExtraTreeFruitGene.CherryPlum.addProduct(Food.CherryPlum.get(1), 1.0f);
        ExtraTreeFruitGene.CherryPlum.setFamily(familyPrune);
        ExtraTreeFruitGene.Peach.addProduct(Food.Peach.get(1), 1.0f);
        ExtraTreeFruitGene.Peach.setFamily(familyPrune);
        ExtraTreeFruitGene.Nectarine.addProduct(Food.Nectarine.get(1), 1.0f);
        ExtraTreeFruitGene.Nectarine.setFamily(familyPrune);
        ExtraTreeFruitGene.Apricot.addProduct(Food.Apricot.get(1), 1.0f);
        ExtraTreeFruitGene.Apricot.setFamily(familyPrune);
        ExtraTreeFruitGene.Almond.addProduct(Food.Almond.get(1), 1.0f);
        ExtraTreeFruitGene.Almond.setFamily(familyPrune);
        ExtraTreeFruitGene.WildCherry.addProduct(Food.WildCherry.get(1), 1.0f);
        ExtraTreeFruitGene.WildCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.SourCherry.addProduct(Food.SourCherry.get(1), 1.0f);
        ExtraTreeFruitGene.SourCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.BlackCherry.addProduct(Food.BlackCherry.get(1), 1.0f);
        ExtraTreeFruitGene.BlackCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.Hazelnut.addProduct(Food.Hazelnut.get(1), 1.0f);
        ExtraTreeFruitGene.Hazelnut.setFamily(familyNuts);
        ExtraTreeFruitGene.Butternut.addProduct(Food.Butternut.get(1), 1.0f);
        ExtraTreeFruitGene.Butternut.setFamily(familyNuts);
        ExtraTreeFruitGene.Beechnut.addProduct(Food.Beechnut.get(1), 1.0f);
        ExtraTreeFruitGene.Beechnut.setFamily(familyNuts);
        ExtraTreeFruitGene.Pecan.addProduct(Food.Pecan.get(1), 1.0f);
        ExtraTreeFruitGene.Pecan.setFamily(familyNuts);
        ExtraTreeFruitGene.Banana.addProduct(Food.Banana.get(2), 1.0f);
        ExtraTreeFruitGene.Banana.setFamily(familyJungle);
        ExtraTreeFruitGene.RedBanana.addProduct(Food.RedBanana.get(2), 1.0f);
        ExtraTreeFruitGene.RedBanana.setFamily(familyJungle);
        ExtraTreeFruitGene.Plantain.addProduct(Food.Plantain.get(2), 1.0f);
        ExtraTreeFruitGene.Plantain.setFamily(familyJungle);
        ExtraTreeFruitGene.BrazilNut.addProduct(Food.BrazilNut.get(4), 1.0f);
        ExtraTreeFruitGene.BrazilNut.setFamily(familyNuts);
        ExtraTreeFruitGene.Fig.addProduct(Food.Fig.get(1), 1.0f);
        ExtraTreeFruitGene.Fig.setFamily(familyPrune);
        ExtraTreeFruitGene.Acorn.addProduct(Food.Acorn.get(1), 1.0f);
        ExtraTreeFruitGene.Acorn.setFamily(familyNuts);
        ExtraTreeFruitGene.Elderberry.addProduct(Food.Elderberry.get(1), 1.0f);
        ExtraTreeFruitGene.Elderberry.setFamily(familyPrune);
        ExtraTreeFruitGene.Olive.addProduct(Food.Olive.get(1), 1.0f);
        ExtraTreeFruitGene.Olive.setFamily(familyPrune);
        ExtraTreeFruitGene.GingkoNut.addProduct(Food.GingkoNut.get(1), 1.0f);
        ExtraTreeFruitGene.GingkoNut.setFamily(familyNuts);
        ExtraTreeFruitGene.Coffee.addProduct(Food.Coffee.get(1), 1.0f);
        ExtraTreeFruitGene.Coffee.setFamily(familyJungle);
        ExtraTreeFruitGene.Pear.addProduct(Food.Pear.get(1), 1.0f);
        ExtraTreeFruitGene.Pear.setFamily(familyPome);
        ExtraTreeFruitGene.OsangeOsange.addProduct(Food.OsangeOrange.get(1), 1.0f);
        ExtraTreeFruitGene.OsangeOsange.setFamily(familyPome);
        ExtraTreeFruitGene.Clove.addProduct(Food.Clove.get(1), 1.0f);
        ExtraTreeFruitGene.Clove.setFamily(familyNuts);
        ExtraTreeFruitGene.Blackcurrant.addProduct(Food.Blackcurrant.get(2), 1.0f);
        ExtraTreeFruitGene.Blackcurrant.setFamily(familyBerry);
        ExtraTreeFruitGene.Redcurrant.addProduct(Food.Redcurrant.get(2), 1.0f);
        ExtraTreeFruitGene.Redcurrant.setFamily(familyBerry);
        ExtraTreeFruitGene.Blackberry.addProduct(Food.Blackberry.get(2), 1.0f);
        ExtraTreeFruitGene.Blackberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Raspberry.addProduct(Food.Raspberry.get(2), 1.0f);
        ExtraTreeFruitGene.Raspberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Blueberry.addProduct(Food.Blueberry.get(2), 1.0f);
        ExtraTreeFruitGene.Blueberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Cranberry.addProduct(Food.Cranberry.get(2), 1.0f);
        ExtraTreeFruitGene.Cranberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Juniper.addProduct(Food.Juniper.get(2), 1.0f);
        ExtraTreeFruitGene.Juniper.setFamily(familyBerry);
        ExtraTreeFruitGene.Gooseberry.addProduct(Food.Gooseberry.get(2), 1.0f);
        ExtraTreeFruitGene.Gooseberry.setFamily(familyBerry);
        ExtraTreeFruitGene.GoldenRaspberry.addProduct(Food.GoldenRaspberry.get(2), 1.0f);
        ExtraTreeFruitGene.GoldenRaspberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Coconut.addProduct(Food.Coconut.get(1), 1.0f);
        ExtraTreeFruitGene.Coconut.setFamily(familyJungle);
        ExtraTreeFruitGene.Cashew.addProduct(Food.Cashew.get(1), 1.0f);
        ExtraTreeFruitGene.Cashew.setFamily(familyJungle);
        ExtraTreeFruitGene.Avacado.addProduct(Food.Avacado.get(1), 1.0f);
        ExtraTreeFruitGene.Avacado.setFamily(familyJungle);
        ExtraTreeFruitGene.Nutmeg.addProduct(Food.Nutmeg.get(1), 1.0f);
        ExtraTreeFruitGene.Nutmeg.setFamily(familyJungle);
        ExtraTreeFruitGene.Allspice.addProduct(Food.Allspice.get(1), 1.0f);
        ExtraTreeFruitGene.Allspice.setFamily(familyJungle);
        ExtraTreeFruitGene.Chilli.addProduct(Food.Chilli.get(1), 1.0f);
        ExtraTreeFruitGene.Chilli.setFamily(familyJungle);
        ExtraTreeFruitGene.StarAnise.addProduct(Food.StarAnise.get(1), 1.0f);
        ExtraTreeFruitGene.StarAnise.setFamily(familyJungle);
        ExtraTreeFruitGene.Mango.addProduct(Food.Mango.get(1), 1.0f);
        ExtraTreeFruitGene.Mango.setFamily(familyPome);
        ExtraTreeFruitGene.Starfruit.addProduct(Food.Starfruit.get(1), 1.0f);
        ExtraTreeFruitGene.Starfruit.setFamily(familyJungle);
        ExtraTreeFruitGene.Candlenut.addProduct(Food.Candlenut.get(1), 1.0f);
        ExtraTreeFruitGene.Candlenut.setFamily(familyJungle);

        if (ConfigurationMain.alterLemon) {
            try {
                IAlleleFruit lemon = (IAlleleFruit) AlleleManager.alleleRegistry.getAllele("forestry.fruitLemon");
                FruitProviderNone prov = (FruitProviderNone) lemon.getProvider();
                Field f = FruitProviderNone.class.getDeclaredField("family");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                f.setAccessible(true);
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                f.set(prov, familyCitrus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (IAlleleSpecies tree : Binnie.Genetics.treeBreedingSystem.getAllSpecies()) {
            if (tree instanceof AlleleTreeSpecies
                    && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(familyPrune)) {
                ((AlleleTreeSpecies) tree).addFruitFamily(familyCitrus);
            }
        }
    }

    public void setRipening(int time, int unripe) {
        ripeningPeriod = time;
        colorUnripe = unripe;
        isRipening = true;
        diffR = (color >> 16 & 0xFF) - (unripe >> 16 & 0xFF);
        diffG = (color >> 8 & 0xFF) - (unripe >> 8 & 0xFF);
        diffB = (color & 0xFF) - (unripe & 0xFF);
    }

    public void addProduct(ItemStack product, float chance) {
        products.put(product, chance);
    }

    @Override
    public String getUID() {
        return "extratrees.fruit." + toString().toLowerCase();
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public IFruitProvider getProvider() {
        return this;
    }

    @Override
    public ItemStack[] getProducts() {
        return products.keySet().toArray(new ItemStack[0]);
    }

    @Override
    public ItemStack[] getSpecialty() {
        return new ItemStack[0];
    }

    @Override
    public String getDescription() {
        return "extratrees.item.food." + name().toLowerCase();
    }

    @Override
    public IFruitFamily getFamily() {
        return family;
    }

    private void setFamily(IFruitFamily family) {
        this.family = family;
    }

    @Override
    public int getColour(ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime) {
        if (!isRipening) {
            return color;
        }

        float stage = getRipeningStage(ripeningTime);
        int r = (colorUnripe >> 16 & 0xFF) + (int) (diffR * stage);
        int g = (colorUnripe >> 8 & 0xFF) + (int) (diffG * stage);
        int b = (colorUnripe & 0xFF) + (int) (diffB * stage);
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    @Override
    public boolean markAsFruitLeaf(ITreeGenome genome, World world, int x, int y, int z) {
        return pod == null;
    }

    @Override
    public int getRipeningPeriod() {
        return ripeningPeriod;
    }

    @Override
    public ItemStack[] getFruits(ITreeGenome genome, World world, int x, int y, int z, int ripeningTime) {
        if (pod != null) {
            if (ripeningTime >= 2) {
                List<ItemStack> product = new ArrayList<>();
                for (Map.Entry<ItemStack, Float> entry : products.entrySet()) {
                    ItemStack single = entry.getKey().copy();
                    single.stackSize = 1;
                    for (int i = 0; i < entry.getKey().stackSize; ++i) {
                        if (world.rand.nextFloat() <= entry.getValue()) {
                            product.add(single.copy());
                        }
                    }
                }
                return product.toArray(new ItemStack[0]);
            }
            return new ItemStack[0];
        }

        ArrayList<ItemStack> product2 = new ArrayList<>();
        float stage = getRipeningStage(ripeningTime);
        if (stage < 0.5f) {
            return new ItemStack[0];
        }

        float modeYieldMod = 1.0f;
        for (Map.Entry<ItemStack, Float> entry2 : products.entrySet()) {
            if (world.rand.nextFloat() <= genome.getYield() * modeYieldMod * entry2.getValue() * stage) {
                product2.add(entry2.getKey().copy());
            }
        }
        return product2.toArray(new ItemStack[0]);
    }

    private float getRipeningStage(int ripeningTime) {
        if (ripeningTime >= ripeningPeriod) {
            return 1.0f;
        }
        return ripeningTime / ripeningPeriod;
    }

    @Override
    public boolean requiresFruitBlocks() {
        return pod != null;
    }

    @Override
    public boolean trySpawnFruitBlock(ITreeGenome genome, World world, int x, int y, int z) {
        return pod != null
                && world.rand.nextFloat() <= genome.getSappiness()
                && Binnie.Genetics.getTreeRoot()
                        .setFruitBlock(
                                world,
                                (IAlleleFruit) genome.getActiveAllele(EnumTreeChromosome.FRUITS),
                                genome.getSappiness(),
                                pod.getTextures(),
                                x,
                                y,
                                z);
    }

    @Override
    public short getIconIndex(
            ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime, boolean fancy) {
        return index.getIndex();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        if (ordinal() == 0) {
            for (FruitSprite sprite : FruitSprite.values()) {
                sprite.registerIcons(register);
            }
        }
    }

    @Override
    public String getName() {
        return I18N.localise("for." + getDescription());
    }

    public String getNameOfFruit() {
        if (this == ExtraTreeFruitGene.Apple) {
            return "Apple";
        }

        for (ItemStack stack : products.keySet()) {
            if (stack.getItem() == ExtraTrees.itemFood) {
                return Food.values()[stack.getItemDamage()].toString();
            }
        }
        return "NoFruit";
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }
}
