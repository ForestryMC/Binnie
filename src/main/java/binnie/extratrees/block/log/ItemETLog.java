package binnie.extratrees.block.log;

import binnie.extratrees.block.WoodManager;
import forestry.api.arboriculture.IWoodType;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.items.ItemBlockWood;
import net.minecraft.item.ItemStack;

public class ItemETLog <V extends BlockETLog> extends ItemBlockWood<V> {

	public ItemETLog(V block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
		setRegistryName(block.getRegistryName());
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		IWoodTyped wood = getBlock();
		int meta = itemstack.getMetadata();
		IWoodType woodType = wood.getWoodType(meta);
		return WoodManager.getDisplayName(wood, woodType);
	}

}
