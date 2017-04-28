package binnie.botany.ceramic;

import binnie.core.block.ItemMetadata;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

// TODO unused class?
public class ItemCeramic extends ItemMetadata {
	public ItemCeramic(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return 0xff00ff;
	}
}
