package binnie.genetics.item;

import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.genetics.IGeneItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemGene extends ItemCore {

//	IIcon[] icons;
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamageForRenderPass(final int damage, final int pass) {
//		return this.icons[pass];
//	}


	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.icons[0] = Genetics.proxy.getIcon(register, "machines/serum.glass");
//		this.icons[1] = Genetics.proxy.getIcon(register, "machines/serum.cap");
//		this.icons[2] = Genetics.proxy.getIcon(register, "machines/serum.edges");
//		this.icons[3] = Genetics.proxy.getIcon(register, "machines/serum.dna");
//	}
//
//	@Override
//	public int getRenderPasses(final int metadata) {
//		return 4;
//	}

	public ItemGene(final String unlocName) {
		super(unlocName);
//		this.icons = new IIcon[4];
		this.setMaxStackSize(1);
		this.setMaxDamage(16);
		this.setUnlocalizedName(unlocName);
		this.setCreativeTab(CreativeTabGenetics.instance);
	}
//
//	@Override
//	public int getColorFromItemStack(final ItemStack itemstack, final int j) {
//		final IGeneItem gene = this.getGeneItem(itemstack);
//		return gene.getColour(j);
//	}

	public int getCharges(final ItemStack stack) {
		return (stack == null) ? 0 : (stack.getItem().getMaxDamage() - stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List list, final boolean advanced) {
		super.addInformation(itemstack, entityPlayer, list, advanced);
		final int damage = this.getMaxDamage() - itemstack.getItemDamage();
		if (damage == 0) {
			list.add("Empty");
		} else if (damage == 1) {
			list.add("1 Charge");
		} else {
			list.add(damage + " Charges");
		}
		final IGeneItem gene = this.getGeneItem(itemstack);
		gene.getInfo(list);
	}

	@Override
	public abstract String getItemStackDisplayName(final ItemStack p0);

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
	}

//	@Override
//	public boolean requiresMultipleRenderPasses() {
//		return true;
//	}

	public abstract IGeneItem getGeneItem(final ItemStack p0);
}
