package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IBlockSoil;
import binnie.core.BinnieCore;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoilMeter extends Item implements IItemModelRegister {

	public ItemSoilMeter() {
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName("soil_meter");
		this.setMaxStackSize(1);
		setRegistryName("soil_meter");
	}

	public static String getPH(ItemStack stack, boolean withColor, boolean byNeutralNone) {
		EnumAcidity ph = EnumAcidity.values()[stack.getItemDamage() / 3];
		if (byNeutralNone) {
			if (ph == EnumAcidity.Neutral) {
				return "";
			}
		}
		return TextFormatting.GRAY + Binnie.LANGUAGE.localise("botany.ph") + ": " + ph.getTranslated(withColor);
	}

	public static String getMoisture(ItemStack stack, boolean withColor, boolean byNormalNone) {
		EnumMoisture moisure = EnumMoisture.values()[stack.getItemDamage() % 3];
		if (byNormalNone) {
			if (moisure == EnumMoisture.Normal) {
				return "";
			}
		}
		return TextFormatting.GRAY + Binnie.LANGUAGE.localise("botany.moisture") + ": " + moisure.getTranslated(withColor);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (!Gardening.isSoil(block)) {
			pos = pos.down();
			block = worldIn.getBlockState(pos).getBlock();
		}
		if (!Gardening.isSoil(block)) {
			pos = pos.down();
			block = worldIn.getBlockState(pos).getBlock();
		}
		if (Gardening.isSoil(block) && !BinnieCore.getBinnieProxy().isSimulating(worldIn)) {
			IBlockSoil soil = (IBlockSoil) block;
			String info = Binnie.LANGUAGE.localise("botany.soil.type") + ": ";
			info = info + soil.getType(worldIn, pos).getTranslated(true);
			info += ", " + TextFormatting.WHITE + Binnie.LANGUAGE.localise("botany.moisture") + ": ";
			info = info + soil.getMoisture(worldIn, pos).getTranslated(true);
			info += ", " + TextFormatting.WHITE + Binnie.LANGUAGE.localise("botany.ph") + ": ";
			info = info + soil.getPH(worldIn, pos).getTranslated(true);
			ITextComponent chat = new TextComponentString(info);
			player.sendStatusMessage(chat, false);
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Soil Meter";
	}
}
