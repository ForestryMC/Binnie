package binnie.extratrees.machines;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelNursery extends ModelBase {
	private ModelRenderer shape1;
	private ModelRenderer shape2;
	private ModelRenderer shape3;
	private ModelRenderer shape4;
	private ModelRenderer shape5;
	private ModelRenderer shape6;
	private ModelRenderer shape7;
	private ModelRenderer shape8;
	private ModelRenderer shape9;
	private ModelRenderer shape10;
	private ModelRenderer shape11;
	private ModelRenderer shape12;
	private ModelRenderer shape13;

	public ModelNursery() {
		textureWidth = 64;
		textureHeight = 64;

		shape1 = new ModelRenderer(this, 0, 0);
		shape1.addBox(-5.0f, -5.0f, -5.0f, 10, 10, 10);
		shape1.setRotationPoint(0.0f, 16.0f, 0.0f);
		shape1.setTextureSize(64, 64);
		shape1.mirror = true;
		setRotation(shape1, 0.0f, 0.0f, 0.0f);

		shape2 = new ModelRenderer(this, 0, 20);
		shape2.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape2.setRotationPoint(-5.0f, 16.0f, -5.0f);
		shape2.setTextureSize(64, 64);
		shape2.mirror = true;
		setRotation(shape2, 0.0f, 0.0f, 0.0f);

		shape3 = new ModelRenderer(this, 0, 20);
		shape3.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape3.setRotationPoint(-5.0f, 16.0f, 5.0f);
		shape3.setTextureSize(64, 64);
		shape3.mirror = true;
		setRotation(shape3, 0.0f, 0.0f, 0.0f);

		shape4 = new ModelRenderer(this, 0, 20);
		shape4.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape4.setRotationPoint(5.0f, 16.0f, -5.0f);
		shape4.setTextureSize(64, 64);
		shape4.mirror = true;
		setRotation(shape4, 0.0f, 0.0f, 0.0f);

		shape5 = new ModelRenderer(this, 0, 20);
		shape5.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape5.setRotationPoint(5.0f, 16.0f, 5.0f);
		shape5.setTextureSize(64, 64);
		shape5.mirror = true;
		setRotation(shape5, 0.0f, 0.0f, 0.0f);

		shape6 = new ModelRenderer(this, 0, 20);
		shape6.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape6.setRotationPoint(5.0f, 21.0f, 0.0f);
		shape6.setTextureSize(64, 64);
		shape6.mirror = true;
		setRotation(shape6, 1.570796f, 0.0f, 0.0f);

		shape7 = new ModelRenderer(this, 0, 20);
		shape7.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape7.setRotationPoint(5.0f, 11.0f, 0.0f);
		shape7.setTextureSize(64, 64);
		shape7.mirror = true;
		setRotation(shape7, 1.570796f, 0.0f, 0.0f);

		shape8 = new ModelRenderer(this, 0, 20);
		shape8.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape8.setRotationPoint(-5.0f, 21.0f, 0.0f);
		shape8.setTextureSize(64, 64);
		shape8.mirror = true;
		setRotation(shape8, 1.570796f, 0.0f, 0.0f);

		shape9 = new ModelRenderer(this, 0, 20);
		shape9.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape9.setRotationPoint(-5.0f, 11.0f, 0.0f);
		shape9.setTextureSize(64, 64);
		shape9.mirror = true;
		setRotation(shape9, 1.570796f, 0.0f, 0.0f);

		shape10 = new ModelRenderer(this, 0, 20);
		shape10.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape10.setRotationPoint(0.0f, 11.0f, 5.0f);
		shape10.setTextureSize(64, 64);
		shape10.mirror = true;
		setRotation(shape10, 0.0f, 0.0f, 1.570796f);

		shape11 = new ModelRenderer(this, 0, 20);
		shape11.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape11.setRotationPoint(0.0f, 21.0f, -5.0f);
		shape11.setTextureSize(64, 64);
		shape11.mirror = true;
		setRotation(shape11, 0.0f, 0.0f, 1.570796f);

		shape12 = new ModelRenderer(this, 0, 20);
		shape12.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape12.setRotationPoint(0.0f, 21.0f, 5.0f);
		shape12.setTextureSize(64, 64);
		shape12.mirror = true;
		setRotation(shape12, 0.0f, 0.0f, 1.570796f);

		shape13 = new ModelRenderer(this, 0, 20);
		shape13.addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		shape13.setRotationPoint(0.0f, 11.0f, -5.0f);
		shape13.setTextureSize(64, 64);
		shape13.mirror = true;
		setRotation(shape13, 0.0f, 0.0f, 1.570796f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		shape1.render(f5);
		shape2.render(f5);
		shape3.render(f5);
		shape4.render(f5);
		shape5.render(f5);
		shape6.render(f5);
		shape7.render(f5);
		shape8.render(f5);
		shape9.render(f5);
		shape10.render(f5);
		shape11.render(f5);
		shape12.render(f5);
		shape13.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
