package binnie.genetics.genetics;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;

public class GeneProject {
	private int id;
	private String name;
	private GameProfile leader;
	private List<GameProfile> members;

	public GeneProject(int id, String name, GameProfile leader) {
		this.id = 0;
		this.leader = leader;
		this.name = name;
		members = new ArrayList<>();
		setID(id);
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

	public boolean isEmpty() {
		return members.isEmpty();
	}

	public void addPlayer(GameProfile player) {
		if (!members.contains(player)) {
			members.add(player);
		}
		// TODO always false?
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
