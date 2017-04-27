package binnie.genetics.genetics;

import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.List;

public class GeneProject {
	protected int id;
	protected String name;
	protected GameProfile leader;
	protected List<GameProfile> members;

	public GeneProject(int id, String name, GameProfile leader) {
		this.id = 0;
		this.leader = null;
		members = new ArrayList<>();
		setID(id);
		setName(name);
		setLeader(leader);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameProfile getLeader() {
		return leader;
	}

	public void setLeader(GameProfile leader) {
		addPlayer(this.leader = leader);
	}

	public boolean isEmpty() {
		return members.isEmpty();
	}

	public void addPlayer(GameProfile player) {
		if (!members.contains(player)) {
			members.add(player);
		}
		if (leader == null) {
			leader = player;
		}
	}

	public void removePlayer(GameProfile player) {
		if (player == leader) {
			throw new RuntimeException("Can't remove leader of a Gene Project");
		}
		members.remove(player);
	}
}
