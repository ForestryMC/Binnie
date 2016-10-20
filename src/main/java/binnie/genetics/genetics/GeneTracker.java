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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.ArrayList;

public class GeneTracker extends WorldSavedData {
    private ArrayList<IGene> discoveredGenes;
    GameProfile username;

    public GeneTracker(final String s, final GameProfile username) {
        super(s);
        this.discoveredGenes = new ArrayList<IGene>();
        this.username = username;
    }

    public GeneTracker(final String s) {
        super(s);
        this.discoveredGenes = new ArrayList<IGene>();
    }

    protected static GeneTracker getCommonTracker(final EntityPlayer player) {
        return getTracker(player.worldObj, null);
    }

    public static GeneTracker getTracker(final World world, final GameProfile player) {
        final String filename = "GeneTracker." + ((player == null) ? "common" : player.getId());
        GeneTracker tracker = (GeneTracker) world.loadItemData(GeneTracker.class, filename);
        if (tracker == null) {
            tracker = new GeneTracker(filename, player);
            world.setItemData(filename, tracker);
        } else {
            tracker.username = player;
        }
        return tracker;
    }

    public void synchToPlayer(final EntityPlayer player) {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        Genetics.proxy.sendToPlayer(new MessageNBT(GeneticsPacket.GeneTrackerSync.ordinal(), nbttagcompound), player);
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            if (!nbt.hasKey(root.getUID())) {
                continue;
            }
            final NBTTagCompound nbtRoot = nbt.getCompoundTag(root.getUID());
            for (final IChromosomeType chromo : root.getKaryotype()) {
                if (nbtRoot.hasKey("" + chromo.ordinal())) {
                    final NBTTagList nbtChromo = nbtRoot.getTagList("" + chromo.ordinal(), 8);
                    for (int i = 0; i < nbtChromo.tagCount(); ++i) {
                        final String uid = nbtChromo.getStringTagAt(i);
                        final IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
                        final Gene gene = new Gene(allele, chromo, root);
                        if (allele != null && !this.discoveredGenes.contains(gene)) {
                            this.discoveredGenes.add(gene);
                        }
                    }
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
        for (final ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            final NBTTagCompound nbtRoot = new NBTTagCompound();
            for (final IChromosomeType chromo : root.getKaryotype()) {
                final NBTTagList nbtChromo = new NBTTagList();
                for (final IGene gene : this.discoveredGenes) {
                    if (gene.getSpeciesRoot() == root && gene.getChromosome() == chromo) {
                        nbtChromo.appendTag(new NBTTagString(gene.getAllele().getUID()));
                    }
                }
                nbtRoot.setTag("" + chromo.ordinal(), nbtChromo);
            }
            nbt.setTag(root.getUID(), nbtRoot);
        }
        return nbt;
    }

    public void registerGene(final IGene iGene) {
        if (!this.discoveredGenes.contains(iGene)) {
            this.discoveredGenes.add(iGene);
        }
        this.markDirty();
    }

    public int getGenesSequenced() {
        return this.discoveredGenes.size();
    }

    public boolean isSequenced(final Gene gene) {
        return this.discoveredGenes.contains(gene);
    }
}
