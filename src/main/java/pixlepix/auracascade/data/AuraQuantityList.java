package pixlepix.auracascade.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class AuraQuantityList {

	public ArrayList<AuraQuantity> quantityList;

	public AuraQuantityList(ArrayList<AuraQuantity> quantities) {
		this.quantityList = quantities;
	}
	public AuraQuantityList() {
		this.quantityList = new ArrayList<AuraQuantity>();
		for(EnumAura type:EnumAura.values()) {
			quantityList.add(new AuraQuantity(type, 0));
		}
	}

	public void subtract(AuraQuantityList other){
		for(AuraQuantity quantity : this.quantityList){
			for(AuraQuantity quantityOther : other.quantityList){
				if(quantity.getType() == quantityOther.getType()){
					quantity.setNum(quantity.getNum()-quantityOther.getNum());
				}
			}
		}
	}
	public void add(AuraQuantityList other){
		for(AuraQuantity quantity : this.quantityList){
			for(AuraQuantity quantityOther : other.quantityList){
				if(quantity.getType() == quantityOther.getType()){
					quantity.setNum(quantity.getNum()+quantityOther.getNum());
				}
			}
		}
	}
	public void add(AuraQuantity other){
		for(AuraQuantity quantity : this.quantityList){
			if(quantity.getType() == other.getType()){
				quantity.setNum(quantity.getNum()+other.getNum());
			}

		}
	}
	public int get(EnumAura aura){
		for(AuraQuantity quantity : this.quantityList){
			if(quantity.getType() == aura){
				return quantity.getNum();
			}

		}
		return 0;
	}

	public void set(EnumAura type, int num){
		for(AuraQuantity quantity : this.quantityList){
			if(quantity.getType() == type){
				quantity.setNum(num);
			}

		}
	}


	public void subtract(EnumAura type, int num){
		set(type, get(type) - num);
	}
	public boolean empty(){
		for(AuraQuantity quantity:quantityList){
			if(quantity.getNum() > 0){
				return false;
			}
		}
		return true;
	}
	public AuraQuantityList percent(float percentage){
		try {
			AuraQuantityList quantityList = (AuraQuantityList) this.clone();

			for(AuraQuantity quantity : this.quantityList){
				quantityList.quantityList.add(new AuraQuantity(quantity.getType(), (int) (quantity.getNum()*percentage)));
			}

			return quantityList;

		} catch (CloneNotSupportedException e) {

			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new AuraQuantityList((ArrayList<AuraQuantity>) quantityList.clone());
	}

	public static final String NBT_AURA_TYPE = "auraType";

	public static final String NBT_AURA_NUM = "auraNum";

	public void readFromNBT(NBTTagList nbt){
		this.quantityList = new ArrayList<AuraQuantity>();
		for(int i = 0; i < nbt.tagCount(); i++){
			NBTTagCompound  auraCompound= nbt.getCompoundTagAt(i);
			EnumAura type = EnumAura.values()[auraCompound.getByte(NBT_AURA_TYPE)];
			int num = auraCompound.getInteger(NBT_AURA_NUM);
			quantityList.add(new AuraQuantity(type, num));
		}
	}

	public void writeToNBT(NBTTagList nbt){
		for(AuraQuantity quantity:this.quantityList){
			NBTTagCompound compound = new NBTTagCompound();
			compound.setByte(NBT_AURA_TYPE, (byte) quantity.getType().ordinal());
			compound.setInteger(NBT_AURA_NUM, quantity.getNum());
			nbt.appendTag(compound);
		}
	}


}
