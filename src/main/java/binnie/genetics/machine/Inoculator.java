package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.*;
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
import binnie.genetics.item.GeneticLiquid;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Inoculator {
    public static final int slotSerumVial = 0;
    public static final int[] slotSerumReserve;
    public static final int[] slotSerumExpended;
    public static final int[] slotReserve;
    public static final int slotTarget = 9;
    public static final int[] slotFinished;
    public static final int tankVector = 0;

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

    static {
        slotSerumReserve = new int[]{1, 2};
        slotSerumExpended = new int[]{3, 4};
        slotReserve = new int[]{5, 6, 7, 8};
        slotFinished = new int[]{10, 11, 12, 13};
    }

    public static class PackageInoculator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageInoculator() {
            super("inoculator", GeneticsTexture.Inoculator, 14819893, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Inoculator);
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
            inventory.addSlotArray(Inoculator.slotSerumReserve, "serum.input");
            for (final InventorySlot slot : inventory.getSlots(Inoculator.slotSerumReserve)) {
                slot.setValidator(serumValid);
                slot.forbidExtraction();
            }
            inventory.addSlotArray(Inoculator.slotSerumExpended, "serum.output");
            for (final InventorySlot slot : inventory.getSlots(Inoculator.slotSerumExpended)) {
                slot.setValidator(serumValid);
                slot.setReadOnly();
            }
            inventory.addSlotArray(Inoculator.slotReserve, "input");
            for (final InventorySlot slot : inventory.getSlots(Inoculator.slotReserve)) {
                slot.forbidExtraction();
                slot.setValidator(new ValidatorIndividualInoculate());
            }
            inventory.addSlot(9, "process");
            inventory.getSlot(9).setValidator(new ValidatorIndividualInoculate());
            inventory.getSlot(9).setReadOnly();
            inventory.getSlot(9).forbidInteraction();
            inventory.addSlotArray(Inoculator.slotFinished, "output");
            for (final InventorySlot slot : inventory.getSlots(Inoculator.slotFinished)) {
                slot.setReadOnly();
                slot.forbidInsertion();
                slot.setValidator(new ValidatorIndividualInoculate());
            }
            final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Inoculator.slotReserve, 9, 1);
            transfer.addRestock(Inoculator.slotSerumReserve, 0);
            transfer.addStorage(0, Inoculator.slotSerumExpended, new ComponentInventoryTransfer.Condition() {
                @Override
                public boolean fufilled(final ItemStack stack) {
                    return Engineering.getCharges(stack) == 0;
                }
            });
            transfer.addStorage(9, Inoculator.slotFinished, new ComponentInventoryTransfer.Condition() {
                @Override
                public boolean fufilled(final ItemStack stack) {
                    return stack != null && this.transfer.getMachine().getMachineUtil().getStack(0) != null && this.transfer.getMachine().getInterface(ComponentInoculatorLogic.class).isValidSerum() != null;
                }
            });
            new ComponentPowerReceptor(machine, 15000);
            new ComponentInoculatorLogic(machine);
            new ComponentInoculatorFX(machine);
            new ComponentTankContainer(machine).addTank(0, "input", 1000).setValidator(new Validator<FluidStack>() {
                @Override
                public boolean isValid(final FluidStack object) {
                    return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
                }

                @Override
                public String getTooltip() {
                    return GeneticLiquid.BacteriaVector.getName();
                }
            });
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentInoculatorLogic extends ComponentProcessSetCost implements IProcess {
        private float bacteriaDrain;

        @Override
        public int getProcessLength() {
            return super.getProcessLength() * this.getNumberOfGenes();
        }

        @Override
        public int getProcessEnergy() {
            return super.getProcessEnergy() * this.getNumberOfGenes();
        }

        private int getNumberOfGenes() {
            final ItemStack serum = this.getUtil().getStack(0);
            if (serum == null) {
                return 1;
            }
            return Engineering.getGenes(serum).length;
        }

        @Override
        public String getTooltip() {
            final int n = this.getNumberOfGenes();
            return "Inoculating with " + n + " gene" + ((n > 1) ? "s" : "");
        }

        public ComponentInoculatorLogic(final Machine machine) {
            super(machine, 600000, 12000);
            this.bacteriaDrain = 0.0f;
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(9)) {
                return new ErrorState.NoItem("No Individual to Inoculate", 9);
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
                Inoculator.setGene(gene, target, 0);
                Inoculator.setGene(gene, target, 1);
            }
            this.getUtil().damageItem(0, 1);
        }

        @Override
        protected void onTickTask() {
            this.bacteriaDrain += 15.0f * this.getProgressPerTick() / 100.0f;
            if (this.bacteriaDrain >= 1.0f) {
                this.getUtil().drainTank(0, 1);
                --this.bacteriaDrain;
            }
        }
    }

    public static class ComponentInoculatorFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
        private final EntityItem dummyEntityItem;

        public ComponentInoculatorFX(final IMachine machine) {
            super(machine);
            this.dummyEntityItem = new EntityItem((World) null);
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
//				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 0.92, z + 0.5, 0.0, 0.0, 0.0) {
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
//						this.motionY = 0.007 + this.rand.nextDouble() * 0.002;
//						this.particleMaxAge = 240;
//						this.particleGravity = 0.0f;
//						this.noClip = true;
//						this.setRBGColorF(0.8f, 0.0f, 1.0f);
//					}
//
//					@Override
//					public void onUpdate() {
//						super.onUpdate();
//						double speed = 5.0E-4;
//						if (this.particleAge > 60) {
//							speed += (this.particleAge - 60) / 4000.0f;
//						}
//						this.angle += speed;
//						final double dist = 0.27;
//						this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
//						this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
//						if (this.particleAge > 40) {
//							this.setRBGColorF(this.particleRed + (1.0f - this.particleRed) / 10.0f, this.particleGreen + (0.0f - this.particleGreen) / 10.0f, this.particleBlue + (0.0f - this.particleBlue) / 10.0f);
//						}
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
            dummyEntityItem.setAgeToCreativeDespawnTime(); //			++dummyEntityItem.age;
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
            customRenderItem.renderItem(this.dummyEntityItem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED); //, 0.0, 0.0, 0.0, 0.0f, 0.0f);
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
            return "Inoculable Individual";
        }
    }
}
