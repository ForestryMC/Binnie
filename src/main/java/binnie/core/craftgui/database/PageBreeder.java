package binnie.core.craftgui.database;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.page.ControlPage;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;

import com.mojang.authlib.GameProfile;
import net.minecraft.util.text.TextFormatting;

public class PageBreeder extends ControlPage<DatabaseTab> {
	private GameProfile player;

	public PageBreeder(final IWidget parent, final GameProfile player, final DatabaseTab tab) {
		super(parent, 0, 0, parent.getSize().x(), parent.getSize().y(), tab);
		this.player = player;
		this.onPageRefresh();
	}

	public void onPageRefresh() {
		while (this.getWidgets().size() > 0) {
			this.deleteChild(this.getWidgets().get(0));
		}
		final BreedingSystem system = ((WindowAbstractDatabase) Window.get(this)).getBreedingSystem();
		final String descriptor = system.getDescriptor();
		new ControlTextCentered(this, 8, TextFormatting.UNDERLINE + system.getDescriptor() + " " + Binnie.LANGUAGE.localise("binniecore.gui.database.breeder.profile"));
		new ControlTextCentered(this, 75, "" + system.discoveredSpeciesCount + "/" + system.totalSpeciesCount + " " + Binnie.LANGUAGE.localise("binniecore.gui.database.species"));
		new ControlBreedingProgress(this, 20, 87, 102, 14, system, system.discoveredSpeciesPercentage);
		new ControlTextCentered(this, 115, "" + system.discoveredBranchCount + "/" + system.totalBranchCount + " " + Binnie.LANGUAGE.localise("binniecore.gui.database.breeder.branches"));
		new ControlBreedingProgress(this, 20, 127, 102, 14, system, system.discoveredBranchPercentage);
		if (system.discoveredSecretCount > 0) {
			new ControlTextCentered(this, 155, "" + system.discoveredSecretCount + "/" + system.totalSecretCount + " " + Binnie.LANGUAGE.localise("binniecore.gui.database.breeder.species.secret"));
		}
		new ControlTextCentered(this, 32, this.player.getName());
		new ControlTextCentered(this, 44, TextFormatting.ITALIC + system.getEpitome());
	}
}
