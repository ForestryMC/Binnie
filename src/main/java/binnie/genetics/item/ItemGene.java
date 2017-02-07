package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.genetics.IGeneItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemGene extends ItemCore {

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	public ItemGene(final String unlocName) {
		super(unlocName);
		this.setMaxStackSize(1);
		this.setMaxDamage(16);
		this.setUnlocalizedName(unlocName);
		this.setCreativeTab(CreativeTabGenetics.instance);
	}

	public int getCharges(final ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List<String> list, final boolean advanced) {
		super.addInformation(itemstack, entityPlayer, list, advanced);

		final int damage = itemstack.getMaxDamage() - itemstack.getItemDamage();
		if (damage == 0) {
			list.add(Binnie.LANGUAGE.localise("genetic.item.gene.empty"));
		} else if (damage == 1) {
			list.add("1 " + Binnie.LANGUAGE.localise("genetic.item.gene.charge"));
		} else {
			list.add(damage + " " + Binnie.LANGUAGE.localise("genetic.item.gene.charges"));
		}
		final IGeneItem gene = this.getGeneItem(itemstack);
		if (gene != null) {
			gene.getInfo(list);
		}
	}

	@Override
	public abstract String getItemStackDisplayName(final ItemStack p0);

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
	}

	@Nullable
	public abstract IGeneItem getGeneItem(final ItemStack itemStack);
}
