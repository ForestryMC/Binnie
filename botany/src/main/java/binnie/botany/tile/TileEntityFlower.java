package binnie.botany.tile;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.common.EnumPlantType;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.blocks.PlantType;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.botany.modules.ModuleFlowers;
import binnie.botany.modules.ModuleGardening;
import binnie.core.BinnieCore;

public class TileEntityFlower extends TileEntity implements IPollinatable, IButterflyNursery {
	@Nullable
	private IFlower flower;
	@Nullable
	private GameProfile owner;
	private int section;
	@Nullable
	private FlowerRenderInfo renderInfo;
	@Nullable
	private IButterfly caterpillar;
	private int matureTime;

	public TileEntityFlower() {
		flower = null;
		section = 0;
		renderInfo = null;
		matureTime = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		if (nbtCompound.hasKey("Flower")) {
			flower = new Flower(nbtCompound.getCompoundTag("Flower"));
			if (flower.getAge() == 0) {
				flower.setFlowered(false);
			}
		}

		if (nbtCompound.hasKey("section")) {
			section = nbtCompound.getByte("section");
		}

		if (nbtCompound.hasKey("owner")) {
			owner = NBTUtil.readGameProfileFromNBT(nbtCompound.getCompoundTag("owner"));
		}

		if (nbtCompound.hasKey("CATER") && BinnieCore.isLepidopteryActive()) {
			matureTime = nbtCompound.getInteger("caterTime");
			caterpillar = ButterflyManager.butterflyRoot.getMember(nbtCompound.getCompoundTag("cater"));
		}

		readRenderInfo(nbtCompound);
		super.readFromNBT(nbtCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
		if (flower != null) {
			nbtCompound.setTag("Flower", flower.writeToNBT(new NBTTagCompound()));
		}

		if (owner != null) {
			NBTTagCompound nbt = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbt, owner);
			nbtCompound.setTag("owner", nbt);
		}

		if (caterpillar != null) {
			nbtCompound.setInteger("caterTime", matureTime);
			NBTTagCompound subcompound = new NBTTagCompound();
			caterpillar.writeToNBT(subcompound);
			nbtCompound.setTag("cater", subcompound);
		}

		nbtCompound.setByte("section", (byte) getSection());
		return super.writeToNBT(nbtCompound);
	}

	public void create(ItemStack stack, @Nullable GameProfile owner) {
		IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
		create(flower, owner);
	}

	public void create(IFlower flower, @Nullable GameProfile owner) {
		this.flower = flower;
		if (flower.getAge() == 0) {
			flower.setFlowered(false);
		}

		updateRender(true);
		this.owner = owner;
	}

	@Override
	public EnumPlantType getPlantType() {
		return EnumPlantType.Plains;
	}

	@Override
	@Nullable
	public IIndividual getPollen() {
		return getFlower();
	}

	@Override
	public boolean canMateWith(IIndividual individual) {
		return isBreeding()
				&& individual instanceof IFlower
				&& getFlower() != null
				&& getFlower().getMate() == null
				&& getFlower().hasFlowered()
				&& !getFlower().isGeneticEqual(individual);
	}

