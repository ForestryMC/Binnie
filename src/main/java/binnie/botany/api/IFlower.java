package binnie.botany.api;

import forestry.api.genetics.IIndividual;
import net.minecraft.world.World;

public interface IFlower extends IIndividual {
    @Override
    IFlowerGenome getGenome();

    IFlowerGenome getMate();

    void mate(IFlower other);

    int getAge();

    void setAge(int age);

    void age();

    IFlower getOffspring(World world);

    int getMaxAge();

    boolean isWilted();

    void setWilted(boolean value);

    boolean hasFlowered();

    void setFlowered(boolean value);

    void removeMate();
}
