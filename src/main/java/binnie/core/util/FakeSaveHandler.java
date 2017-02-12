package binnie.core.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class FakeSaveHandler implements ISaveHandler {
	@Override
	public WorldInfo loadWorldInfo() {
		return null;
	}

	@Override
	public void checkSessionLock() throws MinecraftException {
	}

	@Override
	public IChunkLoader getChunkLoader(WorldProvider provider) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
	}

	@Override
	public void saveWorldInfo(WorldInfo worldInformation) {
	}

	@Override
	public IPlayerFileData getPlayerNBTManager() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void flush() {
	}

	@Override
	public File getMapFileFromName(String mapName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public File getWorldDirectory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TemplateManager getStructureTemplateManager() {
		throw new UnsupportedOperationException();
	}
}
