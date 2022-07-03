package binnie.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Mods {
    public static Mod forestry;
    public static Mod ic2;
    public static Mod botania;

    private static Item findItem(String modId, String name) {
        Item stack = GameRegistry.findItem(modId, name);
        if (stack == null && modId.equals("Forestry")) {
            throw new RuntimeException("Item not found: " + modId + ":" + name);
        }
        return stack;
    }

    private static ItemStack findItemStack(String modId, String name, int stackSize) {
        ItemStack stack = GameRegistry.findItemStack(modId, name, stackSize);
        if (stack == null && modId.equals("Forestry")) {
            throw new RuntimeException("Stack not found: " + modId + ":" + name);
        }
        return stack;
    }

    private static Block findBlock(String modId, String name) {
        Block stack = GameRegistry.findBlock(modId, name);
        if (stack == null && modId.equals("Forestry")) {
            throw new RuntimeException("Block not found: " + modId + ":" + name);
        }
        return stack;
    }

    static {
        Mods.forestry = new Mod("Forestry");
        Mods.ic2 = new Mod("IC2");
        Mods.botania = new Mod("Botania");
    }

    public static class Mod {
        private String id;

        private Mod(String id) {
            this.id = id;
        }

        public Item item(String name) {
            return findItem(id, name);
        }

        public Block block(String name) {
            return findBlock(id, name);
        }

        public ItemStack stack(String name, int stackSize) {
            return findItemStack(id, name, stackSize);
        }

        public ItemStack stack(String name) {
            return stack(name, 1);
        }

        public ItemStack stack(String string, int i, int j) {
            return new ItemStack(item(string), i, j);
        }

        public boolean active() {
            return Loader.isModLoaded(id);
        }
    }
}
