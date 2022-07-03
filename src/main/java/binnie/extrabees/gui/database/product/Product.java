package binnie.extrabees.gui.database.product;

import net.minecraft.item.ItemStack;

public class Product {
    protected ItemStack item;
    protected float chance;

    public Product(ItemStack item, float chance) {
        this.item = item;
        this.chance = chance;
    }
}
