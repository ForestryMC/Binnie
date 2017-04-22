package binnie.core.machines;

import com.mojang.authlib.*;

interface IOwnable {
	GameProfile getOwner();

	void setOwner(GameProfile owner);
}
