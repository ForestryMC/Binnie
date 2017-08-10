package binnie.core.machines;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

interface IOwnable {
	@Nullable
	GameProfile getOwner();

	void setOwner(final GameProfile owner);
}
