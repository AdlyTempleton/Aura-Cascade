package pixlepix.auracascade.data;

public class AuraQuantity {

    private EnumAura type;
    private int num;

    public AuraQuantity(EnumAura type, int num) {
        this.type = type;
        this.num = num;
    }

    public EnumAura getType() {
        return type;
    }

    public void setType(EnumAura type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
