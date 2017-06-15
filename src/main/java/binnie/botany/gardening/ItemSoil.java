package binnie.botany.gardening;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.botany.api.EnumSoilType;

public class ItemSoil extends ItemBlock {
	private final EnumSoilType type;
	private final boolean noWeed;

	public ItemSoil(BlockSoil block) {
		super(block);
		this.type = block.getType();
		this.noWeed = block.isWeedKilled();
		this.hasSubtypes = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List<String> tooltip, final boolean advanced) {
		String info = "";
		info += BlockSoil.getMoisture(stack, true, true);
		if (info.length() > 0) {
			info += ", ";
		}
		info += BlockSoil.getPH(stack, true, true);
		if (info.length() > 0) {
			tooltip.add(info);
		}
		if (this.noWeed) {
			tooltip.add(TextFormatting.GREEN + Binnie.LANGUAGE.localise("botany.soil.weedkiller"));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return this.getUnlocalizedNameInefficiently(stack).trim();
	}

	@Override
	public int getMetadata(final int damage) {
		return damage;
	}
}
