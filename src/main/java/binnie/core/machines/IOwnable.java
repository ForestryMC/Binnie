package binnie.core.machines;

import com.mojang.authlib.GameProfile;

interface IOwnable {
    GameProfile getOwner();

    void setOwner(GameProfile owner);
}
