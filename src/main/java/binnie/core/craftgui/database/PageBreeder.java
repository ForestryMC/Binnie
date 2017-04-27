package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.page.ControlPage;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.EnumChatFormatting;

public class PageBreeder extends ControlPage<DatabaseTab> {
	private GameProfile player;

	public PageBreeder(IWidget parent, GameProfile player, DatabaseTab tab) {
		super(parent, 0.0f, 0.0f, parent.getSize().x(), parent.getSize().y(), tab);
		this.player = player;
		onPageRefresh();
	}

	public void onPageRefresh() {
		while (getWidgets().size() > 0) {
			deleteChild(getWidgets().get(0));
		}

		BreedingSystem system = ((WindowAbstractDatabase) Window.get(this)).getBreedingSystem();
		String descriptor = system.getDescriptor();
		new ControlTextCentered(this, 8.0f, EnumChatFormatting.UNDERLINE + system.getDescriptor() + " Profile" + EnumChatFormatting.RESET);
		new ControlTextCentered(this, 75.0f, "" + system.discoveredSpeciesCount + "/" + system.totalSpeciesCount + " Species");
		new ControlBreedingProgress(this, 20, 87, 102, 14, system, system.discoveredSpeciesPercentage);
		new ControlTextCentered(this, 115.0f, "" + system.discoveredBranchCount + "/" + system.totalBranchCount + " Branches");
		new ControlBreedingProgress(this, 20, 127, 102, 14, system, system.discoveredBranchPercentage);

		if (system.discoveredSecretCount > 0) {
			new ControlTextCentered(this, 155.0f, "" + system.discoveredSecretCount + "/" + system.totalSecretCount + " Secret Species");
		}
		new ControlTextCentered(this, 32.0f, player.getName());
		new ControlTextCentered(this, 44.0f, EnumChatFormatting.ITALIC + system.getEpitome() + EnumChatFormatting.RESET);
	}
}
