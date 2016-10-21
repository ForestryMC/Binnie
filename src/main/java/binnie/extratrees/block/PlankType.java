package binnie.extratrees.block;

import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import com.google.common.base.Optional;
import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class PlankType {
    public static final int MAX_PLANKS = 256;

    public static void setup() {
        for (final VanillaPlanks plank : VanillaPlanks.values()) {
            CarpentryManager.carpentryInterface.registerCarpentryWood(plank.ordinal(), plank);
        }
        for (final ExtraTreePlanks plank2 : ExtraTreePlanks.values()) {
            CarpentryManager.carpentryInterface.registerCarpentryWood(plank2.ordinal() + 32, plank2);
        }
        for (final ForestryPlanks plank3 : ForestryPlanks.values()) {
            CarpentryManager.carpentryInterface.registerCarpentryWood(plank3.ordinal() + 128, plank3);
        }
        for (final ExtraBiomesPlank plank4 : ExtraBiomesPlank.values()) {
            CarpentryManager.carpentryInterface.registerCarpentryWood(plank4.ordinal() + 192, plank4);
        }
    }

    public enum ExtraTreePlanks implements IPlankType, IFenceProvider {
        Fir(12815444),
        Cedar(14181940),
        Hemlock(13088108),
        Cypress(16169052),
        Fig(13142058),
        Beech(14784849),
        Alder(12092755),
        Hazel(13480341),
        Hornbeam(12818528),
        Box(16511430),
        Butternut(15510138),
        Hickory(14333070),
        Whitebeam(13222585),
        Elm(15772004),
        Apple(6305064),
        Yew(14722426),
        Pear(12093805),
        Hawthorn(13402978),
        Rowan(13610394),
        Elder(12489337),
        Maclura(15970862),
        Syzgium(15123393),
        Brazilwood(7487063),
        Logwood(10762028),
        Iroko(7681024),
        Locust(12816736),
        Eucalyptus(16165771),
        Purpleheart(5970991),
        Ash(16107368),
        Holly(16512743),
        Olive(11578760),
        Sweetgum(13997656),
        Rosewood(7738624),
        Gingko(16050106),
        PinkIvory(15502496);

        int color;
        //IIcon icon;

        ExtraTreePlanks(final int color) {
            this.color = color;
        }

        @Override
        public String getName() {
            return ExtraTrees.proxy.localise("block.planks." + this.toString().toLowerCase());
        }

        @Override
        public String getDescription() {
            return ExtraTrees.proxy.localise("block.planks." + this.toString().toLowerCase() + ".desc");
        }

        @Override
        public int getColour() {
            return this.color;
        }

        @Override
        public ItemStack getStack() {
            return TileEntityMetadata.getItemStack(ExtraTrees.blockPlanks, this.ordinal());
        }

//		public IIcon loadIcon(final IIconRegister register) {
//			return this.icon = ExtraTrees.proxy.getIcon(register, "planks/" + this.toString());
//		}
//
//		@Override
//		public IIcon getIcon() {
//			return this.icon;
//		}

        @Override
        public ItemStack getFence() {
            return TileEntityMetadata.getItemStack(ExtraTrees.blockFence, WoodManager.getPlankTypeIndex(this));
        }
    }

    public enum VanillaPlanks implements IPlankType {
        OAK(11833434),
        SPRUCE(8412726),
        BIRCH(14139781),
        JUNGLE(11632732),
        ACACIA(12215095),
        DARKOAK(4599061);

        int color;

        VanillaPlanks(final int color) {
            this.color = color;
        }

        @Override
        public String getName() {
            return ExtraTrees.proxy.localise("block.planks.vanilla." + this.toString().toLowerCase());
        }

        @Override
        public String getDescription() {
            return ExtraTrees.proxy.localise("block.planks.vanilla." + this.toString().toLowerCase() + ".desc");
        }

        @Override
        public int getColour() {
            return this.color;
        }

        @Override
        public ItemStack getStack() {
            return new ItemStack(Blocks.PLANKS, 1, this.ordinal());
        }

//		@Override
//		public IIcon getIcon() {
//			if (this.getStack() != null) {
//				final int meta = this.getStack().getItemDamage();
//				final Block block = Blocks.planks;
//				return block.getIcon(2, meta);
//			}
//			return null;
//		}
    }

    public enum ForestryPlanks implements IPlankType, IFenceProvider {
        LARCH(14131085, EnumForestryWoodType.LARCH),
        TEAK(8223075, EnumForestryWoodType.TEAK),
        ACACIA(9745287, EnumForestryWoodType.ACACIA),
        LIME(13544048, EnumForestryWoodType.LIME),
        CHESTNUT(12298845, EnumForestryWoodType.CHESTNUT),
        WENGE(6182474, EnumForestryWoodType.WENGE),
        BAOBAB(9608290, EnumForestryWoodType.BAOBAB),
        SEQUOIA(10050135, EnumForestryWoodType.SEQUOIA),
        KAPOK(8156212, EnumForestryWoodType.KAPOK),
        EBONY(3946288, EnumForestryWoodType.EBONY),
        MAHOGANY(7749432, EnumForestryWoodType.MAHOGANY),
        BALSA(11117209, EnumForestryWoodType.BALSA),
        WILLOW(11710818, EnumForestryWoodType.WILLOW),
        WALNUT(6836802, EnumForestryWoodType.WALNUT),
        GREENHEART(5144156, EnumForestryWoodType.GREENHEART),
        CHERRY(11895348, EnumForestryWoodType.CHERRY),
        MAHOE(8362154, EnumForestryWoodType.MAHOE),
        POPLAR(13619074, EnumForestryWoodType.POPLAR),
        PALM(13271115, EnumForestryWoodType.PALM),
        PAPAYA(14470005, EnumForestryWoodType.PAPAYA),
        PINE(12885585, EnumForestryWoodType.PINE),
        PLUM(11364479, EnumForestryWoodType.PLUM),
        MAPLE(11431211, EnumForestryWoodType.MAPLE),
        CITRUS(10266653, EnumForestryWoodType.CITRUS),
        GIGANTEUM(5186590, EnumForestryWoodType.GIGANTEUM),
        IPE(5057822, EnumForestryWoodType.IPE),
        PADAUK(11756341, EnumForestryWoodType.PADAUK),
        COCOBOLO(7541506, EnumForestryWoodType.COCOBOLO),
        ZEBRAWOOD(10912334, EnumForestryWoodType.ZEBRAWOOD);

        private final IWoodType woodType;
        private final int color;

        ForestryPlanks(final int color, IWoodType woodType) {
            this.color = color;
            this.woodType = woodType;
        }

        @Override
        public String getName() {
            return ExtraTrees.proxy.localise("block.planks.forestry." + this.toString().toLowerCase());
        }

        @Override
        public String getDescription() {
            return ExtraTrees.proxy.localise("block.planks.forestry." + this.toString().toLowerCase() + ".desc");
        }

        @Override
        public int getColour() {
            return this.color;
        }

        @Override
        public ItemStack getStack() {
            return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, false);
        }

//		@Override
//		public IIcon getIcon() {
//			if (this.getStack() != null) {
//				final int meta = this.getStack().getItemDamage();
//				final Block block = ((ItemBlock) this.getStack().getItem()).field_150939_a;
//				return block.getIcon(2, meta);
//			}
//			return null;
//		}

        @Override
        public ItemStack getFence() {
            return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.FENCE, false);
        }
    }

    public enum ExtraBiomesPlank implements IPlankType {
        Redwood(10185538),
        Fir(8288074),
        Acacia(12561022);

        int color;

        ExtraBiomesPlank(final int color) {
            this.color = color;
        }

        @Override
        public String getName() {
            return ExtraTrees.proxy.localise("block.planks.ebxl." + this.toString().toLowerCase());
        }

        @Override
        public String getDescription() {
            return ExtraTrees.proxy.localise("block.planks.ebxl." + this.toString().toLowerCase() + ".desc");
        }

        @Override
        public int getColour() {
            return this.color;
        }

        @Override
        public ItemStack getStack() {
            try {
                final Class clss = Class.forName("extrabiomes.api.Stuff");
                final Block block = (Block) ((Optional) clss.getField("planks").get(null)).get();
                return new ItemStack(block, 1, this.ordinal());
            } catch (Exception e) {
                return null;
            }
        }

//		@Override
//		public IIcon getIcon() {
//			if (this.getStack() != null) {
//				final int meta = this.getStack().getItemDamage();
//				final Block block = ((ItemBlock) this.getStack().getItem()).field_150939_a;
//				return block.getIcon(2, meta);
//			}
//			return null;
//		}
    }
}
