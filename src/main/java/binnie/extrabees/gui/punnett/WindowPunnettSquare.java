package binnie.extrabees.gui.punnett;

import binnie.core.AbstractMod;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.resource.StyleSheet;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.PaddedTexture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeTexture;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// TODO unused class?
public class WindowPunnettSquare extends Window {
	ControlSlot bee1;
	ControlSlot bee2;
	ControlPunnett punnett;
	ISpeciesRoot root;

	public WindowPunnettSquare(EntityPlayer player, IInventory inventory, Side side) {
		super(245.0f, 205.0f, player, inventory, side);
		root = null;
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowPunnettSquare(player, inventory, side);
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
		setTitle("Punnett Square");
		CraftGUI.Render.stylesheet(new StyleSheetPunnett());
	}

	static class StyleSheetPunnett extends StyleSheet {
		public StyleSheetPunnett() {
			textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, ExtraBeeTexture.GUIPunnett, 32, 32, 32, 32));
			textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, ExtraBeeTexture.GUIPunnett));
			textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
			textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, ExtraBeeTexture.GUIPunnett));
		}
	}
}
