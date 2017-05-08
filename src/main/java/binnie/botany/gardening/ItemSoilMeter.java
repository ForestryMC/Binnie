package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemSoilMeter extends Item {
	public ItemSoilMeter() {
		setCreativeTab(CreativeTabBotany.instance);
		setUnlocalizedName("soilMeter");
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float blockX, float blockY, float blockZ) {
		Block block = world.getBlock(x, y, z);
		if (!Gardening.isSoil(block)) {
			y--;
			block = world.getBlock(x, y, z);
		}

		if (Gardening.isSoil(block) && !BinnieCore.proxy.isSimulating(world)) {
			IBlockSoil soil = (IBlockSoil) block;
			EnumSoilType type = soil.getType(world, x, y, z);
			EnumMoisture moisture = soil.getMoisture(world, x, y, z);
			EnumAcidity pH = soil.getPH(world, x, y, z);

			// TODO remove hardcode strings ans localize
			EnumChatFormatting[] colors = new EnumChatFormatting[]{
				EnumChatFormatting.DARK_GRAY,
				EnumChatFormatting.GOLD,
				EnumChatFormatting.LIGHT_PURPLE,
			};
			String info = "Type: ";
			info += colors[type.ordinal()] + Binnie.I18N.localise(type) + EnumChatFormatting.RESET;

			colors = new EnumChatFormatting[]{
				EnumChatFormatting.YELLOW,
				EnumChatFormatting.GRAY,
				EnumChatFormatting.BLUE,
			};
			info += ", Moisture: ";
			info += colors[moisture.ordinal()] + Binnie.I18N.localise(moisture) + EnumChatFormatting.RESET;

			colors = new EnumChatFormatting[]{
				EnumChatFormatting.RED,
				EnumChatFormatting.GREEN,
				EnumChatFormatting.AQUA,
			};
			info += ", pH: ";
			info += colors[pH.ordinal()] + Binnie.I18N.localise(pH) + EnumChatFormatting.RESET;
			IChatComponent chat = new ChatComponentText(info);
			player.addChatMessage(chat);
		}
		return super.onItemUse(stack, player, world, x, y, z, side, blockX, blockY, blockZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = Botany.proxy.getIcon(register, "soilMeter");
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Soil Meter";
	}
}
