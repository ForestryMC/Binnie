package binnie.botany.gardening;

import binnie.botany.api.EnumSoilType;
import binnie.core.util.I18N;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSoil extends ItemBlock {
	private final EnumSoilType type;
	private final boolean noWeed;

	public ItemSoil(BlockSoil block) {
		super(block);
		type = block.getType();
		noWeed = block.isWeedKilled();
		hasSubtypes = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		String info = "";
		info += BlockSoil.getMoisture(stack, true, true);
		if (info.length() > 0) {
			info += ", ";
		}

		info += BlockSoil.getPH(stack, true, true);
		if (info.length() > 0) {
			tooltip.add(info);
		}

		if (noWeed) {
			tooltip.add(TextFormatting.GREEN + I18N.localise("botany.soil.weedkiller"));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return getUnlocalizedNameInefficiently(stack).trim();
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
