package binnie.extrabees.gui.punnett;

import binnie.core.AbstractMod;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.resource.StyleSheet;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.resource.minecraft.PaddedTexture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowPunnettSquare extends Window {
	ControlSlot bee1;
	ControlSlot bee2;
	ControlPunnett punnett;
	ISpeciesRoot root;

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPunnettSquare(player, inventory, side);
	}

	public WindowPunnettSquare(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(245.0f, 205.0f, player, inventory, side);
		this.root = null;
	}

	@Override
	public AbstractMod getMod() {
		return ExtraBees.instance;
	}

	@Override
	public String getName() {
		return "Punnett";
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Punnett Square");
		CraftGUI.Render.stylesheet(new StyleSheetPunnett());
	}

	static class StyleSheetPunnett extends StyleSheet {
		public StyleSheetPunnett() {
			this.textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, ExtraBeeTexture.GUIPunnett, 32, 32, 32, 32));
			this.textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, ExtraBeeTexture.GUIPunnett));
			this.textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
			this.textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
		}
	}
}
