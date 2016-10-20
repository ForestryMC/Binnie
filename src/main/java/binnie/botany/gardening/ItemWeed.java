// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.gardening;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemWeed extends ItemBlock
{
	public ItemWeed(final Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setHasSubtypes(true);
		this.hasSubtypes = true;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		return BlockPlant.Type.values()[stack.getItemDamage()].getName();
	}

	@Override
	public int getMetadata(final int p_77647_1_) {
		return p_77647_1_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(final int p_77617_1_) {
		return this.field_150939_a.getIcon(2, p_77617_1_);
	}
}
