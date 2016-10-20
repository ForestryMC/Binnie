// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabBotany extends CreativeTabs
{
	public static CreativeTabs instance;

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Blocks.red_flower, 1, 5);
	}

	public CreativeTabBotany() {
		super("Botany");
	}

	@Override
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel() {
		return Botany.proxy.localise("tab.botany");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.yellow_flower);
	}

	static {
		CreativeTabBotany.instance = new CreativeTabBotany();
	}
}
