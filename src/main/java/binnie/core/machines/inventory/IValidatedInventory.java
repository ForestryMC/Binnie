// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import net.minecraft.inventory.IInventory;

interface IValidatedInventory extends IInventory
{
	boolean isReadOnly(final int p0);
}
