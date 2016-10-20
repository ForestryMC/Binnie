package binnie.core.liquid;

import binnie.Binnie;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFluidContainer extends ItemFood {
    private FluidContainer container;
    public static int LiquidExtraBee;
    public static int LiquidExtraTree;
    public static int LiquidJuice;
    public static int LiquidAlcohol;
    public static int LiquidSpirit;
    public static int LiquidLiqueuer;
    public static int LiquidGenetics;
    private static Map<Integer, String> idToFluid;
    private static Map<String, Integer> fluidToID;

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
        return (liquid == null) ? null : Binnie.Liquid.getLiquidStack(liquid, 1000);
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
        for (final IFluidType liquid : Binnie.Liquid.fluids.values()) {
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
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getColorFromItemStack(final ItemStack item, final int pass) {
//		final FluidStack fluid = this.getLiquid(item);
//		if (fluid == null) {
//			return 16777215;
//		}
//		if (pass == 0 && fluid.getFluid() instanceof BinnieFluid) {
//			return ((BinnieFluid) fluid.getFluid()).fluidType.getContainerColour();
//		}
//		return super.getColorFromItemStack(item, pass);
//	}
//
//	@Override
//	public boolean requiresMultipleRenderPasses() {
//		return true;
//	}
//
//	@Override
//	public ItemStack onEaten(final ItemStack stack, final World world, final EntityPlayer player) {
//		player.getFoodStats().func_151686_a(this, stack);
//		world.playSoundAtEntity(player, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
//		this.onFoodEaten(stack, world, player);
//		return this.container.getEmpty();
//	}

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

    static {
        ItemFluidContainer.LiquidExtraBee = 64;
        ItemFluidContainer.LiquidExtraTree = 128;
        ItemFluidContainer.LiquidJuice = 256;
        ItemFluidContainer.LiquidAlcohol = 384;
        ItemFluidContainer.LiquidSpirit = 512;
        ItemFluidContainer.LiquidLiqueuer = 640;
        ItemFluidContainer.LiquidGenetics = 768;
        ItemFluidContainer.idToFluid = new HashMap<Integer, String>();
        ItemFluidContainer.fluidToID = new HashMap<String, Integer>();
    }
}
