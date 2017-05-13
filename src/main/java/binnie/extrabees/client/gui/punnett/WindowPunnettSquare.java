package binnie.extrabees.client.gui.punnett;

import binnie.core.AbstractMod;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.resource.StyleSheet;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.PaddedTexture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.client.GuiHack;
import binnie.extrabees.client.ExtraBeeTexture;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WindowPunnettSquare extends Window {
	ControlSlot bee1;
	ControlSlot bee2;
	ControlPunnett punnett;
	@Nullable
	ISpeciesRoot root;

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPunnettSquare(player, inventory, side);
	}

	public WindowPunnettSquare(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(245, 205, player, inventory, side);
		this.root = null;
	}

	@Override
	public AbstractMod getMod() {
		return GuiHack.INSTANCE;
	}

	@Override
	public String getBackgroundTextureName() {
		return "Punnett";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setTitle("Punnett Square");
		CraftGUI.render.setStyleSheet(new StyleSheetPunnett());
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
