package binnie.botany.gardening;

import binnie.botany.CreativeTabBotany;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSoilMeter extends Item {
//	@Override
//	public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, int y, final int z, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
//		Block block = world.getBlock(x, y, z);
//		if (!Gardening.isSoil(block)) {
//			--y;
//			block = world.getBlock(x, y, z);
//		}
//		if (Gardening.isSoil(block) && !BinnieCore.proxy.isSimulating(world)) {
//			final IBlockSoil soil = (IBlockSoil) block;
//			final EnumSoilType type = soil.getType(world, x, y, z);
//			final EnumMoisture moisture = soil.getMoisture(world, x, y, z);
//			final EnumAcidity pH = soil.getPH(world, x, y, z);
//			String info = "Type: ";
//			info = info + "§" + (new char[] { '8', '6', 'd' })[type.ordinal()] + Binnie.Language.localise(type) + "§f";
//			info += ", Moisture: ";
//			info = info + "§" + (new char[] { 'e', '7', '9' })[moisture.ordinal()] + Binnie.Language.localise(moisture) + "§f";
//			info += ", pH: ";
//			info = info + "§" + (new char[] { 'c', 'a', 'b' })[pH.ordinal()] + Binnie.Language.localise(pH) + "§f";
//			final IChatComponent chat = new ChatComponentText(info);
//			player.addChatMessage(chat);
//		}
//		return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Botany.proxy.getIcon(register, "soilMeter");
//	}

    public ItemSoilMeter() {
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("soilMeter");
        this.setMaxStackSize(1);
        setRegistryName("soilMeter");
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return "Soil Meter";
    }
}
