package binnie.extrabees.genetics.effect;

import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FireworkCreator {
    public enum Shape {
        Ball,
        LargeBall,
        Star,
        Creeper,
        Burst
    }

    public static class Firework {
        protected boolean flicker;
        protected boolean trail;
        protected ArrayList<Integer> colors;
        protected byte shape;

        public Firework() {
            flicker = false;
            trail = false;
            colors = new ArrayList<>();
            shape = 0;
        }

        public void setTrail() {
            trail = true;
        }

        public void setShape(Shape shape) {
            this.shape = (byte) shape.ordinal();
        }

        public void addColor(int color) {
            colors.add(color);
        }

        public NBTTagCompound getNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            if (flicker) {
                nbt.setBoolean("Flicker", true);
            }

            if (trail) {
                nbt.setBoolean("Trail", true);
            }

            if (colors.size() == 0) {
                addColor(0xffffff);
            }

            int[] array = new int[colors.size()];
            for (int i = 0; i < colors.size(); ++i) {
                array[i] = colors.get(i);
            }

            nbt.setIntArray("Colors", array);
            nbt.setByte("Type", shape);
            return nbt;
        }

        public ItemStack getFirework() {
            NBTTagCompound fireworksNbt = new NBTTagCompound();
            NBTTagCompound fireworkNbt = new NBTTagCompound();
            NBTTagList explosions = new NBTTagList();
            explosions.appendTag(getNBT());
            fireworkNbt.setTag("Explosions", explosions);
            fireworkNbt.setByte("Flight", (byte) 0);
            fireworksNbt.setTag("Fireworks", fireworkNbt);
            ItemStack item = new ItemStack(Items.fireworks);
            item.setTagCompound(fireworksNbt);
            return item;
        }
    }
}
