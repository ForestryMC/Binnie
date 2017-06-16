package binnie.genetics.genetics;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;

public class GeneProject {
	private int id;
	private String name;
	private GameProfile leader;
	private List<GameProfile> members;

	public GeneProject(final int id, final String name, final GameProfile leader) {
		this.id = 0;
		this.leader = leader;
		this.members = new ArrayList<>();
		this.setID(id);
		this.name = name;
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
