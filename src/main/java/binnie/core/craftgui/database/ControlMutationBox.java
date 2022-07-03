package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;
import java.util.List;

class ControlMutationBox extends ControlListBox<IMutation> {
    private Type type;
    private IAlleleSpecies species;

    @Override
    public IWidget createOption(IMutation value, int y) {
        return new ControlMutationItem(getContent(), value, species, y);
    }

    public ControlMutationBox(IWidget parent, int x, int y, int width, int height, Type type) {
        super(parent, x, y, width, height, 12.0f);
        species = null;
        this.type = type;
    }

    public void setSpecies(IAlleleSpecies species) {
        if (species == this.species) {
            return;
        }

        this.species = species;
        movePercentage(-100.0f);
        BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
        List<IMutation> discovered = system.getDiscoveredMutations(
                Window.get(this).getWorld(), Window.get(this).getUsername());

        if (species == null) {
            return;
        }

        if (type == Type.RESULTANT) {
            setOptions(system.getResultantMutations(species));
            return;
        }

        List<IMutation> mutations = system.getFurtherMutations(species);
        int i = 0;
        while (i < mutations.size()) {
            IMutation mutation = mutations.get(i);
            if (!discovered.contains(mutations) && !((IAlleleSpecies) mutation.getTemplate()[0]).isCounted()) {
                mutations.remove(i);
            } else {
                i++;
            }
        }
        setOptions(mutations);
    }

    enum Type {
        RESULTANT,
        FURTHER
    }
}
