package binnie.genetics.machine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMachine extends ModelBase {
    private ModelRenderer base;
    private ModelRenderer body;
    private ModelRenderer arm1;
    private ModelRenderer arm2;
    private ModelRenderer arm3;
    private ModelRenderer arm4;
    private ModelRenderer top;

    public ModelMachine() {
        textureWidth = 64;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 18);
        base.addBox(0.0f, 0.0f, 0.0f, 16, 4, 16);
        base.setRotationPoint(-8.0f, 20.0f, -8.0f);
        base.setTextureSize(64, 64);
        base.mirror = true;
        setRotation(base, 0.0f, 0.0f, 0.0f);

        body = new ModelRenderer(this, 0, 26);
        body.addBox(0.0f, 0.0f, 0.0f, 12, 7, 12);
        body.setRotationPoint(-6.0f, 13.0f, -6.0f);
        body.setTextureSize(64, 64);
        body.mirror = true;
        setRotation(body, 0.0f, 0.0f, 0.0f);

        arm1 = new ModelRenderer(this, 0, 0);
        arm1.addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
        arm1.setRotationPoint(5.0f, 8.0f, 5.0f);
        arm1.setTextureSize(64, 64);
        arm1.mirror = true;
        setRotation(arm1, 0.0f, 0.0f, 0.0f);

        arm2 = new ModelRenderer(this, 0, 0);
        arm2.addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
        arm2.setRotationPoint(-5.0f, 8.0f, 5.0f);
        arm2.setTextureSize(64, 64);
        arm2.mirror = true;
        setRotation(arm2, 0.0f, -1.57075f, 0.0f);

        arm3 = new ModelRenderer(this, 0, 0);
        arm3.addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
        arm3.setRotationPoint(-5.0f, 8.0f, -5.0f);
        arm3.setTextureSize(64, 64);
        arm3.mirror = true;
        setRotation(arm3, 0.0f, -3.1415f, 0.0f);

        arm4 = new ModelRenderer(this, 0, 0);
        arm4.addBox(0.0f, 0.0f, 0.0f, 3, 6, 3);
        arm4.setRotationPoint(5.0f, 8.0f, -5.0f);
        arm4.setTextureSize(64, 64);
        arm4.mirror = true;
        setRotation(arm4, 0.0f, -4.7122498f, 0.0f);

        top = new ModelRenderer(this, 0, 0);
        top.addBox(0.0f, 0.0f, 0.0f, 14, 4, 14);
        top.setRotationPoint(-7.0f, 9.0f, -7.0f);
        top.setTextureSize(64, 64);
        top.mirror = true;
        setRotation(top, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(null, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        base.render(f5);
        body.render(f5);
        arm1.render(f5);
        arm2.render(f5);
        arm3.render(f5);
        arm4.render(f5);
        top.render(f5);
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
