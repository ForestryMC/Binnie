package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemInsulatedTube extends Item {
	IIcon[] icons;

	public ItemInsulatedTube() {
		icons = new IIcon[3];
		setUnlocalizedName("insulatedTube");
		setCreativeTab(CreativeTabBotany.instance);
	}

	public static String getInsulate(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getName();
	}

	public static ItemStack getInsulateStack(ItemStack stack) {
		return Insulate.get(stack.getItemDamage()).getStack();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		for (Material mat : Material.values()) {
			for (Insulate ins : Insulate.values()) {
				p_150895_3_.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		for (int i = 0; i < 3; ++i) {
			icons[i] = Botany.proxy.getIcon(p_94581_1_, "insulatedTube." + i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return (pass == 0) ? 16777215 : ((pass == 1) ? Material.get(stack.getItemDamage()).getColor() : Insulate.get(stack.getItemDamage()).getColor());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add(Insulate.get(stack.getItemDamage()).getName());
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Material.get(stack.getItemDamage()).getName() + " Insulated Tube";
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass % 3];
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	enum Material {
		Copper(0xe3b78e, "Copper"),
		Tin(0xe1eef4, "Tin"),
		Bronze(0xddc276, "Bronze"),
		Iron(0xd8d8d8, "Iron");

		protected int color;
		protected String name;

		Material(int color, String name) {
			this.color = color;
			this.name = name;
		}

		public static Material get(int i) {
			return values()[i % values().length];
		}

		public int getColor() {
			return color;
		}

		public String getName() {
			return name;
		}
	}

	enum Insulate {
		Clay(0xa1aacc, "Clay"),
		Cobble(0x7b7b7b, "Cobblestone"),
		Sand(0xefeab5, "Sand"),
		HardenedClay(0x935c43, "Hardened Clay"),
		Stone(0x6d6d6d, "Smooth Stone"),
		Sandstone(0xc1b989, "Sandstone");

		protected int color;
		protected String name;

		Insulate(int color, String name) {
			this.color = color;
			this.name = name;
		}

		public static Insulate get(int i) {
			return values()[i / 128 % values().length];
		}

		public int getColor() {
			return color;
		}

		public String getName() {
			return name;
		}

		public ItemStack getStack() {
			switch (this) {
				case Clay:
					return new ItemStack(Blocks.clay);

				case Cobble:
					return new ItemStack(Blocks.cobblestone);

				case HardenedClay:
					return new ItemStack(Blocks.hardened_clay);

				case Sand:
					return new ItemStack(Blocks.sand);

				case Sandstone:
					return new ItemStack(Blocks.sandstone);

				case Stone:
					return new ItemStack(Blocks.stone);
			}
			return null;
		}
	}
}
