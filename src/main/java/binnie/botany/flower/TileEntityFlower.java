package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColor;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.botany.network.MessageFlowerUpdate;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import forestry.lepidopterology.entities.EntityButterfly;
import java.util.EnumSet;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;

public class TileEntityFlower extends TileEntity implements IPollinatable, IButterflyNursery {
    protected IFlower flower;
    protected GameProfile owner;
    protected int section;
    protected RenderInfo renderInfo;
    protected IButterfly caterpillar;
    protected int matureTime;

    public TileEntityFlower() {
        flower = null;
        section = 0;
        renderInfo = null;
        matureTime = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        flower = new Flower(nbttagcompound);
        section = nbttagcompound.getByte("section");
        if (flower.getAge() == 0) {
            flower.setFlowered(false);
        }

        owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
        if (nbttagcompound.hasKey("CATER") && BinnieCore.isLepidopteryActive()) {
            matureTime = nbttagcompound.getInteger("caterTime");
            caterpillar = Binnie.Genetics.getButterflyRoot().getMember(nbttagcompound.getCompoundTag("cater"));
        }
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        if (flower != null) {
            flower.writeToNBT(nbttagcompound);
        }

        if (owner != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTUtil.func_152460_a(nbt, owner);
            nbttagcompound.setTag("owner", nbt);
        }

        if (caterpillar != null) {
            nbttagcompound.setInteger("caterTime", matureTime);
            NBTTagCompound subcompound = new NBTTagCompound();
            caterpillar.writeToNBT(subcompound);
            nbttagcompound.setTag("cater", subcompound);
        }
        nbttagcompound.setByte("section", (byte) getSection());
        super.writeToNBT(nbttagcompound);
    }

    public void create(ItemStack stack, GameProfile owner) {
        create(BotanyCore.getFlowerRoot().getMember(stack), owner);
    }

    public void create(IFlower stack, GameProfile owner) {
        flower = stack;
        if (flower.getAge() == 0) {
            flower.setFlowered(false);
        }

        updateRender(true);
        this.owner = owner;
    }

    @Override
    public EnumSet<EnumPlantType> getPlantType() {
        return EnumSet.of(EnumPlantType.Plains);
    }

    @Override
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

        IAlleleFlowerSpecies primary =
                (IAlleleFlowerSpecies) individual.getGenome().getPrimary();
        IAlleleFlowerSpecies primary2 = getFlower().getGenome().getPrimary();
        if (primary == primary2 || worldObj.rand.nextInt(4) == 0) {
            getFlower().mate((IFlower) individual);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public IFlower getFlower() {
        if (getSection() <= 0) {
            return flower;
        }

        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - getSection(), zCoord);
        if (tile instanceof TileEntityFlower) {
            return ((TileEntityFlower) tile).getFlower();
        }
        return null;
    }

    public boolean isBreeding() {
        Block roots = getWorldObj().getBlock(xCoord, yCoord - 1, zCoord);
        return Gardening.isSoil(roots);
    }

    public void randomUpdate(Random rand) {
        if (worldObj.getBlock(xCoord, yCoord, zCoord) != Botany.flower) {
            invalidate();
            return;
        }
        if (getSection() > 0 || flower == null) {
            return;
        }

        if (flower.getGenome() == null) {
            invalidate();
            return;
        }
        if (!isBreeding()) {
            return;
        }

        float light = worldObj.getBlockLightValue(xCoord, yCoord, zCoord);
        if (light < 6.0f) {
            for (int dx = -2; dx <= 2; ++dx) {
                for (int dy = -2; dy <= 2; ++dy) {
                    light -= (worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) ? 0.0f : 0.5f);
                }
            }
        }

        boolean canTolerate = Gardening.canTolerate(getFlower(), worldObj, xCoord, yCoord, zCoord);
        EnumSoilType soil = Gardening.getSoilType(worldObj, xCoord, yCoord, zCoord);
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

        float chanceDispersal = 0.8f;
        chanceDispersal += 0.2f * flower.getGenome().getFertility();
        chanceDispersal *= 1.0f + soil.ordinal() * 0.5f;
        float chancePollinate = 1.0f;
        chancePollinate += 0.25f * flower.getGenome().getFertility();
        chancePollinate *= 1.0f + soil.ordinal() * 0.5f;
        float chanceSelfPollinate = 0.2f * chancePollinate;

        if (worldObj.rand.nextFloat() < chanceDispersal && flower.hasFlowered() && !flower.isWilted()) {
            IFlowerGenome mate = flower.getMate();
            if (mate != null) {
                boolean dispersed = false;
                for (int a = 0; a < 5 && !dispersed; ++a) {
                    int dx2 = 0;
                    int dz = 0;
                    while (dx2 == 0 && dz == 0) {
                        dx2 = rand.nextInt(3) - 1;
                        dz = rand.nextInt(3) - 1;
                    }

                    Block b = getWorldObj().getBlock(xCoord + dx2, yCoord, zCoord + dz);
                    Block b2 = getWorldObj().getBlock(xCoord + dx2, yCoord - 1, zCoord + dz);
                    if (b == Blocks.air && Gardening.isSoil(b2)) {
                        IFlower offspring = flower.getOffspring(getWorldObj());
                        if (offspring != null) {
                            Gardening.plant(getWorldObj(), xCoord + dx2, yCoord, zCoord + dz, offspring, getOwner());
                            flower.removeMate();
                            dispersed = true;
                        }
                    }
                }
            }
        }

