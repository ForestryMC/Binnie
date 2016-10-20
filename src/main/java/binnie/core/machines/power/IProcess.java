// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

public interface IProcess extends IErrorStateSource
{
	float getEnergyPerTick();

	String getTooltip();

	boolean isInProgress();

	ProcessInfo getInfo();
}