	@Override
	public void mateWith(IIndividual individual) {
		if (getFlower() == null || !(individual instanceof IFlower)) {
			return;
		}

		IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) individual.getGenome().getPrimary();
		IAlleleFlowerSpecies primary2 = getFlower().getGenome().getPrimary();
		if (primary == primary2 || world.rand.nextInt(4) == 0) {
			getFlower().mate((IFlower) individual);
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Nullable
	public IFlower getFlower() {
		if (getSection() <= 0) {
			return flower;
		}

		TileEntity tile = world.getTileEntity(pos.down(getSection()));
		if (tile instanceof TileEntityFlower) {
			return ((TileEntityFlower) tile).getFlower();
		}
		return null;
	}

	public boolean isBreeding() {
		Block roots = getWorld().getBlockState(getPos().down()).getBlock();
		return BotanyCore.getGardening().isSoil(roots);
	}

	public void randomUpdate(Random rand) {
		if (world.getBlockState(pos).getBlock() != ModuleFlowers.flower) {
			invalidate();
			return;
		}
		if (getSection() > 0) {
			return;
		}
		if (flower == null) {
			return;
		}

		if (!isBreeding()) {
			return;
		}

		if (updateState(rand)) {
			return;
		}

		IGardeningManager gardening = BotanyCore.getGardening();
		EnumSoilType soil = gardening.getSoilType(world, pos.down());
		float chanceDispersal = 0.8f;
		chanceDispersal += 0.2f * flower.getGenome().getFertility();
		chanceDispersal *= 1.0f + soil.ordinal() * 0.5f;
		float chancePollinate = 1.0f;
		chancePollinate += 0.25f * flower.getGenome().getFertility();
		chancePollinate *= 1.0f + soil.ordinal() * 0.5f;
		float chanceSelfPollinate = 0.2f * chancePollinate;
		plantOffspring(rand, chanceDispersal);

		mateFlower(rand, chancePollinate, chanceSelfPollinate);
		spawnButterflies();
		matureCaterpillar();
		checkIfDead(false);
		updateRender(true);
	}

	private void mateFlower(Random rand, float chancePollinate, float chanceSelfPollinate) {
		if (world.rand.nextFloat() < chancePollinate && flower.hasFlowered() && !flower.isWilted()) {
			for (int tries = 0; tries < 4; ++tries) {
				int x = rand.nextInt(5) - 2;
				int z = rand.nextInt(5) - 2;
				TileEntity tile = world.getTileEntity(pos.add(x, 0, z));
				if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(getFlower())) {
					((IPollinatable) tile).mateWith(getFlower());
				}
			}
		}

		if (world.rand.nextFloat() < chanceSelfPollinate && flower.hasFlowered() && flower.getMate() == null) {
			mateWith(getFlower());
		}
	}

	private void plantOffspring(Random rand, float chanceDispersal) {
		if (world.rand.nextFloat() < chanceDispersal && flower.hasFlowered() && !flower.isWilted()) {
			IFlowerGenome mate = flower.getMate();
			if (mate != null) {
				boolean dispersed = false;
				for (int tries = 0; tries < 5 && !dispersed; ++tries) {
					int x = rand.nextInt(3) - 1;
					int z = rand.nextInt(3) - 1;

					Block b2 = world.getBlockState(pos.add(x, -1, z)).getBlock();
					if (world.isAirBlock(pos.add(x, 0, z)) && BotanyCore.getGardening().isSoil(b2)) {
						IFlower offspring = flower.getOffspring(world, pos);
						if (offspring != null) {
							BotanyCore.getFlowerRoot().plant(world, pos.add(x, 0, z), offspring, getOwner());
							flower.removeMate();
							dispersed = true;
						}
					}
				}
			}
		}
	}

	private boolean updateState(Random rand) {
		float light = world.getLight(pos);
		if (light < 6.0f) {
			for (int offsetX = -2; offsetX <= 2; ++offsetX) {
				for (int offsetY = -2; offsetY <= 2; ++offsetY) {
					light -= (world.canBlockSeeSky(pos.add(offsetX, 0, offsetY)) ? 0.0f : 0.5f);
				}
			}
		}

		IGardeningManager gardening = BotanyCore.getGardening();
		boolean canTolerate = gardening.canTolerate(getFlower(), world, pos);
		if (rand.nextFloat() < getFlower().getGenome().getAgeChance()) {
			if (flower.getAge() < 1) {
				if (canTolerate && light > 6.0f) {
					doFlowerAge();
				}
			} else {
				doFlowerAge();
			}
		}

		if (canTolerate && flower.getAge() > 1 && !flower.isWilted() && light > 6.0f) {
			flower.setFlowered(true);
		}

		if (!canTolerate && flower.isWilted() && rand.nextInt(2 + Math.max(flower.getAge(), 2)) == 0) {
			kill();
			return true;
		}

		if (light < 2.0f && flower.isWilted()) {
			kill();
			return true;
		}

		if (!canTolerate || light < 1.0f) {
			flower.setWilted(true);
		} else {
			flower.setWilted(false);
		}

		return false;
	}

