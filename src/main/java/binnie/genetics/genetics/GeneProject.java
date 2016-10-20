package binnie.genetics.genetics;

import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.List;

public class GeneProject {
    int id;
    String name;
    GameProfile leader;
    List<GameProfile> members;

    public GeneProject(final int id, final String name, final GameProfile leader) {
        this.id = 0;
        this.leader = null;
        this.members = new ArrayList<GameProfile>();
        this.setID(id);
        this.setName(name);
        this.setLeader(leader);
    }

    public int getID() {
        return this.id;
    }

    public void setID(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public GameProfile getLeader() {
        return this.leader;
    }

    public void setLeader(final GameProfile leader) {
        this.addPlayer(this.leader = leader);
    }

    public boolean isEmpty() {
        return this.members.isEmpty();
    }

    public void addPlayer(final GameProfile player) {
        if (!this.members.contains(player)) {
            this.members.add(player);
        }
        if (this.leader == null) {
            this.leader = player;
        }
    }

    public void removePlayer(final GameProfile player) {
        if (player == this.leader) {
            throw new RuntimeException("Can't remove leader of a Gene Project");
        }
        this.members.remove(player);
    }
}
