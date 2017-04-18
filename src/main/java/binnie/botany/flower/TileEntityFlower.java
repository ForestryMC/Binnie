// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.flower;

import binnie.botany.api.IFlowerColour;

import java.util.HashSet;
import java.util.Set;

import forestry.api.core.IErrorState;

import net.minecraft.world.biome.BiomeGenBase;

import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.api.IFlowerType;
import binnie.botany.genetics.EnumFlowerColor;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

import net.minecraft.world.World;

import forestry.lepidopterology.entities.EntityButterfly;
import binnie.botany.gardening.BlockPlant;

import net.minecraft.entity.item.EntityItem;

import binnie.botany.api.EnumFlowerStage;

import binnie.botany.network.MessageFlowerUpdate;

import net.minecraft.network.Packet;

import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.EnumSoilType;

import net.minecraft.init.Blocks;

import binnie.botany.Botany;

import java.util.Random;

import net.minecraft.block.Block;

import binnie.botany.gardening.Gardening;
import binnie.botany.api.IAlleleFlowerSpecies;
import forestry.api.genetics.IIndividual;

import net.minecraftforge.common.EnumPlantType;

import java.util.EnumSet;

import binnie.botany.core.BotanyCore;

import net.minecraft.item.ItemStack;

import binnie.Binnie;
import binnie.core.BinnieCore;

import net.minecraft.nbt.NBTUtil;

import binnie.botany.genetics.Flower;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.lepidopterology.IButterfly;

import com.mojang.authlib.GameProfile;

