// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.gardening;

import binnie.botany.CreativeTabBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.botany.Botany;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import net.minecraft.block.Block;
import net.minecraft.util.ChatComponentText;
import binnie.Binnie;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.core.BinnieCore;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemSoilMeter extends Item
{
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, int y, final int z, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
		Block block = world.getBlock(x, y, z);
		if (!Gardening.isSoil(block)) {
			--y;
			block = world.getBlock(x, y, z);
		}
		if (Gardening.isSoil(block) && !BinnieCore.proxy.isSimulating(world)) {
			final IBlockSoil soil = (IBlockSoil) block;
			final EnumSoilType type = soil.getType(world, x, y, z);
			final EnumMoisture moisture = soil.getMoisture(world, x, y, z);
			final EnumAcidity pH = soil.getPH(world, x, y, z);

			// TODO remove hardcode strings ans localize
			EnumChatFormatting[] colors = new EnumChatFormatting[] {
				EnumChatFormatting.DARK_GRAY,
				EnumChatFormatting.GOLD,
				EnumChatFormatting.LIGHT_PURPLE,
			};
			String info = "Type: ";
			info += colors[type.ordinal()] + Binnie.Language.localise(type) + EnumChatFormatting.RESET;

			colors = new EnumChatFormatting[] {
				EnumChatFormatting.YELLOW,
				EnumChatFormatting.GRAY,
				EnumChatFormatting.BLUE,
			};
			info += ", Moisture: ";
			info += colors[moisture.ordinal()] + Binnie.Language.localise(moisture) + EnumChatFormatting.RESET;

			colors = new EnumChatFormatting[] {
				EnumChatFormatting.RED,
				EnumChatFormatting.GREEN,
				EnumChatFormatting.AQUA,
			};
			info += ", pH: ";
			info += colors[pH.ordinal()] + Binnie.Language.localise(pH) + EnumChatFormatting.RESET;
			final IChatComponent chat = new ChatComponentText(info);
			player.addChatMessage(chat);
		}
		return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = Botany.proxy.getIcon(register, "soilMeter");
	}

	public ItemSoilMeter() {
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName("soilMeter");
		this.setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Soil Meter";
	}
}
