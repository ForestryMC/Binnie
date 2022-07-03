package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.core.BotanyGUI;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDictionary extends Item {
    protected IIcon iconMaster;

    public ItemDictionary() {
        setCreativeTab(CreativeTabBotany.instance);
        setUnlocalizedName("database");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        itemIcon = Botany.proxy.getIcon(register, "botanistDatabase");
        iconMaster = Botany.proxy.getIcon(register, "masterBotanistDatabase");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return (damage == 0) ? itemIcon : iconMaster;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        super.getSubItems(item, tab, list);
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        BotanyGUI guiId = (stack.getItemDamage() == 0) ? BotanyGUI.Database : BotanyGUI.DatabaseNEI;
        Botany.proxy.openGui(guiId, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return (stack.getItemDamage() == 0)
                ? I18N.localise("botany.item.database.0.name")
                : I18N.localise("botany.item.database.1.name");
    }
}