import binnie.botany.api.IFlower;
import forestry.api.lepidopterology.IButterflyNursery;
import forestry.api.genetics.IPollinatable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class TileEntityFlower extends TileEntity implements IPollinatable, IButterflyNursery
{
	IFlower flower;
	GameProfile owner;
	int section;
	RenderInfo renderInfo;
	IButterfly caterpillar;
	int matureTime;

	public TileEntityFlower() {
		this.flower = null;
		this.section = 0;
		this.renderInfo = null;
		this.matureTime = 0;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		this.flower = new Flower(nbttagcompound);
		this.section = nbttagcompound.getByte("section");
		if (this.flower.getAge() == 0) {
			this.flower.setFlowered(false);
		}
		this.owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
		if (nbttagcompound.hasKey("CATER") && BinnieCore.isLepidopteryActive()) {
			this.matureTime = nbttagcompound.getInteger("caterTime");
			this.caterpillar = Binnie.Genetics.getButterflyRoot().getMember(nbttagcompound.getCompoundTag("cater"));
		}
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		if (this.flower != null) {
			this.flower.writeToNBT(nbttagcompound);
		}
		if (this.owner != null) {
			final NBTTagCompound nbt = new NBTTagCompound();
			NBTUtil.func_152460_a(nbt, this.owner);
			nbttagcompound.setTag("owner", nbt);
		}
		if (this.caterpillar != null) {
			nbttagcompound.setInteger("caterTime", this.matureTime);
			final NBTTagCompound subcompound = new NBTTagCompound();
			this.caterpillar.writeToNBT(subcompound);
			nbttagcompound.setTag("cater", subcompound);
		}
		nbttagcompound.setByte("section", (byte) this.getSection());
		super.writeToNBT(nbttagcompound);
	}

	public void create(final ItemStack stack, final GameProfile owner) {
		this.create(BotanyCore.getFlowerRoot().getMember(stack), owner);
	}

	public void create(final IFlower stack, final GameProfile owner) {
		this.flower = stack;
		if (this.flower.getAge() == 0) {
			this.flower.setFlowered(false);
		}
		this.updateRender(true);
		this.owner = owner;
	}

	@Override
	public EnumSet<EnumPlantType> getPlantType() {
		return EnumSet.of(EnumPlantType.Plains);
	}

	@Override
	public IIndividual getPollen() {
		return this.getFlower();
	}

	@Override
	public boolean canMateWith(final IIndividual individual) {
		return this.isBreeding() && individual instanceof IFlower && this.getFlower() != null && this.getFlower().getMate() == null && this.getFlower().hasFlowered() && !this.getFlower().isGeneticEqual(individual);
	}

	@Override
	public void mateWith(final IIndividual individual) {
		if (this.getFlower() == null || !(individual instanceof IFlower)) {
			return;
		}
		final IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) individual.getGenome().getPrimary();
		final IAlleleFlowerSpecies primary2 = this.getFlower().getGenome().getPrimary();
		if (primary == primary2 || this.worldObj.rand.nextInt(4) == 0) {
			this.getFlower().mate((IFlower) individual);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public IFlower getFlower() {
		if (this.getSection() <= 0) {
			return this.flower;
		}
		final TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord - this.getSection(), this.zCoord);
		if (tile instanceof TileEntityFlower) {
			return ((TileEntityFlower) tile).getFlower();
		}
		return null;
	}

	public boolean isBreeding() {
		final Block roots = this.getWorldObj().getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
		return Gardening.isSoil(roots);
	}

	public void randomUpdate(final Random rand) {
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != Botany.flower) {
			this.invalidate();
			return;
		}
		if (this.getSection() > 0) {
			return;
		}
		if (this.flower == null) {
			return;
		}
		if (this.flower.getGenome() == null) {
			this.invalidate();
			return;
		}
		if (!this.isBreeding()) {
			return;
		}
		float light = this.worldObj.getBlockLightValue(this.xCoord, this.yCoord, this.zCoord);
		if (light < 6.0f) {
			for (int dx = -2; dx <= 2; ++dx) {
				for (int dy = -2; dy <= 2; ++dy) {
					light -= (this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord) ? 0.0f : 0.5f);
				}
			}
		}
		final boolean canTolerate = Gardening.canTolerate(this.getFlower(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		final EnumSoilType soil = Gardening.getSoilType(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		if (rand.nextFloat() < this.getFlower().getGenome().getAgeChance()) {
			if (this.flower.getAge() < 1) {
				if (canTolerate && light > 6.0f) {
					this.doFlowerAge();
				}
			}
			else {
				this.doFlowerAge();
			}
		}
		if (canTolerate && this.flower.getAge() > 1 && !this.flower.isWilted() && light > 6.0f) {
			this.flower.setFlowered(true);
		}
		if (!canTolerate && this.flower.isWilted() && rand.nextInt(2 + Math.max(this.flower.getAge(), 2)) == 0) {
			this.kill();
			return;
		}
		if (light < 2.0f && this.flower.isWilted()) {
			this.kill();
			return;
		}
		if (!canTolerate || light < 1.0f) {
			this.flower.setWilted(true);
		}
		else {
			this.flower.setWilted(false);
		}
		float CHANCE_DISPERSAL = 0.8f;
		CHANCE_DISPERSAL += 0.2f * this.flower.getGenome().getFertility();
		CHANCE_DISPERSAL *= 1.0f + soil.ordinal() * 0.5f;
		float CHANCE_POLLINATE = 1.0f;
		CHANCE_POLLINATE += 0.25f * this.flower.getGenome().getFertility();
		CHANCE_POLLINATE *= 1.0f + soil.ordinal() * 0.5f;
		final float CHANCE_SELFPOLLINATE = 0.2f * CHANCE_POLLINATE;
		if (this.worldObj.rand.nextFloat() < CHANCE_DISPERSAL && this.flower.hasFlowered() && !this.flower.isWilted()) {
			final IFlowerGenome mate = this.flower.getMate();
			if (mate != null) {
				boolean dispersed = false;
				for (int a = 0; a < 5 && !dispersed; ++a) {
					int dx2;
					int dz;
					for (dx2 = 0, dz = 0; dx2 == 0 && dz == 0; dx2 = rand.nextInt(3) - 1, dz = rand.nextInt(3) - 1) {
					}
					final Block b = this.getWorldObj().getBlock(this.xCoord + dx2, this.yCoord, this.zCoord + dz);
					final Block b2 = this.getWorldObj().getBlock(this.xCoord + dx2, this.yCoord - 1, this.zCoord + dz);
					if (b == Blocks.air && Gardening.isSoil(b2)) {
						final IFlower offspring = this.flower.getOffspring(this.getWorldObj());
						if (offspring != null) {
							Gardening.plant(this.getWorldObj(), this.xCoord + dx2, this.yCoord, this.zCoord + dz, offspring, this.getOwner());
							this.flower.removeMate();
							dispersed = true;
						}
					}
				}
			}
		}
		if (this.worldObj.rand.nextFloat() < CHANCE_POLLINATE && this.flower.hasFlowered() && !this.flower.isWilted()) {
			for (int a2 = 0; a2 < 4; ++a2) {
				int dx3;
				int dz2;
				for (dx3 = 0, dz2 = 0; dx3 == 0 && dz2 == 0; dx3 = rand.nextInt(5) - 2, dz2 = rand.nextInt(5) - 2) {
				}
				final TileEntity tile = this.getWorldObj().getTileEntity(this.xCoord + dx3, this.yCoord, this.zCoord + dz2);
				if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(this.getFlower())) {
					((IPollinatable) tile).mateWith(this.getFlower());
				}
			}
		}
		if (this.worldObj.rand.nextFloat() < CHANCE_SELFPOLLINATE && this.flower.hasFlowered() && this.flower.getMate() == null) {
			this.mateWith(this.getFlower());
		}
		this.spawnButterflies();
		this.matureCaterpillar();
		this.checkIfDead(false);
		this.updateRender(true);
	}

	private void doFlowerAge() {
		this.getFlower().age();
		if (this.getFlower().getAge() == 1) {
			Gardening.onGrowFromSeed(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if (this.getOwner() != null && this.getFlower() != null) {
				BotanyCore.getFlowerRoot().getBreedingTracker(this.getWorldObj(), this.getOwner()).registerBirth(this.getFlower());
			}
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		if (this.renderInfo == null && this.getFlower() != null && this.getFlower().getGenome() != null) {
			this.renderInfo = new RenderInfo(this.getFlower(), this);
		}
		return (this.renderInfo != null) ? Botany.instance.getNetworkWrapper().getPacketFrom(new MessageFlowerUpdate(this.xCoord, this.yCoord, this.zCoord, this.renderInfo).getMessage()) : null;
	}

	public void updateRender(final boolean update) {
		if (update && this.getFlower() != null && this.getFlower().getGenome() != null) {
			final RenderInfo newInfo = new RenderInfo(this.getFlower(), this);
			if (this.renderInfo == null || !newInfo.equals(this.renderInfo)) {
				this.setRender(newInfo);
			}
		}
		final TileEntity above = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
		if (above instanceof TileEntityFlower) {
			((TileEntityFlower) above).updateRender(true);
		}
	}

	public int getSection() {
		return this.section;
	}

	public void setSection(final int i) {
		this.section = i;
		if (BinnieCore.proxy.isSimulating(this.worldObj)) {
			this.updateRender(true);
		}
	}

	public ItemStack getItemStack() {
		if (this.flower == null) {
			return null;
		}
		return Binnie.Genetics.getFlowerRoot().getMemberStack(this.getFlower(), (this.flower.getAge() == 0) ? EnumFlowerStage.SEED.ordinal() : EnumFlowerStage.FLOWER.ordinal());
	}

	private TileEntityFlower getRoot() {
		if (this.getSection() == 0) {
			return null;
		}
		final TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord - this.getSection(), this.zCoord);
		return (tile instanceof TileEntityFlower) ? ((TileEntityFlower) tile) : null;
	}

	public void onShear() {
		if (this.getRoot() != null) {
			this.getRoot().onShear();
		}
		if (this.getFlower() != null && this.getFlower().getAge() > 1) {
			final Random rand = new Random();
			final IFlower cutting = (IFlower) this.getFlower().copy();
			cutting.setAge(0);
			final ItemStack cuttingStack = BotanyCore.getFlowerRoot().getMemberStack(cutting, EnumFlowerStage.SEED.ordinal());
			final float f = 0.7f;
			final double d = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final double d2 = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final double d3 = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + d, this.yCoord + d2, this.zCoord + d3, cuttingStack);
			entityitem.delayBeforeCanPickup = 10;
			this.worldObj.spawnEntityInWorld(entityitem);
			for (int maxAge = this.getFlower().getMaxAge(), i = 0; i < maxAge; ++i) {
				if (rand.nextBoolean()) {
					this.getFlower().age();
					if (this.checkIfDead(true)) {
						return;
					}
				}
			}
		}
	}

	public boolean checkIfDead(final boolean wasCut) {
		if (this.getSection() != 0) {
			return this.getRoot().checkIfDead(wasCut);
		}
		final EnumSoilType soil = Gardening.getSoilType(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		final int maxAge = (int) (this.flower.getMaxAge() * (1.0f + soil.ordinal() * 0.25f));
		if (this.flower.getAge() > maxAge) {
			if (!wasCut && this.flower.getMate() != null) {
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
				final IFlower offspring = this.flower.getOffspring(this.getWorldObj());
				final TileEntity above = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
				if (above instanceof TileEntityFlower) {
					this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
				}
				Gardening.plant(this.worldObj, this.xCoord, this.yCoord, this.zCoord, offspring, this.getOwner());
			}
			else {
				this.kill();
			}
			return true;
		}
		return false;
	}

	public void kill() {
		if (this.flower.getAge() > 0) {
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Botany.plant, BlockPlant.Type.DeadFlower.ordinal(), 2);
		}
		else {
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		}
		for (int i = 1; this.worldObj.getTileEntity(this.xCoord, this.yCoord + i, this.zCoord) instanceof TileEntityFlower; ++i) {
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord + i, this.zCoord);
		}
	}

	public boolean onBonemeal() {
		if (this.getFlower() == null) {
			return false;
		}
		if (!this.isBreeding()) {
			return false;
		}
		if (this.getFlower().isWilted()) {
			return false;
		}
		this.doFlowerAge();
		if (this.getFlower().getAge() > 1 && !this.getFlower().hasFlowered()) {
			this.getFlower().setFlowered(true);
		}
		this.checkIfDead(false);
		this.updateRender(true);
		return true;
	}

	public GameProfile getOwner() {
		return this.owner;
	}

	public void setOwner(final GameProfile ownerName) {
		this.owner = ownerName;
	}

	public void spawnButterflies() {
		if (!BinnieCore.isLepidopteryActive()) {
			return;
		}
		final int x = this.xCoord;
		final int y = this.yCoord;
		final int z = this.zCoord;
		final World world = this.worldObj;
		if (this.getCaterpillar() != null) {
			return;
		}
		if (world.rand.nextFloat() >= this.getFlower().getGenome().getSappiness()) {
			return;
		}
		if (this.worldObj.rand.nextFloat() >= 0.2f) {
			return;
		}
		final IButterfly spawn = Binnie.Genetics.getButterflyRoot().getIndividualTemplates().get(this.worldObj.rand.nextInt(Binnie.Genetics.getButterflyRoot().getIndividualTemplates().size()));
		if (this.worldObj.rand.nextFloat() >= spawn.getGenome().getPrimary().getRarity() * 0.5f) {
			return;
		}
		if (this.worldObj.countEntities(EntityButterfly.class) > 100) {
			return;
		}
		if (!spawn.canSpawn(this.worldObj, x, y, z)) {
			return;
		}
		if (world.isAirBlock(x, y + 1, z)) {
			this.attemptButterflySpawn(world, spawn, x, y + 1, z);
		}
		else if (world.isAirBlock(x - 1, y, z)) {
			this.attemptButterflySpawn(world, spawn, x - 1, y, z);
		}
		else if (world.isAirBlock(x + 1, y, z)) {
			this.attemptButterflySpawn(world, spawn, x + 1, y, z);
		}
		else if (world.isAirBlock(x, y, z - 1)) {
			this.attemptButterflySpawn(world, spawn, x, y, z - 1);
		}
		else if (world.isAirBlock(x, y, z + 1)) {
			this.attemptButterflySpawn(world, spawn, x, y, z + 1);
		}
	}

	private void attemptButterflySpawn(final World world, final IButterfly butterfly, final double x, final double y, final double z) {
		if (BinnieCore.isLepidopteryActive()) {
			Binnie.Genetics.getButterflyRoot().spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612, z);
		}
	}

	public GameProfile getOwnerName() {
		return this.getOwner();
	}

	public World getWorld() {
		return this.getWorldObj();
	}

	public int getXCoord() {
		return this.xCoord;
	}

	public int getYCoord() {
		return this.yCoord;
	}

	public int getZCoord() {
		return this.zCoord;
	}

	public int getBiomeId() {
		return this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID;
	}

	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).temperature);
	}

	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).rainfall);
	}

	public void setErrorState(final int state) {
	}

	public int getErrorOrdinal() {
		return 0;
	}

	public boolean addProduct(final ItemStack product, final boolean all) {
		return false;
	}

	@Override
	public IButterfly getCaterpillar() {
		return this.caterpillar;
	}

	@Override
	public IIndividual getNanny() {
		return this.getFlower();
	}

	@Override
	public void setCaterpillar(final IButterfly butterfly) {
		this.caterpillar = butterfly;
		this.matureTime = 0;
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return this.getFlower() != null && !this.getFlower().isWilted() && this.getFlower().getAge() > 1;
	}

	private void matureCaterpillar() {
		if (this.getCaterpillar() == null) {
			return;
		}
		++this.matureTime;
		if (this.matureTime >= this.caterpillar.getGenome().getLifespan() / (this.caterpillar.getGenome().getFertility() * 2) && this.caterpillar.canTakeFlight(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
			if (this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord)) {
				this.attemptButterflySpawn(this.worldObj, this.caterpillar, this.xCoord, this.yCoord + 1, this.zCoord);
			}
			else if (this.worldObj.isAirBlock(this.xCoord - 1, this.yCoord, this.zCoord)) {
				this.attemptButterflySpawn(this.worldObj, this.caterpillar, this.xCoord - 1, this.yCoord, this.zCoord);
			}
			else if (this.worldObj.isAirBlock(this.xCoord + 1, this.yCoord, this.zCoord)) {
				this.attemptButterflySpawn(this.worldObj, this.caterpillar, this.xCoord + 1, this.yCoord, this.zCoord);
			}
			else if (this.worldObj.isAirBlock(this.xCoord, this.yCoord, this.zCoord - 1)) {
				this.attemptButterflySpawn(this.worldObj, this.caterpillar, this.xCoord, this.yCoord, this.zCoord - 1);
			}
			else if (this.worldObj.isAirBlock(this.xCoord, this.yCoord, this.zCoord + 1)) {
				this.attemptButterflySpawn(this.worldObj, this.caterpillar, this.xCoord, this.yCoord, this.zCoord + 1);
			}
			this.setCaterpillar(null);
		}
	}

	public void setRender(final RenderInfo render) {
		this.renderInfo = render;
		this.section = this.renderInfo.section;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	public int getAge() {
		return (this.renderInfo == null) ? 1 : this.renderInfo.age;
	}

	public int getRenderSection() {
		return (this.renderInfo == null) ? 1 : this.renderInfo.section;
	}

	public boolean isWilted() {
		return this.renderInfo != null && this.renderInfo.wilted;
	}

	public boolean isFlowered() {
		return this.renderInfo == null || this.renderInfo.flowered;
	}

	public int getPrimaryColour() {
		return (this.renderInfo == null) ? EnumFlowerColor.Red.getColor(false) : this.renderInfo.primary.getColor(this.isWilted());
	}

	public int getSecondaryColour() {
		return (this.renderInfo == null) ? EnumFlowerColor.Red.getColor(false) : this.renderInfo.secondary.getColor(this.isWilted());
	}

	public int getStemColour() {
		return (this.renderInfo == null) ? EnumFlowerColor.Green.getColor(false) : this.renderInfo.stem.getColor(this.isWilted());
	}

	public IFlowerType getType() {
		return (this.renderInfo == null) ? EnumFlowerType.Poppy : this.renderInfo.type;
	}

	public BiomeGenBase getBiome() {
		return this.getWorld().getBiomeGenForCoords(this.xCoord, this.zCoord);
	}

	public void setErrorState(final IErrorState state) {
	}

	public IErrorState getErrorState() {
		return null;
	}

	public boolean setErrorCondition(final boolean condition, final IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return new HashSet<IErrorState>();
	}

	public static class RenderInfo
	{
		public IFlowerColour primary;
		public IFlowerColour secondary;
		public IFlowerColour stem;
		public IFlowerType type;
		public byte age;
		public boolean wilted;
		public boolean flowered;
		public byte section;

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof RenderInfo) {
				final RenderInfo o = (RenderInfo) obj;
				return o.age == this.age && o.wilted == this.wilted && o.flowered == this.flowered && o.primary == this.primary && o.secondary == this.secondary && o.stem == this.stem && o.type == this.type;
			}
			return super.equals(obj);
		}

		public RenderInfo() {
		}

		public RenderInfo(final IFlower flower, final TileEntityFlower tile) {
			this.section = (byte) tile.getSection();
			this.primary = flower.getGenome().getPrimaryColor();
			this.secondary = flower.getGenome().getSecondaryColor();
			this.stem = flower.getGenome().getStemColor();
			this.age = (byte) flower.getAge();
			this.wilted = flower.isWilted();
			this.flowered = flower.hasFlowered();
			this.type = flower.getGenome().getType();
		}
	}

	@Override
	public ChunkCoordinates getCoordinates() {
		return new ChunkCoordinates(xCoord, yCoord, zCoord);
	}

	// TODO ??
	@Override
	public boolean isPollinated() {
		return isBreeding();
	}

}
