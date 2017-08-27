package binnie.core.models;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

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

import org.lwjgl.util.vector.Vector3f;

import forestry.api.core.IModelBaker;
import forestry.api.core.IModelBakerModel;
import forestry.core.models.ModelManager;
import forestry.core.models.baker.ModelBakerFace;
import forestry.core.models.baker.ModelBakerModel;

//AABB = AxisAlignedBoundingBox
@SideOnly(Side.CLIENT)
public class AABBModelBaker implements IModelBaker {
	protected final FaceBakery faceBakery = new FaceBakery();
	private final List<BoundModelBakerFace> faces = new ArrayList<>();
	private final List<Pair<IBlockState, IBakedModel>> bakedModels = new ArrayList<>();
	protected AxisAlignedBB modelBounds;
	protected final ModelBakerModel currentModel = new ModelBakerModel(ModelManager.getInstance().getDefaultBlockState());
	protected int colorIndex = -1;

	public AABBModelBaker() {
		this(Block.FULL_BLOCK_AABB);
	}

	public AABBModelBaker(AxisAlignedBB modelBounds) {
		this.modelBounds = modelBounds;
	}

	public void setModelBounds(AxisAlignedBB modelBounds) {
		this.modelBounds = modelBounds;
	}

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

	protected float[] getFaceUvs(EnumFacing face, Vector3f to, Vector3f from) {
		float minU;
		float minV;
		float maxU;
		float maxV;
		switch (face) {
			case SOUTH: {
				minU = from.x;
				minV = from.y;
				maxU = to.x;
				maxV = to.y;
				break;
			}
			case NORTH: {
				minU = from.x;
				minV = from.y;
				maxU = to.x;
				maxV = to.y;
				break;
			}
			case WEST: {
				minU = from.z;
				minV = from.y;
				maxU = to.z;
				maxV = to.y;
				break;
			}
			case EAST: {
				minU = from.z;
				minV = from.y;
				maxU = to.z;
				maxV = to.y;
				break;
			}
			case UP: {
				minU = from.x;
				minV = from.z;
				maxU = to.x;
				maxV = to.z;
				break;
			}
			case DOWN: {
				minU = from.x;
				minV = from.z;
				maxU = to.x;
				maxV = to.z;
				break;
			}
			default: {
				minU = 0;
				minV = 0;
				maxU = 16;
				maxV = 16;
				break;
			}
		}
		if (minU < 0 || maxU > 16) {
			minU = 0;
			maxU = 16;
		}
		if (minV < 0 || maxV > 16) {
			minV = 0;
			maxV = 16;
		}
		minU = 16 - minU;
		minV = 16 - minV;
		maxU = 16 - maxU;
		maxV = 16 - maxV;
		return new float[]{
			minU,
			minV,

			maxU,
			maxV
		};
	}

	@Override
	public void addFace(EnumFacing facing, TextureAtlasSprite sprite) {
		if (sprite == null) {
			return;
		}

		faces.add(new BoundModelBakerFace(facing, colorIndex, sprite, modelBounds));
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

		for (BoundModelBakerFace face : faces) {
			final AxisAlignedBB modelBounds = face.modelBounds;
			final Vector3f from = new Vector3f((float) modelBounds.minX * 16.0f, (float) modelBounds.minY * 16.0f, (float) modelBounds.minZ * 16.0f);
			final Vector3f to = new Vector3f((float) modelBounds.maxX * 16.0f, (float) modelBounds.maxY * 16.0f, (float) modelBounds.maxZ * 16.0f);
			final EnumFacing myFace = face.face;
			final float[] uvs = getFaceUvs(myFace, to, from);

			final BlockFaceUV uv = new BlockFaceUV(uvs, 0);
			final BlockPartFace bpf = new BlockPartFace(myFace, face.colorIndex, "", uv);

			BakedQuad bf = faceBakery.makeBakedQuad(from, to, bpf, face.spite, myFace, mr, null, true, true);

			currentModel.addQuad(myFace, bf);
		}

		return currentModel;
	}

	@Override
	public void setModelState(@Nullable IModelState modelState) {
		currentModel.setModelState(modelState);
	}

	@Override
	public void setParticleSprite(TextureAtlasSprite particleSprite) {
		currentModel.setParticleSprite(particleSprite);
	}

	private static class BoundModelBakerFace extends ModelBakerFace {

		protected final AxisAlignedBB modelBounds;

		public BoundModelBakerFace(EnumFacing face, int colorIndex, TextureAtlasSprite sprite, AxisAlignedBB modelBounds) {
			super(face, colorIndex, sprite);

			this.modelBounds = modelBounds;
		}
	}
}
