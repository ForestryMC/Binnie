package binnie.extratrees.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.extratrees.api.IToolHammer;

public class ItemSetSquare extends Item implements IToolHammer, IItemModelRegister {
	private EnumSetSquareMode mode;

	public ItemSetSquare(final EnumSetSquareMode mode) {
		this.mode = mode;
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setUnlocalizedName("setSquare" + mode);
		setRegistryName("setSquare" + mode);
		this.setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "setSquare" + this.mode.ordinal());
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
		Swap
	}
}
