package binnie.core.gui.database;

import javax.annotation.Nullable;
import java.util.List;

import binnie.core.api.genetics.IBreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.minecraft.Window;

class ControlMutationBox extends ControlListBox<IMutation> {
	private final Type type;
	@Nullable
	private IAlleleSpecies species;

	public ControlMutationBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12);
		this.species = null;
		this.type = type;
	}

	@Override
	public IWidget createOption(final IMutation value, final int y) {
		return new ControlMutationItem(this.getContent(), value, this.species, y);
	}

	public void setSpecies(@Nullable final IAlleleSpecies species) {
		if (species != this.species) {
			this.species = species;
			this.movePercentage(-100.0f);
			final IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
			final List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
			if (species != null) {
				if (this.type == Type.Resultant) {
					this.setOptions(system.getResultantMutations(species));
				} else {
					final List<IMutation> mutations = system.getFurtherMutations(species);
					int i = 0;
					while (i < mutations.size()) {
						final IMutation mutation = mutations.get(i);
						if (!discovered.contains(mutation) && !((IAlleleSpecies) mutation.getTemplate()[0]).isCounted()) {
							mutations.remove(i);
						} else {
							++i;
						}
					}
					this.setOptions(mutations);
				}
			}
		}
	}

	enum Type {
		Resultant,
		Further
	}
}
