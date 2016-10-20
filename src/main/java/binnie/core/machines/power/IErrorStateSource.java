// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

public interface IErrorStateSource
{
	ErrorState canWork();

	ErrorState canProgress();
}
