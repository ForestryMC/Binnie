package binnie.genetics.item;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.items.IColoredItem;

import binnie.core.item.ItemCore;
import binnie.core.util.I18N;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.genetics.IGeneItem;

public abstract class ItemGene extends ItemCore implements IColoredItem {

	public ItemGene(String unlocName) {
		super(unlocName);
		this.setMaxStackSize(1);
		this.setMaxDamage(16);
		this.setUnlocalizedName(unlocName);
		this.setCreativeTab(CreativeTabGenetics.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		int damage = itemstack.getMaxDamage() - itemstack.getItemDamage();
		if (damage == 0) {
			list.add(I18N.localise("genetics.item.gene.empty"));
		} else if (damage == 1) {
			list.add("1 " + I18N.localise("genetics.item.gene.charge"));
		} else {
			list.add(damage + " " + I18N.localise("genetics.item.gene.charges"));
		}
		IGeneItem gene = this.getGeneItem(itemstack);
		if (gene != null) {
			gene.getInfo(list);
		}
	}

	public int getCharges(final ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	public abstract String getItemStackDisplayName(final ItemStack itemStack);

	@Override
	public abstract void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items);

	@Nullable
	public abstract IGeneItem getGeneItem(ItemStack itemStack);

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IGeneItem gene = this.getGeneItem(stack);
		if (gene != null) {
			return gene.getColor(tintIndex);
		}
		return 16777215;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}
}
