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
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.api.IItemAnalysable;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Analyser {
	public static int[] slotReserve = new int[]{0, 1, 2, 3, 4, 5};
	public static int[] slotFinished = new int[]{7, 8, 9, 10, 11, 12};
	public static int slotTarget = 6;
	public static int slotDye = 13;

	static {
	}

	public static boolean isAnalysable(ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		return ind != null || stack.getItem() instanceof IItemAnalysable || Binnie.Genetics.getConversion(stack) != null;
	}

	public static boolean isAnalysed(ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		if (ind != null) {
			return ind.isAnalyzed();
		}
		return stack.getItem() instanceof IItemAnalysable && ((IItemAnalysable) stack.getItem()).isAnalysed(stack);
	}

	public static ItemStack analyse(ItemStack stack) {
		ItemStack conv = Binnie.Genetics.getConversionStack(stack);
		if (conv != null) {
			stack = conv;
		}

		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		if (root != null) {
			IIndividual ind = root.getMember(stack);
			ind.analyze();
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			ind.writeToNBT(nbttagcompound);
			stack.setTagCompound(nbttagcompound);
			return stack;
		}

		if (stack.getItem() instanceof IItemAnalysable) {
			return ((IItemAnalysable) stack.getItem()).analyse(stack);
		}
		return stack;
	}

	public static class PackageAnalyser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageAnalyser() {
			super("analyser", GeneticsTexture.Analyser, 9961727, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Analyser);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Analyser.slotReserve, "input");
			for (InventorySlot slot : inventory.getSlots(Analyser.slotReserve)) {
				slot.setValidator(new SlotValidatorUnanalysed());
				slot.forbidExtraction();
			}

			inventory.addSlot(6, "analyse");
			inventory.getSlot(6).setValidator(new SlotValidatorUnanalysed());
			inventory.getSlot(6).setReadOnly();
			inventory.getSlot(6).forbidInteraction();
			inventory.addSlot(13, "dye");
			inventory.getSlot(13).forbidExtraction();
			inventory.getSlot(13).setValidator(new SlotValidator(ModuleMachine.IconDye) {
				@Override
				public boolean isValid(ItemStack itemStack) {
					return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
				}

				@Override
				public String getTooltip() {
					return "DNA Dye";
				}
			});

			inventory.addSlotArray(Analyser.slotFinished, "output");
			for (InventorySlot slot : inventory.getSlots(Analyser.slotFinished)) {
				slot.forbidInsertion();
				slot.setReadOnly();
			}

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Analyser.slotReserve, 6, 1);
			transfer.addStorage(6, Analyser.slotFinished, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(ItemStack stack) {
					return Analyser.isAnalysed(stack);
				}
			});

			new ComponentChargedSlots(machine).addCharge(13);
			new ComponentPowerReceptor(machine, 500);
			new ComponentAnalyserLogic(machine);
			new ComponentAnalyserFX(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentAnalyserLogic extends ComponentProcessSetCost implements IProcess {
		private static float DYE_PER_TICK = 0.002f;

		public ComponentAnalyserLogic(Machine machine) {
			super(machine, 9000, 300);
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(6)) {
				return new ErrorState.NoItem("No item to analyse", 6);
			}

			boolean analysed = Analyser.isAnalysed(getUtil().getStack(6));
			if (analysed) {
				return new ErrorState.InvalidItem("Already Analysed", "Item has already been analysed", 6);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (getUtil().getSlotCharge(13) == 0.0f) {
				return new ErrorState.Item("Insufficient Dye", "Not enough DNA dye to analyse", new int[]{13});
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			ItemStack itemStack = getUtil().getStack(6);
			itemStack = Analyser.analyse(itemStack);
			getInventory().setInventorySlotContents(6, itemStack);
		}

		@Override
		protected void onTickTask() {
			getUtil().useCharge(13, 0.002f);
		}
	}

	public static class SlotValidatorUnanalysed extends SlotValidator {
		public SlotValidatorUnanalysed() {
			super(null);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Analyser.isAnalysable(itemStack) && !Analyser.isAnalysed(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Unanalysed Item";
		}
	}

	public static class ComponentAnalyserFX extends MachineComponent implements
		IRender.RandomDisplayTick,
		IRender.DisplayTick,
		IRender.Render,
		INetwork.TilePacketSync {
		private EntityItem dummyEntityItem;

		public ComponentAnalyserFX(IMachine machine) {
			super(machine);
			dummyEntityItem = new EntityItem(null);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
			// ignored
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			if (rand.nextFloat() < 1.0f && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.3 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
					double axisX = posX;
					double axisZ = posZ;
					double angle = rand.nextDouble() * 2.0 * 3.1415;

					{
						axisX = 0.0;
						axisZ = 0.0;
						angle = 0.0;
						motionX = 0.05 * (rand.nextDouble() - 0.5);
						motionZ = 0.05 * (rand.nextDouble() - 0.5);
						motionY = 0.0;
						particleMaxAge = 25;
						particleGravity = 0.05f;
						noClip = true;
						setRBGColorF(0.6f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						setAlphaF((float) Math.cos(1.57 * particleAge / particleMaxAge));
					}

					@Override
					public int getFXLayer() {
						return 0;
					}
				});
			}
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void renderInWorld(RenderItem renderItem, double x, double y, double z) {
			if (!getUtil().getProcess().isInProgress()) {
				return;
			}

			ItemStack stack = getUtil().getStack(6);
			dummyEntityItem.worldObj = getMachine().getWorld();
			dummyEntityItem.setEntityItemStack(stack);
			EntityItem dummyEntityItem = this.dummyEntityItem;
			dummyEntityItem.age++;
			this.dummyEntityItem.hoverStart = 0.0f;
			if (stack == null) {
				return;
			}

			EntityPlayer player = BinnieCore.proxy.getPlayer();
			double dx = x + 0.5 - player.lastTickPosX;
			double dz = z + 0.5 - player.lastTickPosZ;
			double t = Math.atan2(dz, dx) * 180.0 / 3.1415;
			GL11.glPushMatrix();
			GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, -0.2f, 0.0f);
			renderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
			GL11.glPopMatrix();
		}

		@Override
		public void syncToNBT(NBTTagCompound nbt) {
			NBTTagCompound item = new NBTTagCompound();
			ItemStack stack = getUtil().getStack(6);
			if (stack == null) {
				return;
			}

			stack.writeToNBT(item);
			nbt.setTag("item", item);
		}

		@Override
		public void syncFromNBT(NBTTagCompound nbt) {
			if (nbt.hasKey("item")) {
				getUtil().setStack(6, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
			} else {
				getUtil().setStack(6, null);
			}
		}

		@Override
		public void onInventoryUpdate() {
			if (!getUtil().isServer()) {
				return;
			}

			IMachine machine = getMachine();
			TileEntity tile = machine.getTileEntity();
			machine.getWorld().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		}
	}
}
