package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.core.BotanyGUI;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDictionary extends Item implements IItemModelRegister {

    public ItemDictionary() {
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
        setRegistryName("database");
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel(Item item, IModelManager manager) {
    	manager.registerItemModel(item, 0, "botanistDatabase");
    	manager.registerItemModel(item, 1, "masterBotanistDatabase");
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
    	if(itemStack.getMetadata() == 0){
    		Botany.proxy.openGui(BotanyGUI.Database, player, player.getPosition());
    	}else{
    		Botany.proxy.openGui(BotanyGUI.DatabaseNEI, player, player.getPosition());
    	}
    	return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Botanist Database" : "Master Botanist Database";
    }
}
