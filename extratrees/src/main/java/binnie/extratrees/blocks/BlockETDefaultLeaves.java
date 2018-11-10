package binnie.extratrees.blocks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ILeafSpriteProvider;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.TreeManager;
import forestry.api.core.IModelManager;
import forestry.arboriculture.blocks.BlockAbstractLeaves;

import binnie.core.Constants;
import binnie.extratrees.blocks.property.PropertyETType;
import binnie.extratrees.genetics.ETTreeDefinition;

/**
 * Genetic leaves with no tile entity, used for worldgen trees.
 * Similar to decorative leaves, but these will drop saplings and can be used for pollination.
 */
public abstract class BlockETDefaultLeaves extends BlockAbstractLeaves {
	private static final int VARIANTS_PER_BLOCK = 4;

	public static List<BlockETDefaultLeaves> create() {
		List<BlockETDefaultLeaves> blocks = new ArrayList<>();
		final int blockCount = PropertyETType.getBlockCount(VARIANTS_PER_BLOCK);
		for (int blockNumber = 0; blockNumber < blockCount; blockNumber++) {
			final PropertyETType variant = PropertyETType.create("variant", blockNumber, VARIANTS_PER_BLOCK);
			BlockETDefaultLeaves block = new BlockETDefaultLeaves(blockNumber) {
				@Override
				public PropertyETType getVariant() {
					return variant;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	private final int blockNumber;

	public BlockETDefaultLeaves(int blockNumber) {
		this.blockNumber = blockNumber;
		PropertyETType variant = getVariant();
		setDefaultState(this.blockState.getBaseState()
			.withProperty(variant, variant.getFirstType())
			.withProperty(CHECK_DECAY, false)
			.withProperty(DECAYABLE, true));
		String name = "leaves.default." + blockNumber;
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
	}

	public int getBlockNumber() {
		return blockNumber;
	}

	public abstract PropertyETType getVariant();

	@Nullable
	public ETTreeDefinition getTreeDefinition(IBlockState blockState) {
		if (blockState.getBlock() == this) {
			return blockState.getValue(getVariant());
		} else {
			return null;
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		ETTreeDefinition treeDefinition = getTreeDefinition(state);
		if (treeDefinition == null) {
			return 0;
		}
		return treeDefinition.getMetadata() - blockNumber * VARIANTS_PER_BLOCK;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
			.withProperty(getVariant(), getTreeType(meta))
			.withProperty(DECAYABLE, (meta & 4) == 0)
			.withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = damageDropped(state);

		if (!state.getValue(DECAYABLE)) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY)) {
			i |= 8;
		}

		return i;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getVariant(), CHECK_DECAY, DECAYABLE);
	}

	public ETTreeDefinition getTreeType(int meta) {
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + blockNumber * VARIANTS_PER_BLOCK;
		return ETTreeDefinition.byMetadata(variantMeta);
	}

	@Override
	protected void getLeafDrop(NonNullList<ItemStack> drops, World world, @Nullable GameProfile playerProfile, BlockPos pos, float saplingModifier, int fortune) {
		ITree tree = getTree(world, pos);
		if (tree == null) {
			return;
		}

		// Add saplings
		List<ITree> saplings = tree.getSaplings(world, playerProfile, pos, saplingModifier);
		for (ITree sapling : saplings) {
			if (sapling != null) {
				drops.add(TreeManager.treeRoot.getMemberStack(sapling, EnumGermlingType.SAPLING));
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		ETTreeDefinition type = getTreeType(meta);
		return getDefaultState().withProperty(getVariant(), type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (IBlockState state : blockState.getValidStates()) {
			int meta = getMetaFromState(state);
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("extratrees:leaves.default." + blockNumber, "inventory"));
		}
	}

	@Override
	protected ITree getTree(IBlockAccess world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		ETTreeDefinition treeDefinition = getTreeDefinition(blockState);
		if (treeDefinition != null) {
			return treeDefinition.getIndividual();
		} else {
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
		ETTreeDefinition treeDefinition = getTreeDefinition(state);
		if (treeDefinition == null) {
			treeDefinition = ETTreeDefinition.OrchardApple;
		}
		ITreeGenome genome = treeDefinition.getGenome();

		ILeafSpriteProvider spriteProvider = genome.getPrimary().getLeafSpriteProvider();
		return spriteProvider.getColor(false);
	}
}