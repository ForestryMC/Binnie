// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.machine;

import net.minecraft.nbt.NBTBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.particle.EntityFX;
import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.entity.item.EntityItem;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.component.IRender;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.genetics.item.GeneticsItems;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.genetics.core.GeneticsGUI;
import binnie.core.machines.Machine;
import binnie.genetics.core.GeneticsTexture;
import binnie.craftgui.minecraft.IMachineInformation;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.nbt.NBTTagCompound;
import forestry.api.genetics.IIndividual;
import binnie.Binnie;
import binnie.genetics.api.IItemAnalysable;
import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;

public class Analyser
{
	public static final int[] slotReserve;
	public static final int slotTarget = 6;
	public static final int[] slotFinished;
	public static final int slotDye = 13;

	public static boolean isAnalysable(final ItemStack stack) {
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		return ind != null || stack.getItem() instanceof IItemAnalysable || Binnie.Genetics.getConversion(stack) != null;
	}

	public static boolean isAnalysed(final ItemStack stack) {
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		if (ind != null) {
			return ind.isAnalyzed();
		}
		return stack.getItem() instanceof IItemAnalysable && ((IItemAnalysable) stack.getItem()).isAnalysed(stack);
	}

	public static ItemStack analyse(ItemStack stack) {
		final ItemStack conv = Binnie.Genetics.getConversionStack(stack);
		if (conv != null) {
			stack = conv;
		}
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		if (root != null) {
			final IIndividual ind = root.getMember(stack);
			ind.analyze();
			final NBTTagCompound nbttagcompound = new NBTTagCompound();
			ind.writeToNBT(nbttagcompound);
			stack.setTagCompound(nbttagcompound);
			return stack;
		}
		if (stack.getItem() instanceof IItemAnalysable) {
			return ((IItemAnalysable) stack.getItem()).analyse(stack);
		}
		return stack;
	}

	static {
		slotReserve = new int[] { 0, 1, 2, 3, 4, 5 };
		slotFinished = new int[] { 7, 8, 9, 10, 11, 12 };
	}

	public static class PackageAnalyser extends GeneticMachine.PackageGeneticBase implements IMachineInformation
	{
		public PackageAnalyser() {
			super("analyser", GeneticsTexture.Analyser, 9961727, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Analyser);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlotArray(Analyser.slotReserve, "input");
			for (final InventorySlot slot : inventory.getSlots(Analyser.slotReserve)) {
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
				public boolean isValid(final ItemStack itemStack) {
					return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
				}

				@Override
				public String getTooltip() {
					return "DNA Dye";
				}
			});
			inventory.addSlotArray(Analyser.slotFinished, "output");
			for (final InventorySlot slot : inventory.getSlots(Analyser.slotFinished)) {
				slot.forbidInsertion();
				slot.setReadOnly();
			}
			final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Analyser.slotReserve, 6, 1);
			transfer.addStorage(6, Analyser.slotFinished, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(final ItemStack stack) {
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

		@Override
		public void register() {
		}
	}

	public static class ComponentAnalyserLogic extends ComponentProcessSetCost implements IProcess
	{
		private static final float DYE_PER_TICK = 0.002f;

		public ComponentAnalyserLogic(final Machine machine) {
			super(machine, 9000, 300);
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isSlotEmpty(6)) {
				return new ErrorState.NoItem("No item to analyse", 6);
			}
			final boolean analysed = Analyser.isAnalysed(this.getUtil().getStack(6));
			if (analysed) {
				return new ErrorState.InvalidItem("Already Analysed", "Item has already been analysed", 6);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (this.getUtil().getSlotCharge(13) == 0.0f) {
				return new ErrorState.Item("Insufficient Dye", "Not enough DNA dye to analyse", new int[] { 13 });
			}
			return super.canProgress();
		}

		@Override
		protected void onFinishTask() {
			super.onFinishTask();
			ItemStack itemStack = this.getUtil().getStack(6);
			itemStack = Analyser.analyse(itemStack);
			this.getInventory().setInventorySlotContents(6, itemStack);
		}

		@Override
		protected void onTickTask() {
			this.getUtil().useCharge(13, 0.002f);
		}
	}

	public static class SlotValidatorUnanalysed extends SlotValidator
	{
		public SlotValidatorUnanalysed() {
			super(null);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return Analyser.isAnalysable(itemStack) && !Analyser.isAnalysed(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Unanalysed Item";
		}
	}

	public static class ComponentAnalyserFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync
	{
		private final EntityItem dummyEntityItem;

		public ComponentAnalyserFX(final IMachine machine) {
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
			if (rand.nextFloat() < 1.0f && this.getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.3 + rand.nextDouble() * 0.2, z + 0.5, 0.0, 0.0, 0.0) {
					double axisX = this.posX;
					double axisZ = this.posZ;
					double angle = this.rand.nextDouble() * 2.0 * 3.1415;

					{
						this.axisX = 0.0;
						this.axisZ = 0.0;
						this.angle = 0.0;
						this.motionX = 0.05 * (this.rand.nextDouble() - 0.5);
						this.motionZ = 0.05 * (this.rand.nextDouble() - 0.5);
						this.motionY = 0.0;
						this.particleMaxAge = 25;
						this.particleGravity = 0.05f;
						this.noClip = true;
						this.setRBGColorF(0.6f, 0.0f, 1.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
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
		public void renderInWorld(final RenderItem customRenderItem, final double x, final double y, final double z) {
			if (!this.getUtil().getProcess().isInProgress()) {
				return;
			}
			final ItemStack stack = this.getUtil().getStack(6);
			this.dummyEntityItem.worldObj = this.getMachine().getWorld();
			this.dummyEntityItem.setEntityItemStack(stack);
			final EntityItem dummyEntityItem = this.dummyEntityItem;
			++dummyEntityItem.age;
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
			GL11.glTranslatef(0.0f, -0.2f, 0.0f);
			customRenderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
			GL11.glPopMatrix();
		}

		@Override
		public void syncToNBT(final NBTTagCompound nbt) {
			final NBTTagCompound item = new NBTTagCompound();
			final ItemStack stack = this.getUtil().getStack(6);
			if (stack != null) {
				stack.writeToNBT(item);
				nbt.setTag("item", item);
			}
		}

		@Override
		public void syncFromNBT(final NBTTagCompound nbt) {
			if (nbt.hasKey("item")) {
				this.getUtil().setStack(6, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
			}
			else {
				this.getUtil().setStack(6, null);
			}
		}

		@Override
		public void onInventoryUpdate() {
			if (this.getUtil().isServer()) {
				this.getMachine().getWorld().markBlockForUpdate(this.getMachine().getTileEntity().xCoord, this.getMachine().getTileEntity().yCoord, this.getMachine().getTileEntity().zCoord);
			}
		}
	}
}
