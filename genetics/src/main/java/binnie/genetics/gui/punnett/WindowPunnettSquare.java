package binnie.genetics.gui.punnett;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.ISpeciesRoot;

import binnie.core.AbstractMod;
import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.resource.stylesheet.StyleSheetManager;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;

public class WindowPunnettSquare extends Window {

	private static final IBinnieTexture texture = new GuiTexture();
	ControlSlot bee1;
	ControlSlot bee2;
	ControlPunnett punnett;
	@Nullable
	ISpeciesRoot root;

	public WindowPunnettSquare(EntityPlayer player, IInventory inventory, Side side) {
		super(245, 205, player, inventory, side);
		root = null;
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowPunnettSquare(player, inventory, side);
	}

	@Override
	public AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "Punnett";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		setTitle("Punnett Square");
		CraftGUI.RENDER.setStyleSheet(StyleSheetManager.getSheet(StyleSheetManager.PUNNETT_SHEET));
	}

	private static class GuiTexture implements IBinnieTexture {

		@SideOnly(Side.CLIENT)
		@Nullable
		private BinnieResource resource;

		@Override
		@SideOnly(Side.CLIENT)
		public BinnieResource getTexture() {
			if (resource == null) {
				resource = Binnie.RESOURCE.getPNG(Constants.CORE_MOD_ID, ResourceType.GUI, "punnett");
			}
			return resource;
		}
	}
}
