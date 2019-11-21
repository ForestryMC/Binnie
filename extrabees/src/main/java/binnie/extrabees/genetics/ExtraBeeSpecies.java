package binnie.extrabees.genetics;

import binnie.extrabees.items.ItemHoneyComb;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesBuilder;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.apiculture.genetics.alleles.AlleleBeeSpecies;
import net.minecraft.item.ItemStack;

public class ExtraBeeSpecies extends AlleleBeeSpecies {
	private State state;

	public ExtraBeeSpecies(String modId, String uid, String unlocalizedName, String authority, String unlocalizedDescription, boolean dominant, IClassification branch, String binomial, int primaryColor, int secondaryColor) {
		super(modId, uid, unlocalizedName, authority, unlocalizedDescription, dominant, branch, binomial, primaryColor, secondaryColor);
		this.state = State.ACTIVE;
	}

	@Override
	public IAlleleBeeSpeciesBuilder addProduct(ItemStack product, Float chance) {
		if (product.isEmpty() || ItemHoneyComb.isInvalidComb(product)) {
			if (state == State.ACTIVE) {
				state = State.INACTIVE;
				return this;
			}
		}
		return super.addProduct(product, chance);
	}

	@Override
	public IAlleleBeeSpeciesBuilder addSpecialty(ItemStack specialty, Float chance) {
		if (specialty.isEmpty() || ItemHoneyComb.isInvalidComb(specialty)) {
			if (state == State.ACTIVE) {
				state = State.INACTIVE;
				return this;
			}
		}
		return super.addSpecialty(specialty, chance);
	}

	@Override
	public IAlleleBeeSpecies build() {
		if (state == State.INACTIVE) {
			if (this.state != State.ACTIVE) {
				AlleleManager.alleleRegistry.blacklistAllele(this.getUID());
			}
		}
		return super.build();
	}

	public boolean isActive() {
		return state == State.ACTIVE;
	}

	public enum State {
		ACTIVE,
		INACTIVE,
		DEPRECATED,
	}
}
