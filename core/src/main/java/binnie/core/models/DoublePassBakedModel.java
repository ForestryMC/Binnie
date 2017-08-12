package binnie.core.models;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DoublePassBakedModel implements IBakedModel {
	private static final int c = DefaultVertexFormats.ITEM.getColorOffset() / 4;
	private static final int v = DefaultVertexFormats.ITEM.getNextOffset() / 4;
	private IBakedModel mainModel;
	private int primaryColor = 0xFF000000;
	private int secondaryColor = 0xFF000000;

	/**
	 * It colorizes the quads according to tintindex, which is defined in the json file.
	 *
	 * @param mainModel      old model
	 * @param primaryColor   color for tintindex 0
	 * @param secondaryColor color for tintindex 1
	 */
	public DoublePassBakedModel(IBakedModel mainModel, int primaryColor, int secondaryColor) {
		this.mainModel = mainModel;
		this.primaryColor |= (Integer.reverseBytes(primaryColor) >> 8);
		this.secondaryColor |= (Integer.reverseBytes(secondaryColor) >> 8);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		List<BakedQuad> quads = mainModel.getQuads(state, side, rand);
		List<BakedQuad> recolored = new LinkedList<>();
		quads.forEach(quad -> {
			if (quad.hasTintIndex()) {
				if (quad.getTintIndex() == 0)
					recolored.add(recolorQuad(quad, primaryColor));
				else
					recolored.add(recolorQuad(quad, secondaryColor));
			} else {
				recolored.add(quad);
			}
		});

		return recolored;
	}

	public BakedQuad recolorQuad(BakedQuad quad, int color) {
		int[] vertexData = quad.getVertexData();
		for (int i = 0; i < 4; i++) {
			vertexData[v * i + c] = color;
		}
		return quad;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return mainModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return mainModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return mainModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return mainModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return mainModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return mainModel.getOverrides();
	}
}
