package binnie.core.machines;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

class ModelBlock extends ModelBase {
    private ModelRenderer block;

    public ModelBlock() {
        textureWidth = 64;
        textureHeight = 32;

        block = new ModelRenderer(this, 0, 0);
        block.addBox(0.0f, 0.0f, 0.0f, 16, 16, 16);
        block.setRotationPoint(-8.0f, 8.0f, -8.0f);
        block.setTextureSize(64, 32);
        block.mirror = true;
        setRotation(block, 0.0f, 0.0f, 0.0f);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(null, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        block.render(f5);
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
