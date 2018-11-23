package binnie.core.machines.inventory;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.AlleleManager;

public abstract class SlotValidator extends Validator<ItemStack> {

	@Nullable
	private final ValidatorSprite sprite;

	public SlotValidator(@Nullable ValidatorSprite icon) {
		this.sprite = icon;
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(boolean input) {
		return (this.sprite == null) ? null : this.sprite.getSprite(input).getSprite();
	}

	public static class Item extends SlotValidator {
		private final ItemStack target;

		public Item(ItemStack target, ValidatorSprite icon) {
			super(icon);
			this.target = target;
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return itemStack.isItemEqual(this.target);
		}

		@Override
		public String getTooltip() {
			return this.target.getDisplayName();
		}
	}

	public static class Individual extends SlotValidator {
		public Individual() {
			super(null);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return AlleleManager.alleleRegistry.getIndividual(itemStack) != null;
		}

		@Override
		public String getTooltip() {
			return "Breedable Individual";
		}
	}
}
