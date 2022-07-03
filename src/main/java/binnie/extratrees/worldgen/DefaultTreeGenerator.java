package binnie.extratrees.worldgen;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.ITreeGenerator;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.blocks.BlockForestryLeaves;
import forestry.arboriculture.genetics.Tree;
import forestry.arboriculture.tiles.TileLeaves;
import forestry.core.config.Constants;
import forestry.plugins.PluginArboriculture;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class DefaultTreeGenerator implements ITreeGenerator {
    private ExtraTreeSpecies extraTreeSpecies;

    public DefaultTreeGenerator(ExtraTreeSpecies extraTreeSpecies) {
        this.extraTreeSpecies = extraTreeSpecies;
    }

    @Override
    public void setLogBlock(ITreeGenome genome, World world, int x, int y, int z, ForgeDirection direction) {
        if (extraTreeSpecies.wood != null) {
            extraTreeSpecies.wood.placeBlock(world, x, y, z);
        }
    }

    @Override
    public void setLeaves(ITreeGenome genome, World world, GameProfile owner, int x, int y, int z, boolean decorative) {
        boolean placed =
                world.setBlock(x, y, z, PluginArboriculture.blocks.leaves, 0, Constants.FLAG_BLOCK_SYNCH_AND_UPDATE);
        if (!placed) {
            return;
        }

        Block block = world.getBlock(x, y, z);
        if (PluginArboriculture.blocks.leaves != block) {
            world.setBlockToAir(x, y, z);
            return;
        }

        TileLeaves tileLeaves = BlockForestryLeaves.getLeafTile(world, x, y, z);
        if (tileLeaves == null) {
            world.setBlockToAir(x, y, z);
            return;
        }

        tileLeaves.setOwner(owner);
        if (decorative) {
            tileLeaves.setDecorative();
        }

        tileLeaves.setTree(new Tree(genome));
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    public void setLogBlock(World world, int x, int y, int z, ForgeDirection direction) {
        extraTreeSpecies.wood.placeBlock(world, x, y, z);
    }

    @Override
    public void setLeaves(World world, GameProfile player, int x, int y, int z, boolean arg5) {
        // ignored
    }

    @Override
    public WorldGenerator getWorldGenerator(ITreeGenData tree) {
        return extraTreeSpecies.getGenerator(tree);
    }
}
