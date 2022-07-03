package binnie.core.machines;

import java.util.Collection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IMachine extends IOwnable {
    void addComponent(MachineComponent component);

    MachineUtil getMachineUtil();

    <T> T getInterface(Class<T> cls);

    void markDirty();

    World getWorld();

    TileEntity getTileEntity();

    <T> Collection<T> getInterfaces(Class<T> cls);

    MachinePackage getPackage();
}
