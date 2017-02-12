package binnie.extratrees.item;

import binnie.extratrees.api.IToolHammer;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHammer extends Item implements IToolHammer, IItemModelRegister {
	private boolean isDurableHammer;

	public ItemHammer(final boolean durable) {
		this.isDurableHammer = false;
		this.isDurableHammer = durable;
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setUnlocalizedName(durable ? "durable_hammer" : "hammer");
		this.setMaxStackSize(1);
		this.setMaxDamage(durable ? 1562 : 251);
		setRegistryName(durable ? "durable_hammer" : "hammer");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, isDurableHammer ? "durable_hammer" : "carpentry_hammer");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return this.isDurableHammer ? "Master Carpentry Hammer" : "Carpentry Hammer";
	}

	@Override
	public boolean isActive(final ItemStack item) {
		return true;
	}

	@Override
	public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
		item.damageItem(1, player);
	}
}
