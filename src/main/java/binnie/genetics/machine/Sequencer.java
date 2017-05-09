package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.resource.BinnieIcon;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class Sequencer {
	public static final int[] SLOT_RESERVE = new int[]{1, 2, 3, 4};
	public static final int SLOT_DYE_INDEX = 0;
	public static final int SLOT_TARGET_INDEX = 5;
	public static final int SLOT_DONE_INDEX = 6;

	public static BinnieIcon fxSeqA;
	public static BinnieIcon fxSeqG;
	public static BinnieIcon fxSeqT;
	public static BinnieIcon fxSeqC;

	private static ComponentSequencerLogic logic;

	public static class PackageSequencer extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageSequencer() {
			super("sequencer", GeneticsTexture.Sequencer, 0xb7ff32, true);
			Sequencer.fxSeqA = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.a");
			Sequencer.fxSeqG = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.g");
			Sequencer.fxSeqT = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.t");
			Sequencer.fxSeqC = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.c");
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Sequencer);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);

			InventorySlot slotDye = inventory.addSlot(SLOT_DYE_INDEX, "dye");
			slotDye.setValidator(new SlotValidator.Item(GeneticsItems.FluorescentDye.get(1), ModuleMachine.IconDye));
			slotDye.forbidExtraction();

			inventory.addSlotArray(SLOT_RESERVE, "input");
			for (InventorySlot slot : inventory.getSlots(SLOT_RESERVE)) {
				slot.setValidator(new SlotValidatorUnsequenced());
				slot.forbidExtraction();
			}

			InventorySlot slotTarget = inventory.addSlot(SLOT_TARGET_INDEX, "process");
			slotTarget.setValidator(new SlotValidatorUnsequenced());
			slotTarget.setReadOnly();
			slotTarget.forbidInteraction();

			InventorySlot slotDone = inventory.addSlot(SLOT_DONE_INDEX, "output");
			slotDone.setReadOnly();

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(SLOT_RESERVE, SLOT_TARGET_INDEX, 1);

			new ComponentChargedSlots(machine).addCharge(SLOT_DYE_INDEX);
			new ComponentPowerReceptor(machine, 10000);
			logic = new ComponentSequencerLogic(machine);
			new ComponentSequencerFX(machine);

			transfer.setTransferListener(logic);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentSequencerLogic extends ComponentProcess implements IProcess, ComponentInventoryTransfer.ITransferRestockListener {
		public ComponentSequencerLogic(Machine machine) {
			super(machine);
		}

		public float getSequenceStrength() {
			ItemStack stack = getUtil().getStack(SLOT_TARGET_INDEX);
			if (stack == null) {
				return 1.0f;
			}

			float mult = 1.0f - stack.getItemDamage() % 6 / 5.0f;
			return 1.0f - mult * mult * 0.75f;
		}

		@Override
		public int getProcessLength() {
			return (int) (19200.0f * getSequenceStrength());
		}

		@Override
		public int getProcessEnergy() {
			return getProcessLength() * 20;
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(SLOT_TARGET_INDEX)) {
				return new ErrorState.NoItem("No DNA sequence", SLOT_TARGET_INDEX);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (getMachine().getOwner() == null) {
				return new ErrorState("No Owner", "Replace this block to claim this machine");
			}
			if (getUtil().getSlotCharge(SLOT_DYE_INDEX) == 0.0f) {
				return new ErrorState.NoItem("Insufficient Dye", SLOT_DYE_INDEX);
			}

			ItemStack stack = getUtil().getStack(SLOT_DONE_INDEX);
			if (stack != null && stack.stackSize >= 64) {
				return new ErrorState.NoSpace("No space for empty sequences", new int[]{SLOT_DONE_INDEX});
			}
			return super.canProgress();
		}

		@Override
		public void onRestock(int target) {
			onStartTask();
		}

		@Override
		protected void onStartTask() {
			super.onStartTask();
			ItemStack item = getUtil().getStack(SLOT_TARGET_INDEX);
			SequencerItem seqItem = new SequencerItem(item);
			int seq = seqItem.sequenced;
			if (seq != 0) {
				setProgress(seq);
			}
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			updateSequence();
			SequencerItem seqItem = new SequencerItem(getUtil().getStack(SLOT_TARGET_INDEX));
			GeneTracker.getTracker(getMachine().getWorld(), getMachine().getOwner()).registerGene(seqItem.getGene());
			getUtil().decreaseStack(SLOT_TARGET_INDEX, 1);

			if (getUtil().getStack(SLOT_DONE_INDEX) == null) {
				getUtil().setStack(SLOT_DONE_INDEX, GeneticsItems.EmptySequencer.get(1));
			} else {
				getUtil().decreaseStack(SLOT_DONE_INDEX, -1);
			}
		}

		@Override
		protected void onTickTask() {
			updateSequence();
			getUtil().useCharge(SLOT_DYE_INDEX, 0.4f * getProgressPerTick() / 100.0f);
		}

		private void updateSequence() {
			int prog = (int) getProgress();
			ItemStack item = getUtil().getStack(SLOT_TARGET_INDEX);
			SequencerItem seqItem = new SequencerItem(item);
			int seq = seqItem.sequenced;
			if (prog != seq) {
				seqItem.sequenced = prog;
				seqItem.writeToItem(item);
			}
		}
	}

	public static class ComponentSequencerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
		public ComponentSequencerFX(IMachine machine) {
			super(machine);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
			if (!getUtil().getProcess().isInProgress()) {
				return;
			}

			BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.2 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
				double axisX = posX;
				double axisZ = posZ;
				double angle = rand.nextDouble() * 2.0 * 3.1415;

				{
					axisX = 0.0;
					axisZ = 0.0;
					angle = 0.0;
					motionX = 0.0;
					motionZ = 0.0;
					motionY = 0.0;
					particleMaxAge = 200;
					particleGravity = 0.0f;
					noClip = true;
					setRBGColorF(0.6f + rand.nextFloat() * 0.2f, 1.0f, 0.8f * rand.nextFloat() * 0.2f);
				}

				@Override
				public void onUpdate() {
					super.onUpdate();
					angle += 0.03;
					setPosition(axisX + 0.4 * Math.sin(angle), posY, axisZ + 0.4 * Math.cos(angle));
					motionY = 0.0;
					setAlphaF((float) Math.sin(3.14 * particleAge / particleMaxAge));
				}

				@Override
				public int getFXLayer() {
					return 0;
				}
			});
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			int ticks = (int) (world.getTotalWorldTime() % 16L);
			if (ticks == 0 && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1, z + 0.5, 0.0, 0.0, 0.0) {
					{
						motionX = 0.0;
						motionZ = 0.0;
						motionY = 0.012;
						particleMaxAge = 50;
						particleGravity = 0.0f;
						noClip = true;
						particleScale = 2.0f;

						setParticleIcon((new BinnieIcon[]{
							fxSeqA,
							fxSeqG,
							fxSeqC, 
							fxSeqT
						})[rand.nextInt(4)].getIcon());
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						motionY = 0.012;
						if (particleAge > 40) {
							setAlphaF((50 - particleAge) / 10.0f);
						}
					}

					@Override
					public int getFXLayer() {
						return 1;
					}
				});
			}
		}
	}

	public static class SlotValidatorUnsequenced extends SlotValidator {
		public SlotValidatorUnsequenced() {
			super(ModuleMachine.IconSequencer);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			if (itemStack.getItem() == Genetics.itemSequencer) {
				SequencerItem seq = new SequencerItem(itemStack);
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
