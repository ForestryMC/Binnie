package binnie.botany.flower;

import binnie.Constants;
import binnie.botany.api.IFlowerType;
import com.google.common.collect.Maps;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class StateMapperFlower extends StateMapperBase {

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		final Map<IProperty<?>, Comparable<?>> properties = Maps.newLinkedHashMap(state.getProperties());
		IFlowerType flowerType = (IFlowerType) state.getValue(BlockFlower.FLOWER);
		if (flowerType.getSections() < 2) {
			properties.remove(BlockFlower.SECTION);
		} else if (flowerType.getSections() <= state.getValue(BlockFlower.SECTION)) {
			properties.put(BlockFlower.SECTION, flowerType.getSections() - 1);
		}
		if (state.getValue(BlockFlower.SEED)) {
			properties.remove(BlockFlower.SECTION);
			properties.remove(BlockFlower.FLOWER);
			properties.remove(BlockFlower.FLOWERED);
		} else {
			properties.remove(BlockFlower.SEED);
		}

		return new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":flower", getPropertyString(properties));
	}

}
