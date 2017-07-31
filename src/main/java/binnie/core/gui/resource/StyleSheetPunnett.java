package binnie.core.gui.resource;

import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.gui.resource.minecraft.PaddedTexture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;
import binnie.genetics.gui.punnett.ExtraBeeGUITexture;

public class StyleSheetPunnett extends StyleSheet {
	public StyleSheetPunnett() {
		this.textures.put(CraftGUITexture.Window, new PaddedTexture(0, 0, 160, 160, 0, BinnieCoreTexture.GUI_PUNNETT, 32, 32, 32, 32));
		this.textures.put(CraftGUITexture.Slot, new StandardTexture(160, 0, 18, 18, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(ExtraBeeGUITexture.Chromosome, new StandardTexture(160, 36, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(ExtraBeeGUITexture.Chromosome2, new StandardTexture(160, 52, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(CraftGUITexture.HelpButton, new StandardTexture(178, 0, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
		this.textures.put(CraftGUITexture.InfoButton, new StandardTexture(178, 16, 16, 16, 0, BinnieCoreTexture.GUI_PUNNETT));
	}
}
