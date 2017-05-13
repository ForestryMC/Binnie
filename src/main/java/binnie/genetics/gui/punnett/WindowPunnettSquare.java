package binnie.genetics.gui.punnett;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.resource.StyleSheet;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.PaddedTexture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;
import binnie.genetics.Genetics;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WindowPunnettSquare extends Window {

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPunnettSquare(player, inventory, side);
	}

	public WindowPunnettSquare(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(245, 205, player, inventory, side);
		this.root = null;
	}

	private static final IBinnieTexture texture = new GuiTexture();
	ControlSlot bee1;
	ControlSlot bee2;
	ControlPunnett punnett;
	@Nullable
	ISpeciesRoot root;

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
		this.setTitle("Punnett Square");
		CraftGUI.render.setStyleSheet(new StyleSheetPunnett());
	}

	static class StyleSheetPunnett extends StyleSheet {
		public StyleSheetPunnett() {
			this.textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, texture, 32, 32, 32, 32));
			this.textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, texture));
			this.textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, texture));
			this.textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, texture));
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
