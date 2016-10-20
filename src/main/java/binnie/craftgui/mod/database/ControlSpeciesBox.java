package binnie.craftgui.mod.database;

import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.Window;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControlSpeciesBox extends ControlListBox<IAlleleSpecies> {
    private IClassification branch;

    @Override
    public IWidget createOption(final IAlleleSpecies value, final int y) {
        return new ControlSpeciexBoxOption(((ControlScrollableContent<ControlList<IAlleleSpecies>>) this).getContent(), value, y);
    }

    public ControlSpeciesBox(final IWidget parent, final float x, final float y, final float width, final float height) {
        super(parent, x, y, width, height, 12.0f);
        this.branch = null;
    }

    public void setBranch(final IClassification branch) {
        if (branch != this.branch) {
            this.branch = branch;
            final List<IAlleleSpecies> speciesList2 = new ArrayList<IAlleleSpecies>();
            this.movePercentage(-100.0f);
            this.setOptions(speciesList2);
            final EntityPlayer player = Window.get(this).getPlayer();
            final GameProfile playerName = Window.get(this).getUsername();
            final WindowAbstractDatabase db = Window.get(this);
            final Collection<IAlleleSpecies> speciesList3 = db.isNEI ? db.getBreedingSystem().getAllSpecies() : db.getBreedingSystem().getDiscoveredSpecies(db.getWorld(), playerName);
            if (branch != null) {
                for (final IAlleleSpecies species : branch.getMemberSpecies()) {
                    if (speciesList3.contains(species)) {
                        speciesList2.add(species);
                    }
                }
            }
            this.setOptions(speciesList2);
        }
    }
}
