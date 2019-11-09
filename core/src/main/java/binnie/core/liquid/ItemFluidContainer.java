package binnie.core.liquid;

import javax.annotation.Nullable;

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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.core.Binnie;

public class ItemFluidContainer extends ItemFood implements IItemModelRegister {
	private final FluidContainerType container;

	public ItemFluidContainer(FluidContainerType container) {
		super(0, false);
		this.container = container;
		container.setItem(this);
		this.maxStackSize = container.getMaxStackSize();
		this.setHasSubtypes(true);
		this.setTranslationKey("container" + container.getName());
		this.setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName(container.getName());
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		FluidStack fluid = getContained(itemstack);
		if (fluid == null) {
			return "Empty " + container.getDisplayName();
		}
		return fluid.getFluid().getLocalizedName(fluid) + ' ' + this.container.getDisplayName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (this.isInCreativeTab(tab)) {
			for (FluidType type : Binnie.LIQUID.getFluids().values()) {
				if (!type.canPlaceIn(this.container)) {
					continue;
				}
				if (!type.showInCreative(this.container)) {
					continue;
				}
				items.add(getContainer(type));
			}
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

	public ItemStack getContainer(FluidType type) {
		ItemStack itemStack = new ItemStack(this);
		IFluidHandler fluidHandler = new FluidHandlerItemBinnie(itemStack, container);
		FluidStack fluidStack = type.get();
		if (fluidStack == null) {
			return container.getEmpty();
		}
		if (fluidHandler.fill(fluidStack, true) == Fluid.BUCKET_VOLUME) {
			return itemStack;
		}
		return container.getEmpty();
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			player.getFoodStats().addStats(this, stack);
			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			onFoodEaten(stack, world, player);
			player.inventory.addItemStackToInventory(this.container.getEmpty());
			stack.shrink(1);
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
		if (fluid == null) {
			return false;
		}
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

		private final ModelResourceLocation location;
		private final ModelResourceLocation empty;

		public FluidContainerMeshDefinition() {
			ResourceLocation location = getRegistryName();
			this.location = new ModelResourceLocation(location, "inventory");
			this.empty = new ModelResourceLocation(new ResourceLocation(location.getNamespace(), location.getPath() + "_empty"), "inventory");
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
