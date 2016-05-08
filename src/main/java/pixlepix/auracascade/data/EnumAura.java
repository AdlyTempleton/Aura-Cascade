package pixlepix.auracascade.data;

import java.util.List;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.main.EnumColor;

public enum EnumAura {


    WHITE_AURA("White", 1, 1, 1, EnumColor.BLACK, new int[]{0}),

    GREEN_AURA("Green", .1, 1, .1, EnumColor.DARK_GREEN, new int[]{5, 13}) {
        @Override
        public double getRelativeMass(World world) {

            AuraCascade.analytics.eventDesign("greenAura");
            if (world.isDaytime()) {
                return 2D;
            }
            return .5D;
        }
    },

    BLACK_AURA("Black", .1, .1, .1, EnumColor.BLACK, new int[]{12, 15, 7, 8}) {
        @Override
        public double getRelativeMass(World world) {

            AuraCascade.analytics.eventDesign("blackAura");
            return 0D;

        }
    },
    RED_AURA("Red", 1, .1, .1, EnumColor.RED, new int[]{14}) {
        @Override
        public void updateTick(World world, BlockPos pos, AuraQuantity quantity) {
            //Implementation loosely based off of Vazkii's Botania
            AxisAlignedBB search = PosUtil.getBoundingBox(pos, 3);
            List<EntityTNTPrimed> tntList = world.getEntitiesWithinAABB(EntityTNTPrimed.class, search);
            for (EntityTNTPrimed tntPrimed : tntList) {
                if (tntPrimed.getFuse() <= 2 && !tntPrimed.isDead) {
                    tntPrimed.setDead();
                    explosionPushUp(world, pos, 200000);
                }
            }
            List<EntityCreeper> creeperList = world.getEntitiesWithinAABB(EntityCreeper.class, search);
            for (EntityCreeper creeper : creeperList) {
                if (creeper.timeSinceIgnited + 2 >= creeper.fuseTime && !creeper.isDead) {
                    creeper.setDead();
                    explosionPushUp(world, pos, 50000);
                }
            }
            AuraCascade.analytics.eventDesign("redAura", AuraUtil.formatLocation(pos));
        }
    },
    ORANGE_AURA("Orange", 1, .5, 0, EnumColor.ORANGE, new int[]{1}) {
        @Override
        public void onTransfer(World world, BlockPos pos, AuraQuantity quantity, EnumFacing direction) {
            for (BlockPos nearbyNode : PosUtil.inRange(pos, 2)) {
                if (world.getTileEntity(nearbyNode) instanceof AuraTile && !pos.equals(nearbyNode) && PosUtil.directionTo(pos, nearbyNode) != direction && PosUtil.directionTo(pos, nearbyNode) != direction.getOpposite()) {
                    AuraTile auraTile = (AuraTile) world.getTileEntity(nearbyNode);
                    for (BlockPos targetNode : auraTile.connected) {
                        if (PosUtil.directionTo(nearbyNode, targetNode)== direction) {
                            ((AuraTile) world.getTileEntity(nearbyNode)).inducedBurstMap.put(targetNode, quantity.getNum());
                            break;
                        }
                    }

                }
            }
            AuraCascade.analytics.eventDesign("orangeAura", AuraUtil.formatLocation(pos));
        }

        @Override
        public double getRelativeMass(World world) {
            return 0D;
        }
    },
    YELLOW_AURA("Yellow", 1, 1, .1, EnumColor.YELLOW, new int[]{4}) {
        @Override
        public double getAscentBoost(World world) {
            return 2D;
        }

        @Override
        public void updateTick(World world, BlockPos pos, AuraQuantity quantity) {
            if (world.getTotalWorldTime() % 1200 == 5) {
                AuraTile tile = (AuraTile) world.getTileEntity(pos);
                tile.storage.set(this, (int) (.8D * (double) tile.storage.get(this)));
                AuraCascade.analytics.eventDesign("yellowAura", AuraUtil.formatLocation(pos), 0);
            }
        }
    },
    BLUE_AURA("Blue", .1, .1, 1, EnumColor.DARK_BLUE, new int[]{3, 9, 11}) {
        @Override
        public double getAscentBoost(World world) {

            AuraCascade.analytics.eventDesign("blueAura");
            return world.isRaining() ? 4 : .5;
        }
    },
    VIOLET_AURA("Violet", 1, .1, 1, EnumColor.PURPLE, new int[]{2, 6, 10}) {
        @Override
        public void updateTick(World world, BlockPos pos, AuraQuantity quantity) {
            if (quantity.getNum() > 2600) {
                AuraTile tile = (AuraTile) world.getTileEntity(pos);
                if (tile != null) {
                    tile.storage.set(this, 0);

                }
            } else if (world.getTotalWorldTime() % 300 == 5) {
                AuraTile tile = (AuraTile) world.getTileEntity(pos);
                if (tile != null) {

                    //Achieve growth along logarithmic curve
                    int num = quantity.getNum();
                    int delta = num <= 25 ? -num : 5 * (Math.min(100, (int) Math.floor(((double) 2500 / num))));

                    AuraCascade.analytics.eventDesign("violetAura", AuraUtil.formatLocation(tile), num);

                    if (tile.storage == null) {
                        AuraCascade.log.error("Storage data is null in updateTick of EnumAura");
                    } else {
                        tile.storage.set(this, num + delta);
                    }
                }
            }

        }
    };
    public String name;
    public double r;
    public double g;
    public double b;
    public EnumColor color;

    public int[] dyes;

    EnumAura(String name, double r, double g, double b, EnumColor color, int[] dyes) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.color = color;
        this.dyes = dyes;
    }

    public static EnumAura getColorFromDyeMeta(int i) {
        for (EnumAura aura : EnumAura.values()) {
            for (int dyeMeta : aura.dyes) {
                if (dyeMeta == i) {
                    return aura;
                }
            }
        }
        return null;
    }

    public void updateTick(World world, BlockPos tuple, AuraQuantity quantity) {
    }

    public double getRelativeMass(World world) {
        return 1D;
    }

    public double getAscentBoost(World world) {
        return 1D;
    }

    public void onTransfer(World world, BlockPos tuple, AuraQuantity quantity, EnumFacing direction) {
    }

    public void explosionPushUp(World world, BlockPos pos, int power) {
        //Make graphical explosion
        Explosion explosion = new Explosion(world, null, pos.getX(), pos.getY(), pos.getZ(), 4F, false, true);
        explosion.doExplosionB(false);

        //Move up mana

        if (world.getTileEntity(pos) instanceof AuraTile) {
            AuraTile tile = (AuraTile) world.getTileEntity(pos);
            tile.verifyConnections();
            for (BlockPos connectedNode : tile.connected) {
                if (connectedNode.getY() > tile.getPos().getY()) {
                    AuraTile transferTile = (AuraTile) world.getTileEntity(connectedNode);

                    int auraPower = power / (connectedNode.getY() - tile.getPos().getY());
                    auraPower = Math.min(auraPower, tile.storage.get(this));

                    tile.burst(connectedNode, "magicCrit", this, 1D);
                    tile.storage.subtract(this, auraPower);
                    transferTile.storage.add(new AuraQuantity(this, auraPower));
                }
            }
        }

    }
}
