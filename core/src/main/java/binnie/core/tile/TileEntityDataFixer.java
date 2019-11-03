package binnie.core.tile;

import binnie.core.util.Log;
import binnie.core.util.MigrationUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class TileEntityDataFixer implements IFixableData {
    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        String oldName = compound.getString("id").replace("minecraft:", "");
        String remappedName = MigrationUtil.getRemappedTile(oldName);
        if (remappedName != null) {
            Log.debug("Remapped tile entity '{}' to '{}'.", oldName, remappedName);
            compound.setString("id", remappedName);
        }
        return compound;
    }
}
