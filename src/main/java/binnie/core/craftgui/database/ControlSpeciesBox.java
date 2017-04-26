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
	public IWidget createOption(IAlleleSpecies value, int y) {
		return new ControlSpeciexBoxOption(this.getContent(), value, y);
	}

	public ControlSpeciesBox(IWidget parent, float x, float y, float width, float height) {
		super(parent, x, y, width, height, 12.0f);
		branch = null;
	}

	public void setBranch(IClassification branch) {
		if (branch == this.branch) {
			return;
		}

		this.branch = branch;
		List<IAlleleSpecies> speciesList2 = new ArrayList<>();
		movePercentage(-100.0f);
		setOptions(speciesList2);

		EntityPlayer player = Window.get(this).getPlayer();
		GameProfile playerName = Window.get(this).getUsername();
		WindowAbstractDatabase db = Window.get(this);
		Collection<IAlleleSpecies> speciesList3 = db.isNEI ? db.getBreedingSystem().getAllSpecies() : db.getBreedingSystem().getDiscoveredSpecies(db.getWorld(), playerName);
		if (branch != null) {
			for (IAlleleSpecies species : branch.getMemberSpecies()) {
				if (speciesList3.contains(species)) {
					speciesList2.add(species);
				}
			}
		}
		setOptions(speciesList2);
	}
}
