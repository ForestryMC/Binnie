// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.minecraft.Window;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControlSpeciesBox extends ControlListBox<IAlleleSpecies>
{
	private IClassification branch;

	@Override
	public IWidget createOption(final IAlleleSpecies value, final int y) {
		return new ControlSpeciexBoxOption(this.getContent(), value, y);
	}

	public ControlSpeciesBox(final IWidget parent, final float x, final float y, final float width, final float height) {
		super(parent, x, y, width, height, 12.0f);
		branch = null;
	}

	public void setBranch(final IClassification branch) {
		if (branch != this.branch) {
			this.branch = branch;
			final List<IAlleleSpecies> speciesList2 = new ArrayList<IAlleleSpecies>();
			movePercentage(-100.0f);
			setOptions(speciesList2);
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
			setOptions(speciesList2);
		}
	}
}
