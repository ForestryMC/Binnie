package binnie.extratrees.block;

import binnie.core.block.TileEntityMetadata;
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
		Apple("Apple", 8092283, PlankType.ExtraTreePlanks.Apple),
		Fig("Fig", 8418135, PlankType.ExtraTreePlanks.Fig),
		Butternut("Butternut", 12037536, PlankType.ExtraTreePlanks.Butternut),
		Cherry("Cherry", 7432272, PlankType.ForestryPlanks.CHERRY),
		Whitebeam("Whitebeam", 7891565, PlankType.ExtraTreePlanks.Whitebeam),
		Rowan("Rowan", 11972763, PlankType.ExtraTreePlanks.Rowan),
		Hemlock("Hemlock", 11379611, PlankType.ExtraTreePlanks.Hemlock),
		Ash("Ash", 9013634, PlankType.ExtraTreePlanks.Ash),
		Alder("Alder", 13025464, PlankType.ExtraTreePlanks.Alder),
		Beech("Beech", 11702654, PlankType.ExtraTreePlanks.Beech),
		Hawthorn("Hawthorn", 6248261, PlankType.ExtraTreePlanks.Hawthorn),
		Banana("Banana", 8753743),
		Yew("Yew", 13745089, PlankType.ExtraTreePlanks.Yew),
		Cypress("Cypress", 10126467, PlankType.ExtraTreePlanks.Cypress),
		Fir("Fir", 8553346, PlankType.ExtraTreePlanks.Fir),
		Hazel("Hazel", 11180143, PlankType.ExtraTreePlanks.Hazel),
		Hickory("Hickory", 4076848, PlankType.ExtraTreePlanks.Hickory),
		Elm("Elm", 8684422, PlankType.ExtraTreePlanks.Elm),
		Elder("Elder", 14202996, PlankType.ExtraTreePlanks.Elder),
		Holly("Holly", 11905669, PlankType.ExtraTreePlanks.Holly),
		Hornbeam("Hornbeam", 10719862, PlankType.ExtraTreePlanks.Hornbeam),
		Cedar("Cedar", 11368015, PlankType.ExtraTreePlanks.Cedar),
		Olive("Olive", 8089706, PlankType.ExtraTreePlanks.Olive),
		Sweetgum("Sweetgum", 10592668, PlankType.ExtraTreePlanks.Sweetgum),
		Locust("Locust", 11381948, PlankType.ExtraTreePlanks.Locust),
		Pear("Pear", 11048825, PlankType.ExtraTreePlanks.Pear),
		Maclura("Maclura", 9131828, PlankType.ExtraTreePlanks.Maclura),
		Brazilwood("Brazilwood", 10387560, PlankType.ExtraTreePlanks.Brazilwood),
		Logwood("Logwood", 16376530, PlankType.ExtraTreePlanks.Logwood),
		Rosewood("Rosewood", 10061414, PlankType.ExtraTreePlanks.Rosewood),
		Purpleheart("Purpleheart", 9671330, PlankType.ExtraTreePlanks.Purpleheart),
		Iroko("Iroko", 6315099, PlankType.ExtraTreePlanks.Iroko),
		Gingko("Gingko", 11382428, PlankType.ExtraTreePlanks.Gingko),
		Eucalyptus("Eucalyptus", 15392474, PlankType.ExtraTreePlanks.Eucalyptus),
		Eucalyptus2("Eucalyptus", 8814181, PlankType.ExtraTreePlanks.Eucalyptus),
		Box("Box", 11235159, PlankType.ExtraTreePlanks.Box),
		Syzgium("Syzgium", 11235159, PlankType.ExtraTreePlanks.Syzgium),
		Eucalyptus3("Eucalyptus", 7123007, PlankType.ExtraTreePlanks.Eucalyptus),
		Cinnamon("Cinnamon", 8804412, PlankType.VanillaPlanks.JUNGLE),
		PinkIvory("Pink Ivory", 8349012, PlankType.ExtraTreePlanks.PinkIvory);

		protected String name;
		protected int color;
		protected IDesignMaterial plank;
		protected IIcon trunk;
		protected IIcon bark;

		ExtraTreeLog(String name, int color) {
			plank = null;
			this.name = name;
			this.color = color;
		}

		ExtraTreeLog(String name, int color, IDesignMaterial plank) {
			this.plank = null;
			this.name = name;
			this.color = color;
			this.plank = plank;
		}

		public static void registerIcons(IIconRegister register) {
			for (ExtraTreeLog log : values()) {
				log.trunk = ExtraTrees.proxy.getIcon(register, "logs/" + log.toString().toLowerCase() + "Trunk");
				log.bark = ExtraTrees.proxy.getIcon(register, "logs/" + log.toString().toLowerCase() + "Bark");
			}
		}

		public String getName() {
			return name;
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
			return TileEntityMetadata.getItemStack(ExtraTrees.blockLog, ordinal()).copy();
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
			BlockTypeLog logBlock = new BlockTypeLog(TreeManager.woodItemAccess.getLog(EnumWoodType.valueOf(name().toUpperCase()), false));
			logBlock.setDirection(ForgeDirection.UP);
			logBlock.setBlock(world, x, y, z);

		}

		@Override
		public ItemStack getItemStack() {
			return TreeManager.woodItemAccess.getLog(EnumWoodType.valueOf(name().toUpperCase()), false);
			// return new ItemStack(Mods.Forestry.item("logs"), 1,
			// this.metadata);
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
