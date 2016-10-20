package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.*;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.resource.BinnieIcon;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class Sequencer {
    public static final int slotDye = 0;
    public static final int[] slotReserve;
    public static final int slotTarget = 5;
    public static final int slotDone = 6;
    public static BinnieIcon fxSeqA;
    public static BinnieIcon fxSeqG;
    public static BinnieIcon fxSeqT;
    public static BinnieIcon fxSeqC;

    static {
        slotReserve = new int[]{1, 2, 3, 4};
    }

    public static class PackageSequencer extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageSequencer() {
            super("sequencer", GeneticsTexture.Sequencer, 12058418, true);
            Sequencer.fxSeqA = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.a");
            Sequencer.fxSeqG = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.g");
            Sequencer.fxSeqT = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.t");
            Sequencer.fxSeqC = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.c");
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Sequencer);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(0, "dye");
            inventory.getSlot(0).setValidator(new SlotValidator.Item(GeneticsItems.FluorescentDye.get(1), ModuleMachine.IconDye));
            inventory.getSlot(0).forbidExtraction();
            inventory.addSlotArray(Sequencer.slotReserve, "input");
            for (final InventorySlot slot : inventory.getSlots(Sequencer.slotReserve)) {
                slot.setValidator(new SlotValidatorUnsequenced());
                slot.forbidExtraction();
            }
            inventory.addSlot(5, "process");
            inventory.getSlot(5).setValidator(new SlotValidatorUnsequenced());
            inventory.getSlot(5).setReadOnly();
            inventory.getSlot(5).forbidInteraction();
            inventory.addSlot(6, "output");
            inventory.getSlot(6).setReadOnly();
            final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Sequencer.slotReserve, 5, 1);
            new ComponentChargedSlots(machine).addCharge(0);
            new ComponentPowerReceptor(machine, 10000);
            new ComponentSequencerLogic(machine);
            new ComponentSequencerFX(machine);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentSequencerLogic extends ComponentProcess implements IProcess {
        public ComponentSequencerLogic(final Machine machine) {
            super(machine);
        }

        public float getSequenceStrength() {
            final ItemStack stack = this.getUtil().getStack(5);
            if (stack == null) {
                return 1.0f;
            }
            final float mult = 1.0f - stack.getItemDamage() % 6 / 5.0f;
            return 1.0f - mult * mult * 0.75f;
        }

        @Override
        public int getProcessLength() {
            return (int) (19200.0f * this.getSequenceStrength());
        }

        @Override
        public int getProcessEnergy() {
            return this.getProcessLength() * 20;
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(5)) {
                return new ErrorState.NoItem("No DNA sequence", 5);
            }
            return super.canWork();
        }

        @Override
        public ErrorState canProgress() {
            if (this.getMachine().getOwner() == null) {
                return new ErrorState("No Owner", "Replace this block to claim this machine");
            }
            if (this.getUtil().getSlotCharge(0) == 0.0f) {
                return new ErrorState.NoItem("Insufficient Dye", 0);
            }
            if (this.getUtil().getStack(6) != null && this.getUtil().getStack(6).stackSize >= 64) {
                return new ErrorState.NoSpace("No space for empty sequences", new int[]{6});
            }
            return super.canProgress();
        }

        @Override
        protected void onStartTask() {
            super.onStartTask();
            final ItemStack item = this.getUtil().getStack(5);
            final SequencerItem seqItem = new SequencerItem(item);
            final int seq = seqItem.sequenced;
            if (seq != 0) {
                this.setProgress(seq);
            }
        }

        @Override
        protected void onFinishTask() {
            super.onFinishTask();
            this.updateSequence();
            final SequencerItem seqItem = new SequencerItem(this.getUtil().getStack(5));
            GeneTracker.getTracker(this.getMachine().getWorld(), this.getMachine().getOwner()).registerGene(seqItem.getGene());
            this.getUtil().decreaseStack(5, 1);
            if (this.getUtil().getStack(6) == null) {
                this.getUtil().setStack(6, GeneticsItems.EmptySequencer.get(1));
            } else {
                this.getUtil().decreaseStack(6, -1);
            }
        }

        @Override
        protected void onTickTask() {
            this.updateSequence();
            this.getUtil().useCharge(0, 0.4f * this.getProgressPerTick() / 100.0f);
        }

        private void updateSequence() {
            final int prog = (int) this.getProgress();
            final ItemStack item = this.getUtil().getStack(5);
            final SequencerItem seqItem = new SequencerItem(item);
            final int seq = seqItem.sequenced;
            if (prog != seq) {
                seqItem.sequenced = prog;
                seqItem.writeToItem(item);
            }
        }
    }

    public static class ComponentSequencerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
        public ComponentSequencerFX(final IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onRandomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
//			if (!this.getUtil().getProcess().isInProgress()) {
//				return;
//			}
//			BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.2 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
//				double axisX = this.posX;
//				double axisZ = this.posZ;
//				double angle = this.rand.nextDouble() * 2.0 * 3.1415;
//
//				{
//					this.axisX = 0.0;
//					this.axisZ = 0.0;
//					this.angle = 0.0;
//					this.motionX = 0.0;
//					this.motionZ = 0.0;
//					this.motionY = 0.0;
//					this.particleMaxAge = 200;
//					this.particleGravity = 0.0f;
//					this.noClip = true;
//					this.setRBGColorF(0.6f + this.rand.nextFloat() * 0.2f, 1.0f, 0.8f * this.rand.nextFloat() * 0.2f);
//				}
//
//				@Override
//				public void onUpdate() {
//					super.onUpdate();
//					this.angle += 0.03;
//					this.setPosition(this.axisX + 0.4 * Math.sin(this.angle), this.posY, this.axisZ + 0.4 * Math.cos(this.angle));
//					this.motionY = 0.0;
//					this.setAlphaF((float) Math.sin(3.14 * this.particleAge / this.particleMaxAge));
//				}
//
//				@Override
//				public int getFXLayer() {
//					return 0;
//				}
//			});
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
//			final int ticks = (int) (world.getTotalWorldTime() % 16L);
//			if (ticks == 0 && this.getUtil().getProcess().isInProgress()) {
//				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0) {
//					double axisX = 0.0;
//					double axisZ = 0.0;
//					double angle = 0.0;
//
//					{
//						this.motionX = 0.0;
//						this.motionZ = 0.0;
//						this.motionY = 0.012;
//						this.particleMaxAge = 50;
//						this.particleGravity = 0.0f;
//						this.noClip = true;
//						this.particleScale = 2.0f;
//						this.setParticleIcon((new BinnieIcon[] { Sequencer.fxSeqA, Sequencer.fxSeqG, Sequencer.fxSeqC, Sequencer.fxSeqT })[this.rand.nextInt(4)].getIcon());
//					}
//
//					@Override
//					public void onUpdate() {
//						super.onUpdate();
//						this.motionY = 0.012;
//						if (this.particleAge > 40) {
//							this.setAlphaF((50 - this.particleAge) / 10.0f);
//						}
//					}
//
//					@Override
//					public int getFXLayer() {
//						return 1;
//					}
//				});
//			}
        }
    }

    public static class SlotValidatorUnsequenced extends SlotValidator {
        public SlotValidatorUnsequenced() {
            super(ModuleMachine.IconSequencer);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            if (itemStack.getItem() == Genetics.itemSequencer) {
                final SequencerItem seq = new SequencerItem(itemStack);
                return seq.sequenced < 100;
            }
            return false;
        }

        @Override
        public String getTooltip() {
            return "Unsequenced DNA";
        }
    }
}
