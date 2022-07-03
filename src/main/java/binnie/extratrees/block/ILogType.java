package binnie.extratrees.block;

import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesignMaterial;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.arboriculture.EnumWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.arboriculture.worldgen.BlockTypeLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface ILogType {
    void placeBlock(World p0, int p1, int p2, int p3);

    ItemStack getItemStack();

    int getColour();

    enum ExtraTreeLog implements ILogType {
        Apple("apple", 0x7b7a7b, PlankType.ExtraTreePlanks.Apple),
        Fig("fig", 0x807357, PlankType.ExtraTreePlanks.Fig),
        Butternut("butternut", 0xb7ada0, PlankType.ExtraTreePlanks.Butternut),
        Cherry("cherry", 0x716850, PlankType.ForestryPlanks.CHERRY),
        Whitebeam("whitebeam", 0x786a6d, PlankType.ExtraTreePlanks.Whitebeam),
        Rowan("rowan", 0xb6b09b, PlankType.ExtraTreePlanks.Rowan),
        Hemlock("hemlock", 0xada39b, PlankType.ExtraTreePlanks.Hemlock),
        Ash("ash", 0x898982, PlankType.ExtraTreePlanks.Ash),
        Alder("alder", 0xc6c0b8, PlankType.ExtraTreePlanks.Alder),
        Beech("beech", 0xb2917e, PlankType.ExtraTreePlanks.Beech),
        Hawthorn("hawthorn", 0x5f5745, PlankType.ExtraTreePlanks.Hawthorn),
        Banana("banana", 0x85924f),
        Yew("yew", 0xd1bbc1, PlankType.ExtraTreePlanks.Yew),
        Cypress("cypress", 0x9a8483, PlankType.ExtraTreePlanks.Cypress),
        Fir("fir", 0x828382, PlankType.ExtraTreePlanks.Fir),
        Hazel("hazel", 0xaa986f, PlankType.ExtraTreePlanks.Hazel),
        Hickory("hickory", 0x3e3530, PlankType.ExtraTreePlanks.Hickory),
        Elm("elm", 0x848386, PlankType.ExtraTreePlanks.Elm),
        Elder("elder", 0xd8b874, PlankType.ExtraTreePlanks.Elder),
        Holly("holly", 0xb5aa85, PlankType.ExtraTreePlanks.Holly),
        Hornbeam("hornbeam", 0xa39276, PlankType.ExtraTreePlanks.Hornbeam),
        Cedar("cedar", 0xad764f, PlankType.ExtraTreePlanks.Cedar),
        Olive("olive", 0x7b706a, PlankType.ExtraTreePlanks.Olive),
        Sweetgum("sweetgum", 0xa1a19c, PlankType.ExtraTreePlanks.Sweetgum),
        Locust("locust", 0xadacbc, PlankType.ExtraTreePlanks.Locust),
        Pear("pear", 0xa89779, PlankType.ExtraTreePlanks.Pear),
        Maclura("maclura", 0x8b5734, PlankType.ExtraTreePlanks.Maclura),
        Brazilwood("brazilwood", 0x9e8068, PlankType.ExtraTreePlanks.Brazilwood),
        Logwood("logwood", 0xf9e2d2, PlankType.ExtraTreePlanks.Logwood),
        Rosewood("rosewood", 0x998666, PlankType.ExtraTreePlanks.Rosewood),
        Purpleheart("purpleheart", 0x9392a2, PlankType.ExtraTreePlanks.Purpleheart),
        Iroko("iroko", 0x605c5b, PlankType.ExtraTreePlanks.Iroko),
        Gingko("gingko", 0xadae9c, PlankType.ExtraTreePlanks.Gingko),
        Eucalyptus("eucalyptus", 0xeadeda, PlankType.ExtraTreePlanks.Eucalyptus),
        Eucalyptus2("eucalyptus", 0x867e65, PlankType.ExtraTreePlanks.Eucalyptus),
        Box("box", 0xab6f57, PlankType.ExtraTreePlanks.Box),
        Syzgium("syzgium", 0xab6f57, PlankType.ExtraTreePlanks.Syzgium),
        Eucalyptus3("eucalyptus", 0x6cb03f, PlankType.ExtraTreePlanks.Eucalyptus),
        Cinnamon("cinnamon", 0x86583c, PlankType.VanillaPlanks.JUNGLE),
        PinkIvory("pinkivory", 0x7f6554, PlankType.ExtraTreePlanks.PinkIvory);

        protected String name;
        protected int color;
        protected IDesignMaterial plank;
        protected IIcon trunk;
        protected IIcon bark;

        ExtraTreeLog(String name, int color) {
            this.name = name;
            this.color = color;
            plank = null;
        }

        ExtraTreeLog(String name, int color, IDesignMaterial plank) {
            this.name = name;
            this.color = color;
            this.plank = plank;
        }

        public static void registerIcons(IIconRegister register) {
            for (ExtraTreeLog log : values()) {
                log.trunk = ExtraTrees.proxy.getIcon(
                        register, "logs/" + log.toString().toLowerCase() + "Trunk");
                log.bark = ExtraTrees.proxy.getIcon(
                        register, "logs/" + log.toString().toLowerCase() + "Bark");
            }
        }

        public String getName() {
            return I18N.localise("extratrees.block.planks." + name);
        }

        public void addRecipe() {
            if (plank == null) {
                return;
            }

            ItemStack log = getItemStack();
            ItemStack result = plank.getStack();
            result.stackSize = 4;
            GameRegistry.addShapelessRecipe(result, log);
        }

        @Override
        public void placeBlock(World world, int x, int y, int z) {
            world.setBlock(x, y, z, ExtraTrees.blockLog, 0, 2);
            if (world.getTileEntity(x, y, z) != null) {
                ((TileEntityMetadata) world.getTileEntity(x, y, z)).setTileMetadata(ordinal(), false);
            }
        }

        public IIcon getTrunk() {
            return trunk;
        }

        public IIcon getBark() {
            return bark;
        }

        @Override
        public ItemStack getItemStack() {
            return TileEntityMetadata.getItemStack(ExtraTrees.blockLog, ordinal())
                    .copy();
        }

        @Override
        public int getColour() {
            return color;
        }
    }

    enum ForestryLog implements ILogType {
        LARCH(1, 0, 6376529),
        TEAK(1, 1, 3486249),
        ACACIA(1, 2, 7565906),
        LIME(1, 3, 7431512),
        CHESTNUT(2, 0, 6183484),
        WENGE(2, 1, 6444875),
        BAOBAB(2, 2, 14326376),
        SEQUOIA(2, 3, 11563861),
        KAPOK(3, 0, 5396559),
        EBONY(3, 1, 10453073),
        MAHOGANY(3, 2, 9403501),
        BALSA(3, 3, 8551285),
        WILLOW(4, 0, 10590869),
        WALNUT(4, 1, 9474682),
        GREENHEART(4, 2, 7956050),
        CHERRY(4, 3, 6296064),
        MAHOE(5, 0, 6382152),
        POPLAR(5, 1, 9217671),
        PALM(5, 2, 8941379),
        PAPAYA(5, 3, 9069862),
        PINE(6, 0, 7558729),
        PLUM(6, 1, 11961953),
        MAPLE(6, 2, 9078657),
        CITRUS(6, 3, 5983033);

        protected int block;
        protected int metadata;
        protected int colour;

        ForestryLog(int blockOffset, int meta, int colour) {
            block = blockOffset;
            metadata = meta;
            this.colour = colour;
        }

        @Override
        public void placeBlock(World world, int x, int y, int z) {
            BlockTypeLog logBlock = new BlockTypeLog(
                    TreeManager.woodItemAccess.getLog(EnumWoodType.valueOf(name().toUpperCase()), false));
            logBlock.setDirection(ForgeDirection.UP);
            logBlock.setBlock(world, x, y, z);
        }

        @Override
        public ItemStack getItemStack() {
            return TreeManager.woodItemAccess.getLog(EnumWoodType.valueOf(name().toUpperCase()), false);
        }

        @Override
        public int getColour() {
            return colour;
        }
    }

    enum VanillaLog implements ILogType {
        Oak(6376752),
        Spruce(2759179),
        Birch(6376752),
        Jungle(5456154);

        protected int colour;

        VanillaLog(int colour) {
            this.colour = colour;
        }

        @Override
        public void placeBlock(World world, int x, int y, int z) {
            world.setBlock(x, y, z, Blocks.log, ordinal(), 2);
        }

        @Override
        public ItemStack getItemStack() {
            return new ItemStack(Blocks.log, 1, ordinal());
        }

        @Override
        public int getColour() {
            return colour;
        }
    }
}
