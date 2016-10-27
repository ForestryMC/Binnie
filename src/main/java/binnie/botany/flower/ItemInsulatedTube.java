package binnie.botany.flower;

import binnie.botany.CreativeTabBotany;
import binnie.core.item.ItemCore;
import forestry.api.core.IModelManager;
import forestry.core.items.IColoredItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemInsulatedTube extends ItemCore implements IColoredItem {

	public ItemInsulatedTube() {
		super("insulatedTube");
		setUnlocalizedName("insulatedTube");
		setCreativeTab(CreativeTabBotany.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (Material mat : Material.values()) {
			for (Insulate ins : Insulate.values()) {
				subItems.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (Material mat : Material.values()) {
			for (Insulate ins : Insulate.values()) {
				manager.registerItemModel(item, mat.ordinal() + ins.ordinal() * 128);
			}
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if (tintIndex == 0) {
			return 16777215;
		} else if (tintIndex == 1) {
			Material.get(stack.getItemDamage()).getColor();
		}
		return Insulate.get(stack.getItemDamage()).getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
		tooltip.add(Insulate.get(itemStack.getItemDamage()).getName());
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		return Material.get(p_77653_1_.getItemDamage()).getName() + " Insulated Tube";
	}

	public static String getInsulate(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getName();
	}

	public static ItemStack getInsulateStack(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getStack();
	}

	enum Material {
		Copper(14923662, "Copper"),
		Tin(14806772, "Tin"),
		Bronze(14533238, "Bronze"),
		Iron(14211288, "Iron");

		int color;
		String name;

		public int getColor() {
			return this.color;
		}

		public String getName() {
			return this.name;
		}

		Material(final int color, final String name) {
			this.color = color;
			this.name = name;
		}

		public static Material get(final int i) {
			return values()[i % values().length];
		}
	}

	enum Insulate {
		Clay(10595020, "Clay"),
		Cobble(8092539, "Cobblestone"),
		Sand(15723189, "Sand"),
		HardenedClay(9657411, "Hardened Clay"),
		Stone(7171437, "Smooth Stone"),
		Sandstone(12695945, "Sandstone");

		int color;
		String name;

		public int getColor() {
			return this.color;
		}

		public String getName() {
			return this.name;
		}

		Insulate(final int color, final String name) {
			this.color = color;
			this.name = name;
		}

		public static Insulate get(final int i) {
			return values()[i / 128 % values().length];
		}

		public ItemStack getStack() {
			switch (this) {
				case Clay: {
					return new ItemStack(Blocks.CLAY);
				}
				case Cobble: {
					return new ItemStack(Blocks.COBBLESTONE);
				}
				case HardenedClay: {
					return new ItemStack(Blocks.HARDENED_CLAY);
				}
				case Sand: {
					return new ItemStack(Blocks.SAND);
				}
				case Sandstone: {
					return new ItemStack(Blocks.SANDSTONE);
				}
				case Stone: {
					return new ItemStack(Blocks.STONE);
				}
				default: {
					return null;
				}
			}
		}
	}
}
