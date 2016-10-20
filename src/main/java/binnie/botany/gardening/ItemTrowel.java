package binnie.botany.gardening;

import binnie.botany.CreativeTabBotany;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrowel extends Item {
    protected Item.ToolMaterial theToolMaterial;
    String locName;

    public ItemTrowel(final Item.ToolMaterial p_i45343_1_, final String material) {
        this.theToolMaterial = p_i45343_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45343_1_.getMaxUses());
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("trowel" + material);
        this.locName = "trowel" + material;
        setRegistryName("trowel" + material);
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister p_94581_1_) {
//		this.itemIcon = Botany.proxy.getIcon(p_94581_1_, "tools/" + this.locName);
//	}
//
//	@Override
//	public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
//		if (!player.canPlayerEdit(x, y, z, p_77648_7_, stack)) {
//			return false;
//		}
//		final Block block = world.getBlock(x, y, z);
//		if (p_77648_7_ == 0 || (!world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && world.getBlock(x, y + 1, z) != Botany.flower) || (block != Blocks.grass && block != Blocks.dirt)) {
//			return false;
//		}
//		final Block block2 = Botany.soil;
//		world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, block2.stepSound.getStepResourcePath(), (block2.stepSound.getVolume() + 1.0f) / 2.0f, block2.stepSound.getPitch() * 0.8f);
//		if (world.isRemote) {
//			return true;
//		}
//		final EnumMoisture moisture = Gardening.getNaturalMoisture(world, x, y, z);
//		final EnumAcidity acidity = Gardening.getNaturalPH(world, x, y, z);
//		Gardening.createSoil(world, x, y, z, EnumSoilType.SOIL, moisture, acidity);
//		stack.damageItem(1, player);
//		return true;
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public String getToolMaterialName() {
        return this.theToolMaterial.toString();
    }
}
