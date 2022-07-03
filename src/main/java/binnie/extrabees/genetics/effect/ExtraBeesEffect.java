package binnie.extrabees.genetics.effect;

import binnie.Binnie;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import cofh.api.energy.IEnergyReceiver;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IArmorApiarist;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;
import forestry.core.proxy.Proxies;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public enum ExtraBeesEffect implements IAlleleBeeEffect {
    ECTOPLASM,
    ACID,
    SPAWN_ZOMBIE,
    SPAWN_SKELETON,
    SPAWN_CREEPER,
    LIGHTNING,
    RADIOACTIVE,
    METEOR,
    HUNGER,
    FOOD,
    BLINDNESS,
    CONFUSION,
    FIREWORKS,
    FESTIVAL,
    BIRTHDAY,
    TELEPORT,
    GRAVITY,
    THIEF,
    WITHER,
    WATER,
    SLOW,
    BONEMEAL_SAPLING,
    BONEMEAL_FRUIT,
    BONEMEAL_MUSHROOM,
    POWER;

    protected static List<Birthday> birthdays;

    static {
        birthdays = new ArrayList<>();
        birthdays.add(new Birthday(3, 10, "Binnie"));
    }

    public boolean combinable;
    public boolean dominant;
    public int id;
    String fx;
    private String uid;

    ExtraBeesEffect() {
        fx = "";
        uid = toString().toLowerCase();
        combinable = false;
        dominant = true;
    }

    public static void doInit() {
        ExtraBeesEffect.BLINDNESS.setFX("blindness");
        ExtraBeesEffect.FOOD.setFX("food");
        ExtraBeesEffect.GRAVITY.setFX("gravity");
        ExtraBeesEffect.THIEF.setFX("gravity");
        ExtraBeesEffect.TELEPORT.setFX("gravity");
        ExtraBeesEffect.LIGHTNING.setFX("lightning");
        ExtraBeesEffect.METEOR.setFX("meteor");
        ExtraBeesEffect.RADIOACTIVE.setFX("radioactive");
        ExtraBeesEffect.WATER.setFX("water");
        ExtraBeesEffect.WITHER.setFX("wither");
        for (ExtraBeesEffect effect : values()) {
            effect.register();
        }
    }

    public static boolean wearsHelmet(EntityPlayer player) {
        ItemStack armorItem = player.inventory.armorInventory[3];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsChest(EntityPlayer player) {
        ItemStack armorItem = player.inventory.armorInventory[2];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsLegs(EntityPlayer player) {
        ItemStack armorItem = player.inventory.armorInventory[1];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsBoots(EntityPlayer player) {
        ItemStack armorItem = player.inventory.armorInventory[0];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static int wearsItems(EntityPlayer player) {
        int count = 0;
        if (wearsHelmet(player)) {
            count++;
        }
        if (wearsChest(player)) {
            count++;
        }
        if (wearsLegs(player)) {
            count++;
        }
        if (wearsBoots(player)) {
            count++;
        }
        return count;
    }

    public void register() {
        AlleleManager.alleleRegistry.registerAllele(this);
    }

    @Override
    public boolean isCombinable() {
        return combinable;
    }

    @Override
    public IEffectData validateStorage(IEffectData storedData) {
        return storedData;
    }

    @Override
    public String getName() {
        return I18N.localise("extrabees.effect." + name().toLowerCase().replace("_", "") + ".name");
    }

    @Override
    public boolean isDominant() {
        return dominant;
    }

    public void spawnMob(World world, int x, int y, int z, String name) {
        if (!anyPlayerInRange(world, x, y, z, 16)) {
            return;
        }

        double xOffset = x + world.rand.nextFloat();
        double yOffset = y + world.rand.nextFloat();
        double zOffset = z + world.rand.nextFloat();
        world.spawnParticle("smoke", xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
        world.spawnParticle("flame", xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
        EntityLiving entity = (EntityLiving) EntityList.createEntityByName(name, world);

        if (entity == null) {
            return;
        }

        int entityCount = world.getEntitiesWithinAABB(
                        entity.getClass(),
                        AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1)
                                .expand(8.0, 4.0, 8.0))
                .size();
        if (entityCount >= 6) {
            return;
        }

        xOffset = x + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
        yOffset = y + world.rand.nextInt(3) - 1;
        zOffset = z + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
        entity.setLocationAndAngles(xOffset, yOffset, zOffset, world.rand.nextFloat() * 360.0f, 0.0f);

        if (entity.getCanSpawnHere()) {
            world.spawnEntityInWorld(entity);
            world.playAuxSFX(2004, x, y, z, 0);
            entity.spawnExplosionParticle();
        }
    }

    private boolean anyPlayerInRange(World world, int x, int y, int z, int distance) {
        return world.getClosestPlayer(x + 0.5, y + 0.5, z + 0.5, distance) != null;
    }

    @Override
    public String getUID() {
        return "extrabees.effect." + uid;
    }

    @Override
    public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        World world = housing.getWorld();
        int xHouse = housing.getCoordinates().posX;
        int yHouse = housing.getCoordinates().posY;
        int zHouse = housing.getCoordinates().posZ;
        int[] area = getModifiedArea(genome, housing);
        int xd = 1 + area[0] / 2;
        int yd = 1 + area[1] / 2;
        int zd = 1 + area[2] / 2;
        int x = xHouse - xd + world.rand.nextInt(2 * xd + 1);
        int y = yHouse - yd + world.rand.nextInt(2 * yd + 1);
        int z = zHouse - zd + world.rand.nextInt(2 * zd + 1);

        switch (this) {
            case ECTOPLASM:
                onEctoplasmEffect(world, x, y, z);
                break;

            case ACID:
                onAcidEffect(world, x, y, z);
                break;

            case SPAWN_ZOMBIE:
                onSpawnMobEffect(world, x, y, z, "Zombie");
                break;

            case SPAWN_SKELETON:
                onSpawnMobEffect(world, x, y, z, "Skeleton");
                break;

            case SPAWN_CREEPER:
                onSpawnMobEffect(world, x, y, z, "Creeper");
                break;

            case LIGHTNING:
                onLightingEffect(world, x, y, z);
                break;

            case METEOR:
                onMeteorEffect(world, x, y, z);
                break;

            case RADIOACTIVE:
                onRadioactiveEffect(genome, housing);
                break;

            case FOOD:
                onFoodEffect(genome, housing);
                break;

            case HUNGER:
                onHungerEffect(genome, housing, world);
                break;

            case BLINDNESS:
                onPotionEffect(genome, housing, world, new PotionEffect(Potion.blindness.id, 200));
                break;

            case SLOW:
                onPotionEffect(genome, housing, world, new PotionEffect(Potion.weakness.id, 200));
                break;

            case CONFUSION: {
                onPotionEffect(genome, housing, world, new PotionEffect(Potion.confusion.id, 200));
                break;
            }

            case BIRTHDAY:
            case FESTIVAL:
            case FIREWORKS:
                onFeastEffect(genome, world, x, y, z);
                break;

            case GRAVITY:
                onGravityEffect(genome, housing, x, y, z);
                break;

            case THIEF:
                onThiefEffect(genome, housing, x, y, z);
                break;

            case TELEPORT:
                onTeleportEffect(genome, housing, world, x, y, z);
                break;

            case WATER:
                onWaterEffect(world, x, y, z);
                break;

            case BONEMEAL_SAPLING:
                onBonemealSaplingEffect(world, x, y, z);
                break;

            case BONEMEAL_FRUIT:
                onBonemealFruitEffect(world, x, y, z);
                break;

            case BONEMEAL_MUSHROOM:
                onBonemealMushroomEffect(world, x, y, z);
                break;

            case POWER:
                onPowerEffect(world, x, y, z);
                break;
        }
        return null;
    }

    private void onLightingEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x, y, z) && world instanceof WorldServer) {
            world.addWeatherEffect(new EntityBeeLightning(world, x, y, z));
        }
    }

    private void onEctoplasmEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(100) >= 4) {
            return;
        }

        if (world.isAirBlock(x, y, z)
                && (world.isBlockNormalCubeDefault(x, y - 1, z, false)
                        || world.getBlock(x, y - 1, z) == ExtraBees.ectoplasm)) {
            world.setBlock(x, y, z, ExtraBees.ectoplasm, 0, 0);
        }
    }

    private void onAcidEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(100) >= 6) {
            return;
        }

        Block block = world.getBlock(x, y, z);
        if (block == Blocks.cobblestone || block == Blocks.stone) {
            world.setBlock(x, y, z, Blocks.gravel, 0, 0);
        } else if (block == Blocks.dirt | block == Blocks.grass) {
            world.setBlock(x, y, z, Blocks.sand, 0, 0);
        }
    }

    private void onSpawnMobEffect(World world, int x, int y, int z, String mobName) {
        if (world.rand.nextInt(200) < 2) {
            spawnMob(world, x, y, z, mobName);
        }
    }

    private void onMeteorEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x, y, z)) {
            world.spawnEntityInWorld(new EntitySmallFireball(world, x, y + 64, z, 0.0, -0.6, 0.0));
        }
    }

    private void onRadioactiveEffect(IBeeGenome genome, IBeeHousing housing) {
        for (EntityLivingBase entity : getEntities(EntityLivingBase.class, genome, housing)) {
            int damage = 4;
            if (entity instanceof EntityPlayer) {
                int count = wearsItems((EntityPlayer) entity);
                if (count > 3) {
                    continue;
                }

                if (count > 2) {
                    damage = 1;
                } else if (count > 1) {
                    damage = 2;
                } else if (count > 0) {
                    damage = 3;
                }
            }
            entity.attackEntityFrom(DamageSource.generic, damage);
        }
    }

    private void onFoodEffect(IBeeGenome genome, IBeeHousing housing) {
        for (EntityLivingBase entity : getEntities(EntityLivingBase.class, genome, housing)) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                player.getFoodStats().addStats(2, 0.2f);
            }
        }
    }

    private void onHungerEffect(IBeeGenome genome, IBeeHousing housing, World world) {
        for (EntityLivingBase entity : getEntities(EntityLivingBase.class, genome, housing)) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (world.rand.nextInt(4) < wearsItems(player)) {
                    continue;
                }

                player.getFoodStats().addExhaustion(4.0f);
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100));
            }
        }
    }

    private void onPotionEffect(IBeeGenome genome, IBeeHousing housing, World world, PotionEffect potion) {
        for (EntityLivingBase entity : getEntities(EntityLivingBase.class, genome, housing)) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (world.rand.nextInt(4) < wearsItems(player)) {
                    continue;
                }
                player.addPotionEffect(potion);
            }
        }
    }

    private void onFeastEffect(IBeeGenome genome, World world, int x, int y, int z) {
        if (world.rand.nextInt((this == ExtraBeesEffect.FIREWORKS) ? 8 : 12) >= 1) {
            return;
        }

        FireworkCreator.Firework firework = new FireworkCreator.Firework();
        switch (this) {
            case BIRTHDAY:
                firework.setShape(FireworkCreator.Shape.Star);
                firework.addColor(0xffdd00);
                for (Birthday birthday : ExtraBeesEffect.birthdays) {
                    if (birthday.isToday()) {
                        firework.addColor(0xff0000);
                        firework.addColor(0x00ff00);
                        firework.addColor(0x0000ff);
                        firework.setTrail();
                        break;
                    }
                }

            case FIREWORKS:
                firework.setShape(FireworkCreator.Shape.Ball);
                firework.addColor(genome.getPrimary().getIconColour(0));
                firework.addColor(genome.getPrimary().getIconColour(0));
                firework.addColor(genome.getPrimary().getIconColour(1));
                firework.addColor(genome.getSecondary().getIconColour(0));
                firework.addColor(genome.getSecondary().getIconColour(0));
                firework.addColor(genome.getPrimary().getIconColour(1));
                firework.setTrail();
                break;
        }

        EntityFireworkRocket rocket = new EntityFireworkRocket(world, x, y, z, firework.getFirework());
        if (world.canBlockSeeTheSky(x, y, z)) {
            world.spawnEntityInWorld(rocket);
        }
    }

    private void onGravityEffect(IBeeGenome genome, IBeeHousing housing, int x, int y, int z) {
        List<Entity> entities = getEntities(Entity.class, genome, housing);
        for (Entity entity : entities) {
            float entityStrength = 1.0f;
            if (entity instanceof EntityPlayer) {
                entityStrength *= 100.0f;
            }

            double dx = x - entity.posX;
            double dy = y - entity.posY;
            double dz = z - entity.posZ;
            if (dx * dx + dy * dy + dz * dz < 2.0) {
                break;
            }

            double strength = 0.5 / (dx * dx + dy * dy + dz * dz) * entityStrength;
            entity.addVelocity(dx * strength, dy * strength, dz * strength);
        }
    }

    private void onThiefEffect(IBeeGenome genome, IBeeHousing housing, int x, int y, int z) {
        List<EntityPlayer> players = getEntities(EntityPlayer.class, genome, housing);
        for (EntityPlayer player : players) {
            double dx = x - player.posX;
            double dy = y - player.posY;
            double dz = z - player.posZ;
            if (dx * dx + dy * dy + dz * dz < 2.0) {
                break;
            }
            double strength = 0.5 / (dx * dx + dy * dy + dz * dz);
            player.addVelocity(-dx * strength, -dy * strength, -dz * strength);
        }
    }

    private void onTeleportEffect(IBeeGenome genome, IBeeHousing housing, World world, int x, int y, int z) {
        if (world.rand.nextInt(80) > 1) {
            return;
        }

        List<Entity> entities = getEntities(Entity.class, genome, housing);
        if (entities.size() == 0) {
            return;
        }

        Entity entity = entities.get(world.rand.nextInt(entities.size()));
        if (!(entity instanceof EntityLiving)) {
            return;
        }

        if (y < 4) {
            y = 4;
        }
        if (!world.isAirBlock(x, y, z) || !world.isAirBlock(x, y + 1, z)) {
            return;
        }

        EntityLiving living = (EntityLiving) entity;
        living.setPositionAndUpdate(x, y, z);
        living.addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 10));
    }

    private void onWaterEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(120) > 1) {
            return;
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IFluidHandler) {
            ((IFluidHandler) tile).fill(ForgeDirection.UP, Binnie.Liquid.getLiquidStack("water", 100), true);
        }
    }

    private void onBonemealSaplingEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(20) > 1) {
            return;
        }

        if (ExtraBeesFlowers.SAPLING.isAcceptedFlower(world, x, y, z)) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x, y, z, null);
        }
    }

    private void onBonemealFruitEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(20) > 1) {
            return;
        }

        if (ExtraBeesFlowers.FRUIT.isAcceptedFlower(world, x, y, z)) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x, y, z, null);
        }
    }

    private void onBonemealMushroomEffect(World world, int x, int y, int z) {
        if (world.rand.nextInt(20) > 1) {
            return;
        }

        if (world.getBlock(x, y, z) == Blocks.brown_mushroom || world.getBlock(x, y, z) == Blocks.red_mushroom) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x, y, z, null);
        }
    }

    private void onPowerEffect(World world, int x, int y, int z) {
        TileEntity tile2 = world.getTileEntity(x, y, z);
        if (tile2 instanceof IEnergyReceiver) {
            ((IEnergyReceiver) tile2).receiveEnergy(ForgeDirection.getOrientation(0), 5, true);
        }
    }

    protected int[] getModifiedArea(IBeeGenome genome, IBeeHousing housing) {
        int[] territory;
        int[] area = territory = genome.getTerritory();
        int n = 0;
        territory[n] *=
                (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f);

        int[] array = area;
        int n2 = 1;
        array[n2] *=
                (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f);

        int[] array2 = area;
        int n3 = 2;
        array2[n3] *=
                (int) (BeeManager.beeRoot.createBeeHousingModifier(housing).getTerritoryModifier(genome, 1.0f) * 3.0f);

        if (area[0] < 1) {
            area[0] = 1;
        }
        if (area[1] < 1) {
            area[1] = 1;
        }
        if (area[2] < 1) {
            area[2] = 1;
        }
        return area;
    }

    @Override
    public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        Proxies.render.addBeeHiveFX(
                "particles/swarm_bee",
                housing.getWorld(),
                housing.getBeeFXCoordinates().xCoord,
                housing.getBeeFXCoordinates().yCoord,
                housing.getBeeFXCoordinates().zCoord,
                genome.getPrimary().getIconColour(0));
        return storedData;
    }

    private void setFX(String string) {
        fx = "particles/" + string;
    }

    public <T extends Entity> List<T> getEntities(Class<T> eClass, IBeeGenome genome, IBeeHousing housing) {
        int[] area = genome.getTerritory();
        int[] offset = {-Math.round(area[0] / 2), -Math.round(area[1] / 2), -Math.round(area[2] / 2)};
        int[] min = {
            housing.getCoordinates().posX + offset[0],
            housing.getCoordinates().posY + offset[1],
            housing.getCoordinates().posZ + offset[2]
        };
        int[] max = {
            housing.getCoordinates().posX + offset[0] + area[0],
            housing.getCoordinates().posY + offset[1] + area[1],
            housing.getCoordinates().posZ + offset[2] + area[2]
        };
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(min[0], min[1], min[2], max[0], max[1], max[2]);
        return housing.getWorld().getEntitiesWithinAABB(eClass, box);
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }

    public static class Birthday {
        protected int day;
        protected int month;
        protected String name;

        private Birthday(int day, int month, String name) {
            this.day = day;
            this.month = month + 1;
            this.name = name;
        }

        public boolean isToday() {
            return Calendar.getInstance().get(5) == month
                    && Calendar.getInstance().get(2) == day;
        }

        public String getName() {
            return name;
        }
    }
}
