package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColour;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.botany.network.PacketFlowerUpdate;
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
		this.flower = null;
		this.section = 0;
		this.renderInfo = null;
		this.matureTime = 0;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbtCompound) {
		if (nbtCompound.hasKey("Flower")) {
			this.flower = new Flower(nbtCompound.getCompoundTag("Flower"));
			if (flower.getAge() == 0) {
				flower.setFlowered(false);
			}
		}
		if (nbtCompound.hasKey("section")) {
			this.section = nbtCompound.getByte("section");
		}
		if (nbtCompound.hasKey("owner")) {
			this.owner = NBTUtil.readGameProfileFromNBT(nbtCompound.getCompoundTag("owner"));
		}
		if (nbtCompound.hasKey("CATER") && BinnieCore.isLepidopteryActive()) {
			this.matureTime = nbtCompound.getInteger("caterTime");
			this.caterpillar = Binnie.GENETICS.getButterflyRoot().getMember(nbtCompound.getCompoundTag("cater"));
		}
		readRenderInfo(nbtCompound);
		super.readFromNBT(nbtCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbtCompound) {
		if (this.flower != null) {
			nbtCompound.setTag("Flower", flower.writeToNBT(new NBTTagCompound()));
		}
		if (this.owner != null) {
			final NBTTagCompound nbt = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbt, this.owner);
			nbtCompound.setTag("owner", nbt);
		}
		if (this.caterpillar != null) {
			nbtCompound.setInteger("caterTime", this.matureTime);
			final NBTTagCompound subcompound = new NBTTagCompound();
			this.caterpillar.writeToNBT(subcompound);
			nbtCompound.setTag("cater", subcompound);
		}
		nbtCompound.setByte("section", (byte) getSection());
		return super.writeToNBT(nbtCompound);
	}

	public void create(final ItemStack stack, @Nullable final GameProfile owner) {
		IFlower flower = BotanyCore.getFlowerRoot().getMember(stack);
		this.create(flower, owner);
	}

	public void create(final IFlower flower, @Nullable final GameProfile owner) {
		this.flower = flower;
		if (this.flower.getAge() == 0) {
			this.flower.setFlowered(false);
		}
		this.updateRender(true);
		this.owner = owner;
	}

	@Override
	public EnumPlantType getPlantType() {
		return EnumPlantType.Plains;
	}

	@Override
	@Nullable
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
		if (primary == primary2 || this.world.rand.nextInt(4) == 0) {
			this.getFlower().mate((IFlower) individual);
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Nullable
	public IFlower getFlower() {
		if (this.getSection() <= 0) {
			return this.flower;
		}
		TileEntity tile = this.world.getTileEntity(pos.down(getSection()));
		if (tile instanceof TileEntityFlower) {
			return ((TileEntityFlower) tile).getFlower();
		}
		return null;
	}

	public boolean isBreeding() {
		final Block roots = this.getWorld().getBlockState(getPos().down()).getBlock();
		return Gardening.isSoil(roots);
	}

	public void randomUpdate(final Random rand) {
		if (this.world.getBlockState(pos).getBlock() != Botany.flower) {
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
		float light = this.world.getLight(pos);
		if (light < 6.0f) {
			for (int dx = -2; dx <= 2; ++dx) {
				for (int dy = -2; dy <= 2; ++dy) {
					light -= (this.world.canBlockSeeSky(pos) ? 0.0f : 0.5f);
				}
			}
		}
		final boolean canTolerate = Gardening.canTolerate(this.getFlower(), this.world, pos);
		final EnumSoilType soil = Gardening.getSoilType(this.world, pos.down());
		if (rand.nextFloat() < this.getFlower().getGenome().getAgeChance()) {
			if (this.flower.getAge() < 1) {
				if (canTolerate && light > 6.0f) {
					this.doFlowerAge();
				}
			} else {
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
		} else {
			this.flower.setWilted(false);
		}
		float CHANCE_DISPERSAL = 0.8f;
		CHANCE_DISPERSAL += 0.2f * this.flower.getGenome().getFertility();
		CHANCE_DISPERSAL *= 1.0f + soil.ordinal() * 0.5f;
		float CHANCE_POLLINATE = 1.0f;
		CHANCE_POLLINATE += 0.25f * this.flower.getGenome().getFertility();
		CHANCE_POLLINATE *= 1.0f + soil.ordinal() * 0.5f;
		final float CHANCE_SELFPOLLINATE = 0.2f * CHANCE_POLLINATE;
		if (this.world.rand.nextFloat() < CHANCE_DISPERSAL && this.flower.hasFlowered() && !this.flower.isWilted()) {
			final IFlowerGenome mate = this.flower.getMate();
			if (mate != null) {
				boolean dispersed = false;
				for (int a = 0; a < 5 && !dispersed; ++a) {
					int dx2;
					int dz;
					for (dx2 = 0, dz = 0; dx2 == 0 && dz == 0; dx2 = rand.nextInt(3) - 1, dz = rand.nextInt(3) - 1) {
					}

					final Block b2 = world.getBlockState(pos.add(dx2, -1, dz)).getBlock();
					if (world.isAirBlock(pos.add(dx2, 0, dz)) && Gardening.isSoil(b2)) {
						final IFlower offspring = this.flower.getOffspring(world, pos);
						if (offspring != null) {
							Gardening.plant(world, pos.add(dx2, 0, dz), offspring, this.getOwner());
							this.flower.removeMate();
							dispersed = true;
						}
					}
				}
			}
		}
		if (this.world.rand.nextFloat() < CHANCE_POLLINATE && this.flower.hasFlowered() && !this.flower.isWilted()) {
			for (int a2 = 0; a2 < 4; ++a2) {
				int dx3;
				int dz2;
				for (dx3 = 0, dz2 = 0; dx3 == 0 && dz2 == 0; dx3 = rand.nextInt(5) - 2, dz2 = rand.nextInt(5) - 2) {
				}
				final TileEntity tile = world.getTileEntity(pos.add(dx3, 0, dz2));
				if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(this.getFlower())) {
					((IPollinatable) tile).mateWith(this.getFlower());
				}
			}
		}
		if (this.world.rand.nextFloat() < CHANCE_SELFPOLLINATE && this.flower.hasFlowered() && this.flower.getMate() == null) {
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
			Gardening.onGrowFromSeed(this.world, pos);
			if (this.getOwner() != null && this.getFlower() != null) {
				BotanyCore.getFlowerRoot().getBreedingTracker(world, this.getOwner()).registerBirth(this.getFlower());
			}
		}
	}

	private NBTTagCompound writeRenderInfo(NBTTagCompound tag){
		if(renderInfo!=null){
			NBTTagCompound nbtRenderInfo = new NBTTagCompound();
			nbtRenderInfo.setByte("primary",(byte)this.renderInfo.primary.getID());
			nbtRenderInfo.setByte("secondary",(byte)this.renderInfo.secondary.getID());
			nbtRenderInfo.setByte("stem",(byte)this.renderInfo.stem.getID());
			nbtRenderInfo.setByte("type",(byte)this.renderInfo.type.ordinal());
			nbtRenderInfo.setByte("age",this.renderInfo.age);
			nbtRenderInfo.setByte("section",this.renderInfo.section);
			nbtRenderInfo.setBoolean("wilted",this.renderInfo.wilted);
			nbtRenderInfo.setBoolean("flowered",this.renderInfo.flowered);
			tag.setTag("renderinfo", nbtRenderInfo);
		}
		return tag;
	}

	private void readRenderInfo(NBTTagCompound tag){
		if(tag.hasKey("renderinfo")){
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
		readRenderInfo(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		updateRender(true);
		return writeRenderInfo(super.getUpdateTag());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		if (this.renderInfo == null && this.getFlower() != null && this.getFlower().getGenome() != null) {
			this.renderInfo = new RenderInfo(this.getFlower(), this);
		}
		return (this.renderInfo != null) ? new SPacketUpdateTileEntity(pos, 0, getUpdateTag()) : null;
	}

	public void updateRender(final boolean update) {
		if (update && this.getFlower() != null && this.getFlower().getGenome() != null) {
			final RenderInfo newInfo = new RenderInfo(this.getFlower(), this);
			if (this.renderInfo == null || !newInfo.equals(this.renderInfo)) {
				this.setRender(newInfo);
			}
		}
		final TileEntity above = this.world.getTileEntity(pos.up());
		if (above instanceof TileEntityFlower) {
			((TileEntityFlower) above).updateRender(true);
		}
	}

	public int getSection() {
		return this.section;
	}

	public void setSection(final int i) {
		this.section = i;
		if (BinnieCore.getBinnieProxy().isSimulating(this.world)) {
			this.updateRender(true);
		}
	}

	public ItemStack getItemStack() {
		IFlower flower = this.getFlower();
		if (flower == null) {
			return ItemStack.EMPTY;
		}
		return Binnie.GENETICS.getFlowerRoot().getMemberStack(flower, EnumFlowerStage.getStage(flower));
	}

	@Nullable
	private TileEntityFlower getRoot() {
		if (this.getSection() == 0) {
			return null;
		}
		final TileEntity tile = this.world.getTileEntity(pos.down(getSection()));
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
			final ItemStack cuttingStack = BotanyCore.getFlowerRoot().getMemberStack(cutting, EnumFlowerStage.SEED);
			final float f = 0.7f;
			final double d = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final double d2 = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final double d3 = rand.nextFloat() * f + (1.0f - f) * 0.5;
			final EntityItem entityitem = new EntityItem(this.world, pos.getX() + d, pos.getY() + d2, pos.getZ() + d3, cuttingStack);
			entityitem.setPickupDelay(10);
			this.world.spawnEntity(entityitem);
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
		final EnumSoilType soil = Gardening.getSoilType(this.world, pos);
		final int maxAge = (int) (this.flower.getMaxAge() * (1.0f + soil.ordinal() * 0.25f));
		if (this.flower.getAge() > maxAge) {
			if (!wasCut && this.flower.getMate() != null) {
				this.world.setBlockToAir(pos);
				final IFlower offspring = this.flower.getOffspring(this.world, pos.down());
				final TileEntity above = this.world.getTileEntity(pos.up());
				if (above instanceof TileEntityFlower) {
					this.world.setBlockToAir(pos.up());
				}
				Gardening.plant(this.world, pos, offspring, this.getOwner());
			} else {
				this.kill();
			}
			return true;
		}
		return false;
	}

	public void kill() {
		if (this.flower.getAge() > 0) {
			this.world.setBlockState(pos, Botany.plant.getStateFromMeta(BlockPlant.Type.DeadFlower.ordinal()), 2);
		} else {
			this.world.setBlockToAir(pos);
		}
		for (int i = 1; this.world.getTileEntity(pos.up(i)) instanceof TileEntityFlower; ++i) {
			this.world.setBlockToAir(pos.up(i));
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
		//this.doFlowerAge();
		if (this.getFlower().getAge() > 1 && !this.getFlower().hasFlowered()) {
			this.getFlower().setFlowered(true);
		}
		this.checkIfDead(false);
		this.updateRender(true);
		return true;
	}

	@Nullable
	public GameProfile getOwner() {
		return this.owner;
	}

	public void setOwner(final GameProfile ownerName) {
		this.owner = ownerName;
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

	private void attemptButterflySpawn(final World world, final IButterfly butterfly, final double x, final double y, final double z) {
		if (BinnieCore.isLepidopteryActive()) {
			Binnie.GENETICS.getButterflyRoot().spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612, z);
		}
	}

	public GameProfile getOwnerName() {
		return this.getOwner();
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.getFromValue(this.world.getBiome(getPos()).getTemperature());
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.getFromValue(this.world.getBiome(getPos()).getRainfall());
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
	@Nullable
	public IButterfly getCaterpillar() {
		return this.caterpillar;
	}

	@Override
	public void setCaterpillar(@Nullable final IButterfly butterfly) {
		this.caterpillar = butterfly;
		this.matureTime = 0;
	}

	@Override
	public IIndividual getNanny() {
		return this.getFlower();
	}

	@Override
	public boolean canNurse(final IButterfly butterfly) {
		return this.getFlower() != null && !this.getFlower().isWilted() && this.getFlower().getAge() > 1;
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

	public void setRender(final RenderInfo render) {
		this.renderInfo = render;
		this.section = this.renderInfo.section;
		if (!world.isRemote) {
			IBlockState blockState = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, blockState, blockState, 0);
		} else {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
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
		return (this.renderInfo == null) ? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false) : this.renderInfo.primary.getColor(this.isWilted());
	}

	public int getSecondaryColour() {
		return (this.renderInfo == null) ? EnumFlowerColor.Red.getFlowerColorAllele().getColor(false) : this.renderInfo.secondary.getColor(this.isWilted());
	}

	public int getStemColour() {
		return (this.renderInfo == null) ? EnumFlowerColor.Green.getFlowerColorAllele().getColor(false) : this.renderInfo.stem.getColor(this.isWilted());
	}

	public IFlowerType getType() {
		return (this.renderInfo == null) ? EnumFlowerType.Poppy : this.renderInfo.type;
	}

	@Override
	public Biome getBiome() {
		return this.getWorld().getBiome(getPos());
	}

	@Nullable
	public IErrorState getErrorState() {
		return null;
	}

	public void setErrorState(final IErrorState state) {
	}

	public boolean setErrorCondition(final boolean condition, final IErrorState errorState) {
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

		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof RenderInfo) {
				final RenderInfo o = (RenderInfo) obj;
				return o.age == this.age && o.wilted == this.wilted && o.flowered == this.flowered && o.primary == this.primary && o.secondary == this.secondary && o.stem == this.stem && o.type == this.type;
			}
			return super.equals(obj);
		}
	}
}
