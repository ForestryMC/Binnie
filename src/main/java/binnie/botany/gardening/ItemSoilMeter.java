package binnie.botany.gardening;

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

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IBlockSoil;
import binnie.botany.api.IGardeningManager;
import binnie.botany.core.BotanyCore;
import binnie.core.BinnieCore;
import binnie.core.util.I18N;

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
			if (ph == EnumAcidity.NEUTRAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.ph") + ": " + ph.getLocalisedName(withColor);
	}

	public static String getMoisture(ItemStack stack, boolean withColor, boolean byNormalNone) {
		EnumMoisture moisure = EnumMoisture.values()[stack.getItemDamage() % 3];
		if (byNormalNone) {
			if (moisure == EnumMoisture.NORMAL) {
				return "";
			}
		}
		return TextFormatting.GRAY + I18N.localise("botany.moisture") + ": " + moisure.getLocalisedName(withColor);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		IGardeningManager gardening = BotanyCore.getGardening();
		if (!gardening.isSoil(block)) {
			pos = pos.down();
			block = worldIn.getBlockState(pos).getBlock();
		}
		if (!gardening.isSoil(block)) {
			pos = pos.down();
			block = worldIn.getBlockState(pos).getBlock();
		}
		if (gardening.isSoil(block) && !BinnieCore.getBinnieProxy().isSimulating(worldIn)) {
			IBlockSoil soil = (IBlockSoil) block;
			String info = I18N.localise("botany.soil.type") + ": ";
			info = info + soil.getType(worldIn, pos).getTranslated();
			info += ", " + TextFormatting.WHITE + I18N.localise("botany.moisture") + ": ";
			info = info + soil.getMoisture(worldIn, pos).getLocalisedName(true);
			info += ", " + TextFormatting.WHITE + I18N.localise("botany.ph") + ": ";
			info = info + soil.getPH(worldIn, pos).getLocalisedName(true);
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
