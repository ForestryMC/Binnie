package binnie.extratrees.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.core.util.I18N;
import binnie.extratrees.api.IToolHammer;

public class ItemHammer extends Item implements IToolHammer, IItemModelRegister {
	private boolean isDurableHammer;

	public ItemHammer(boolean durable) {
		this.isDurableHammer = durable;
		setCreativeTab(CreativeTabs.TOOLS);
		setMaxStackSize(1);
		setMaxDamage(durable ? 1562 : 251);
		String name = durable ? "durable_hammer" : "hammer";
		setUnlocalizedName(name);
		setRegistryName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, isDurableHammer ? "durable_hammer" : "carpentry_hammer");
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return I18N.localise("extratrees.item.hammer." + (isDurableHammer ? "master.name" : "name"));
	}

	@Override
	public boolean isActive(ItemStack itemStack) {
		return true;
	}

	@Override
	public void onHammerUsed(ItemStack itemStack, EntityPlayer player) {
		itemStack.damageItem(1, player);
	}
}
