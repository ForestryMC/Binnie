package binnie.genetics.item;

import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public abstract class ItemGene extends Item {
	protected IIcon[] icons;

	public ItemGene(String unlocName) {
		icons = new IIcon[4];
		setMaxStackSize(1);
		setMaxDamage(16);
		setUnlocalizedName(unlocName);
		setCreativeTab(CreativeTabGenetics.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
		return icons[pass];
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons[0] = Genetics.proxy.getIcon(register, "machines/serum.glass");
		icons[1] = Genetics.proxy.getIcon(register, "machines/serum.cap");
		icons[2] = Genetics.proxy.getIcon(register, "machines/serum.edges");
		icons[3] = Genetics.proxy.getIcon(register, "machines/serum.dna");
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 4;
	}

	@Override
	public int getColorFromItemStack(ItemStack itemstack, int j) {
		IGeneItem gene = getGeneItem(itemstack);
		return gene.getColour(j);
	}

	public int getCharges(ItemStack stack) {
		if (stack == null) {
			return 0;
		}
		return stack.getItem().getMaxDamage() - stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List tooltip, boolean advanced) {
		super.addInformation(itemstack, player, tooltip, advanced);
		int damage = getMaxDamage() - itemstack.getItemDamage();
		if (damage == 0) {
			tooltip.add("Empty");
		} else if (damage == 1) {
			tooltip.add("1 Charge");
		} else {
			tooltip.add(damage + " Charges");
		}

		IGeneItem gene = getGeneItem(itemstack);
		gene.getInfo(tooltip);
	}

	@Override
	public abstract String getItemStackDisplayName(ItemStack stack);

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		// ignored
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public abstract IGeneItem getGeneItem(ItemStack stack);
}
