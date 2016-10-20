// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import java.util.Collection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IMachine extends IOwnable
{
	void addComponent(final MachineComponent p0);

	MachineUtil getMachineUtil();

	<T> T getInterface(final Class<T> p0);

	void markDirty();

	World getWorld();

	TileEntity getTileEntity();

	<T> Collection<T> getInterfaces(final Class<T> p0);

	MachinePackage getPackage();
}
