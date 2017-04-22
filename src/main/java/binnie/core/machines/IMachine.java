package binnie.core.machines;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;

import java.util.*;

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
