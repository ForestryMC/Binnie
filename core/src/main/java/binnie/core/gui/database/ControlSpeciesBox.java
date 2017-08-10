package binnie.core.gui.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.minecraft.Window;

@SideOnly(Side.CLIENT)
public class ControlSpeciesBox extends ControlListBox<IAlleleSpecies> {
	private IClassification branch;

	public ControlSpeciesBox(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height, 12);
		this.branch = null;
	}

	@Override
	public IWidget createOption(final IAlleleSpecies value, final int y) {
		return new ControlSpeciesBoxOption(this.getContent(), value, y);
	}

	public void setBranch(final IClassification branch) {
		if (branch != this.branch) {
			this.branch = branch;
			final List<IAlleleSpecies> speciesList2 = new ArrayList<>();
			this.movePercentage(-100.0f);
			this.setOptions(speciesList2);
			//final EntityPlayer player = Window.get(this).getPlayer();
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