        if (worldObj.rand.nextFloat() < chancePollinate && flower.hasFlowered() && !flower.isWilted()) {
            for (int a2 = 0; a2 < 4; ++a2) {
                int dx3 = 0;
                int dz2 = 0;
                while (dx3 == 0 && dz2 == 0) {
                    dx3 = rand.nextInt(5) - 2;
                    dz2 = rand.nextInt(5) - 2;
                }

                TileEntity tile = getWorldObj().getTileEntity(xCoord + dx3, yCoord, zCoord + dz2);
                if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(getFlower())) {
                    ((IPollinatable) tile).mateWith(getFlower());
                }
            }
        }

        if (worldObj.rand.nextFloat() < chanceSelfPollinate && flower.hasFlowered() && flower.getMate() == null) {
            mateWith(getFlower());
        }
        spawnButterflies();
        matureCaterpillar();
        checkIfDead(false);
        updateRender(true);
    }

    private void doFlowerAge() {
        getFlower().age();
        if (getFlower().getAge() != 1) {
            return;
        }

        Gardening.onGrowFromSeed(worldObj, xCoord, yCoord, zCoord);
        if (getOwner() != null && getFlower() != null) {
            BotanyCore.getFlowerRoot()
                    .getBreedingTracker(getWorldObj(), getOwner())
                    .registerBirth(getFlower());
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        if (renderInfo == null && getFlower() != null && getFlower().getGenome() != null) {
            renderInfo = new RenderInfo(getFlower(), this);
        }
        return (renderInfo != null)
                ? Botany.instance
                        .getNetworkWrapper()
                        .getPacketFrom(new MessageFlowerUpdate(xCoord, yCoord, zCoord, renderInfo).getMessage())
                : null;
    }

    public void updateRender(boolean update) {
        if (update && getFlower() != null && getFlower().getGenome() != null) {
            RenderInfo newInfo = new RenderInfo(getFlower(), this);
            if (renderInfo == null || !newInfo.equals(renderInfo)) {
                setRender(newInfo);
            }
        }

        TileEntity above = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (above instanceof TileEntityFlower) {
            ((TileEntityFlower) above).updateRender(true);
        }
    }

    public int getSection() {
        return section;
    }

    public void setSection(int i) {
        section = i;
        if (BinnieCore.proxy.isSimulating(worldObj)) {
            updateRender(true);
        }
    }

    public ItemStack getItemStack() {
        if (flower == null) {
            return null;
        }

        int stage = (flower.getAge() == 0) ? EnumFlowerStage.SEED.ordinal() : EnumFlowerStage.FLOWER.ordinal();
        return Binnie.Genetics.getFlowerRoot().getMemberStack(getFlower(), stage);
    }

    private TileEntityFlower getRoot() {
        if (getSection() == 0) {
            return null;
        }

        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - getSection(), zCoord);
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
        ItemStack cuttingStack = BotanyCore.getFlowerRoot().getMemberStack(cutting, EnumFlowerStage.SEED.ordinal());
        float f = 0.7f;
        double d = rand.nextFloat() * f + (1.0f - f) * 0.5;
        double d2 = rand.nextFloat() * f + (1.0f - f) * 0.5;
        double d3 = rand.nextFloat() * f + (1.0f - f) * 0.5;
        EntityItem entityitem = new EntityItem(worldObj, xCoord + d, yCoord + d2, zCoord + d3, cuttingStack);
        entityitem.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem);

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

        EnumSoilType soil = Gardening.getSoilType(worldObj, xCoord, yCoord, zCoord);
        int maxAge = (int) (flower.getMaxAge() * (1.0f + soil.ordinal() * 0.25f));
        if (flower.getAge() <= maxAge) {
            return false;
        }

        if (!wasCut && flower.getMate() != null) {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            IFlower offspring = flower.getOffspring(getWorldObj());
            TileEntity above = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            if (above instanceof TileEntityFlower) {
                worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
            }
            Gardening.plant(worldObj, xCoord, yCoord, zCoord, offspring, getOwner());
        } else {
            kill();
        }
        return true;
    }

    public void kill() {
        if (flower.getAge() > 0) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Botany.plant, BlockPlant.Type.DeadFlower.ordinal(), 2);
        } else {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }

        for (int i = 1; worldObj.getTileEntity(xCoord, yCoord + i, zCoord) instanceof TileEntityFlower; ++i) {
            worldObj.setBlockToAir(xCoord, yCoord + i, zCoord);
        }
    }

    public boolean onBonemeal() {
        if (getFlower() == null || !isBreeding() || getFlower().isWilted()) {
            return false;
        }

        doFlowerAge();
        if (getFlower().getAge() > 1 && !getFlower().hasFlowered()) {
            getFlower().setFlowered(true);
        }

        checkIfDead(false);
        updateRender(true);
        return true;
    }

    public GameProfile getOwner() {
        return owner;
    }

    public void setOwner(GameProfile ownerName) {
        owner = ownerName;
    }

    public void spawnButterflies() {
        if (!BinnieCore.isLepidopteryActive()) {
            return;
        }

        int x = xCoord;
        int y = yCoord;
        int z = zCoord;
        World world = worldObj;
        if (getCaterpillar() != null) {
            return;
        }
        if (world.rand.nextFloat() >= getFlower().getGenome().getSappiness()) {
            return;
        }
        if (worldObj.rand.nextFloat() >= 0.2f) {
            return;
        }

        IButterfly spawn = Binnie.Genetics.getButterflyRoot()
                .getIndividualTemplates()
                .get(worldObj.rand.nextInt(Binnie.Genetics.getButterflyRoot()
                        .getIndividualTemplates()
                        .size()));
        if (worldObj.rand.nextFloat() >= spawn.getGenome().getPrimary().getRarity() * 0.5f) {
            return;
        }
        if (worldObj.countEntities(EntityButterfly.class) > 100) {
            return;
        }
        if (!spawn.canSpawn(worldObj, x, y, z)) {
            return;
        }

        if (world.isAirBlock(x, y + 1, z)) {
            attemptButterflySpawn(world, spawn, x, y + 1, z);
        } else if (world.isAirBlock(x - 1, y, z)) {
            attemptButterflySpawn(world, spawn, x - 1, y, z);
        } else if (world.isAirBlock(x + 1, y, z)) {
            attemptButterflySpawn(world, spawn, x + 1, y, z);
        } else if (world.isAirBlock(x, y, z - 1)) {
            attemptButterflySpawn(world, spawn, x, y, z - 1);
        } else if (world.isAirBlock(x, y, z + 1)) {
            attemptButterflySpawn(world, spawn, x, y, z + 1);
        }
    }

    private void attemptButterflySpawn(World world, IButterfly butterfly, double x, double y, double z) {
        if (BinnieCore.isLepidopteryActive()) {
            Binnie.Genetics.getButterflyRoot()
                    .spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612, z);
        }
    }

    public World getWorld() {
        return getWorldObj();
    }

    public EnumTemperature getTemperature() {
        return EnumTemperature.getFromValue(worldObj.getBiomeGenForCoords(xCoord, zCoord).temperature);
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.getFromValue(worldObj.getBiomeGenForCoords(xCoord, zCoord).rainfall);
    }

    @Override
    public IButterfly getCaterpillar() {
        return caterpillar;
    }

    @Override
    public void setCaterpillar(IButterfly butterfly) {
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
        if (getCaterpillar() == null) {
            return;
        }

        matureTime++;
        if (matureTime
                        >= caterpillar.getGenome().getLifespan()
                                / (caterpillar.getGenome().getFertility() * 2)
                && caterpillar.canTakeFlight(worldObj, xCoord, yCoord, zCoord)) {
            if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                attemptButterflySpawn(worldObj, caterpillar, xCoord, yCoord + 1, zCoord);
            } else if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord)) {
                attemptButterflySpawn(worldObj, caterpillar, xCoord - 1, yCoord, zCoord);
            } else if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord)) {
                attemptButterflySpawn(worldObj, caterpillar, xCoord + 1, yCoord, zCoord);
            } else if (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1)) {
                attemptButterflySpawn(worldObj, caterpillar, xCoord, yCoord, zCoord - 1);
            } else if (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1)) {
                attemptButterflySpawn(worldObj, caterpillar, xCoord, yCoord, zCoord + 1);
            }
            setCaterpillar(null);
        }
    }

    public void setRender(RenderInfo render) {
        renderInfo = render;
        section = renderInfo.section;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

    public int getPrimaryColor() {
        return (renderInfo == null) ? EnumFlowerColor.RED.getColor(false) : renderInfo.primary.getColor(isWilted());
    }

    public int getSecondaryColor() {
        return (renderInfo == null) ? EnumFlowerColor.RED.getColor(false) : renderInfo.secondary.getColor(isWilted());
    }

    public int getStemColour() {
        return (renderInfo == null) ? EnumFlowerColor.GREEN.getColor(false) : renderInfo.stem.getColor(isWilted());
    }

    public IFlowerType getType() {
        return (renderInfo == null) ? EnumFlowerType.POPPY : renderInfo.type;
    }

    public BiomeGenBase getBiome() {
        return getWorld().getBiomeGenForCoords(xCoord, zCoord);
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

    public static class RenderInfo {
        public IFlowerColor primary;
        public IFlowerColor secondary;
        public IFlowerColor stem;
        public IFlowerType type;
        public byte age;
        public boolean wilted;
        public boolean flowered;
        public byte section;

        public RenderInfo() {}

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
                return o.age == age
                        && o.wilted == wilted
                        && o.flowered == flowered
                        && o.primary == primary
                        && o.secondary == secondary
                        && o.stem == stem
                        && o.type == type;
            }
            return super.equals(obj);
        }
    }
}
