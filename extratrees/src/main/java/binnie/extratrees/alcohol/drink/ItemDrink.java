package binnie.extratrees.alcohol.drink;

import binnie.core.liquid.DrinkManager;
import binnie.core.liquid.IDrinkLiquid;
import binnie.extratrees.alcohol.Alcohol;
import binnie.core.liquid.AlcoholEffect;
import binnie.extratrees.alcohol.Glassware;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemDrink extends ItemFood implements IItemModelRegister {
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

	public ItemStack getStack(final Glassware glass, @Nullable final FluidStack fluid, int amount) {
		final ItemStack stack = new ItemStack(this, amount);
		this.saveGlassware(glass, stack);
		this.saveFluid(fluid, stack);
		return stack;
	}

	public ItemStack getStack(final Glassware glass, @Nullable final FluidStack fluid) {
		return getStack(glass, fluid, 1);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (Glassware glassware : Glassware.values()) {
			ModelLoader.registerItemVariants(item, glassware.getLocation());
		}
		manager.registerItemModel(item, (ItemStack stack) -> {
			Glassware glassware = getGlassware(stack);
			return glassware.getLocation();
		});
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidHandlerItemGlassware(stack, getGlassware(stack));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (final Glassware glassware : Glassware.values()) {
				items.add(this.getStack(glassware, null));
			}
			items.add(this.getStack(Glassware.Wine, Alcohol.RedWine.get(Glassware.Wine.getCapacity())));
		}
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

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		AlcoholEffect.makeDrunk(player, 2.1f);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack itemStack) {
		if (FluidUtil.getFluidContained(itemStack) != null) {
			return EnumAction.DRINK;
		} else {
			return EnumAction.NONE;
		}
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
		return 16;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return super.onItemRightClick(world, player, hand);
	}
}
