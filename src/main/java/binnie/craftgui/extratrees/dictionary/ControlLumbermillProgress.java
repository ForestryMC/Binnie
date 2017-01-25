package binnie.craftgui.extratrees.dictionary;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.craftgui.window.Panel;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.machines.lumbermill.LumbermillRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ControlLumbermillProgress extends ControlProgressBase {
	float oldProgress;
	float animation;
	static Texture Saw = new StandardTexture(0, 0, 6, 32, ExtraTreeTexture.Gui);
	static Texture Saw2 = new StandardTexture(2, 0, 4, 32, ExtraTreeTexture.Gui);

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.oldProgress != this.progress) {
			this.oldProgress = this.progress;
			this.animation += 5;
		}
	}

	@Override
	public void onRenderForeground(int guiWidth, int guiHeight) {
		GL11.glDisable(2896);
		final int sawX = (int) (63 * this.progress);
		CraftGUI.render.texture(ControlLumbermillProgress.Saw, new IPoint(sawX, -8 + Math.round(6 * (float) Math.sin(this.animation))));
		final ItemStack item = Window.get(this).getInventory().getStackInSlot(LumbermillMachine.SLOT_LOG);
		if (item == null) {
			return;
		}
		GL11.glDisable(2896);
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
		final IPoint size = this.getSize();
		final IPoint pos = this.getAbsolutePosition();
		CraftGUI.render.limitArea(new IArea(pos.add(IPoint.ZERO), new IPoint(Math.round(this.progress * 64) + 2, 18)), guiWidth, guiHeight);
		GL11.glEnable(3089);
		for (int j = 0; j < 4; ++j) {
			//TODO RENDERING
			//CraftGUI.Render.iconBlock(new IPoint(1 + j * 16, 1), icon2);
		}
		GL11.glDisable(3089);
		CraftGUI.render.texture(ControlLumbermillProgress.Saw2, new IPoint(sawX + 2, -8 + Math.round(6 * (float) Math.sin(this.animation))));
	}

	protected ControlLumbermillProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 66, 18);
		this.oldProgress = 0;
		this.animation = 0;
		new Panel(this, 0, 0, 66, 18, MinecraftGUI.PanelType.Black);
	}

}
