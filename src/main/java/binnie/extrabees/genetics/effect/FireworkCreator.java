package binnie.extrabees.genetics.effect;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class FireworkCreator {
	public enum Shape {
		Ball,
		LargeBall,
		Star,
		Creeper,
		Burst;
	}

	public static class Firework {
		boolean flicker;
		boolean trail;
		ArrayList<Integer> colors;
		byte shape;

		public Firework() {
			this.flicker = false;
			this.trail = false;
			this.colors = new ArrayList<>();
			this.shape = 0;
		}

		public void setFlicker() {
			this.flicker = true;
		}

		public void setTrail() {
			this.trail = true;
		}

		public void setShape(final Shape shape) {
			this.shape = (byte) shape.ordinal();
		}

		public void addColor(final int color) {
			this.colors.add(color);
		}

		NBTTagCompound getNBT() {
			final NBTTagCompound nbt = new NBTTagCompound();
			if (this.flicker) {
				nbt.setBoolean("Flicker", true);
			}
			if (this.trail) {
				nbt.setBoolean("Trail", true);
			}
			if (this.colors.size() == 0) {
				this.addColor(16777215);
			}
			final int[] array = new int[this.colors.size()];
			for (int i = 0; i < this.colors.size(); ++i) {
				array[i] = this.colors.get(i);
			}
			nbt.setIntArray("Colors", array);
			nbt.setByte("Type", this.shape);
			return nbt;
		}

		public ItemStack getFirework() {
			final NBTTagCompound var15 = new NBTTagCompound();
			final NBTTagCompound var16 = new NBTTagCompound();
			final NBTTagList var17 = new NBTTagList();
			var17.appendTag(this.getNBT());
			var16.setTag("Explosions", var17);
			var16.setByte("Flight", (byte) 0);
			var15.setTag("Fireworks", var16);
			final ItemStack item = new ItemStack(Items.FIREWORKS);
			item.setTagCompound(var15);
			return item;
		}
	}
}
