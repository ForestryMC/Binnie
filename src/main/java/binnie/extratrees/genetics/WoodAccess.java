package binnie.extratrees.genetics;

import forestry.api.arboriculture.IWoodAccess;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WoodAccess implements IWoodAccess {
	private WoodHolder wood = new WoodHolder();
	private WoodHolder woodFireproof= new WoodHolder();

	private static final WoodAccess instance = new WoodAccess();

	public static WoodAccess getInstance() {
		return instance;
	}

	public <T extends Block, V extends Enum<V> & IWoodType> void registerWithVariants(T woodTyped, WoodBlockKind woodBlockKind, PropertyEnum<V> property, boolean fireproof) {
		for (V value : property.getAllowedValues()) {
			IBlockState blockState = woodTyped.getDefaultState().withProperty(property, value);
			if(fireproof) {
				wood.addWood(value, woodBlockKind, blockState);
			}else {
				woodFireproof.addWood(value, woodBlockKind, blockState);
			}
		}
	}

	@Override
	public ItemStack getStack(IWoodType woodType, WoodBlockKind kind, boolean fireproof) {
		throw new NotImplementedException("");
	}

	@Override
	public IBlockState getBlock(IWoodType woodType, WoodBlockKind kind, boolean fireproof) {
		IBlockState state = fireproof?woodFireproof.getWood(woodType, kind) : wood.getWood(woodType, kind);
		return state==null? TreeManager.woodAccess.getBlock(woodType, kind, fireproof) : state;
	}

	@Override
	public List<IWoodType> getRegisteredWoodTypes() {
		throw new NotImplementedException("");
	}

	private class WoodHolder{
		private Map<WoodBlockKind, Map<IWoodType, IBlockState>> woodMap = new HashMap<>();

		public void addWood(IWoodType woodType, WoodBlockKind kind, IBlockState state){
			Map<IWoodType, IBlockState> woodTypes = woodMap.get(kind);
			if (woodTypes == null){
				woodTypes = new HashMap<>();
				woodMap.put(kind, woodTypes);
			}
			woodTypes.put(woodType, state);
		}

		public IBlockState getWood(IWoodType woodType, WoodBlockKind kind){
			Map<IWoodType, IBlockState> woodTypes = woodMap.get(kind);
			return woodTypes!=null ? woodTypes.get(woodType) : null;
		}
	}
}
