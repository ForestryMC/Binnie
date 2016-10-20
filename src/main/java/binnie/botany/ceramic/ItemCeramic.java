// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.ceramic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import binnie.core.block.ItemMetadata;

public class ItemCeramic extends ItemMetadata
{
	public ItemCeramic(final Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(final ItemStack stack, final int p_82790_2_) {
		return 16711935;
	}
}
