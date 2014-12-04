package pixlepix.auracascade.data;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pixlepix.auracascade.block.tile.AuraTile;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public enum EnumAura {
	WHITE_AURA("White", 1, 1, 1),

	GREEN_AURA("Green", .1, 1, .1){
		@Override
		public double getRelativeMass(World world, CoordTuple tuple) {
			if(world.isDaytime()){
				return 2D;
			}
			return .5D;
		}
	},

	BLACK_AURA("Black", .1, .1, .1){
		@Override
		public double getRelativeMass(World world, CoordTuple tuple) {
			return 0D;
		}
	},
	RED_AURA("Red", 1, .1, .1){
		@Override
		public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity){
			//Implementation loosely based off of Vazkii's Botania
			AxisAlignedBB search = tuple.getBoundingBox(3);
			List<EntityTNTPrimed> tntList = world.getEntitiesWithinAABB(EntityTNTPrimed.class, search);
			for(EntityTNTPrimed tntPrimed:tntList){
				if(tntPrimed.fuse == 1 && !tntPrimed.isDead){
					tntPrimed.setDead();

					//Make graphical explosion
					Explosion explosion = new Explosion(world, null, tuple.getX(), tuple.getY(), tuple.getZ(), 4F);
					explosion.isFlaming = false;
					explosion.isSmoking = true;
					explosion.doExplosionB(false);

					//Move up mana

					if(tuple.getTile(world) instanceof AuraTile){
						AuraTile tile = (AuraTile) tuple.getTile(world);
						tile.verifyConnections();
						for(CoordTuple connectedNode: tile.connected){
							if(connectedNode.getY() > tile.yCoord){
								AuraTile transferTile = (AuraTile) connectedNode.getTile(world);

								int auraPower = 1000000 / (connectedNode.getY() - tile.yCoord);
								auraPower = Math.min(auraPower, tile.storage.get(this));

								tile.burst(connectedNode, "magicCrit", this, 1D);
								tile.storage.subtract(this, auraPower);
								transferTile.storage.add(new AuraQuantity(this, auraPower));

							}
						}
					}
				}
			}
		}
	},
	ORANGE_AURA("Orange", 1, .5, 0){
		@Override
		public void onTransfer(World world, CoordTuple tuple, AuraQuantity quantity, ForgeDirection direction){
			for(CoordTuple nearbyNode:tuple.inRange(2)){
				if(nearbyNode.getTile(world) instanceof AuraTile && !tuple.equals(nearbyNode) && tuple.getDirectionTo(nearbyNode) != direction){
					AuraTile auraTile = (AuraTile) nearbyNode.getTile(world);
					for(CoordTuple targetNode: auraTile.connected){
						if(nearbyNode.getDirectionTo(targetNode) == direction){
							if(auraTile.burstMap == null) {

								AuraQuantityList auraToSend = auraTile.storage.percent(Math.max(1F,(float)quantity.getNum() / (float) auraTile.storage.getTotalAura()));
								auraToSend.set(this, 0);
								auraTile.transferAura(targetNode, auraToSend, false);
							}else{
								AuraQuantityList auraToSend = (AuraQuantityList) auraTile.storage.clone();

								//Simulate the node being discharged before the burst is calculated
								for(AuraQuantityList quantityToSubtract:auraTile.burstMap.values()){
									auraToSend.subtract(quantityToSubtract);
								}
								auraToSend.percent(Math.max(1F, (float)quantity.getNum() / (float) auraToSend.getTotalAura()));
								auraToSend.set(this, 0);
								auraTile.burstMap.put(targetNode, auraToSend);
							}

						}
					}

				}
			}
		}
	},
	YELLOW_AURA("Yellow", 1, 1, .1){
		@Override
		public double getAscentBoost(World world, CoordTuple tuple) {
			return 10D;
		}

		@Override
		public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
			if(world.getTotalWorldTime() % 1200 == 5) {
				AuraTile tile = (AuraTile) tuple.getTile(world);
				tile.storage.set(this, (int) (.9D * (double) tile.storage.get(this)));
			}
		}
	},
	BLUE_AURA("Blue", .1, .1, 1){
		@Override
		public double getAscentBoost(World world, CoordTuple tuple) {
			return world.isRaining() ? 50 : .5;
		}
	},
	VIOLET_AURA("Violet", 1, .1, 1){

		@Override
		public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity) {
			if(world.getTotalWorldTime() % 400 == 5) {
				AuraTile tile = (AuraTile) tuple.getTile(world);

				//Achieve growth along logarithmic curve
				double t = Math.log(tile.storage.get(this));
				t += .1;
				int newAura = (int) Math.pow(Math.E, t);
				tile.storage.set(this, newAura);
			}
		}
	};
	public String name;
	public double r;
	public double g;
	public double b;

	EnumAura(String name, double r, double g, double b){
		this.name = name;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void updateTick(World world, CoordTuple tuple, AuraQuantity quantity){}

	public double getRelativeMass(World world, CoordTuple tuple){
		return 1D;
	}

	public double getAscentBoost(World world, CoordTuple tuple){
		return 1D;
	}

	public void onTransfer(World world, CoordTuple tuple, AuraQuantity quantity, ForgeDirection direction){}
}
