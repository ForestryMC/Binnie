package binnie.botany.api;

import net.minecraft.util.IIcon;

public interface IFlowerType {
    IIcon getStem(EnumFlowerStage stage, boolean flowered, int section);

    IIcon getPetalIcon(EnumFlowerStage stage, boolean flowered, int section);

    IIcon getVariantIcon(EnumFlowerStage stage, boolean flowered, int section);

    int getID();

    int getSections();

    int ordinal();
}
