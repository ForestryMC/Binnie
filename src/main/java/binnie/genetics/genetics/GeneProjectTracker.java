package binnie.genetics.genetics;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import com.mojang.authlib.GameProfile;

public class GeneProjectTracker extends WorldSavedData {
	private Map<Integer, GeneProject> projects;
	private Map<GameProfile, Set<Integer>> TeamInvites;

	public GeneProjectTracker(String s) {
		super(s);
		projects = new HashMap<>();
		TeamInvites = new HashMap<>();
	}

	public static GeneProjectTracker getTracker(World world) {
		String filename = "GeneProjectTracker.common";
		GeneProjectTracker tracker = (GeneProjectTracker) world.loadData(GeneProjectTracker.class, filename);
		if (tracker == null) {
			tracker = new GeneProjectTracker(filename);
			world.setData(filename, tracker);
		}
		return tracker;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// ignored
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		return nbt;
	}

	public int createProject(String name, GameProfile leader) {
		int i;
		for (i = 1; projects.keySet().contains(i); ++i) {
		}
		GeneProject project = new GeneProject(i, name, leader);
		projects.put(i, project);
		markDirty();
		return i;
	}

	public void removeProject(int id) {
		projects.remove(id);
		for (Map.Entry<GameProfile, Set<Integer>> entry : TeamInvites.entrySet()) {
			entry.getValue().remove(id);
		}
		markDirty();
	}

	public void leaveProject(int id, GameProfile player) {
		GeneProject project = projects.get(id);
		if (project == null) {
			return;
		}

		project.removePlayer(player);
		if (project.isEmpty()) {
			removeProject(id);
		}
		markDirty();
	}

	public void joinProject(int id, GameProfile player) {
		GeneProject project = projects.get(id);
		if (project == null) {
			return;
		}
		project.addPlayer(player);
		markDirty();
	}

	public void reassignPlayer(int id, int id2, GameProfile player) {
		GeneProject project = projects.get(id);
		if (project == null) {
			return;
		}

		GeneProject project2 = projects.get(id2);
		if (project2 == null) {
			return;
		}
		leaveProject(id, player);
		joinProject(id2, player);
	}

	public void renameProject(int id, String newName) {
		GeneProject project = projects.get(id);
		if (project != null) {
			project.setName(newName);
		}
		markDirty();
	}

	public void invitePlayer(int id, GameProfile player) {
		if (!TeamInvites.containsKey(player)) {
			TeamInvites.put(player, new LinkedHashSet<>());
		}
		TeamInvites.get(player).add(id);
		markDirty();
	}

	public void revokeInvite(int id, GameProfile player) {
		if (!TeamInvites.containsKey(player)) {
			TeamInvites.put(player, new LinkedHashSet<>());
		}
		TeamInvites.get(player).add(id);
		markDirty();
	}
}
