package binnie.extratrees.items;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;

import binnie.core.liquid.AlcoholEffect;
import binnie.core.liquid.DrinkManager;
import binnie.core.liquid.IDrinkLiquid;
import binnie.extratrees.alcohol.GlasswareType;
import binnie.extratrees.alcohol.drink.FluidHandlerItemGlassware;
import binnie.extratrees.liquid.Alcohol;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple.FLUID_NBT_KEY;

public class ItemDrink extends ItemFood implements IItemModelRegister {
	public static final String TYPE_NBT_KEY = "glassware";

	public ItemDrink() {
		super(0, 0.0f, false);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setTranslationKey("drink");
		this.setRegistryName("drink");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
	}

	public GlasswareType getGlassware(ItemStack container) {
		NBTTagCompound nbt = container.getTagCompound();
		if (nbt == null || !nbt.hasKey(TYPE_NBT_KEY)) {
			return GlasswareType.BEER_MUG;
		}
		return GlasswareType.values()[nbt.getShort(TYPE_NBT_KEY)];
	}

	public ItemStack getStack(GlasswareType glass, @Nullable FluidStack fluid, int amount) {
		ItemStack stack = new ItemStack(this, amount);
		this.saveGlassware(glass, stack);
		this.saveFluid(fluid, stack);
		return stack;
	}

	public ItemStack getStack(GlasswareType glass, @Nullable FluidStack fluid) {
		return getStack(glass, fluid, 1);
	}

	public void saveGlassware(GlasswareType container, ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			stack.setTagCompound(nbt = new NBTTagCompound());
		}
		nbt.setShort(TYPE_NBT_KEY, (short) container.ordinal());
	}

	public void saveFluid(@Nullable FluidStack fluid, ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			stack.setTagCompound(nbt = new NBTTagCompound());
		}

		if (fluid == null) {
			nbt.removeTag(FLUID_NBT_KEY);
		} else {
			IFluidHandler handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			handler.fill(fluid, true);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (GlasswareType glasswareType : GlasswareType.values()) {
			ModelLoader.registerItemVariants(item, glasswareType.getModelLocation());
		}
		manager.registerItemModel(item, (ItemStack stack) -> {
			GlasswareType glasswareType = getGlassware(stack);
			return glasswareType.getModelLocation();
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidHandlerItemGlassware(stack, getGlassware(stack));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (GlasswareType glasswareType : GlasswareType.values()) {
				items.add(this.getStack(glasswareType, null));
			}
			items.add(this.getStack(GlasswareType.WINE, Alcohol.RedWine.stack(GlasswareType.WINE.getCapacity())));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluid = FluidUtil.getFluidContained(stack);
		IDrinkLiquid liquid = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		String liquidName = (liquid == null) ? null : liquid.getName();
		return this.getGlassware(stack).getName(liquidName);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		IFluidHandler handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (handler != null && entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			entityplayer.getFoodStats().addStats(this, stack);
			worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, worldIn, entityplayer);
			entityplayer.addStat(StatList.getObjectUseStats(this));
			handler.drain(Integer.MAX_VALUE, true);
			if (entityplayer instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
			}
			return stack;
		} else {
			return super.onItemUseFinish(stack, worldIn, entityLiving);
		}
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		FluidStack fluid = FluidUtil.getFluidContained(stack);
		IDrinkLiquid liquid = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		float strength = (liquid == null) ? 0.0f : liquid.getABV();
		if (strength > 0.0f) {
			AlcoholEffect.makeDrunk(player, strength);
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemStack) {
		if (FluidUtil.getFluidContained(itemStack) != null) {
			return EnumAction.DRINK;
		} else {
			return EnumAction.NONE;
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return super.onItemRightClick(world, player, hand);
	}
}
