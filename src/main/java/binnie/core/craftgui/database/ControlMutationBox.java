// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import java.util.List;

class ControlMutationBox extends ControlListBox<IMutation>
{
	private int index;
	private Type type;
	private IAlleleSpecies species;

	@Override
	public IWidget createOption(final IMutation value, final int y) {
		return new ControlMutationItem(this.getContent(), value, species, y);
	}

	public ControlMutationBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12.0f);
		species = null;
		this.type = type;
	}

	public void setSpecies(final IAlleleSpecies species) {
		if (species != this.species) {
			this.species = species;
			index = 0;
			movePercentage(-100.0f);
			final BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
			final List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
			if (species != null) {
				if (type == Type.Resultant) {
					setOptions(system.getResultantMutations(species));
				}
				else {
					final List<IMutation> mutations = system.getFurtherMutations(species);
					int i = 0;
					while (i < mutations.size()) {
						final IMutation mutation = mutations.get(i);
						if (!discovered.contains(mutations) && !((IAlleleSpecies) mutation.getTemplate()[0]).isCounted()) {
							mutations.remove(i);
						}
						else {
							++i;
						}
					}
					setOptions(mutations);
				}
			}
		}
	}

	enum Type
	{
		Resultant,
		Further
	}
}
