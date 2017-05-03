package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.Glassware;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import java.util.List;

public class ItemDrink extends ItemFood implements IFluidContainerItem {
	public ItemDrink() {
		super(0, 0.0f, false);
		setCreativeTab(Tabs.tabArboriculture);
		setUnlocalizedName("drink");
		setHasSubtypes(true);
		setMaxStackSize(1);
		setAlwaysEdible();
	}

	public Glassware getGlassware(ItemStack container) {
		if (!container.hasTagCompound() || !container.getTagCompound().hasKey("glassware")) {
			return Glassware.BeerMug;
		}
		return Glassware.values()[container.getTagCompound().getShort("glassware")];
	}

	public ItemStack getStack(Glassware glass, FluidStack fluid) {
		ItemStack stack = new ItemStack(this);
		saveGlassware(glass, stack);
		saveFluid(fluid, stack);
		return stack;
	}

	public void saveGlassware(Glassware container, ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setShort("glassware", (short) container.ordinal());
	}

	public void saveFluid(FluidStack fluid, ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound nbt = stack.getTagCompound();
		if (fluid == null) {
			nbt.removeTag("fluid");
		} else {
			NBTTagCompound liq = new NBTTagCompound();
			fluid.writeToNBT(liq);
			nbt.setTag("fluid", liq);
		}
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("fluid")) {
			return null;
		}
		return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("fluid"));
	}

	@Override
	public int getCapacity(ItemStack container) {
		return getGlassware(container).getCapacity();
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		if (resource == null || !container.hasTagCompound() || DrinkManager.getLiquid(resource.getFluid()) == null) {
			return 0;
		}

		FluidStack existing = getFluid(container);
		int space = getGlassware(container).getCapacity() - ((existing == null) ? 0 : existing.amount);
		int added = Math.min(space, resource.amount);
		if (space <= 0) {
			return 0;
		}

		if (existing == null) {
			if (doFill) {
				FluidStack fill = resource.copy();
				fill.amount = added;
				saveFluid(fill, container);
			}
			return added;
		}

		if (!existing.isFluidEqual(resource)) {
			return 0;
		}

		if (doFill) {
			FluidStack fill = existing.copy();
			fill.amount += added;
			saveFluid(fill, container);
		}
		return added;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		if (!container.hasTagCompound()) {
			return null;
		}

		FluidStack content = getFluid(container);
		if (content == null) {
			return null;
		}

		int toRemove = Math.min(maxDrain, content.amount);
		FluidStack fill = content.copy();
		FluidStack drain = content.copy();
		drain.amount = toRemove;
		fill.amount -= toRemove;
		if (fill.amount == 0) {
			fill = null;
		}

		if (doDrain) {
			saveFluid(fill, container);
		}
		return drain;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (Glassware glassware : Glassware.values()) {
			list.add(getStack(glassware, null));
		}
		list.add(getStack(Glassware.Wine, Alcohol.RedWine.get(Glassware.Wine.getCapacity())));
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluid = getFluid(stack);
		IDrinkLiquid liquid = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		return getGlassware(stack).getName((liquid == null) ? null : liquid.getName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		for (Glassware glassware : Glassware.values()) {
			glassware.registerIcons(register);
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		Glassware glass = getGlassware(stack);
		return (pass == 0) ? glass.glass : ((getFluid(stack) == null) ? glass.glass : glass.contents);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		FluidStack fluid = getFluid(stack);
		IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
		if (pass == 0 || drink == null) {
			return 0xffffff;
		}
		return drink.getColour();
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		drain(stack, 30, true);
		AlcoholEffect.makeDrunk(player, 2.1f);
		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return (getFluid(stack) == null) ? EnumAction.none : EnumAction.drink;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 16;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}
}
