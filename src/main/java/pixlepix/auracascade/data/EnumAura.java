package pixlepix.auracascade.data;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import pixlepix.auracascade.AuraCascade;
import pixlepix.auracascade.block.tile.AuraTile;
import pixlepix.auracascade.main.AuraUtil;
import pixlepix.auracascade.main.EnumColor;

import java.util.List;

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
        public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
            //Implementation loosely based off of Vazkii's Botania
            AxisAlignedBB search = tuple.getBoundingBox(3);
            List<EntityTNTPrimed> tntList = world.getEntitiesWithinAABB(EntityTNTPrimed.class, search);
            for (EntityTNTPrimed tntPrimed : tntList) {
                if (tntPrimed.fuse <= 2 && !tntPrimed.isDead) {
                    tntPrimed.setDead();
                    explosionPushUp(world, tuple, 200000);
                }
            }

            List<EntityCreeper> creeperList = world.getEntitiesWithinAABB(EntityCreeper.class, search);
            for (EntityCreeper creeper : creeperList) {
                if (creeper.timeSinceIgnited + 2 >= creeper.fuseTime && !creeper.isDead) {
                    creeper.setDead();
                    explosionPushUp(world, tuple, 50000);
                }
            }

            AuraCascade.analytics.eventDesign("redAura", AuraUtil.formatLocation(tuple));
        }
    },
    ORANGE_AURA("Orange", 1, .5, 0, EnumColor.ORANGE, new int[]{1}) {
        @Override
        public void onTransfer(World world, CoordTuple tuple, AuraQuantity quantity, EnumFacing direction) {
            for (CoordTuple nearbyNode : tuple.inRange(2)) {
                if (nearbyNode.getTile(world) instanceof AuraTile && !tuple.equals(nearbyNode) && tuple.getDirectionTo(nearbyNode) != direction && tuple.getDirectionTo(nearbyNode) != direction.getOpposite()) {
                    AuraTile auraTile = (AuraTile) nearbyNode.getTile(world);
                    for (CoordTuple targetNode : auraTile.connected) {
                        if (nearbyNode.getDirectionTo(targetNode) == direction) {
                            ((AuraTile) nearbyNode.getTile(world)).inducedBurstMap.put(targetNode, quantity.getNum());
                            break;
                        }
                    }

                }
            }
            AuraCascade.analytics.eventDesign("orangeAura", AuraUtil.formatLocation(tuple));
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
        public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
            if (world.getTotalWorldTime() % 1200 == 5) {
                AuraTile tile = (AuraTile) tuple.getTile(world);
                tile.storage.set(this, (int) (.8D * (double) tile.storage.get(this)));
                AuraCascade.analytics.eventDesign("yellowAura", AuraUtil.formatLocation(tuple), 0);
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
        public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
            if (quantity.getNum() > 2600) {
                AuraTile tile = (AuraTile) tuple.getTile(world);
                if (tile != null) {
                    tile.storage.set(this, 0);

                }
            } else if (world.getTotalWorldTime() % 300 == 5) {
                AuraTile tile = (AuraTile) tuple.getTile(world);
                if (tile != null) {

                    //Achieve growth along logarithmic curve
                    int num = quantity.getNum();
                    int delta = num <= 25 ? -num : 5 * (Math.min(100, (int) Math.floor(((double) 2500 / num))));

                    AuraCascade.analytics.eventDesign("violetAura", AuraUtil.formatLocation(tile), num);

                    if (tile == null) {
                        AuraCascade.log.error("Tile entity is null in updateTick of EnumAura X: " + tuple.getX() + "Y: " + tuple.getY() + "Z: " + tuple.getZ());
                    } else if (tile.storage == null) {
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

    public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
    }

    public double getRelativeMass(World world) {
        return 1D;
    }

    public double getAscentBoost(World world) {
        return 1D;
    }

    public void onTransfer(World world, CoordTuple tuple, AuraQuantity quantity, EnumFacing direction) {
    }

    public void explosionPushUp(World world, CoordTuple tuple, int power) {
        //Make graphical explosion
        Explosion explosion = new Explosion(world, null, tuple.getX(), tuple.getY(), tuple.getZ(), 4F, false, true);
        explosion.doExplosionB(false);

        //Move up mana

        if (tuple.getTile(world) instanceof AuraTile) {
            AuraTile tile = (AuraTile) tuple.getTile(world);
            tile.verifyConnections();
            for (CoordTuple connectedNode : tile.connected) {
                if (connectedNode.getY() > tile.getPos().getY()) {
                    AuraTile transferTile = (AuraTile) connectedNode.getTile(world);

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
