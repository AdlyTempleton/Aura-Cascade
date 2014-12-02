package pixlepix.auracascade.data;

public enum EnumAura {
	WHITE_AURA("White", 1, 1, 1),

	GREEN_AURA("Green", .1, 1, .1);
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
}
