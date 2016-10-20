// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import com.mojang.authlib.GameProfile;

interface IOwnable
{
	GameProfile getOwner();

	void setOwner(final GameProfile p0);
}
