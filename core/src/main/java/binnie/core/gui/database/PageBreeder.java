package binnie.core.gui.database;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.page.ControlPage;
import binnie.core.gui.minecraft.Window;
import binnie.core.util.I18N;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.text.TextFormatting;

public class PageBreeder extends ControlPage<DatabaseTab> {
	private final GameProfile player;

	public PageBreeder(final IWidget parent, final GameProfile player, final DatabaseTab tab) {
		super(parent, 0, 0, parent.getSize().xPos(), parent.getSize().yPos(), tab);
		this.player = player;
		this.onPageRefresh();
	}

	public void onPageRefresh() {
		this.deleteAllChildren();
		final IBreedingSystem system = ((WindowAbstractDatabase) Window.get(this)).getBreedingSystem();
		new ControlTextCentered(this, 8, TextFormatting.UNDERLINE + system.getDescriptor() + ' ' + I18N.localise(DatabaseConstants.BREEDER_KEY + ".profile"));
		new ControlTextCentered(this, 75, system.getDiscoveredSpeciesCount() + '/' + system.getTotalSpeciesCount() + ' ' + I18N.localise(DatabaseConstants.BREEDER_KEY + ".species"));
		new ControlBreedingProgress(this, 20, 87, 102, 14, system, system.getDiscoveredSpeciesPercentage());
		new ControlTextCentered(this, 115, system.getDiscoveredBranchCount() + '/' + system.getTotalBranchCount() + ' ' + I18N.localise(DatabaseConstants.BREEDER_KEY + ".branches"));
		new ControlBreedingProgress(this, 20, 127, 102, 14, system, system.getDiscoveredBranchPercentage());
		if (system.getDiscoveredSecretCount() > 0) {
			new ControlTextCentered(this, 155, system.getDiscoveredSecretCount() + '/' + system.getTotalSecretCount() + ' ' + I18N.localise(DatabaseConstants.BREEDER_KEY + ".species.secret"));
		}
		new ControlTextCentered(this, 32, this.player.getName());
		new ControlTextCentered(this, 44, TextFormatting.ITALIC + system.getEpitome());
	}
}
