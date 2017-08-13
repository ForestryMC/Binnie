package binnie.core.gui.resource;

import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.gui.resource.minecraft.PaddedTexture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;

public class StyleSheetPunnett extends StyleSheet {
	public StyleSheetPunnett() {
		this.textures.put(CraftGUITexture.WINDOW, new PaddedTexture(0, 0, 160, 160, 0, BinnieCoreTexture.GUI_PUNNETT, 32, 32, 32, 32));
		this.textures.put(CraftGUITexture.SLOT, new StandardTexture(160, 0, 18, 18, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(GeneticsGUITexture.CHROMOSOME, new StandardTexture(160, 36, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(GeneticsGUITexture.CHROMOSOME_OVERLAY, new StandardTexture(160, 52, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(CraftGUITexture.HELP_BUTTON, new StandardTexture(178, 0, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(CraftGUITexture.INFO_BUTTON, new StandardTexture(178, 16, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
	}
}
