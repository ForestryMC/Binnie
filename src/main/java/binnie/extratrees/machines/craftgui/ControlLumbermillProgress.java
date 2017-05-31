package binnie.extratrees.machines.craftgui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.craftgui.window.Panel;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.machines.lumbermill.LumbermillRecipes;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ControlLumbermillProgress extends ControlProgressBase {
	static Texture Saw = new StandardTexture(0, 0, 6, 32, ExtraTreeTexture.Gui);
	static Texture Saw2 = new StandardTexture(2, 0, 4, 32, ExtraTreeTexture.Gui);
	float oldProgress;
	float animation;

	protected ControlLumbermillProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 66, 18);
		this.oldProgress = 0;
		this.animation = 0;
		new Panel(this, 0, 0, 66, 18, MinecraftGUI.PanelType.Black);
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.oldProgress != this.progress) {
			this.oldProgress = this.progress;
			this.animation += 5;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		GlStateManager.disableLighting();
		final int sawX = (int) (63 * this.progress);
		CraftGUI.render.texture(ControlLumbermillProgress.Saw, new Point(sawX, -8 + Math.round(6 * (float) Math.sin(this.animation))));
		final ItemStack item = Window.get(this).getInventory().getStackInSlot(LumbermillMachine.SLOT_LOG);
		if (item.isEmpty()) {
			return;
		}
		GlStateManager.disableLighting();
		Block block = null;
		if (item.getItem() instanceof ItemBlock) {
			block = ((ItemBlock) item.getItem()).getBlock();
		}
		if (block == null) {
			return;
		}
		//TODO RENDERING
		//final IIcon icon = block.getIcon(2, item.getItemDamage());
		for (int i = 0; i < 4; ++i) {
			//CraftGUI.Render.iconBlock(new IPoint(1 + i * 16, 1), icon);
		}
		final ItemStack result = LumbermillRecipes.getPlankProduct(item);
		if (result == null) {
			return;
		}
		Block block2 = null;
		if (item.getItem() instanceof ItemBlock) {
			block2 = ((ItemBlock) result.getItem()).getBlock();
		}
		if (block2 == null) {
			return;
		}
		//final IIcon icon2 = block2.getIcon(2, result.getItemDamage());
		final Point size = this.getSize();
		final Point pos = this.getAbsolutePosition();
		CraftGUI.render.limitArea(new Area(pos.add(Point.ZERO), new Point(Math.round(this.progress * 64) + 2, 18)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		for (int j = 0; j < 4; ++j) {
			//TODO RENDERING
			//CraftGUI.Render.iconBlock(new IPoint(1 + j * 16, 1), icon2);
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		CraftGUI.render.texture(ControlLumbermillProgress.Saw2, new Point(sawX + 2, -8 + Math.round(6 * (float) Math.sin(this.animation))));
	}
}
