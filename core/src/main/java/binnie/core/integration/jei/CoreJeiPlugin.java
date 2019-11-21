package binnie.core.integration.jei;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.minecraft.GuiCraftGUI;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
@JEIPlugin
public class CoreJeiPlugin implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		registry.addAdvancedGuiHandlers(new CoreGuiHandler());
	}

	private static class CoreGuiHandler implements IAdvancedGuiHandler<GuiCraftGUI> {
		@Override
		public Class<GuiCraftGUI> getGuiContainerClass() {
			return GuiCraftGUI.class;
		}

		@Nullable
		@Override
		public Object getIngredientUnderMouse(GuiCraftGUI guiContainer, int mouseX, int mouseY) {
			IWidget widgetUnderMouse = guiContainer.getWidgetUnderMouse();
			if (widgetUnderMouse != null) {
				return widgetUnderMouse.getIngredient();
			}
			return null;
		}
	}
}
