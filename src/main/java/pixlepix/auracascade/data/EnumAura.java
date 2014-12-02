package pixlepix.auracascade.data;

import net.minecraft.world.World;

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

	public double getRelativeMass(World world, CoordTuple tuple){
		return 1D;
	}
}
