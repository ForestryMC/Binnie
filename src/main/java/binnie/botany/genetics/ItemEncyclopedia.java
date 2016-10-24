package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.CreativeTabBotany;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEncyclopedia extends Item implements IItemModelRegister {
    boolean reinforced;

    public ItemEncyclopedia(boolean reinforced) {
        this.reinforced = reinforced;
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("encylopedia" + (reinforced ? "Reinforced" : ""));
        this.setMaxStackSize(1);
        this.setMaxDamage(reinforced ? 480 : 120);
        setRegistryName("encylopedia" + (reinforced ? "Reinforced" : ""));
    }
    
    @Override
    public void registerModel(Item item, IModelManager manager) {
    	manager.registerItemModel(item, 0, "encylopedia" + (this.reinforced ? "Reinforced" : ""));
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        return Binnie.Language.localise("item.botany.encylopedia" + (this.reinforced ? "_reinforced" : ""));
    }
}
