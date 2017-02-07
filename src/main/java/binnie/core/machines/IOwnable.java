package binnie.core.machines;

import com.mojang.authlib.GameProfile;

import javax.annotation.Nullable;

interface IOwnable {
	@Nullable
	GameProfile getOwner();

	void setOwner(final GameProfile owner);
}
