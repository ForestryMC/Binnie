package binnie.core.machines.inventory;

import binnie.core.BinnieCore;
import forestry.api.genetics.AlleleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public abstract class SlotValidator extends Validator<ItemStack> {
	public static ValidatorIcon IconBee;
	public static ValidatorIcon IconFrame;
	public static ValidatorIcon IconCircuit;
	public static ValidatorIcon IconBlock;
	private ValidatorIcon icon;

	public SlotValidator(final ValidatorIcon icon) {
		this.icon = icon;
	}

	public TextureAtlasSprite getIcon(final boolean input) {
		return (this.icon == null) ? null : BinnieCore.proxy.getTextureAtlasSprite(this.icon.getIcon(input).getResourceLocation());
	}

	public static class Item extends SlotValidator {
		private ItemStack target;

		public Item(final ItemStack target, final ValidatorIcon icon) {
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
