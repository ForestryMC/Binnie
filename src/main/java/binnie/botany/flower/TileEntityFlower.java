package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColour;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.api.IFlowerType;
import binnie.botany.api.IGardeningManager;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
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
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TileEntityFlower extends TileEntity implements IPollinatable, IButterflyNursery {
	@Nullable
	IFlower flower;
	@Nullable
	GameProfile owner;
	int section;
	@Nullable
	RenderInfo renderInfo;
	@Nullable
	IButterfly caterpillar;
	int matureTime;

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
			caterpillar = Binnie.GENETICS.getButterflyRoot().getMember(nbtCompound.getCompoundTag("cater"));
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
		if (world.getBlockState(pos).getBlock() != Botany.flower) {
			invalidate();
			return;
		}
		if (getSection() > 0) {
			return;
		}
		if (flower == null) {
			return;
		}
		
		// TODO always false?
		if (flower.getGenome() == null) {
			invalidate();
			return;
		}
		if (!isBreeding()) {
			return;
		}

		float light = world.getLight(pos);
		if (light < 6.0f) {
			for (int dx = -2; dx <= 2; ++dx) {
				for (int dy = -2; dy <= 2; ++dy) {
					light -= (world.canBlockSeeSky(pos) ? 0.0f : 0.5f);
				}
			}
		}

		IGardeningManager gardening = BotanyCore.getGardening();
		boolean canTolerate = gardening.canTolerate(getFlower(), world, pos);
		EnumSoilType soil = gardening.getSoilType(world, pos.down());
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
			return;
		}

		if (light < 2.0f && flower.isWilted()) {
			kill();
			return;
		}

		if (!canTolerate || light < 1.0f) {
			flower.setWilted(true);
		} else {
			flower.setWilted(false);
		}

		float CHANCE_DISPERSAL = 0.8f;
		CHANCE_DISPERSAL += 0.2f * flower.getGenome().getFertility();
		CHANCE_DISPERSAL *= 1.0f + soil.ordinal() * 0.5f;
		float CHANCE_POLLINATE = 1.0f;
		CHANCE_POLLINATE += 0.25f * flower.getGenome().getFertility();
		CHANCE_POLLINATE *= 1.0f + soil.ordinal() * 0.5f;
		float CHANCE_SELFPOLLINATE = 0.2f * CHANCE_POLLINATE;
		if (world.rand.nextFloat() < CHANCE_DISPERSAL && flower.hasFlowered() && !flower.isWilted()) {
			IFlowerGenome mate = flower.getMate();
			if (mate != null) {
				boolean dispersed = false;
				for (int a = 0; a < 5 && !dispersed; ++a) {
					int dx2;
					int dz;
					for (dx2 = 0, dz = 0; dx2 == 0 && dz == 0; dx2 = rand.nextInt(3) - 1, dz = rand.nextInt(3) - 1) {
					}

					Block b2 = world.getBlockState(pos.add(dx2, -1, dz)).getBlock();
					if (world.isAirBlock(pos.add(dx2, 0, dz)) && BotanyCore.getGardening().isSoil(b2)) {
						IFlower offspring = flower.getOffspring(world, pos);
						if (offspring != null) {
							BotanyCore.getFlowerRoot().plant(world, pos.add(dx2, 0, dz), offspring, getOwner());
							flower.removeMate();
							dispersed = true;
						}
					}
				}
			}
		}

		if (world.rand.nextFloat() < CHANCE_POLLINATE && flower.hasFlowered() && !flower.isWilted()) {
			for (int a2 = 0; a2 < 4; ++a2) {
				int dx3;
				int dz2;
				for (dx3 = 0, dz2 = 0; dx3 == 0 && dz2 == 0; dx3 = rand.nextInt(5) - 2, dz2 = rand.nextInt(5) - 2) {
				}
				TileEntity tile = world.getTileEntity(pos.add(dx3, 0, dz2));
				if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(getFlower())) {
					((IPollinatable) tile).mateWith(getFlower());
				}
			}
		}

		if (world.rand.nextFloat() < CHANCE_SELFPOLLINATE && flower.hasFlowered() && flower.getMate() == null) {
			mateWith(getFlower());
		}
		spawnButterflies();
		matureCaterpillar();
		checkIfDead(false);
		updateRender(true);
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
			nbtRenderInfo.setByte("primary", (byte) renderInfo.primary.getID());
			nbtRenderInfo.setByte("secondary", (byte) renderInfo.secondary.getID());
			nbtRenderInfo.setByte("stem", (byte) renderInfo.stem.getID());
			nbtRenderInfo.setByte("type", (byte) renderInfo.type.ordinal());
			nbtRenderInfo.setByte("age", renderInfo.age);
			nbtRenderInfo.setByte("section", renderInfo.section);
			nbtRenderInfo.setBoolean("wilted", renderInfo.wilted);
			nbtRenderInfo.setBoolean("flowered", renderInfo.flowered);
			tag.setTag("renderinfo", nbtRenderInfo);
		}
		return tag;
	}

	private void readRenderInfo(NBTTagCompound tag) {
		if (tag.hasKey("renderinfo")) {
			NBTTagCompound infotag = tag.getCompoundTag("renderinfo");
			RenderInfo info = new RenderInfo();
			info.primary = EnumFlowerColor.values()[infotag.getByte("primary")].getFlowerColorAllele();
			info.secondary = EnumFlowerColor.values()[infotag.getByte("secondary")].getFlowerColorAllele();
			info.stem = EnumFlowerColor.values()[infotag.getByte("stem")].getFlowerColorAllele();
			info.type = EnumFlowerType.values()[infotag.getByte("type")];
			info.age = infotag.getByte("age");
			info.section = infotag.getByte("section");
			info.wilted = infotag.getBoolean("wilted");
			info.flowered = infotag.getBoolean("flowered");
			setRender(info);
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		// TODO client only call?
		readRenderInfo(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		updateRender(true);
		return writeRenderInfo(super.getUpdateTag());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		// TODO getFlower().getGenome() != null always true?
		if (renderInfo == null && getFlower() != null && getFlower().getGenome() != null) {
			renderInfo = new RenderInfo(getFlower(), this);
		}
		return (renderInfo != null) ? new SPacketUpdateTileEntity(pos, 0, getUpdateTag()) : null;
	}

	public void updateRender(boolean update) {
		if (update && getFlower() != null && getFlower().getGenome() != null) {
			RenderInfo newInfo = new RenderInfo(getFlower(), this);
			if (renderInfo == null || !newInfo.equals(renderInfo)) {
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
		if (BinnieCore.getBinnieProxy().isSimulating(world)) {
			updateRender(true);
		}
	}

	public ItemStack getItemStack() {
		IFlower flower = getFlower();
		if (flower == null) {
			return ItemStack.EMPTY;
		}
		return Binnie.GENETICS.getFlowerRoot().getMemberStack(flower, EnumFlowerStage.getStage(flower));
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
		double d = rand.nextFloat() * f + (1.0f - f) * 0.5;
		double d2 = rand.nextFloat() * f + (1.0f - f) * 0.5;
		double d3 = rand.nextFloat() * f + (1.0f - f) * 0.5;
		EntityItem entityitem = new EntityItem(world, pos.getX() + d, pos.getY() + d2, pos.getZ() + d3, cuttingStack);
		entityitem.setPickupDelay(10);
		world.spawnEntity(entityitem);
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
			world.setBlockState(pos, Botany.plant.getStateFromMeta(BlockPlant.Type.DeadFlower.ordinal()), 2);
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
			Binnie.GENETICS.getButterflyRoot().spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612, z);
		}
	}

	public GameProfile getOwnerName() {
		return getOwner();
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(world.getBiome(getPos()).getTemperature());
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(world.getBiome(getPos()).getRainfall());
	}

	public void setErrorState(int state) {
	}

	public int getErrorOrdinal() {
		return 0;
	}

	public boolean addProduct(ItemStack product, boolean all) {
		return false;
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

	public void setRender(RenderInfo render) {
		renderInfo = render;
		section = renderInfo.section;
		if (!world.isRemote) {
			IBlockState blockState = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, blockState, blockState, 0);
		} else {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	public int getAge() {
		return (renderInfo == null) ? 1 : renderInfo.age;
	}

	public int getRenderSection() {
		return (renderInfo == null) ? 1 : renderInfo.section;
	}

	public boolean isWilted() {
		return renderInfo != null && renderInfo.wilted;
	}

	public boolean isFlowered() {
		return renderInfo == null || renderInfo.flowered;
	}

	public int getPrimaryColour() {
		return (renderInfo == null)
			? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false)
			: renderInfo.primary.getColor(isWilted());
	}

	public int getSecondaryColour() {
		return (renderInfo == null)
			? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false)
			: renderInfo.secondary.getColor(isWilted());
	}

	public int getStemColour() {
		return (renderInfo == null)
			? EnumFlowerColor.Green.getFlowerColorAllele().getColor(false)
			: renderInfo.stem.getColor(isWilted());
	}

	public IFlowerType getType() {
		return (renderInfo == null)
			? EnumFlowerType.Poppy
			: renderInfo.type;
	}

	@Override
	public Biome getBiome() {
		return getWorld().getBiome(getPos());
	}

	@Nullable
	public IErrorState getErrorState() {
		return null;
	}

	public void setErrorState(IErrorState state) {
	}

	public boolean setErrorCondition(boolean condition, IErrorState errorState) {
		return false;
	}

	public Set<IErrorState> getErrorStates() {
		return new HashSet<>();
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

	public static class RenderInfo {
		public IFlowerColour primary;
		public IFlowerColour secondary;
		public IFlowerColour stem;
		public IFlowerType type;
		public byte age;
		public boolean wilted;
		public boolean flowered;
		public byte section;

		public RenderInfo() {
		}

		public RenderInfo(IFlower flower, TileEntityFlower tile) {
			section = (byte) tile.getSection();
			primary = flower.getGenome().getPrimaryColor();
			secondary = flower.getGenome().getSecondaryColor();
			stem = flower.getGenome().getStemColor();
			age = (byte) flower.getAge();
			wilted = flower.isWilted();
			flowered = flower.hasFlowered();
			type = flower.getGenome().getType();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof RenderInfo) {
				RenderInfo o = (RenderInfo) obj;
				return o.age == age && o.wilted == wilted && o.flowered == flowered && o.primary == primary && o.secondary == secondary && o.stem == stem && o.type == type;
			}
			return super.equals(obj);
		}
	}
}
