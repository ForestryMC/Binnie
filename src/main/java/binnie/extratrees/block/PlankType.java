package binnie.extratrees.block;

import binnie.core.Mods;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PlankType {
	public static void setup() {
		for (VanillaPlanks plank : VanillaPlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank.ordinal(), plank);
		}
		for (ExtraTreePlanks plank2 : ExtraTreePlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank2.ordinal() + 32, plank2);
		}
		for (ForestryPlanks plank3 : ForestryPlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank3.ordinal() + 128, plank3);
		}
		for (ExtraBiomesPlank plank4 : ExtraBiomesPlank.values()) {
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

		protected int color;
		protected IIcon icon;

		ExtraTreePlanks(int color) {
			this.color = color;
		}

		@Override
		public String getName() {
			return ExtraTrees.proxy.localise("block.planks." + toString().toLowerCase());
		}

		@Override
		public String getDescription() {
			return ExtraTrees.proxy.localise("block.planks." + toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColor() {
			return color;
		}

		@Override
		public ItemStack getStack() {
			return TileEntityMetadata.getItemStack(ExtraTrees.blockPlanks, ordinal());
		}

		public IIcon loadIcon(IIconRegister register) {
			return icon = ExtraTrees.proxy.getIcon(register, "planks/" + toString());
		}

		@Override
		public IIcon getIcon() {
			return icon;
		}

		@Override
		public ItemStack getFence() {
			return TileEntityMetadata.getItemStack(ExtraTrees.blockFence, WoodManager.getPlankTypeIndex(this));
		}
	}

	public enum VanillaPlanks implements IPlankType {
		OAK(0xb4905a),
		SPRUCE(0x805e36),
		BIRCH(0xd7c185),
		JUNGLE(0xb1805c),
		ACACIA(0xba6337),
		DARKOAK(0x462d15);

		protected int color;

		VanillaPlanks(int color) {
			this.color = color;
		}

		@Override
		public String getName() {
			return ExtraTrees.proxy.localise("block.planks.vanilla." + toString().toLowerCase());
		}

		@Override
		public String getDescription() {
			return ExtraTrees.proxy.localise("block.planks.vanilla." + toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColor() {
			return color;
		}

		@Override
		public ItemStack getStack() {
			return new ItemStack(Blocks.planks, 1, ordinal());
		}

		@Override
		public IIcon getIcon() {
			int meta = getStack().getItemDamage();
			Block block = Blocks.planks;
			return block.getIcon(2, meta);
		}
	}

	public enum ForestryPlanks implements IPlankType, IFenceProvider {
		LARCH(0xd79f8d),
		TEAK(0x7d7963),
		ACACIA(0x94b387),
		LIME(0xceaa70),
		CHESTNUT(0xbbaa5d),
		WENGE(0x5e564a),
		BAOBAB(0x929c62),
		SEQUOIA(0x995a57),
		KAPOK(0x7c7434),
		EBONY(0x3c3730),
		MAHOGANY(0x763f38),
		BALSA(0xa9a299),
		WILLOW(0xb2b162),
		WALNUT(0x685242),
		GREENHEART(0x4e7e5c),
		CHERRY(0xb58234),
		MAHOE(0x7f98aa),
		POPLAR(0xcfcf82),
		PALM(0xca804b),
		PAPAYA(0xdccb75),
		PINE(0xc49e51),
		PLUM(0xad687f),
		MAPLE(0xae6d2b),
		CITRUS(0x9ca81d),
		GIGANTEUM(0x4f241e),
		IPE(0x4d2d1e),
		PADAUK(0xb36335),
		COCOBOLO(0x731302),
		ZEBRAWOOD(0xa6824e);

		protected int color;

		ForestryPlanks(int color) {
			this.color = color;
		}

		@Override
		public String getName() {
			return ExtraTrees.proxy.localise("block.planks.forestry." + toString().toLowerCase());
		}

		@Override
		public String getDescription() {
			return ExtraTrees.proxy.localise("block.planks.forestry." + toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColor() {
			return color;
		}

		@Override
		public ItemStack getStack() {
			Item stack = Mods.forestry.item("planks");
			return new ItemStack(stack, 1, ordinal());
		}

		@Override
		public IIcon getIcon() {
			if (getStack() != null) {
				int meta = getStack().getItemDamage();
				Block block = ((ItemBlock) getStack().getItem()).field_150939_a;
				return block.getIcon(2, meta);
			}
			return null;
		}

		@Override
		public ItemStack getFence() {
			ItemStack fence = Mods.forestry.stack("fences");
			fence.setItemDamage(ordinal());
			return fence;
		}
	}

	public enum ExtraBiomesPlank implements IPlankType {
		Redwood(0x9b6b42),
		Fir(0x7e774a),
		Acacia(0xbfaa7e);

		protected int color;

		ExtraBiomesPlank(int color) {
			this.color = color;
		}

		@Override
		public String getName() {
			return ExtraTrees.proxy.localise("block.planks.ebxl." + toString().toLowerCase());
		}

		@Override
		public String getDescription() {
			return ExtraTrees.proxy.localise("block.planks.ebxl." + toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColor() {
			return color;
		}

		@Override
		public ItemStack getStack() {
			try {
				Class clss = Class.forName("extrabiomes.api.Stuff");
				Block block = (Block) ((Optional) clss.getField("planks").get(null)).get();
				return new ItemStack(block, 1, ordinal());
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public IIcon getIcon() {
			if (getStack() != null) {
				int meta = getStack().getItemDamage();
				Block block = ((ItemBlock) getStack().getItem()).field_150939_a;
				return block.getIcon(2, meta);
			}
			return null;
		}
	}
}
