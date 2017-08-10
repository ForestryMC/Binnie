package binnie.core.machines;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IMachine extends IOwnable {
	void addComponent(MachineComponent component);

	MachineUtil getMachineUtil();

	@Nullable
	<T> T getInterface(Class<T> Interface);

	void markDirty();

	World getWorld();

	TileEntity getTileEntity();

	<T> Collection<T> getInterfaces(Class<T> Interface);

	MachinePackage getPackage();
}
