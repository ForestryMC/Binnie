package binnie.extratrees.block;

import binnie.Constants;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import com.google.common.base.Optional;
import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

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

	public enum ExtraTreePlanks implements IPlankType, IFenceProvider, IStringSerializable {
		Fir(12815444, EnumExtraTreeLog.Fir),
		Cedar(14181940, EnumExtraTreeLog.Cedar),
		Hemlock(13088108, EnumExtraTreeLog.Hemlock),
		Cypress(16169052, EnumExtraTreeLog.Cypress),
		Fig(13142058, EnumExtraTreeLog.Fig),
		Beech(14784849, EnumExtraTreeLog.Beech),
		Alder(12092755, EnumExtraTreeLog.Alder),
		Hazel(13480341, EnumExtraTreeLog.Hazel),
		Hornbeam(12818528, EnumExtraTreeLog.Hornbeam),
		Box(16511430, EnumExtraTreeLog.Box),
		Butternut(15510138, EnumExtraTreeLog.Butternut),
		Hickory(14333070, EnumExtraTreeLog.Hickory),
		Whitebeam(13222585, EnumExtraTreeLog.Whitebeam),
		Elm(15772004, EnumExtraTreeLog.Elm),
		Apple(6305064, EnumExtraTreeLog.Apple),
		Yew(14722426, EnumExtraTreeLog.Yew),
		Pear(12093805, EnumExtraTreeLog.Pear),
		Hawthorn(13402978, EnumExtraTreeLog.Hawthorn),
		Rowan(13610394, EnumExtraTreeLog.Rowan),
		Elder(12489337, EnumExtraTreeLog.Elder),
		Maclura(15970862, EnumExtraTreeLog.Maclura),
		Syzgium(15123393, EnumExtraTreeLog.Syzgium),
		Brazilwood(7487063, EnumExtraTreeLog.Brazilwood),
		Logwood(10762028, EnumExtraTreeLog.Logwood),
		Iroko(7681024, EnumExtraTreeLog.Iroko),
		Locust(12816736, EnumExtraTreeLog.Locust),
		Eucalyptus(16165771, EnumExtraTreeLog.Eucalyptus),
		Purpleheart(5970991, EnumExtraTreeLog.Purpleheart),
		Ash(16107368, EnumExtraTreeLog.Ash),
		Holly(16512743, EnumExtraTreeLog.Holly),
		Olive(11578760, EnumExtraTreeLog.Olive),
		Sweetgum(13997656, EnumExtraTreeLog.Sweetgum),
		Rosewood(7738624, EnumExtraTreeLog.Rosewood),
		Gingko(16050106, EnumExtraTreeLog.Gingko),
		PinkIvory(15502496, EnumExtraTreeLog.PinkIvory),
		Banana(0, EnumExtraTreeLog.Banana);

		private final IWoodType woodType;
		private int color;
		//IIcon icon;

		ExtraTreePlanks(final int color, IWoodType woodType) {
			this.color = color;
			this.woodType = woodType;
		}

		@Override
		public String getName() {
			return ExtraTrees.proxy.localise("block.planks." + this.toString().toLowerCase());
		}

		@Override
		public String getPlankTextureName(){
			return Constants.EXTRA_TREES_MOD_ID + ":blocks/planks/" + name();
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
			return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, true);
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
		OAK(11833434, EnumVanillaWoodType.OAK),
		SPRUCE(8412726, EnumVanillaWoodType.SPRUCE),
		BIRCH(14139781, EnumVanillaWoodType.BIRCH),
		JUNGLE(11632732, EnumVanillaWoodType.JUNGLE),
		ACACIA(12215095, EnumVanillaWoodType.ACACIA),
		BIG_OAK(4599061, EnumVanillaWoodType.DARK_OAK);

		private final IWoodType woodType;
		private int color;

		VanillaPlanks(final int color, IWoodType woodType) {
			this.color = color;
			this.woodType = woodType;
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
		public String getPlankTextureName(){
			return "minecraft:blocks/planks_" + name().toLowerCase();
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
		public String getPlankTextureName(){
			return "forestry:blocks/wood/planks." + name().toLowerCase();
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
		public String getPlankTextureName() {
			//TODO implement
			return "";
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
				Optional planks = (Optional) clss.getField("planks").get(null);
				if (planks.isPresent()) {
					final Block block = (Block) planks.get();
					return new ItemStack(block, 1, this.ordinal());
				} else {
					return null;
				}
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
