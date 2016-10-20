package binnie.botany.flower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemInsulatedTube extends Item {
//	IIcon[] icons;

    public ItemInsulatedTube() {
        setRegistryName("insulatedTube");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        for (final Material mat : Material.values()) {
            for (final Insulate ins : Insulate.values()) {
                p_150895_3_.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
            }
        }
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister p_94581_1_) {
//		for (int i = 0; i < 3; ++i) {
//			this.icons[i] = Botany.proxy.getIcon(p_94581_1_, "insulatedTube." + i);
//		}
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getColorFromItemStack(final ItemStack stack, final int pass) {
//		return (pass == 0) ? 16777215 : ((pass == 1) ? Material.get(stack.getItemDamage()).getColor() : Insulate.get(stack.getItemDamage()).getColor());
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(Insulate.get(p_77624_1_.getItemDamage()).getName());
    }

    @Override
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        return Material.get(p_77653_1_.getItemDamage()).getName() + " Insulated Tube";
    }

//	@Override
//	public int getRenderPasses(final int metadata) {
//		return 3;
//	}
//
//	@Override
//	public IIcon getIcon(final ItemStack stack, final int pass) {
//		return this.icons[pass % 3];
//	}
//
//	@Override
//	public boolean requiresMultipleRenderPasses() {
//		return true;
//	}
//
//	public ItemInsulatedTube() {
//		this.icons = new IIcon[3];
//		this.setUnlocalizedName("insulatedTube");
//		this.setCreativeTab(CreativeTabBotany.instance);
//	}

    public static String getInsulate(final ItemStack stack) {
        return Insulate.get(stack.getItemDamage()).getName();
    }

    public static ItemStack getInsulateStack(final ItemStack stack) {
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

        private Material(final int color, final String name) {
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

        private Insulate(final int color, final String name) {
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
