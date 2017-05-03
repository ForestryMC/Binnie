package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// TODO unused class?
public class ItemSetSquare extends Item implements IToolHammer {
	EnumSetSquareMode mode;

	public ItemSetSquare(EnumSetSquareMode mode) {
		this.mode = EnumSetSquareMode.Rotate;
		this.mode = mode;
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("setSquare" + mode);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = ExtraTrees.proxy.getIcon(register, "setSquare" + mode.ordinal());
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Set Square";
	}

	@Override
	public boolean isActive(ItemStack stack) {
		return mode == EnumSetSquareMode.Rotate;
	}

	@Override
	public void onHammerUsed(ItemStack stack, EntityPlayer player) {
		// ignored
	}

	public enum EnumSetSquareMode {
		Rotate,
		Edit,
		Swap
	}
}
