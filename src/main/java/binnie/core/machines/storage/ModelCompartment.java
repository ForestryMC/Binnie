package binnie.core.machines.storage;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

class ModelCompartment extends ModelBase {
	private ModelRenderer Column1;
	private ModelRenderer Column4;
	private ModelRenderer Column2;
	private ModelRenderer Column3;
	private ModelRenderer Lid_1;
	private ModelRenderer Body_1;
	private ModelRenderer Lock_1;
	private ModelRenderer Lid_2;
	private ModelRenderer Body_2;
	private ModelRenderer Lock_2;
	private ModelRenderer Body;
	private ModelRenderer Lid_3;
	private ModelRenderer Body_3;
	private ModelRenderer Lock_3;
	private ModelRenderer Lid_4;
	private ModelRenderer Body_4;
	private ModelRenderer Lock_4;

	public ModelCompartment() {
		this.textureWidth = 128;
		this.textureHeight = 128;
		(this.Column1 = new ModelRenderer(this, 0, 0)).addBox(-8.0f, 8.0f, -8.0f, 4, 16, 4);
		this.Column1.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Column1.setTextureSize(128, 128);
		this.Column1.mirror = true;
		this.setRotation(this.Column1, 0.0f, 0.0f, 0.0f);
		(this.Column4 = new ModelRenderer(this, 0, 0)).addBox(4.0f, 8.0f, -8.0f, 4, 16, 4);
		this.Column4.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Column4.setTextureSize(128, 128);
		this.Column4.mirror = true;
		this.setRotation(this.Column4, 0.0f, 0.0f, 0.0f);
		(this.Column2 = new ModelRenderer(this, 0, 0)).addBox(-8.0f, 8.0f, 4.0f, 4, 16, 4);
		this.Column2.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Column2.setTextureSize(128, 128);
		this.Column2.mirror = true;
		this.setRotation(this.Column2, 0.0f, 0.0f, 0.0f);
		(this.Column3 = new ModelRenderer(this, 0, 0)).addBox(4.0f, 8.0f, 4.0f, 4, 16, 4);
		this.Column3.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Column3.setTextureSize(128, 128);
		this.Column3.mirror = true;
		this.setRotation(this.Column3, 0.0f, 0.0f, 0.0f);
		(this.Lid_1 = new ModelRenderer(this, 48, 0)).addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
		this.Lid_1.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lid_1.setTextureSize(128, 128);
		this.Lid_1.mirror = true;
		this.setRotation(this.Lid_1, 0.0f, 0.0f, 0.0f);
		(this.Body_1 = new ModelRenderer(this, 0, 24)).addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
		this.Body_1.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Body_1.setTextureSize(128, 128);
		this.Body_1.mirror = true;
		this.setRotation(this.Body_1, 0.0f, 0.0f, 0.0f);
		(this.Lock_1 = new ModelRenderer(this, 22, 24)).addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
		this.Lock_1.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lock_1.setTextureSize(128, 128);
		this.Lock_1.mirror = true;
		this.setRotation(this.Lock_1, 0.0f, 0.0f, 0.0f);
		(this.Lid_2 = new ModelRenderer(this, 48, 0)).addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
		this.Lid_2.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lid_2.setTextureSize(128, 128);
		this.Lid_2.mirror = true;
		this.setRotation(this.Lid_2, 0.0f, 1.570796f, 0.0f);
		(this.Body_2 = new ModelRenderer(this, 0, 24)).addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
		this.Body_2.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Body_2.setTextureSize(128, 128);
		this.Body_2.mirror = true;
		this.setRotation(this.Body_2, 0.0f, 1.570796f, 0.0f);
		(this.Lock_2 = new ModelRenderer(this, 22, 24)).addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
		this.Lock_2.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lock_2.setTextureSize(128, 128);
		this.Lock_2.mirror = true;
		this.setRotation(this.Lock_2, 0.0f, 1.570796f, 0.0f);
		(this.Body = new ModelRenderer(this, 16, 0)).addBox(-4.0f, 8.0f, -4.0f, 8, 16, 8);
		this.Body.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Body.setTextureSize(128, 128);
		this.Body.mirror = true;
		this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
		(this.Lid_3 = new ModelRenderer(this, 48, 0)).addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
		this.Lid_3.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lid_3.setTextureSize(128, 128);
		this.Lid_3.mirror = true;
		this.setRotation(this.Lid_3, 0.0f, 3.141593f, 0.0f);
		(this.Body_3 = new ModelRenderer(this, 0, 24)).addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
		this.Body_3.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Body_3.setTextureSize(128, 128);
		this.Body_3.mirror = true;
		this.setRotation(this.Body_3, 0.0f, 3.141593f, 0.0f);
		(this.Lock_3 = new ModelRenderer(this, 22, 24)).addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
		this.Lock_3.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lock_3.setTextureSize(128, 128);
		this.Lock_3.mirror = true;
		this.setRotation(this.Lock_3, 0.0f, 3.141593f, 0.0f);
		(this.Lid_4 = new ModelRenderer(this, 48, 0)).addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
		this.Lid_4.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lid_4.setTextureSize(128, 128);
		this.Lid_4.mirror = true;
		this.setRotation(this.Lid_4, 0.0f, -1.570796f, 0.0f);
		(this.Body_4 = new ModelRenderer(this, 0, 24)).addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
		this.Body_4.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Body_4.setTextureSize(128, 128);
		this.Body_4.mirror = true;
		this.setRotation(this.Body_4, 0.0f, -1.570796f, 0.0f);
		(this.Lock_4 = new ModelRenderer(this, 22, 24)).addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
		this.Lock_4.setRotationPoint(0.0f, 0.0f, 0.0f);
		this.Lock_4.setTextureSize(128, 128);
		this.Lock_4.mirror = true;
		this.setRotation(this.Lock_4, 0.0f, -1.570796f, 0.0f);
	}

	@Override
	public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
		//		super.render(entity, f, f1, f2, f3, f4, f5);
		//		this.setRotationAngles(f, f1, f2, f3, f4, f5);
		//		this.Column1.render(f5);
		//		this.Column4.render(f5);
		//		this.Column2.render(f5);
		//		this.Column3.render(f5);
		//		this.Lid_1.render(f5);
		//		this.Body_1.render(f5);
		//		this.Lock_1.render(f5);
		//		this.Lid_2.render(f5);
		//		this.Body_2.render(f5);
		//		this.Lock_2.render(f5);
		//		this.Body.render(f5);
		//		this.Lid_3.render(f5);
		//		this.Body_3.render(f5);
		//		this.Lock_3.render(f5);
		//		this.Lid_4.render(f5);
		//		this.Body_4.render(f5);
		//		this.Lock_4.render(f5);
	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}
