package binnie.extratrees.genetics;

import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.arboriculture.IWoodTyped;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marcin on 18.10.2016.
 */
public class WoodAccess implements IWoodAccess{
    static HashMap<IWoodType, IBlockState> woodMap = new HashMap<>();

    public static <T extends Block, V extends Enum<V> & IWoodType> void registerWithVariants(T woodTyped, WoodBlockKind woodBlockKind, PropertyEnum<V> property) {
        for (V value : property.getAllowedValues()) {
            IBlockState blockState = woodTyped.getDefaultState().withProperty(property, value);
            woodMap.put(value, blockState);
        }
    }

    @Override
    public ItemStack getStack(IWoodType woodType, WoodBlockKind kind, boolean fireproof) {
        throw new NotImplementedException("");
    }

    @Override
    public IBlockState getBlock(IWoodType woodType, WoodBlockKind kind, boolean fireproof) {
        return woodMap.get(woodType);
    }

    @Override
    public List<IWoodType> getRegisteredWoodTypes() {
        throw new NotImplementedException("");
    }
}
