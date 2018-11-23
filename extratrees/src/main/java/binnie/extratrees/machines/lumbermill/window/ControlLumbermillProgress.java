package binnie.extratrees.machines.lumbermill.window;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlProgressBase;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.window.Panel;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.lumbermill.LumbermillMachine;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;

import org.lwjgl.opengl.GL11;

public class ControlLumbermillProgress extends ControlProgressBase {
	private static final Texture SAW = new StandardTexture(0, 0, 6, 32, ExtraTreeTexture.GUI);
	private static final Texture SAW_2 = new StandardTexture(2, 0, 4, 32, ExtraTreeTexture.GUI);
	private float oldProgress;
	private float animation;

	protected ControlLumbermillProgress(IWidget parent, int x, int y) {
		super(parent, x, y, 66, 18);
		this.oldProgress = 0;
		this.animation = 0;
		new Panel(this, 0, 0, 66, 18, MinecraftGUI.PanelType.BLACK);
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
		int sawX = (int) (63 * this.progress);
		CraftGUI.RENDER.texture(ControlLumbermillProgress.SAW, new Point(sawX, -8 + Math.round(6 * (float) Math.sin(this.animation))));
		ItemStack item = Window.get(this).getInventory().getStackInSlot(LumbermillMachine.SLOT_LOG);
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
		TextureAtlasSprite icon = getWoodSprite(item);
		for (int i = 0; i < 4; ++i) {
			//CraftGUI.Render.iconBlock(new IPoint(1 + i * 16, 1), icon);
			RenderUtil.drawSprite(new Point(1 + i * 16, 1), icon);
		}
		ItemStack result = LumbermillRecipeManager.getPlankProduct(item, Minecraft.getMinecraft().world);
		if (result.isEmpty()) {
			return;
		}
		Block block2 = null;
		if (result.getItem() instanceof ItemBlock) {
			block2 = ((ItemBlock) result.getItem()).getBlock();
		}
		if (block2 == null) {
			return;
		}
		//final IIcon icon2 = block2.getIcon(2, result.getItemDamage());
		TextureAtlasSprite sprite = getWoodSprite(result);
		IPoint pos = this.getAbsolutePosition();
		CraftGUI.RENDER.limitArea(new Area(pos.add(Point.ZERO), new Point(Math.round(this.progress * 64) + 2, 18)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		for (int j = 0; j < 4; ++j) {
			//TODO RENDERING
			RenderUtil.drawSprite(new Point(1 + j * 16, 1), sprite);
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		CraftGUI.RENDER.texture(ControlLumbermillProgress.SAW, new Point(sawX, -8 + Math.round(6 * (float) Math.sin(this.animation))));
		CraftGUI.RENDER.texture(ControlLumbermillProgress.SAW_2, new Point(sawX + 2, -8 + Math.round(6 * (float) Math.sin(this.animation))));
	}

	@SideOnly(Side.CLIENT)
	private TextureAtlasSprite getWoodSprite(ItemStack stack) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ItemModelMesher modelMesher = renderItem.getItemModelMesher();
		IBakedModel model = modelMesher.getItemModel(stack);
		TextureAtlasSprite sprite = model.getParticleTexture();
		if (sprite == mc.getTextureMapBlocks().getMissingSprite()) {
			return getWoodSprite(new ItemStack(Blocks.LOG));
		}
		return sprite;
	}
}
