package binnie.core.liquid;

import binnie.Binnie;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemFluidContainer extends ItemFood implements IItemModelRegister {
	private final FluidContainerType container;

	public ItemFluidContainer(FluidContainerType container) {
		super(0, false);
		this.container = container;
		container.setItem(this);
		this.maxStackSize = container.getMaxStackSize();
		this.setHasSubtypes(true);
		this.setUnlocalizedName("container" + container.getName());
		this.setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName(container.getName());
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		FluidStack fluid = getContained(itemstack);
		if (fluid == null) {
			return "Empty " + container.getDisplayName();
		}
		return fluid.getFluid().getLocalizedName(fluid) + " " + this.container.getDisplayName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item item, final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
		super.getSubItems(item, tab, subItems);
		for (final IFluidType liquid : Binnie.LIQUID.fluids.values()) {
			if (!liquid.canPlaceIn(this.container)) {
				continue;
			}
			if (!liquid.showInCreative(this.container)) {
				continue;
			}
			subItems.add(getContainer(liquid));
		}
	}

	@Nullable
	protected FluidStack getContained(ItemStack itemStack) {
		if (itemStack.getCount() != 1) {
			itemStack = itemStack.copy();
			itemStack.setCount(1);
		}
		IFluidHandler fluidHandler = new FluidHandlerItemBinnie(itemStack, container);
		return fluidHandler.drain(Integer.MAX_VALUE, false);
	}

	public ItemStack getContainer(final IFluidType liquid) {
		ItemStack itemStack = new ItemStack(this);
		IFluidHandler fluidHandler = new FluidHandlerItemBinnie(itemStack, container);
		if (fluidHandler.fill(new FluidStack(FluidRegistry.getFluid(liquid.getIdentifier()), Fluid.BUCKET_VOLUME), true) == Fluid.BUCKET_VOLUME) {
			return itemStack;
		}
		return container.getEmpty();
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			player.getFoodStats().addStats(this, stack);
			world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			onFoodEaten(stack, world, player);
			return this.container.getEmpty();
		}
		return stack;
	}

	@Override
	protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer player) {
		if (!world.isRemote) {
			FluidStack fluid = getContained(itemStack);
			if (fluid != null) {
				final IDrinkLiquid liquid = DrinkManager.getLiquid(fluid);
				if (liquid != null) {
					AlcoholEffect.makeDrunk(player, liquid.getABV() * fluid.amount);
				}
			}
		}
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		if (isDrinkable(stack)) {
			return EnumAction.DRINK;
		}
		return EnumAction.NONE;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (this.isDrinkable(stack) && playerIn.canEat(false)) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		} else {
			return super.onItemRightClick(worldIn, playerIn, handIn);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if (isDrinkable(stack)) {
			return 32;
		} else {
			return 0;
		}
	}

	@Override
	public float getSaturationModifier(ItemStack stack) {
		return 0.0F;
	}

	@Override
	public int getHealAmount(ItemStack stack) {
		return 0;
	}

	private boolean isDrinkable(final ItemStack stack) {
		final FluidStack fluid = getContained(stack);
		final IDrinkLiquid liquid = DrinkManager.getLiquid(fluid);
		return liquid != null && liquid.isConsumable();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		FluidContainerMeshDefinition meshDefinition = new FluidContainerMeshDefinition();
		manager.registerItemModel(item, meshDefinition);
		ModelBakery.registerItemVariants(item, meshDefinition.location, meshDefinition.empty);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidHandlerItemBinnie(stack, container);
	}

	@SideOnly(Side.CLIENT)
	private class FluidContainerMeshDefinition implements ItemMeshDefinition {

		final ModelResourceLocation location;
		final ModelResourceLocation empty;

		public FluidContainerMeshDefinition() {
			ResourceLocation location = getRegistryName();
			this.location = new ModelResourceLocation(location, "inventory");
			this.empty = new ModelResourceLocation(new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + "_empty"), "inventory");
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			if (stack.getItemDamage() == 0) {
				return empty;
			}
			return location;
		}

	}

}
