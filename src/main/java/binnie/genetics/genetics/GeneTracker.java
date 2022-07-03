package binnie.genetics.genetics;

import binnie.core.genetics.Gene;
import binnie.core.network.packet.MessageNBT;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.core.GeneticsPacket;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class GeneTracker extends WorldSavedData {
    GameProfile username;
    private ArrayList<IGene> discoveredGenes;

    public GeneTracker(String s, GameProfile username) {
        super(s);
        discoveredGenes = new ArrayList<>();
        this.username = username;
    }

    public GeneTracker(String s) {
        super(s);
        discoveredGenes = new ArrayList<>();
    }

    protected static GeneTracker getCommonTracker(EntityPlayer player) {
        return getTracker(player.worldObj, null);
    }

    public static GeneTracker getTracker(World world, GameProfile player) {
        String filename = "GeneTracker." + ((player == null) ? "common" : player.getId());
        GeneTracker tracker = (GeneTracker) world.loadItemData(GeneTracker.class, filename);
        if (tracker == null) {
            tracker = new GeneTracker(filename, player);
            world.setItemData(filename, tracker);
        } else {
            tracker.username = player;
        }
        return tracker;
    }

    public void synchToPlayer(EntityPlayer player) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        Genetics.proxy.sendToPlayer(new MessageNBT(GeneticsPacket.GeneTrackerSync.ordinal(), nbttagcompound), player);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            if (!nbt.hasKey(root.getUID())) {
                continue;
            }
            NBTTagCompound nbtRoot = nbt.getCompoundTag(root.getUID());
            for (IChromosomeType chromo : root.getKaryotype()) {
                if (!nbtRoot.hasKey("" + chromo.ordinal())) {
                    continue;
                }

                NBTTagList nbtChromo = nbtRoot.getTagList("" + chromo.ordinal(), 8);
                for (int i = 0; i < nbtChromo.tagCount(); ++i) {
                    String uid = nbtChromo.getStringTagAt(i);
                    IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
                    Gene gene = new Gene(allele, chromo, root);
                    if (allele != null && !discoveredGenes.contains(gene)) {
                        discoveredGenes.add(gene);
                    }
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            NBTTagCompound nbtRoot = new NBTTagCompound();
            for (IChromosomeType chromo : root.getKaryotype()) {
                NBTTagList nbtChromo = new NBTTagList();
                for (IGene gene : discoveredGenes) {
                    if (gene.getSpeciesRoot() == root && gene.getChromosome() == chromo) {
                        nbtChromo.appendTag(new NBTTagString(gene.getAllele().getUID()));
                    }
                }
                nbtRoot.setTag("" + chromo.ordinal(), nbtChromo);
            }
            nbt.setTag(root.getUID(), nbtRoot);
        }
    }

    public void registerGene(IGene iGene) {
        if (!discoveredGenes.contains(iGene)) {
            discoveredGenes.add(iGene);
        }
        markDirty();
    }

    public int getGenesSequenced() {
        return discoveredGenes.size();
    }

    public boolean isSequenced(Gene gene) {
        return discoveredGenes.contains(gene);
    }
}
