package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.Window;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import java.util.List;

class ControlMutationBox extends ControlListBox<IMutation> {
    private int index;
    private Type type;
    private IAlleleSpecies species;

    @Override
    public IWidget createOption(final IMutation value, final int y) {
        return new ControlMutationItem(((ControlScrollableContent<ControlList<IMutation>>) this).getContent(), value, this.species, y);
    }

    public ControlMutationBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
        super(parent, x, y, width, height, 12.0f);
        this.species = null;
        this.type = type;
    }

    public void setSpecies(final IAlleleSpecies species) {
        if (species != this.species) {
            this.species = species;
            this.index = 0;
            this.movePercentage(-100.0f);
            final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
            final List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
            if (species != null) {
                if (this.type == Type.Resultant) {
                    this.setOptions(system.getResultantMutations(species));
                } else {
                    final List<IMutation> mutations = system.getFurtherMutations(species);
                    int i = 0;
                    while (i < mutations.size()) {
                        final IMutation mutation = mutations.get(i);
                        if (!discovered.contains(mutations) && !((IAlleleSpecies) mutation.getTemplate()[0]).isCounted()) {
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
        Further;
    }
}
