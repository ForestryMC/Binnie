package binnie.genetics.machine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMachine extends ModelBase {
	ModelRenderer Base;
	ModelRenderer Body;
	ModelRenderer Arm1;
	ModelRenderer Arm2;
	ModelRenderer Arm3;
	ModelRenderer Arm4;
	ModelRenderer Top;

	public ModelMachine() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		(this.Base = new ModelRenderer(this, 0, 18)).addBox(0.0f, 0.0f, 0.0f, 16, 4, 16);
		this.Base.setRotationPoint(-8.0f, 20.0f, -8.0f);
		this.Base.setTextureSize(64, 64);
		this.Base.mirror = true;
		this.setRotation(this.Base, 0.0f, 0.0f, 0.0f);
		(this.Body = new ModelRenderer(this, 0, 26)).addBox(0.0f, 0.0f, 0.0f, 12, 7, 12);
		this.Body.setRotationPoint(-6.0f, 13.0f, -6.0f);
		this.Body.setTextureSize(64, 64);
		this.Body.mirror = true;
		this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
		(this.Arm1 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
		this.Arm1.setRotationPoint(5.0f, 8.0f, 5.0f);
		this.Arm1.setTextureSize(64, 64);
		this.Arm1.mirror = true;
		this.setRotation(this.Arm1, 0.0f, 0.0f, 0.0f);
		(this.Arm2 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
		this.Arm2.setRotationPoint(-5.0f, 8.0f, 5.0f);
		this.Arm2.setTextureSize(64, 64);
		this.Arm2.mirror = true;
		this.setRotation(this.Arm2, 0.0f, -1.57075f, 0.0f);
		(this.Arm3 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
		this.Arm3.setRotationPoint(-5.0f, 8.0f, -5.0f);
		this.Arm3.setTextureSize(64, 64);
		this.Arm3.mirror = true;
		this.setRotation(this.Arm3, 0.0f, -3.1415f, 0.0f);
		(this.Arm4 = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
		this.Arm4.setRotationPoint(5.0f, 8.0f, -5.0f);
		this.Arm4.setTextureSize(64, 64);
		this.Arm4.mirror = true;
		this.setRotation(this.Arm4, 0.0f, -4.7122498f, 0.0f);
		(this.Top = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 14, 4, 14);
		this.Top.setRotationPoint(-7.0f, 9.0f, -7.0f);
		this.Top.setTextureSize(64, 64);
		this.Top.mirror = true;
		this.setRotation(this.Top, 0.0f, 0.0f, 0.0f);
	}

//	public void render(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
//		super.render((Entity) null, f, f1, f2, f3, f4, f5);
//		this.setRotationAngles(f, f1, f2, f3, f4, f5);
//		this.Base.render(f5);
//		this.Body.render(f5);
//		this.Arm1.render(f5);
//		this.Arm2.render(f5);
//		this.Arm3.render(f5);
//		this.Arm4.render(f5);
//		this.Top.render(f5);
//	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}
