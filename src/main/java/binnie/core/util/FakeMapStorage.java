package binnie.core.util;

import javax.annotation.Nullable;

import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class FakeMapStorage extends MapStorage {
	public FakeMapStorage() {
		super(null);
	}

	@Override
	@Nullable
	public WorldSavedData getOrLoadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
		return this.loadedDataMap.get(dataIdentifier);
	}

	@Override
	public void setData(String dataIdentifier, WorldSavedData data) {
		this.loadedDataMap.put(dataIdentifier, data);
	}

	@Override
	public void saveAllData() {
	}

	@Override
	public int getUniqueDataId(String key) {
		return 0;
	}
}
