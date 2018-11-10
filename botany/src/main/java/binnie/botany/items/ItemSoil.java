package binnie.botany.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.blocks.BlockSoil;
import binnie.core.util.I18N;

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
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
		StringBuilder builder = new StringBuilder();
		builder.append(BlockSoil.getMoisture(stack, true, true));

		String phInfo = BlockSoil.getPH(stack, true, true);
		if (phInfo.length() > 0) {
			if (builder.length() > 0) {
				builder.append(", ");
			}
			builder.append(phInfo);
		}
		if (builder.length() > 0) {
			tooltip.add(builder.toString());
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
