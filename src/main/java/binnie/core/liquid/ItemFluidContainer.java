package binnie.core.liquid;

import binnie.Binnie;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.items.IColoredItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFluidContainer extends ItemFood implements IItemModelRegister, IColoredItem {
	private FluidContainer container;
	public static int LiquidExtraBee = 64;
	public static int LiquidExtraTree = 128;
	public static int LiquidJuice = 256;
	public static int LiquidAlcohol = 384;
	public static int LiquidSpirit = 512;
	public static int LiquidLiqueuer = 640;
	public static int LiquidGenetics = 768;
	private static Map<Integer, String> idToFluid = new HashMap<>();
	private static Map<String, Integer> fluidToID = new HashMap<>();

	public static void registerFluid(final IFluidType fluid, final int id) {
		ItemFluidContainer.idToFluid.put(id, fluid.getIdentifier().toLowerCase());
		ItemFluidContainer.fluidToID.put(fluid.getIdentifier().toLowerCase(), id);
	}

	public ItemFluidContainer(final FluidContainer container) {
		super(0, false);
		this.container = container;
		container.item = this;
		this.maxStackSize = container.getMaxStackSize();
		this.setHasSubtypes(true);
		this.setUnlocalizedName("container" + container.name());
		this.setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName(container.name());
	}

	private FluidStack getLiquid(final ItemStack stack) {
		final String liquid = ItemFluidContainer.idToFluid.get(stack.getItemDamage());
		return (liquid == null) ? null : Binnie.LIQUID.getFluidStack(liquid, 1000);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.container.updateIcons(register);
//	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		if (itemstack == null) {
			return "???";
		}
		final FluidStack fluid = this.getLiquid(itemstack);
		if (fluid == null) {
			return "Missing Fluid";
		}
		return fluid.getFluid().getLocalizedName(fluid) + " " + this.container.getName();
	}

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (final IFluidType liquid : Binnie.LIQUID.fluids.values()) {
			if (!liquid.canPlaceIn(this.container)) {
				continue;
			}
			if (!liquid.showInCreative(this.container)) {
				continue;
			}
			itemList.add(this.getContainer(liquid));
		}
	}

	public ItemStack getContainer(final IFluidType liquid) {
		final int id = ItemFluidContainer.fluidToID.get(liquid.getIdentifier().toLowerCase());
		final ItemStack itemstack = new ItemStack(this, 1, id);
		return itemstack;
	}

//	@Override
//	public IIcon getIcon(final ItemStack itemstack, final int j) {
//		if (j > 0) {
//			return this.container.getBottleIcon();
//		}
//		return this.container.getContentsIcon();
//	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		final FluidStack fluid = this.getLiquid(stack);
		if (fluid == null) {
			return 16777215;
		}
		if (tintIndex == 0 && fluid.getFluid() instanceof BinnieFluid) {
			return ((BinnieFluid) fluid.getFluid()).fluidType.getContainerColour();
		}
		return -1;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if(entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entityLiving;
			player.getFoodStats().addStats(this, stack);
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			onFoodEaten(stack, world, player);
			return this.container.getEmpty();
		}
		return stack;
	}

	@Override
	protected void onFoodEaten(final ItemStack stack, final World world, final EntityPlayer player) {
		if (!world.isRemote) {
			final FluidStack fluid = this.getLiquid(stack);
			final IDrinkLiquid liquid = DrinkManager.getLiquid(fluid);
			if (liquid != null) {
				AlcoholEffect.makeDrunk(player, liquid.getABV() * fluid.amount);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return this.isDrinkable(stack) ? EnumAction.DRINK : EnumAction.NONE;
	}
	
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

//	@Override
//	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
//		if (this.isDrinkable(stack)) {
//			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
//		}
//		return stack;
//	}

//	@Override
//	public int func_150905_g(final ItemStack p_150905_1_) {
//		return 0;
//	}
//
//	@Override
//	public float func_150906_h(final ItemStack p_150906_1_) {
//		return 0.0f;
//	}

	private boolean isDrinkable(final ItemStack stack) {
		final FluidStack fluid = this.getLiquid(stack);
		final IDrinkLiquid liquid = DrinkManager.getLiquid(fluid);
		return liquid != null && liquid.isConsumable();
	}

}
