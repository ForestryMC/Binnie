package binnie.genetics.genetics;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GeneProjectTracker extends WorldSavedData {
    Map<Integer, GeneProject> projects;
    Map<GameProfile, Set<Integer>> TeamInvites;

    public GeneProjectTracker(final String s) {
        super(s);
        this.projects = new HashMap<Integer, GeneProject>();
        this.TeamInvites = new HashMap<GameProfile, Set<Integer>>();
    }

    public static GeneProjectTracker getTracker(final World world) {
        final String filename = "GeneProjectTracker.common";
        GeneProjectTracker tracker = (GeneProjectTracker) world.loadItemData(GeneProjectTracker.class, filename);
        if (tracker == null) {
            tracker = new GeneProjectTracker(filename);
            world.setItemData(filename, tracker);
        }
        return tracker;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
        return nbt;
    }

    public int createProject(final String name, final GameProfile leader) {
        int i;
        for (i = 1; this.projects.keySet().contains(i); ++i) {
        }
        final GeneProject project = new GeneProject(i, name, leader);
        this.projects.put(i, project);
        this.markDirty();
        return i;
    }

    public void removeProject(final int id) {
        this.projects.remove(id);
        for (final Map.Entry<GameProfile, Set<Integer>> entry : this.TeamInvites.entrySet()) {
            entry.getValue().remove(id);
        }
        this.markDirty();
    }

    public void leaveProject(final int id, final GameProfile player) {
        final GeneProject project = this.projects.get(id);
        if (project == null) {
            return;
        }
        project.removePlayer(player);
        if (project.isEmpty()) {
            this.removeProject(id);
        }
        this.markDirty();
    }

    public void joinProject(final int id, final GameProfile player) {
        final GeneProject project = this.projects.get(id);
        if (project == null) {
            return;
        }
        project.addPlayer(player);
        this.markDirty();
    }

    public void reassignPlayer(final int id, final int id2, final GameProfile player) {
        final GeneProject project = this.projects.get(id);
        if (project == null) {
            return;
        }
        final GeneProject project2 = this.projects.get(id2);
        if (project2 == null) {
            return;
        }
        this.leaveProject(id, player);
        this.joinProject(id2, player);
    }

    public void renameProject(final int id, final String newName) {
        final GeneProject project = this.projects.get(id);
        if (project != null) {
            project.setName(newName);
        }
        this.markDirty();
    }

    public void invitePlayer(final int id, final GameProfile player) {
        if (!this.TeamInvites.containsKey(player)) {
            this.TeamInvites.put(player, new LinkedHashSet<Integer>());
        }
        this.TeamInvites.get(player).add(id);
        this.markDirty();
    }

    public void revokeInvite(final int id, final GameProfile player) {
        if (!this.TeamInvites.containsKey(player)) {
            this.TeamInvites.put(player, new LinkedHashSet<Integer>());
        }
        this.TeamInvites.get(player).add(id);
        this.markDirty();
    }
}
