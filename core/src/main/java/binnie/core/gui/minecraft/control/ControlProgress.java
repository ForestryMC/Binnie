package binnie.core.gui.minecraft.control;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.resource.Texture;

public class ControlProgress extends ControlProgressBase {
	private Texture progressBlank;
	private Texture progressBar;
	private Position direction;

	public ControlProgress(final IWidget parent, final int x, final int y, final Texture progressBlank, final Texture progressBar, final Position dir) {
		super(parent, x, y, (progressBlank == null) ? 0 : progressBlank.width(), (progressBlank == null) ? 0 : progressBlank.height());
		this.progressBlank = progressBlank;
		this.progressBar = progressBar;
		this.progress = 0.0f;
		this.direction = dir;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(this.progressBlank, this.getArea());
		CraftGUI.RENDER.texturePercentage(this.progressBar, this.getArea(), this.direction, this.progress);
	}
}
