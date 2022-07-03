package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemInsulatedTube extends Item {
    IIcon[] icons;

    public ItemInsulatedTube() {
        icons = new IIcon[3];
        setUnlocalizedName("insulatedTube");
        setCreativeTab(CreativeTabBotany.instance);
        setHasSubtypes(true);
    }

    public static ItemStack getInsulateStack(ItemStack stack) {
        return Insulate.get(stack.getItemDamage()).getStack();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (Material mat : Material.values()) {
            for (Insulate ins : Insulate.values()) {
                list.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < 3; ++i) {
            icons[i] = Botany.proxy.getIcon(register, "insulatedTube." + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 0) {
            return 0xffffff;
        }
        return (pass == 1)
                ? Material.get(stack.getItemDamage()).getColor()
                : Insulate.get(stack.getItemDamage()).getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(Insulate.get(stack.getItemDamage()).getName());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return Material.get(stack.getItemDamage()).getName() + " " + I18N.localise("botany.tube.name");
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
        Copper(0xe3b78e, "cooper"),
        Tin(0xe1eef4, "tin"),
        Bronze(0xddc276, "bronze"),
        Iron(0xd8d8d8, "iron");

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
            return I18N.localise("botany.tube." + name);
        }
    }

    enum Insulate {
        Clay(0xa1aacc, "clay"),
        Cobble(0x7b7b7b, "cobblestone"),
        Sand(0xefeab5, "sand"),
        HardenedClay(0x935c43, "hardenedClay"),
        Stone(0x6d6d6d, "smoothStone"),
        Sandstone(0xc1b989, "sandstone");

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
            return I18N.localise("botany.tube.insulate." + name);
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
