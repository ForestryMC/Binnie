package binnie.core.gui.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.minecraft.Window;

@SideOnly(Side.CLIENT)
public class ControlSpeciesBox extends ControlListBox<IAlleleSpecies> {
	private IClassification branch;

	public ControlSpeciesBox(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height, 12);
		this.branch = null;
	}

	@Override
	public IWidget createOption(IAlleleSpecies value, int y) {
		return new ControlSpeciesBoxOption(this.getContent(), value, y);
	}

	public void setBranch(IClassification branch) {
		if (branch != this.branch) {
			this.branch = branch;
			List<IAlleleSpecies> speciesList2 = new ArrayList<>();
			this.movePercentage(-100.0f);
			this.setOptions(speciesList2);
			//final EntityPlayer player = Window.get(this).getPlayer();
			GameProfile playerName = Window.get(this).getUsername();
			WindowAbstractDatabase db = Window.get(this);
			IBreedingSystem breedingSystem = db.getBreedingSystem();
			Collection<IAlleleSpecies> speciesList3 = db.isMaster() ? breedingSystem.getAllSpecies() : breedingSystem.getDiscoveredSpecies(db.getWorld(), playerName);
			if (branch != null) {
				for (IAlleleSpecies species : branch.getMemberSpecies()) {
					if (speciesList3.contains(species)) {
						speciesList2.add(species);
					}
				}
			}
			this.setOptions(speciesList2);
		}
	}
}
