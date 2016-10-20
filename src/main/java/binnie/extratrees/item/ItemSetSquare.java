package binnie.extratrees.item;

import binnie.extratrees.api.IToolHammer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSetSquare extends Item implements IToolHammer {
    EnumSetSquareMode mode;

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraTrees.proxy.getIcon(register, "setSquare" + this.mode.ordinal());
//	}

    public ItemSetSquare(final EnumSetSquareMode mode) {
        this.mode = EnumSetSquareMode.Rotate;
        this.mode = mode;
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setUnlocalizedName("setSquare" + mode);
        setRegistryName("setSquare" + mode);
        this.setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return "Set Square";
    }

    @Override
    public boolean isActive(final ItemStack item) {
        return this.mode == EnumSetSquareMode.Rotate;
    }

    @Override
    public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
    }

    public enum EnumSetSquareMode {
        Rotate,
        Edit,
        Swap;
    }
}
