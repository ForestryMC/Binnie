package binnie.craftgui.mod.database;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.Window;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.text.TextFormatting;

public class PageBreeder extends ControlPage<DatabaseTab> {
	private GameProfile player;

	public PageBreeder(final IWidget parent, final GameProfile player, final DatabaseTab tab) {
		super(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), tab);
		this.player = player;
		this.onPageRefresh();
	}

	public void onPageRefresh() {
		while (this.getWidgets().size() > 0) {
			this.deleteChild(this.getWidgets().get(0));
		}
		final BreedingSystem system = ((WindowAbstractDatabase) Window.get(this)).getBreedingSystem();
		final String descriptor = system.getDescriptor();
		new ControlTextCentered(this, 8.0f, TextFormatting.UNDERLINE + system.getDescriptor() + " " + Binnie.Language.localise("binniecore.gui.database.breeder.profile"));
		new ControlTextCentered(this, 75.0f, "" + system.discoveredSpeciesCount + "/" + system.totalSpeciesCount + " " + Binnie.Language.localise("binniecore.gui.database.species"));
		new ControlBreedingProgress(this, 20, 87, 102, 14, system, system.discoveredSpeciesPercentage);
		new ControlTextCentered(this, 115.0f, "" + system.discoveredBranchCount + "/" + system.totalBranchCount + " " + Binnie.Language.localise("binniecore.gui.database.breeder.branches"));
		new ControlBreedingProgress(this, 20, 127, 102, 14, system, system.discoveredBranchPercentage);
		if (system.discoveredSecretCount > 0) {
			new ControlTextCentered(this, 155.0f, "" + system.discoveredSecretCount + "/" + system.totalSecretCount + " " + Binnie.Language.localise("binniecore.gui.database.breeder.species.secret"));
		}
		new ControlTextCentered(this, 32.0f, this.player.getName());
		new ControlTextCentered(this, 44.0f, TextFormatting.ITALIC + system.getEpitome());
	}
}
