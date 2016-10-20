// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

interface IProcessTimed extends IProcess, IErrorStateSource
{
	int getProcessLength();

	int getProcessEnergy();

	float getProgress();

	float getProgressPerTick();
}
