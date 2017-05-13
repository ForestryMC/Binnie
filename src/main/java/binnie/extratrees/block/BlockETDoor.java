package binnie.extratrees.block;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockETDoor extends BlockDoor implements IBlockMetadata, ITileEntityProvider {
//	private IIcon getFlippedIcon(final boolean upper, final boolean flip, final int tileMeta) {
//		final DoorType type = getDoorType(tileMeta);
//		return upper ? (flip ? type.iconDoorUpperFlip : type.iconDoorUpper) : (flip ? type.iconDoorLowerFlip : type.iconDoorLower);
//	}

	public static DoorType getDoorType(final int tileMeta) {
		final int type = (tileMeta & 0xF00) >> 8;
		if (type >= 0 && type < DoorType.values().length) {
			return DoorType.values()[type];
		}
		return DoorType.Standard;
	}

	protected BlockETDoor() {
		super(Material.WOOD);
		this.setHardness(3.0f);
		setSoundType(SoundType.WOOD);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName("door");
	}

//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return DoorType.Standard.iconDoorLower;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
//		if (par5 != 1 && par5 != 0) {
//			final int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
//			final int j1 = i1 & 0x3;
//			final boolean flag = (i1 & 0x4) != 0x0;
//			boolean flag2 = false;
//			final boolean flag3 = (i1 & 0x8) != 0x0;
//			if (flag) {
//				if (j1 == 0 && par5 == 2) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 1 && par5 == 5) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 2 && par5 == 3) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 3 && par5 == 4) {
//					flag2 = !flag2;
//				}
//			}
//			else {
//				if (j1 == 0 && par5 == 5) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 1 && par5 == 3) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 2 && par5 == 4) {
//					flag2 = !flag2;
//				}
//				else if (j1 == 3 && par5 == 2) {
//					flag2 = !flag2;
//				}
//				if ((i1 & 0x10) != 0x0) {
//					flag2 = !flag2;
//				}
//			}
//			int tileMeta = 0;
//			if (flag3) {
//				tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
//			}
//			else {
//				tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
//			}
//			return this.getFlippedIcon(flag3, flag2, tileMeta);
//		}
//		return DoorType.Standard.iconDoorLower;
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		for (final DoorType type : DoorType.values()) {
//			type.iconDoorLower = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".lower");
//			type.iconDoorUpper = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".upper");
//			type.iconDoorLowerFlip = new IconFlipped(type.iconDoorLower, true, false);
//			type.iconDoorUpperFlip = new IconFlipped(type.iconDoorUpper, true, false);
//		}
//	}
//
//	@Override
//	public int getRenderType() {
//		return ExtraTrees.doorRenderId;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
//		final int par5 = 2;
//		final int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
//		final int j1 = i1 & 0x3;
//		final boolean flag = (i1 & 0x4) != 0x0;
//		boolean flag2 = false;
//		final boolean flag3 = (i1 & 0x8) != 0x0;
//		if (flag) {
//			if (j1 == 0 && par5 == 2) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 1 && par5 == 5) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 2 && par5 == 3) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 3 && par5 == 4) {
//				flag2 = !flag2;
//			}
//		}
//		else {
//			if (j1 == 0 && par5 == 5) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 1 && par5 == 3) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 2 && par5 == 4) {
//				flag2 = !flag2;
//			}
//			else if (j1 == 3 && par5 == 2) {
//				flag2 = !flag2;
//			}
//			if ((i1 & 0x10) != 0x0) {
//				flag2 = !flag2;
//			}
//		}
//		if (flag3) {
//			final int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
//			return WoodManager.getPlankType(meta & 0xFF).getColour();
//		}
//		final int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
//		return WoodManager.getPlankType(meta & 0xFF).getColour();
//	}

//	public int getFullMetadata(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
//		final int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
//		final boolean flag = (l & 0x8) != 0x0;
//		int i1;
//		int j1;
//		if (flag) {
//			i1 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
//			j1 = l;
//		}
//		else {
//			i1 = l;
//			j1 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
//		}
//		final boolean flag2 = (j1 & 0x1) != 0x0;
//		return (i1 & 0x7) | (flag ? 8 : 0) | (flag2 ? 16 : 0);
//	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		int meta = getMetaFromState(state);
		if (player.capabilities.isCreativeMode && (meta & 0x8) != 0x0 && world.getBlockState(pos.down()).getBlock() == this) {
			world.setBlockToAir(pos.down());
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return BlockMetadata.getBlockDroppedAsList(this, world, pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		return BlockMetadata.breakBlock(this, player, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int k) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		return tileMetadata;
	}

	@Override
	public String getDisplayName(ItemStack itemStack) {
		final int meta = TileEntityMetadata.getItemDamage(itemStack);
		final String typeName = getDoorType(meta).getName();
		final String woodName = WoodManager.getPlankType(meta & 0xFF).getName();
		if (typeName.equals("")) {
			return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.door.name", woodName);
		}
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.door.name.adv", woodName, typeName);
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> itemList) {
		for (final IPlankType type : PlankType.ExtraTreePlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
		for (final IPlankType type : PlankType.ForestryPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
		//TODO: extrabiomes 1.10.2
		/*for (final IPlankType type : PlankType.ExtraBiomesPlank.values()) {
			if (type.getStack() != null) {
				itemList.add(WoodManager.getDoor(type, DoorType.Standard));
			}
		}*/
		for (final IPlankType type : PlankType.VanillaPlanks.values()) {
			itemList.add(WoodManager.getDoor(type, DoorType.Standard));
		}
	}


	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 20;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

}
