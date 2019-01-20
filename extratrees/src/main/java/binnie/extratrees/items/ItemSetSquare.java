package binnie.extratrees.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.design.api.IToolHammer;

public class ItemSetSquare extends Item implements IToolHammer, IItemModelRegister {
	private final EnumSetSquareMode mode;

	public ItemSetSquare(EnumSetSquareMode mode) {
		this.mode = mode;
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setTranslationKey("setSquare" + mode);
		setRegistryName("setSquare" + mode);
		this.setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "setSquare" + this.mode.ordinal());
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Set Square";
	}

	@Override
	public boolean isActive(ItemStack item) {
		return this.mode == EnumSetSquareMode.Rotate;
	}

	@Override
	public void onHammerUsed(ItemStack item, EntityPlayer player) {
	}

	public enum EnumSetSquareMode {
		Rotate,
		Edit,
		Swap
	}
}
