package binnie.core.models;

import forestry.api.core.IModelBaker;
import forestry.api.core.IModelBakerModel;
import forestry.core.models.ModelManager;
import forestry.core.models.baker.ModelBakerFace;
import forestry.core.models.baker.ModelBakerModel;
import forestry.core.proxy.Proxies;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class PanelModelBaker implements IModelBaker {

	private final float[] quadsUV;
	private final List<ModelBakerFace> faces = new ArrayList<>();
	private final List<Pair<IBlockState, IBakedModel>> bakedModels = new ArrayList<>();
	protected AxisAlignedBB renderBounds;

	protected ModelBakerModel currentModel = new ModelBakerModel(ModelManager.getInstance().getDefaultBlockState());

	protected final FaceBakery faceBakery = new FaceBakery();

	protected final float[] defUVs;

	public PanelModelBaker() {
		this(Block.FULL_BLOCK_AABB);
	}
	
	public PanelModelBaker(AxisAlignedBB renderBounds) {
		quadsUV = new float[]{0, 0, 1, 1, 0, 0, 1, 1};
		defUVs = new float[]{0, 0, 1, 1};
		this.renderBounds = renderBounds;
	}

	protected int colorIndex = -1;

	@Override
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	@Override
	public void addModel(TextureAtlasSprite[] textures, int colorIndex) {
		setColorIndex(colorIndex);

		for (EnumFacing facing : EnumFacing.VALUES) {
			addFace(facing, textures[facing.ordinal()]);
		}
	}

	@Override
	public void addModel(TextureAtlasSprite texture, int colorIndex) {
		addModel(new TextureAtlasSprite[]{texture, texture, texture, texture, texture, texture}, colorIndex);
	}

	@Override
	public void addBlockModel(@Nullable BlockPos pos, TextureAtlasSprite[] sprites, int colorIndex) {
		setColorIndex(colorIndex);

		if (pos != null) {
			World world = Minecraft.getMinecraft().world;
			IBlockState blockState = world.getBlockState(pos);
			for (EnumFacing facing : EnumFacing.VALUES) {
				if (blockState.shouldSideBeRendered(world, pos, facing)) {
					addFace(facing, sprites[facing.ordinal()]);
				}
			}
		} else {
			for (EnumFacing facing : EnumFacing.VALUES) {
				addFace(facing, sprites[facing.ordinal()]);
			}
		}

	}

	@Override
	public void addBlockModel(@Nullable BlockPos pos, TextureAtlasSprite sprite, int colorIndex) {
		addBlockModel(pos, new TextureAtlasSprite[]{sprite, sprite, sprite, sprite, sprite, sprite}, colorIndex);
	}

	@Override
	public void addBakedModel(@Nullable IBlockState state, IBakedModel model) {
		if (model != null) {
			this.bakedModels.add(Pair.of(state, model));
		}
	}

	protected float[] getFaceUvs(final EnumFacing face, final Vector3f to_16, final Vector3f from_16) {
		float from_a = 0;
		float from_b = 0;
		float to_a = 0;
		float to_b = 0;

		switch (face) {
			case UP:
				from_a = from_16.x / 16.0f;
				from_b = from_16.z / 16.0f;
				to_a = to_16.x / 16.0f;
				to_b = to_16.z / 16.0f;
				break;
			case DOWN:
				from_a = from_16.x / 16.0f;
				from_b = from_16.z / 16.0f;
				to_a = to_16.x / 16.0f;
				to_b = to_16.z / 16.0f;
				break;
			case SOUTH:
				from_a = from_16.y / 16.0f;
				from_b = from_16.x / 16.0f;
				to_a = to_16.y / 16.0f;
				to_b = to_16.x / 16.0f;
				break;
			case NORTH:
				from_a = from_16.y / 16.0f;
				from_b = from_16.x / 16.0f;
				to_a = to_16.y / 16.0f;
				to_b = to_16.x / 16.0f;
				break;
			case EAST:
				from_a = from_16.y / 16.0f;
				from_b = from_16.z / 16.0f;
				to_a = to_16.y / 16.0f;
				to_b = to_16.z / 16.0f;
				break;
			case WEST:
				from_a = from_16.y / 16.0f;
				from_b = from_16.z / 16.0f;
				to_a = to_16.y / 16.0f;
				to_b = to_16.z / 16.0f;
				break;
			default:
		}

		from_a = 1.0f - from_a;
		from_b = 1.0f - from_b;
		to_a = 1.0f - to_a;
		to_b = 1.0f - to_b;

		return new float[]{ // :P
				16.0f * (quadsUV[0] + quadsUV[2] * from_a + quadsUV[4] * from_b), // 0
				16.0f * (quadsUV[1] + quadsUV[3] * from_a + quadsUV[5] * from_b), // 1

				16.0f * (quadsUV[0] + quadsUV[2] * to_a + quadsUV[4] * from_b), // 2
				16.0f * (quadsUV[1] + quadsUV[3] * to_a + quadsUV[5] * from_b), // 3

				16.0f * (quadsUV[0] + quadsUV[2] * to_a + quadsUV[4] * to_b), // 2
				16.0f * (quadsUV[1] + quadsUV[3] * to_a + quadsUV[5] * to_b), // 3

				16.0f * (quadsUV[0] + quadsUV[2] * from_a + quadsUV[4] * to_b), // 0
				16.0f * (quadsUV[1] + quadsUV[3] * from_a + quadsUV[5] * to_b), // 1
		};
	}

	@Override
	public void addFace(EnumFacing facing, TextureAtlasSprite sprite) {
		if (sprite == null) {
			return;
		}

		faces.add(new ModelBakerFace(facing, colorIndex, sprite));
	}

	@Override
	public IModelBakerModel bakeModel(boolean flip) {
		ModelRotation mr = ModelRotation.X0_Y0;

		if (flip) {
			mr = ModelRotation.X0_Y180;
		}

		//Add baked models to the current model.
		for (Pair<IBlockState, IBakedModel> bakedModel : bakedModels) {
			currentModel.addModelQuads(bakedModel);
		}

		Vector3f to = new Vector3f((float) renderBounds.minX * 16.0f, (float) renderBounds.minY * 16.0f, (float) renderBounds.minZ * 16.0f);
		Vector3f from = new Vector3f((float) renderBounds.maxX * 16.0f, (float) renderBounds.maxY * 16.0f, (float) renderBounds.maxZ * 16.0f);

		for (ModelBakerFace face : faces) {

			final EnumFacing myFace = face.face;
			final float[] uvs = getFaceUvs(myFace, from, to);

			final BlockFaceUV uv = new BlockFaceUV(uvs, 0);
			final BlockPartFace bpf = new BlockPartFace(myFace, face.colorIndex, "", uv);

			BakedQuad bf = faceBakery.makeBakedQuad(to, from, bpf, face.spite, myFace, mr, null, true, true);

			currentModel.addQuad(myFace, bf);
		}

		return currentModel;
	}

	@Override
	public void setModelState(IModelState modelState) {
		currentModel.setModelState(modelState);
	}

	@Override
	public void setParticleSprite(TextureAtlasSprite particleSprite) {
		currentModel.setParticleSprite(particleSprite);
	}

}
