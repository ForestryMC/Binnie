package binnie.core.block;

/*
 * Copyright (c) 2015-2016 Adrian Siekierka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.util.EnumMap;

@SideOnly(Side.CLIENT)
public abstract class BaseBakedModel implements IPerspectiveAwareModel {
	private static final TRSRTransformation flipX = new TRSRTransformation(null, null, new Vector3f(-1, 1, 1), null);
	private final EnumMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = new EnumMap<>(ItemCameraTransforms.TransformType.class);
	private final ResourceLocation particle;

	public BaseBakedModel() {
		this(null);
	}

	public BaseBakedModel(@Nullable ResourceLocation particle) {
		this.particle = particle != null ? particle : TextureMap.LOCATION_MISSING_TEXTURE;
	}

	protected static TRSRTransformation toLeftHand(TRSRTransformation transform) {
		return TRSRTransformation.blockCenterToCorner(flipX.compose(TRSRTransformation.blockCornerToCenter(transform)).compose(flipX));
	}

	protected static TRSRTransformation getTransformation(float tx, float ty, float tz, float ax, float ay, float az, float s) {
		return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
			new Vector3f(tx / 16, ty / 16, tz / 16),
			TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)),
			new Vector3f(s, s, s),
			null
		));
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		return ImmutablePair.of(this,
			transformMap.containsKey(cameraTransformType) ? transformMap.get(cameraTransformType).getMatrix() : null
		);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(particle.toString());
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	// ForgeBlockStateV1 transforms

	public void addTransformation(ItemCameraTransforms.TransformType type, TRSRTransformation transformation) {
		transformMap.put(type, TRSRTransformation.blockCornerToCenter(transformation));
	}

	public void addThirdPersonTransformation(TRSRTransformation transformation) {
		addTransformation(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, transformation);
		addTransformation(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, toLeftHand(transformation));
	}

	public void addFirstPersonTransformation(TRSRTransformation transformation) {
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, transformation);
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, toLeftHand(transformation));
	}

	public BaseBakedModel addDefaultBlockTransforms() {
		TRSRTransformation thirdperson = getTransformation(0, 2.5f, 0, 75, 45, 0, 0.375f);
		addTransformation(ItemCameraTransforms.TransformType.GUI, getTransformation(0, 0, 0, 30, 225, 0, 0.625f));
		addTransformation(ItemCameraTransforms.TransformType.GROUND, getTransformation(0, 3, 0, 0, 0, 0, 0.25f));
		addTransformation(ItemCameraTransforms.TransformType.FIXED, getTransformation(0, 0, 0, 0, 0, 0, 0.5f));
		addThirdPersonTransformation(thirdperson);
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, getTransformation(0, 0, 0, 0, 45, 0, 0.4f));
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, getTransformation(0, 0, 0, 0, 255, 0, 0.4f));
		return this;
	}

	public BaseBakedModel addDefaultItemTransforms() {
		TRSRTransformation thirdperson = getTransformation(0, 3, 1, 0, 0, 0, 0.55f);
		TRSRTransformation firstperson = getTransformation(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f);
		addTransformation(ItemCameraTransforms.TransformType.GROUND, getTransformation(0, 2, 0, 0, 0, 0, 0.5f));
		addTransformation(ItemCameraTransforms.TransformType.HEAD, getTransformation(0, 13, 7, 0, 180, 0, 1));
		addThirdPersonTransformation(thirdperson);
		addFirstPersonTransformation(firstperson);
		return this;
	}

	public BaseBakedModel addDefaultToolTransforms() {
		addTransformation(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, getTransformation(0, 4, 0.5f, 0, -90, 55, 0.85f));
		addTransformation(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, getTransformation(0, 4, 0.5f, 0, 90, -55, 0.85f));
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, getTransformation(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
		addTransformation(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, getTransformation(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
		return this;
	}
}
