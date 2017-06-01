package binnie.core.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;

public class FakeCraftingWorld extends World {
	private static final WorldSettings worldSettings = new WorldSettings(0, GameType.SURVIVAL, false, false, WorldType.DEFAULT);
	private static final WorldInfo worldInfo = new WorldInfo(worldSettings, "fake");
	private static final ISaveHandler saveHandler = new FakeSaveHandler();
	private static final WorldProvider worldProvider = new WorldProvider() {
		@Override
		public DimensionType getDimensionType() {
			return DimensionType.OVERWORLD;
		}
	};
	@Nullable
	private static FakeCraftingWorld INSTANCE;

	private FakeCraftingWorld() {
		super(saveHandler, worldInfo, worldProvider, new Profiler(), true);
		this.provider.setWorld(this);
		this.mapStorage = new FakeMapStorage();
	}

	public static FakeCraftingWorld getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FakeCraftingWorld();
		}
		return INSTANCE;
	}

	@Override
	public BlockPos getSpawnPoint() {
		return new BlockPos(0, 0, 0);
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		return Blocks.AIR.getDefaultState();
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
		return false;
	}
}
