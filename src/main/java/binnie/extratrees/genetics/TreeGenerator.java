package binnie.extratrees.genetics;

import binnie.extratrees.gen.WorldGenDefault;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.*;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.genetics.Tree;
import forestry.arboriculture.tiles.TileLeaves;
import forestry.core.genetics.alleles.AlleleBoolean;
import forestry.core.tiles.TileUtil;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nullable;

public class TreeGenerator implements ITreeGenerator {
	Class<? extends WorldGenerator> generator;
	IWoodType woodType;

	public TreeGenerator(Class<? extends WorldGenerator> generator, @Nullable IWoodType woodType) {
		this.generator = generator;
		this.woodType = woodType;
	}

	@Override
	public WorldGenerator getWorldGenerator(ITreeGenData tree) {
		try {
			return generator.getConstructor(ITree.class).newInstance(tree);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new WorldGenDefault(tree);
	}

	@Override
	public boolean setLogBlock(ITreeGenome genome, World world, BlockPos pos, EnumFacing facing) {
		if (woodType != null) {
			AlleleBoolean fireproofAllele = (AlleleBoolean) genome.getActiveAllele(EnumTreeChromosome.FIREPROOF);
			boolean fireproof = fireproofAllele.getValue();
			return world.setBlockState(pos, TreeManager.woodAccess.getBlock(woodType, WoodBlockKind.LOG, fireproof), 2);
		}
		return false;
	}

	@Override
	public boolean setLeaves(ITreeGenome genome, World world, GameProfile owner, BlockPos pos) {
		boolean placed = world.setBlockState(pos, PluginArboriculture.blocks.leaves.getDefaultState());
		if (!placed) {
			return false;
		}

		Block block = world.getBlockState(pos).getBlock();
		if (PluginArboriculture.blocks.leaves != block) {
			world.setBlockToAir(pos);
			return false;
		}

		TileLeaves tileLeaves = TileUtil.getTile(world, pos, TileLeaves.class);
		if (tileLeaves == null) {
			world.setBlockToAir(pos);
			return false;
		}

		tileLeaves.getOwnerHandler().setOwner(owner);
		tileLeaves.setTree(new Tree(genome));

		world.markBlockRangeForRenderUpdate(pos, pos);
		return true;
	}

}
