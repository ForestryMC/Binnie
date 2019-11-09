package binnie.core.util;

import binnie.core.Constants;
import binnie.core.tile.TileEntityDataFixer;

import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MigrationUtil {

    private static Map<String, String> remappedTiles = new HashMap<>();

    static{
        addRemappedTile("botany.tile.flower", "botany:tile.flower");
        addRemappedTile("botany.tile.ceramic", "botany:tile.ceramic");
        addRemappedTile("botany.tile.ceramicBrick", "botany:tile.ceramicBrick");
        addRemappedTile("binnie.tile.metadata", "binniecore:tile.metadata");
        addRemappedTile("binnie.tile.machine", "binniecore:tile.machine");
        addRemappedTile("extrabees.tile.alveary", "extrabees:tile.alveary");
        addRemappedTile("binnie.tile.nursery", "extratrees:tile.nursery");
    }

    @Nullable
    public static String getRemappedTile(String oldName)
    {
       if(remappedTiles.containsKey(oldName))
       {
            return remappedTiles.get(oldName);
       }
       return null;
    }

    public static boolean addRemappedTile(String oldName, String newName)
    {
        if(remappedTiles.containsKey(oldName))
        {
            return false;
        }
        remappedTiles.put(oldName, newName);
        return true;
    }

    public static void RegisterFixable()
    {
        TileEntityDataFixer tileEntityDataFixer = new TileEntityDataFixer();
        CompoundDataFixer compoundDataFixer = FMLCommonHandler.instance().getDataFixer();
        ModFixs modFixs = compoundDataFixer.init(Constants.CORE_MOD_ID, tileEntityDataFixer.getFixVersion());
        modFixs.registerFix(FixTypes.BLOCK_ENTITY, tileEntityDataFixer);
    }
}
