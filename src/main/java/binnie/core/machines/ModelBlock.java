package binnie.core.machines;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

class ModelBlock extends ModelBase {
    private ModelRenderer Block;

    public ModelBlock() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.Block = new ModelRenderer(this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 16, 16, 16);
        this.Block.setRotationPoint(-8.0f, 8.0f, -8.0f);
        this.Block.setTextureSize(64, 32);
        this.Block.mirror = true;
        this.setRotation(this.Block, 0.0f, 0.0f, 0.0f);
    }

    public void render(final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(null, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Block.render(f5);
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