	private void doFlowerAge() {
		getFlower().age();
		if (getFlower().getAge() == 1) {
			IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
			flowerRoot.onGrowFromSeed(world, pos);
			if (getOwner() != null && getFlower() != null) {
				flowerRoot.getBreedingTracker(world, getOwner()).registerBirth(getFlower());
			}
		}
	}

	private NBTTagCompound writeRenderInfo(NBTTagCompound tag) {
		if (renderInfo != null) {
			NBTTagCompound nbtRenderInfo = new NBTTagCompound();
			renderInfo.writeToNBT(nbtRenderInfo);
			tag.setTag("renderinfo", nbtRenderInfo);
		}
		return tag;
	}

	private void readRenderInfo(NBTTagCompound tag) {
		if (tag.hasKey("renderinfo")) {
			NBTTagCompound infotag = tag.getCompoundTag("renderinfo");
			FlowerRenderInfo info = new FlowerRenderInfo(infotag);
			setRender(info);
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readRenderInfo(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		updateRender(true);
		return writeRenderInfo(super.getUpdateTag());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		if (renderInfo == null && getFlower() != null) {
			renderInfo = new FlowerRenderInfo(getFlower(), this);
		}
		return (renderInfo != null) ? new SPacketUpdateTileEntity(pos, 0, getUpdateTag()) : null;
	}

	public void updateRender(boolean update) {
		if (update && getFlower() != null && getFlower().getGenome() != null) {
			FlowerRenderInfo newInfo = new FlowerRenderInfo(getFlower(), this);
			if (!newInfo.equals(renderInfo)) {
				setRender(newInfo);
			}
		}

		TileEntity above = world.getTileEntity(pos.up());
		if (above instanceof TileEntityFlower) {
			((TileEntityFlower) above).updateRender(true);
		}
	}

	public int getSection() {
		return section;
	}

	public void setSection(int i) {
		section = i;
		if (!world.isRemote) {
			updateRender(true);
		}
	}

	public ItemStack getItemStack() {
		IFlower flower = getFlower();
		if (flower == null) {
			return ItemStack.EMPTY;
		}
		return BotanyAPI.flowerRoot.getMemberStack(flower, EnumFlowerStage.getStage(flower));
	}

	@Nullable
	private TileEntityFlower getRoot() {
		if (getSection() == 0) {
			return null;
		}
		TileEntity tile = world.getTileEntity(pos.down(getSection()));
		return (tile instanceof TileEntityFlower) ? ((TileEntityFlower) tile) : null;
	}

	public void onShear() {
		if (getRoot() != null) {
			getRoot().onShear();
		}

		if (getFlower() == null || getFlower().getAge() <= 1) {
			return;
		}

		Random rand = new Random();
		IFlower cutting = (IFlower) getFlower().copy();
		cutting.setAge(0);
		ItemStack cuttingStack = BotanyCore.getFlowerRoot().getMemberStack(cutting, EnumFlowerStage.SEED);
		float f = 0.7f;
		double xPos = rand.nextFloat() * f + (1.0f - f) * 0.5;
		double yPos = rand.nextFloat() * f + (1.0f - f) * 0.5;
		double zPos = rand.nextFloat() * f + (1.0f - f) * 0.5;
		EntityItem entityItem = new EntityItem(world, pos.getX() + xPos, pos.getY() + yPos, pos.getZ() + zPos, cuttingStack);
		entityItem.setPickupDelay(10);
		world.spawnEntity(entityItem);
		for (int maxAge = getFlower().getMaxAge(), i = 0; i < maxAge; ++i) {
			if (rand.nextBoolean()) {
				getFlower().age();
				if (checkIfDead(true)) {
					return;
				}
			}
		}
	}

	public boolean checkIfDead(boolean wasCut) {
		if (getSection() != 0) {
			return getRoot().checkIfDead(wasCut);
		}

		EnumSoilType soil = BotanyCore.getGardening().getSoilType(world, pos);
		int maxAge = (int) (flower.getMaxAge() * (1.0f + soil.ordinal() * 0.25f));
		if (flower.getAge() > maxAge) {
			if (!wasCut && flower.getMate() != null) {
				world.setBlockToAir(pos);
				IFlower offspring = flower.getOffspring(world, pos.down());
				TileEntity above = world.getTileEntity(pos.up());
				if (above instanceof TileEntityFlower) {
					world.setBlockToAir(pos.up());
				}
				BotanyCore.getFlowerRoot().plant(world, pos, offspring, getOwner());
			} else {
				kill();
			}
			return true;
		}
		return false;
	}

	public void kill() {
		if (flower.getAge() > 0) {
			world.setBlockState(pos, ModuleGardening.plant.getStateFromMeta(PlantType.DEAD_FLOWER.ordinal()), 2);
		} else {
			world.setBlockToAir(pos);
		}

		for (int i = 1; world.getTileEntity(pos.up(i)) instanceof TileEntityFlower; ++i) {
			world.setBlockToAir(pos.up(i));
		}
	}

	public boolean onBonemeal() {
		if (getFlower() == null) {
			return false;
		}
		if (!isBreeding()) {
			return false;
		}
		if (getFlower().isWilted()) {
			return false;
		}

		//this.doFlowerAge();
		if (getFlower().getAge() > 1 && !getFlower().hasFlowered()) {
			getFlower().setFlowered(true);
		}
		checkIfDead(false);
		updateRender(true);
		return true;
	}

	@Nullable
	public GameProfile getOwner() {
		return owner;
	}

	public void setOwner(GameProfile ownerName) {
		owner = ownerName;
	}

	public void spawnButterflies() {
		/*if (!BinnieCore.isLepidopteryActive()) {
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
		}*/
	}

	private void attemptButterflySpawn(World world, IButterfly butterfly, double x, double y, double z) {
		if (BinnieCore.isLepidopteryActive()) {
			ButterflyManager.butterflyRoot.spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612, z);
		}
	}

	public GameProfile getOwnerName() {
		return getOwner();
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(world.getBiome(getPos()).getDefaultTemperature());
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(world.getBiome(getPos()).getRainfall());
	}

	@Override
	@Nullable
	public IButterfly getCaterpillar() {
		return caterpillar;
	}

	@Override
	public void setCaterpillar(@Nullable IButterfly butterfly) {
		caterpillar = butterfly;
		matureTime = 0;
	}

	@Override
	public IIndividual getNanny() {
		return getFlower();
	}

	@Override
	public boolean canNurse(IButterfly butterfly) {
		return getFlower() != null && !getFlower().isWilted() && getFlower().getAge() > 1;
	}

	private void matureCaterpillar() {
		//TODO Spawn the caterpillar in a cocon on the next Tree or other thing
		/*if (this.getCaterpillar() == null) {
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
		}*/
	}

	public void setRender(FlowerRenderInfo render) {
		renderInfo = render;
		section = renderInfo.getSection();
		if (!world.isRemote) {
			IBlockState blockState = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, blockState, blockState, 0);
		} else {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	public int getAge() {
		return (renderInfo == null) ? 1 : renderInfo.getAge();
	}

	public int getRenderSection() {
		return (renderInfo == null) ? 1 : renderInfo.getSection();
	}

	public boolean isWilted() {
		return renderInfo != null && renderInfo.isWilted();
	}

	public boolean isFlowered() {
		return renderInfo == null || renderInfo.isFlowered();
	}

	public int getPrimaryColour() {
		return (renderInfo == null)
				? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false)
				: renderInfo.getPrimary().getColor(isWilted());
	}

	public int getSecondaryColour() {
		return (renderInfo == null)
				? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false)
				: renderInfo.getSecondary().getColor(isWilted());
	}

	public int getStemColour() {
		return (renderInfo == null)
				? EnumFlowerColor.Green.getFlowerColorAllele().getColor(false)
				: renderInfo.getStem().getColor(isWilted());
	}

	public IFlowerType getType() {
		return (renderInfo == null)
				? EnumFlowerType.POPPY
				: renderInfo.getType();
	}

	@Override
	public Biome getBiome() {
		return getWorld().getBiome(getPos());
	}

	@Override
	public BlockPos getCoordinates() {
		return getPos();
	}

	@Override
	public boolean isPollinated() {
		return isBreeding();
	}

	@Override
	public World getWorldObj() {
		return world;
	}

}
