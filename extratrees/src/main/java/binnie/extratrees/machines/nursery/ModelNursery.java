package binnie.extratrees.machines.nursery;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelNursery extends ModelBase {
	private final ModelRenderer Shape1;
	private final ModelRenderer Shape2;
	private final ModelRenderer Shape3;
	private final ModelRenderer Shape4;
	private final ModelRenderer Shape5;
	private final ModelRenderer Shape6;
	private final ModelRenderer Shape7;
	private final ModelRenderer Shape8;
	private final ModelRenderer Shape9;
	private final ModelRenderer Shape10;
	private final ModelRenderer Shape11;
	private final ModelRenderer Shape12;
	private final ModelRenderer Shape13;

	public ModelNursery() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		(this.Shape1 = new ModelRenderer(this, 0, 0)).addBox(-5.0f, -5.0f, -5.0f, 10, 10, 10);
		this.Shape1.setRotationPoint(0.0f, 16.0f, 0.0f);
		this.Shape1.setTextureSize(64, 64);
		this.Shape1.mirror = true;
		this.setRotation(this.Shape1, 0.0f, 0.0f, 0.0f);
		(this.Shape2 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape2.setRotationPoint(-5.0f, 16.0f, -5.0f);
		this.Shape2.setTextureSize(64, 64);
		this.Shape2.mirror = true;
		this.setRotation(this.Shape2, 0.0f, 0.0f, 0.0f);
		(this.Shape3 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape3.setRotationPoint(-5.0f, 16.0f, 5.0f);
		this.Shape3.setTextureSize(64, 64);
		this.Shape3.mirror = true;
		this.setRotation(this.Shape3, 0.0f, 0.0f, 0.0f);
		(this.Shape4 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape4.setRotationPoint(5.0f, 16.0f, -5.0f);
		this.Shape4.setTextureSize(64, 64);
		this.Shape4.mirror = true;
		this.setRotation(this.Shape4, 0.0f, 0.0f, 0.0f);
		(this.Shape5 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape5.setRotationPoint(5.0f, 16.0f, 5.0f);
		this.Shape5.setTextureSize(64, 64);
		this.Shape5.mirror = true;
		this.setRotation(this.Shape5, 0.0f, 0.0f, 0.0f);
		(this.Shape6 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape6.setRotationPoint(5.0f, 21.0f, 0.0f);
		this.Shape6.setTextureSize(64, 64);
		this.Shape6.mirror = true;
		this.setRotation(this.Shape6, 1.570796f, 0.0f, 0.0f);
		(this.Shape7 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape7.setRotationPoint(5.0f, 11.0f, 0.0f);
		this.Shape7.setTextureSize(64, 64);
		this.Shape7.mirror = true;
		this.setRotation(this.Shape7, 1.570796f, 0.0f, 0.0f);
		(this.Shape8 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape8.setRotationPoint(-5.0f, 21.0f, 0.0f);
		this.Shape8.setTextureSize(64, 64);
		this.Shape8.mirror = true;
		this.setRotation(this.Shape8, 1.570796f, 0.0f, 0.0f);
		(this.Shape9 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape9.setRotationPoint(-5.0f, 11.0f, 0.0f);
		this.Shape9.setTextureSize(64, 64);
		this.Shape9.mirror = true;
		this.setRotation(this.Shape9, 1.570796f, 0.0f, 0.0f);
		(this.Shape10 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape10.setRotationPoint(0.0f, 11.0f, 5.0f);
		this.Shape10.setTextureSize(64, 64);
		this.Shape10.mirror = true;
		this.setRotation(this.Shape10, 0.0f, 0.0f, 1.570796f);
		(this.Shape11 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape11.setRotationPoint(0.0f, 21.0f, -5.0f);
		this.Shape11.setTextureSize(64, 64);
		this.Shape11.mirror = true;
		this.setRotation(this.Shape11, 0.0f, 0.0f, 1.570796f);
		(this.Shape12 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape12.setRotationPoint(0.0f, 21.0f, 5.0f);
		this.Shape12.setTextureSize(64, 64);
		this.Shape12.mirror = true;
		this.setRotation(this.Shape12, 0.0f, 0.0f, 1.570796f);
		(this.Shape13 = new ModelRenderer(this, 0, 20)).addBox(-1.0f, -8.0f, -1.0f, 2, 16, 2);
		this.Shape13.setRotationPoint(0.0f, 11.0f, -5.0f);
		this.Shape13.setTextureSize(64, 64);
		this.Shape13.mirror = true;
		this.setRotation(this.Shape13, 0.0f, 0.0f, 1.570796f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
		this.Shape4.render(f5);
		this.Shape5.render(f5);
		this.Shape6.render(f5);
		this.Shape7.render(f5);
		this.Shape8.render(f5);
		this.Shape9.render(f5);
		this.Shape10.render(f5);
		this.Shape11.render(f5);
		this.Shape12.render(f5);
		this.Shape13.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
