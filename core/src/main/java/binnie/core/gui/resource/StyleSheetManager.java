package binnie.core.gui.resource;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;

public class StyleSheetManager {
	static IStyleSheet defaultSS = new DefaultStyleSheet();

	@SideOnly(Side.CLIENT)
	public static Texture getTexture(final Object key) {
		return StyleSheetManager.defaultSS.getTexture(key);
	}

	public static IStyleSheet getDefault() {
		return StyleSheetManager.defaultSS;
	}

	private static class DefaultStyleSheet implements IStyleSheet {
		@Override
		@SideOnly(Side.CLIENT)
		public Texture getTexture(final Object key) {
			return CraftGUI.resourceManager.getTexture(key.toString());
		}
	}
}
