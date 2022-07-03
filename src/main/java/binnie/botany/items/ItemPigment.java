package binnie.botany.items;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemPigment extends Item {
    public ItemPigment() {
        setUnlocalizedName("pigment");
        setHasSubtypes(true);
        setCreativeTab(CreativeTabBotany.instance);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(EnumChatFormatting.GRAY
                + EnumFlowerColor.get(stack.getItemDamage()).getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int index) {
        int damage = stack.getItemDamage();
        return EnumFlowerColor.get(damage).getColor(false);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18N.localise("botany.item.pigment.name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            list.add(new ItemStack(this, 1, c.ordinal()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Botany.proxy.getIcon(register, "pigment");
    }
}
