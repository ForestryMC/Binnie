package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.minecraft.IMachineInformation;
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
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Splicer {
	public static int slotSerumVial = 0;
	public static int[] slotSerumReserve = new int[]{1, 2};
	public static int[] slotSerumExpended = new int[]{3, 4};
	public static int[] slotReserve = new int[]{5, 6, 7, 8, 9};
	public static int slotTarget = 9;
	public static int[] slotFinished = new int[]{10, 11, 12, 13};

	public static void setGene(IGene gene, ItemStack target, int chromoN) {
		int chromosomeID = gene.getChromosome().ordinal();
		if (chromosomeID >= EnumBeeChromosome.HUMIDITY.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
			chromosomeID--;
		}

		Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
		if (!cls.isInstance(gene.getAllele())) {
			return;
		}

		NBTTagCompound beeNBT = target.getTagCompound();
		NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
		NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
		NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
		chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
		target.setTagCompound(beeNBT);
	}

	public static class PackageSplicer extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
		public PackageSplicer() {
			super("splicer", GeneticsTexture.Splicer, 14819893, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Splicer);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(0, "serum.active");
			inventory.getSlot(0).forbidInteraction();
			inventory.getSlot(0).setReadOnly();
			SlotValidator serumValid = new SlotValidator(ModuleMachine.IconSerum) {
				@Override
				public boolean isValid(ItemStack itemStack) {
					return itemStack.getItem() instanceof IItemSerum;
				}

				@Override
				public String getTooltip() {
					return "Serum Vials & Arrays";
				}
			};

			inventory.getSlot(0).setValidator(serumValid);
			inventory.addSlotArray(Splicer.slotSerumReserve, "serum.input");
			for (InventorySlot slot : inventory.getSlots(Splicer.slotSerumReserve)) {
				slot.setValidator(serumValid);
				slot.forbidExtraction();
			}

			inventory.addSlotArray(Splicer.slotSerumExpended, "serum.output");
			for (InventorySlot slot : inventory.getSlots(Splicer.slotSerumExpended)) {
				slot.setValidator(serumValid);
				slot.setReadOnly();
			}

			inventory.addSlotArray(Splicer.slotReserve, "input");
			for (InventorySlot slot : inventory.getSlots(Splicer.slotReserve)) {
				slot.forbidExtraction();
				slot.setValidator(new ValidatorIndividualInoculate());
			}

			inventory.addSlot(9, "process");
			inventory.getSlot(9).setValidator(new ValidatorIndividualInoculate());
			inventory.getSlot(9).setReadOnly();
			inventory.getSlot(9).forbidInteraction();
			inventory.addSlotArray(Splicer.slotFinished, "output");
			for (InventorySlot slot : inventory.getSlots(Splicer.slotFinished)) {
				slot.setReadOnly();
				slot.forbidInsertion();
				slot.setValidator(new ValidatorIndividualInoculate());
			}

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Splicer.slotReserve, 9, 1);
			transfer.addRestock(Splicer.slotSerumReserve, 0);
			transfer.addStorage(0, Splicer.slotSerumExpended, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(ItemStack stack) {
					return Engineering.getCharges(stack) == 0;
				}
			});
			transfer.addStorage(9, Splicer.slotFinished, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(ItemStack stack) {
					return stack != null
						&& transfer.getMachine().getMachineUtil().getStack(0) != null
						&& transfer.getMachine().getInterface(ComponentSplicerLogic.class).isValidSerum() != null;
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
	}

	public static class ComponentSplicerLogic extends ComponentProcessSetCost implements IProcess {
		int nOfGenes;

		public ComponentSplicerLogic(Machine machine) {
			super(machine, 12000000, 1200);
			nOfGenes = 0;
		}

		@Override
		public int getProcessLength() {
			float n = getNumberOfGenes();
			if (n > 1.0f) {
				n = 1.0f + (n - 1.0f) * 0.5f;
			}
			// Fix for / by 0
			int temp = (int) (super.getProcessLength() * n);
			return temp != 0 ? temp : 1;
		}

		@Override
		public int getProcessEnergy() {
			float n = getNumberOfGenes();
			if (n > 1.0f) {
				n = 1.0f + (n - 1.0f) * 0.5f;
			}
			return (int) (super.getProcessEnergy() * n);
		}

		@Override
		public void onInventoryUpdate() {
			super.onInventoryUpdate();
			nOfGenes = getGenesToUse();
		}

		protected int getGenesToUse() {
			ItemStack serum = getUtil().getStack(0);
			ItemStack target = getUtil().getStack(9);
			if (serum == null || target == null) {
				return 1;
			}

			IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
			IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
			if (ind.getGenome().getSpeciesRoot() != ((IItemSerum) serum.getItem()).getSpeciesRoot(serum)) {
				return 1;
			}

			int i = 0;
			for (IGene gene : genes) {
				if (ind.getGenome().getActiveAllele(gene.getChromosome()) != gene.getAllele() || ind.getGenome().getInactiveAllele(gene.getChromosome()) != gene.getAllele()) {
					i++;
				}
			}
			return (i < 1) ? 1 : i;
		}

		private int getFullNumberOfGenes() {
			ItemStack serum = getUtil().getStack(0);
			if (serum == null) {
				return 1;
			}
			return Engineering.getGenes(serum).length;
		}

		private int getNumberOfGenes() {
			return nOfGenes;
		}

		@Override
		public String getTooltip() {
			int n = getNumberOfGenes();
			int f = getFullNumberOfGenes();
			return "Splicing in " + n + ((f > 1) ? ("/" + f) : "") + " gene" + ((n > 1) ? "s" : "");
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(9)) {
				return new ErrorState.NoItem("No Individual to Splice", 9);
			}
			if (getUtil().isSlotEmpty(0)) {
				return new ErrorState.NoItem("No Serum", 0);
			}

			ErrorState state = isValidSerum();
			if (state != null) {
				return state;
			}

			if (getUtil().getStack(0) != null && Engineering.getCharges(getUtil().getStack(0)) == 0) {
				return new ErrorState("Empty Serum", "Serum is empty");
			}
			return super.canWork();
		}

		public ErrorState isValidSerum() {
			ItemStack serum = getUtil().getStack(0);
			ItemStack target = getUtil().getStack(9);
			IGene[] genes = Engineering.getGenes(serum);
			if (genes.length == 0) {
				return new ErrorState("Invalid Serum", "Serum does not hold any genes");
			}
			if (!genes[0].getSpeciesRoot().isMember(target)) {
				return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
			}

			IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
			boolean hasAll = true;
			for (IGene gene : genes) {
				if (hasAll) {
					IGenome genome = individual.getGenome();
					IChromosomeType chromosome = gene.getChromosome();
					String geneAlleleUID = gene.getAllele().getUID();
					IAllele a = genome.getActiveAllele(chromosome);
					IAllele b = genome.getInactiveAllele(chromosome);
					hasAll = a.getUID().equals(geneAlleleUID)
						&& b.getUID().equals(geneAlleleUID);
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
			ItemStack serum = getUtil().getStack(0);
			ItemStack target = getUtil().getStack(9);
			IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
			if (!ind.isAnalyzed()) {
				ind.analyze();
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				ind.writeToNBT(nbttagcompound);
				target.setTagCompound(nbttagcompound);
			}

			IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
			for (IGene gene : genes) {
				Splicer.setGene(gene, target, 0);
				Splicer.setGene(gene, target, 1);
			}
			getUtil().damageItem(0, 1);
		}

		@Override
		protected void onTickTask() {
			// ignored
		}
	}

	public static class ComponentSplicerFX extends MachineComponent implements
		IRender.RandomDisplayTick,
		IRender.DisplayTick,
		IRender.Render,
		INetwork.TilePacketSync {
		private EntityItem dummyEntityItem;

		public ComponentSplicerFX(IMachine machine) {
			super(machine);
			dummyEntityItem = new EntityItem(null);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onDisplayTick(World world, int x, int y, int z, Random rand) {
			int tick = (int) (world.getTotalWorldTime() % 3L);
			if (tick == 0 && getUtil().getProcess().isInProgress()) {
				BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, x + 0.5, y + 1.5, z + 0.5, 0.0, 0.0, 0.0) {
					double axisX = posX;
					double axisZ = posZ;
					double angle = (int) (worldObj.getTotalWorldTime() % 4L) * 0.5 * 3.1415;

					{
						axisX = 0.0;
						axisZ = 0.0;
						angle = 0.0;
						motionX = 0.0;
						motionZ = 0.0;
						motionY = (rand.nextDouble() - 0.5) * 0.02;
						particleMaxAge = 240;
						particleGravity = 0.0f;
						noClip = true;
						setRBGColorF(0.3f + rand.nextFloat() * 0.5f, 0.3f + rand.nextFloat() * 0.5f, 0.0f);
					}

					@Override
					public void onUpdate() {
						super.onUpdate();
						double speed = 0.04;
						angle -= speed;
						double dist = 0.25 + 0.2 * Math.sin(particleAge / 50.0f);
						setPosition(axisX + dist * Math.sin(angle), posY, axisZ + dist * Math.cos(angle));
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

			ItemStack stack = getUtil().getStack(9);
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
			GL11.glTranslatef(0.0f, -0.25f, 0.0f);
			renderItem.doRender(this.dummyEntityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
			GL11.glPopMatrix();
		}

		@Override
		public void syncToNBT(NBTTagCompound nbt) {
			NBTTagCompound item = new NBTTagCompound();
			ItemStack stack = getUtil().getStack(9);
			if (stack != null) {
				stack.writeToNBT(item);
				nbt.setTag("item", item);
			}
		}

		@Override
		public void syncFromNBT(NBTTagCompound nbt) {
			if (nbt.hasKey("item")) {
				getUtil().setStack(9, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
			} else {
				getUtil().setStack(9, null);
			}
		}

		@Override
		public void onInventoryUpdate() {
			if (!getUtil().isServer()) {
				return;
			}
			getUtil().refreshBlock();
		}
	}

	public static class ValidatorIndividualInoculate extends SlotValidator {
		public ValidatorIndividualInoculate() {
			super(null);
		}

		@Override
		public boolean isValid(ItemStack object) {
			ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
			return root != null && Binnie.Genetics.getSystem(root).isDNAManipulable(object);
		}

		@Override
		public String getTooltip() {
			return "Splicable Individual";
		}
	}
}
