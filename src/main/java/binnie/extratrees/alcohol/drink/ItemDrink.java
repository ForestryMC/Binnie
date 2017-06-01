package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.alcohol.Glassware;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDrink extends ItemFood {
	public ItemDrink() {
		super(0, 0.0f, false);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setUnlocalizedName("drink");
		setRegistryName("drink");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
	}

	public Glassware getGlassware(final ItemStack container) {
		NBTTagCompound nbt = container.getTagCompound();
		if (nbt == null || !nbt.hasKey("glassware")) {
			return Glassware.BeerMug;
		}
		return Glassware.values()[nbt.getShort("glassware")];
	}

	public ItemStack getStack(final Glassware glass, @Nullable final FluidStack fluid) {
		final ItemStack stack = new ItemStack(this);
		this.saveGlassware(glass, stack);
		this.saveFluid(fluid, stack);
		return stack;
	}

	public void saveGlassware(final Glassware container, final ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		nbt.setShort("glassware", (short) container.ordinal());
	}

	public void saveFluid(@Nullable final FluidStack fluid, final ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}

		if (fluid == null) {
			nbt.removeTag("fluid");
		} else {
			final NBTTagCompound liq = new NBTTagCompound();
			fluid.writeToNBT(liq);
			nbt.setTag("fluid", liq);
		}
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidHandlerItemGlassware(stack, getGlassware(stack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item item, final CreativeTabs creativeTabs, final NonNullList<ItemStack> itemList) {
		for (final Glassware glassware : Glassware.values()) {
			itemList.add(this.getStack(glassware, null));
		}
		itemList.add(this.getStack(Glassware.Wine, Alcohol.RedWine.get(Glassware.Wine.getCapacity())));
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		final FluidStack fluid = FluidUtil.getFluidContained(stack);
		final IDrinkLiquid liquid;
		if (fluid == null) {
			liquid = null;
		} else {
			liquid = DrinkManager.getLiquid(fluid.getFluid());
		}

		final String liquidName;
		if (liquid == null) {
			liquidName = null;
		} else {
			liquidName = liquid.getName();
		}
		return this.getGlassware(stack).getName(liquidName);
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister par1IconRegister) {
		for (final Glassware glassware : Glassware.values()) {
			glassware.registerIcons(par1IconRegister);
		}
	}

	@Override
	public IIcon getIcon(final ItemStack stack, final int pass) {
		final Glassware glass = this.getGlassware(stack);
		return (pass == 0) ? glass.glass : ((this.getFluid(stack) == null) ? glass.glass : glass.contents);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(final ItemStack stack, final int pass) {
		final FluidStack fluid = this.getFluid(stack);
		final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		return (pass == 0) ? 16777215 : ((drink == null) ? 16777215 : drink.getColour());
	}

	@Override
	public int getRenderPasses(final int metadata) {
		return 2;
	}

	@Override
	public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
		this.drain(p_77654_1_, 30, true);
		AlcoholEffect.makeDrunk(p_77654_3_, 2.1f);
		return p_77654_1_;
	}*/

	@Override
	public EnumAction getItemUseAction(final ItemStack itemStack) {
		if (FluidUtil.getFluidContained(itemStack) != null) {
			return EnumAction.DRINK;
		} else {
			return EnumAction.NONE;
		}
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}*/

	@Override
	public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
		return 16;
	}

	/*@Override
	public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}*/
}
