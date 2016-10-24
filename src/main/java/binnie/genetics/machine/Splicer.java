package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Splicer {
    public static final int slotSerumVial = 0;
    public static final int[] slotSerumReserve = new int[]{1, 2};
    public static final int[] slotSerumExpended = new int[]{3, 4};
    public static final int[] slotReserve = new int[]{5, 6, 7, 8, 9};
    public static final int slotTarget = 9;
    public static final int[] slotFinished = new int[]{10, 11, 12, 13};

    public static void setGene(final IGene gene, final ItemStack target, final int chromoN) {
        int chromosomeID;
        final int chromosome = chromosomeID = gene.getChromosome().ordinal();
        if (chromosomeID >= EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
            --chromosomeID;
        }
        final Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
        if (!cls.isInstance(gene.getAllele())) {
            return;
        }
        final NBTTagCompound beeNBT = target.getTagCompound();
        final NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
        final NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
        final NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
        chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
        target.setTagCompound(beeNBT);
    }

    public static class PackageSplicer extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
        public PackageSplicer() {
            super("splicer", GeneticsTexture.Splicer, 14819893, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Splicer);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(0, "serum.active");
            inventory.getSlot(0).forbidInteraction();
            inventory.getSlot(0).setReadOnly();
            final SlotValidator serumValid = new SlotValidator(ModuleMachine.IconSerum) {
                @Override
                public boolean isValid(final ItemStack itemStack) {
                    return itemStack.getItem() instanceof IItemSerum;
                }

                @Override
                public String getTooltip() {
                    return "Serum Vials & Arrays";
                }
            };
            inventory.getSlot(0).setValidator(serumValid);
            inventory.addSlotArray(Splicer.slotSerumReserve, "serum.input");
            for (final InventorySlot slot : inventory.getSlots(Splicer.slotSerumReserve)) {
                slot.setValidator(serumValid);
                slot.forbidExtraction();
            }
            inventory.addSlotArray(Splicer.slotSerumExpended, "serum.output");
            for (final InventorySlot slot : inventory.getSlots(Splicer.slotSerumExpended)) {
                slot.setValidator(serumValid);
                slot.setReadOnly();
            }
            inventory.addSlotArray(Splicer.slotReserve, "input");
            for (final InventorySlot slot : inventory.getSlots(Splicer.slotReserve)) {
                slot.forbidExtraction();
                slot.setValidator(new ValidatorIndividualInoculate());
            }
            inventory.addSlot(9, "process");
            inventory.getSlot(9).setValidator(new ValidatorIndividualInoculate());
            inventory.getSlot(9).setReadOnly();
            inventory.getSlot(9).forbidInteraction();
            inventory.addSlotArray(Splicer.slotFinished, "output");
            for (final InventorySlot slot : inventory.getSlots(Splicer.slotFinished)) {
                slot.setReadOnly();
                slot.forbidInsertion();
                slot.setValidator(new ValidatorIndividualInoculate());
            }
            final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Splicer.slotReserve, 9, 1);
            transfer.addRestock(Splicer.slotSerumReserve, 0);
            transfer.addStorage(0, Splicer.slotSerumExpended, new ComponentInventoryTransfer.Condition() {
                @Override
                public boolean fufilled(final ItemStack stack) {
                    return Engineering.getCharges(stack) == 0;
                }
            });
            transfer.addStorage(9, Splicer.slotFinished, new ComponentInventoryTransfer.Condition() {
                @Override
                public boolean fufilled(final ItemStack stack) {
                    return stack != null && this.transfer.getMachine().getMachineUtil().getStack(0) != null && this.transfer.getMachine().getInterface(ComponentSplicerLogic.class).isValidSerum() != null;
                }
            });
            new ComponentPowerReceptor(machine, 20000);
            new ComponentSplicerLogic(machine);
            new ComponentSplicerFX(machine);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentSplicerLogic extends ComponentProcessSetCost implements IProcess {
        int nOfGenes;

        @Override
        public int getProcessLength() {
            float n = this.getNumberOfGenes();
            if (n > 1.0f) {
                n = 1.0f + (n - 1.0f) * 0.5f;
            }
            // Fix for / by 0
            int temp = (int) (super.getProcessLength() * n);
            return temp != 0 ? temp : 1;
        }

        @Override
        public int getProcessEnergy() {
            float n = this.getNumberOfGenes();
            if (n > 1.0f) {
                n = 1.0f + (n - 1.0f) * 0.5f;
            }
            return (int) (super.getProcessEnergy() * n);
        }

        @Override
        public void onInventoryUpdate() {
            super.onInventoryUpdate();
            this.nOfGenes = this.getGenesToUse();
        }

        protected int getGenesToUse() {
            final ItemStack serum = this.getUtil().getStack(0);
            final ItemStack target = this.getUtil().getStack(9);
            if (serum == null || target == null) {
                return 1;
            }
            final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
            final IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
            if (ind.getGenome().getSpeciesRoot() != ((IItemSerum) serum.getItem()).getSpeciesRoot(serum)) {
                return 1;
            }
            int i = 0;
            for (final IGene gene : genes) {
                if (ind.getGenome().getActiveAllele(gene.getChromosome()) != gene.getAllele() || ind.getGenome().getInactiveAllele(gene.getChromosome()) != gene.getAllele()) {
                    ++i;
                }
            }
            return (i < 1) ? 1 : i;
        }

        private int getFullNumberOfGenes() {
            final ItemStack serum = this.getUtil().getStack(0);
            if (serum == null) {
                return 1;
            }
            return Engineering.getGenes(serum).length;
        }

        private int getNumberOfGenes() {
            return this.nOfGenes;
        }

        @Override
        public String getTooltip() {
            final int n = this.getNumberOfGenes();
            final int f = this.getFullNumberOfGenes();
            return "Splicing in " + n + ((f > 1) ? ("/" + f) : "") + " gene" + ((n > 1) ? "s" : "");
        }

        public ComponentSplicerLogic(final Machine machine) {
            super(machine, 12000000, 1200);
            this.nOfGenes = 0;
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(9)) {
                return new ErrorState.NoItem("No Individual to Splice", 9);
            }
            if (this.getUtil().isSlotEmpty(0)) {
                return new ErrorState.NoItem("No Serum", 0);
            }
            final ErrorState state = this.isValidSerum();
            if (state != null) {
                return state;
            }
            if (this.getUtil().getStack(0) != null && Engineering.getCharges(this.getUtil().getStack(0)) == 0) {
                return new ErrorState("Empty Serum", "Serum is empty");
            }
            return super.canWork();
        }

        public ErrorState isValidSerum() {
            final ItemStack serum = this.getUtil().getStack(0);
            final ItemStack target = this.getUtil().getStack(9);
            final IGene[] genes = Engineering.getGenes(serum);
            if (genes.length == 0) {
                return new ErrorState("Invalid Serum", "Serum does not hold any genes");
            }
            if (!genes[0].getSpeciesRoot().isMember(target)) {
                return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
            }
            final IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
            boolean hasAll = true;
            for (final IGene gene : genes) {
                if (hasAll) {
                    final IAllele a = individual.getGenome().getActiveAllele(gene.getChromosome());
                    final IAllele b = individual.getGenome().getInactiveAllele(gene.getChromosome());
                    hasAll = (hasAll && a.getUID().equals(gene.getAllele().getUID()) && b.getUID().equals(gene.getAllele().getUID()));
                }
            }
            if (hasAll) {
                return new ErrorState("Defunct Serum", "Individual already possesses this allele");
            }
            return null;
        }

        @Override
        public ErrorState canProgress() {
            return super.canProgress();
        }

        @Override
        protected void onFinishTask() {
            super.onFinishTask();
            final ItemStack serum = this.getUtil().getStack(0);
            final ItemStack target = this.getUtil().getStack(9);
            final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
            if (!ind.isAnalyzed()) {
                ind.analyze();
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                ind.writeToNBT(nbttagcompound);
                target.setTagCompound(nbttagcompound);
            }
            final IGene[] arr$;
            final IGene[] genes = arr$ = ((IItemSerum) serum.getItem()).getGenes(serum);
            for (final IGene gene : arr$) {
                Splicer.setGene(gene, target, 0);
                Splicer.setGene(gene, target, 1);
            }
            this.getUtil().damageItem(0, 1);
        }

        @Override
        protected void onTickTask() {
        }
    }

    public static class ComponentSplicerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
        private final EntityItem dummyEntityItem;

        public ComponentSplicerFX(final IMachine machine) {
            super(machine);
            this.dummyEntityItem = new EntityItem(machine.getWorld());
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onRandomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
//			final int tick = (int) (world.getTotalWorldTime() % 3L);
//			if (tick == 0 && this.getUtil().getProcess().isInProgress()) {
//				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.5, z + 0.5, 0.0, 0.0, 0.0) {
//					double axisX = this.posX;
//					double axisZ = this.posZ;
//					double angle = (int) (this.worldObj.getTotalWorldTime() % 4L) * 0.5 * 3.1415;
//
//					{
//						this.axisX = 0.0;
//						this.axisZ = 0.0;
//						this.angle = 0.0;
//						this.motionX = 0.0;
//						this.motionZ = 0.0;
//						this.motionY = (this.rand.nextDouble() - 0.5) * 0.02;
//						this.particleMaxAge = 240;
//						this.particleGravity = 0.0f;
//						this.noClip = true;
//						this.setRBGColorF(0.3f + this.rand.nextFloat() * 0.5f, 0.3f + this.rand.nextFloat() * 0.5f, 0.0f);
//					}
//
//					@Override
//					public void onUpdate() {
//						super.onUpdate();
//						final double speed = 0.04;
//						this.angle -= speed;
//						final double dist = 0.25 + 0.2 * Math.sin(this.particleAge / 50.0f);
//						this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
//						this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
//					}
//
//					@Override
//					public int getFXLayer() {
//						return 0;
//					}
//				});
//			}
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void renderInWorld(final RenderItem customRenderItem, final double x, final double y, final double z) {
            if (!this.getUtil().getProcess().isInProgress()) {
                return;
            }
            final ItemStack stack = this.getUtil().getStack(9);
            this.dummyEntityItem.worldObj = this.getMachine().getWorld();
            this.dummyEntityItem.setEntityItemStack(stack);
            final EntityItem dummyEntityItem = this.dummyEntityItem;
            dummyEntityItem.setAgeToCreativeDespawnTime(); //++dummyEntityItem.age;
            this.dummyEntityItem.hoverStart = 0.0f;
            if (stack == null) {
                return;
            }
            final EntityPlayer player = BinnieCore.proxy.getPlayer();
            final double dx = x + 0.5 - player.lastTickPosX;
            final double dz = z + 0.5 - player.lastTickPosZ;
            final double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
            GL11.glPushMatrix();
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, -0.25f, 0.0f);
            customRenderItem.renderItem(dummyEntityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);//doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
            GL11.glPopMatrix();
        }

        @Override
        public void syncToNBT(final NBTTagCompound nbt) {
            final NBTTagCompound item = new NBTTagCompound();
            final ItemStack stack = this.getUtil().getStack(9);
            if (stack != null) {
                stack.writeToNBT(item);
                nbt.setTag("item", item);
            }
        }

        @Override
        public void syncFromNBT(final NBTTagCompound nbt) {
            if (nbt.hasKey("item")) {
                this.getUtil().setStack(9, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
            } else {
                this.getUtil().setStack(9, null);
            }
        }

        @Override
        public void onInventoryUpdate() {
            if (!this.getUtil().isServer()) {
                return;
            }
            this.getUtil().refreshBlock();
        }
    }

    public static class ValidatorIndividualInoculate extends SlotValidator {
        public ValidatorIndividualInoculate() {
            super(null);
        }

        @Override
        public boolean isValid(final ItemStack object) {
            final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
            return root != null && Binnie.Genetics.getSystem(root).isDNAManipulable(object);
        }

        @Override
        public String getTooltip() {
            return "Splicable Individual";
        }
    }
}
