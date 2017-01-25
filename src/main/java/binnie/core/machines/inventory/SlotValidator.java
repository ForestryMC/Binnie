package binnie.core.machines.inventory;

import forestry.api.genetics.AlleleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public abstract class SlotValidator extends Validator<ItemStack> {
	public static ValidatorSprite spriteBee;
	public static ValidatorSprite spriteFrame;
	public static ValidatorSprite spriteCircuit;
	public static ValidatorSprite spriteBlock;

	@Nullable
	private ValidatorSprite sprite;

	public SlotValidator(@Nullable final ValidatorSprite icon) {
		this.sprite = icon;
	}

	@Nullable
	public TextureAtlasSprite getIcon(final boolean input) {
		return (this.sprite == null) ? null : this.sprite.getSprite(input).getSprite();
	}

	public static class Item extends SlotValidator {
		private ItemStack target;

		public Item(final ItemStack target, final ValidatorSprite icon) {
			super(icon);
			this.target = target;
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
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
		public boolean isValid(final ItemStack itemStack) {
			return AlleleManager.alleleRegistry.getIndividual(itemStack) != null;
		}

		@Override
		public String getTooltip() {
			return "Breedable Individual";
		}
	}
}
