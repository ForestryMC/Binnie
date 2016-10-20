package binnie.botany.flower;

import binnie.botany.api.EnumFlowerStage;

public class ItemSeed extends ItemBotany {
    public ItemSeed() {
        super("seed");
    }

    @Override
    public EnumFlowerStage getStage() {
        return EnumFlowerStage.SEED;
    }

    @Override
    public String getTag() {
        return " Germling";
    }
}
