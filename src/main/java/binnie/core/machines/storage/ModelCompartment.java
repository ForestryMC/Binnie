package binnie.core.machines.storage;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

class ModelCompartment extends ModelBase {
    private ModelRenderer column1;
    private ModelRenderer column4;
    private ModelRenderer column2;
    private ModelRenderer column3;
    private ModelRenderer lid1;
    private ModelRenderer body1;
    private ModelRenderer lock1;
    private ModelRenderer lid2;
    private ModelRenderer body2;
    private ModelRenderer lock2;
    private ModelRenderer body;
    private ModelRenderer lid3;
    private ModelRenderer body3;
    private ModelRenderer lock3;
    private ModelRenderer lid4;
    private ModelRenderer body4;
    private ModelRenderer lock4;

    public ModelCompartment() {
        textureWidth = 128;
        textureHeight = 128;

        column1 = new ModelRenderer(this, 0, 0);
        column1.addBox(-8.0f, 8.0f, -8.0f, 4, 16, 4);
        column1.setRotationPoint(0.0f, 0.0f, 0.0f);
        column1.setTextureSize(128, 128);
        column1.mirror = true;
        setRotation(column1, 0.0f, 0.0f, 0.0f);

        column4 = new ModelRenderer(this, 0, 0);
        column4.addBox(4.0f, 8.0f, -8.0f, 4, 16, 4);
        column4.setRotationPoint(0.0f, 0.0f, 0.0f);
        column4.setTextureSize(128, 128);
        column4.mirror = true;
        setRotation(column4, 0.0f, 0.0f, 0.0f);

        column2 = new ModelRenderer(this, 0, 0);
        column2.addBox(-8.0f, 8.0f, 4.0f, 4, 16, 4);
        column2.setRotationPoint(0.0f, 0.0f, 0.0f);
        column2.setTextureSize(128, 128);
        column2.mirror = true;
        setRotation(column2, 0.0f, 0.0f, 0.0f);

        column3 = new ModelRenderer(this, 0, 0);
        column3.addBox(4.0f, 8.0f, 4.0f, 4, 16, 4);
        column3.setRotationPoint(0.0f, 0.0f, 0.0f);
        column3.setTextureSize(128, 128);
        column3.mirror = true;
        setRotation(column3, 0.0f, 0.0f, 0.0f);

        lid1 = new ModelRenderer(this, 48, 0);
        lid1.addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
        lid1.setRotationPoint(0.0f, 0.0f, 0.0f);
        lid1.setTextureSize(128, 128);
        lid1.mirror = true;
        setRotation(lid1, 0.0f, 0.0f, 0.0f);

        body1 = new ModelRenderer(this, 0, 24);
        body1.addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
        body1.setRotationPoint(0.0f, 0.0f, 0.0f);
        body1.setTextureSize(128, 128);
        body1.mirror = true;
        setRotation(body1, 0.0f, 0.0f, 0.0f);

        lock1 = new ModelRenderer(this, 22, 24);
        lock1.addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
        lock1.setRotationPoint(0.0f, 0.0f, 0.0f);
        lock1.setTextureSize(128, 128);
        lock1.mirror = true;
        setRotation(lock1, 0.0f, 0.0f, 0.0f);

        lid2 = new ModelRenderer(this, 48, 0);
        lid2.addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
        lid2.setRotationPoint(0.0f, 0.0f, 0.0f);
        lid2.setTextureSize(128, 128);
        lid2.mirror = true;
        setRotation(lid2, 0.0f, 1.570796f, 0.0f);

        body2 = new ModelRenderer(this, 0, 24);
        body2.addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
        body2.setRotationPoint(0.0f, 0.0f, 0.0f);
        body2.setTextureSize(128, 128);
        body2.mirror = true;
        setRotation(body2, 0.0f, 1.570796f, 0.0f);

        lock2 = new ModelRenderer(this, 22, 24);
        lock2.addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
        lock2.setRotationPoint(0.0f, 0.0f, 0.0f);
        lock2.setTextureSize(128, 128);
        lock2.mirror = true;
        setRotation(lock2, 0.0f, 1.570796f, 0.0f);

        body = new ModelRenderer(this, 16, 0);
        body.addBox(-4.0f, 8.0f, -4.0f, 8, 16, 8);
        body.setRotationPoint(0.0f, 0.0f, 0.0f);
        body.setTextureSize(128, 128);
        body.mirror = true;
        setRotation(body, 0.0f, 0.0f, 0.0f);

        lid3 = new ModelRenderer(this, 48, 0);
        lid3.addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
        lid3.setRotationPoint(0.0f, 0.0f, 0.0f);
        lid3.setTextureSize(128, 128);
        lid3.mirror = true;
        setRotation(lid3, 0.0f, 3.141593f, 0.0f);

        body3 = new ModelRenderer(this, 0, 24);
        body3.addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
        body3.setRotationPoint(0.0f, 0.0f, 0.0f);
        body3.setTextureSize(128, 128);
        body3.mirror = true;
        setRotation(body3, 0.0f, 3.141593f, 0.0f);

        lock3 = new ModelRenderer(this, 22, 24);
        lock3.addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
        lock3.setRotationPoint(0.0f, 0.0f, 0.0f);
        lock3.setTextureSize(128, 128);
        lock3.mirror = true;
        setRotation(lock3, 0.0f, 3.141593f, 0.0f);

        lid4 = new ModelRenderer(this, 48, 0);
        lid4.addBox(-4.0f, 9.0f, -7.0f, 8, 5, 3);
        lid4.setRotationPoint(0.0f, 0.0f, 0.0f);
        lid4.setTextureSize(128, 128);
        lid4.mirror = true;
        setRotation(lid4, 0.0f, -1.570796f, 0.0f);

        body4 = new ModelRenderer(this, 0, 24);
        body4.addBox(-4.0f, 14.0f, -7.0f, 8, 10, 3);
        body4.setRotationPoint(0.0f, 0.0f, 0.0f);
        body4.setTextureSize(128, 128);
        body4.mirror = true;
        setRotation(body4, 0.0f, -1.570796f, 0.0f);

        lock4 = new ModelRenderer(this, 22, 24);
        lock4.addBox(-1.0f, 12.0f, -8.0f, 2, 4, 1);
        lock4.setRotationPoint(0.0f, 0.0f, 0.0f);
        lock4.setTextureSize(128, 128);
        lock4.mirror = true;
        setRotation(lock4, 0.0f, -1.570796f, 0.0f);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        column1.render(f5);
        column4.render(f5);
        column2.render(f5);
        column3.render(f5);
        lid1.render(f5);
        body1.render(f5);
        lock1.render(f5);
        lid2.render(f5);
        body2.render(f5);
        lock2.render(f5);
        body.render(f5);
        lid3.render(f5);
        body3.render(f5);
        lock3.render(f5);
        lid4.render(f5);
        body4.render(f5);
        lock4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }
}
