package binnie.genetics.gui.punnett;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.resource.StyleSheet;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.gui.resource.minecraft.PaddedTexture;
import binnie.core.gui.resource.minecraft.StandardTexture;
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
		CraftGUI.RENDER.setStyleSheet(new StyleSheetPunnett());
	}

	static class StyleSheetPunnett extends StyleSheet {
		public StyleSheetPunnett() {
			textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, texture, 32, 32, 32, 32));
			textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, texture));
			textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, texture));
			textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, texture));
		}
	}

	private static class GuiTexture implements IBinnieTexture {

		@SideOnly(Side.CLIENT)
		@Nullable
		private BinnieResource resource;

		@Override
		public BinnieResource getTexture() {
			if (resource == null) {
				resource = Binnie.RESOURCE.getPNG("genetics", ResourceType.GUI, "punnett");
			}
			return resource;
		}
	}
}
