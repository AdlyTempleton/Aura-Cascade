package pixlepix.auracascade.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import pixlepix.auracascade.AuraCascade;

import java.util.ArrayList;

public class AuraQuantityList implements Cloneable {

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
	public int getTotalAura(){
		int sum = 0;
		for(EnumAura aura:EnumAura.values()){
			sum += get(aura);
		}
		return sum;
	}

	public double getComposition(EnumAura aura){
		if(getTotalAura() != 0){
			return (double)get(aura) / (double)getTotalAura();
		}
		return 0;
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
		AuraQuantityList quantityList = (AuraQuantityList) this.clone();

		for(AuraQuantity quantity : this.quantityList){
			quantityList.quantityList.add(new AuraQuantity(quantity.getType(), (int) (quantity.getNum()*percentage)));
		}

		return quantityList;

	}
	public AuraQuantityList min(AuraQuantityList other){
		AuraQuantityList result = new AuraQuantityList();
		for(EnumAura aura:EnumAura.values()){
			result.set(aura, Math.min(get(aura), other.get(aura)));
		}
		return result;
	}
	@Override
	public AuraQuantityList clone() {
        AuraQuantityList clone;
        try {
			clone = (AuraQuantityList) super.clone();
		} catch (CloneNotSupportedException e) {
            // Should never be hit.
            AuraCascade.log.fatal("Failed to create clone of {}", getClass().getName());
            clone = new AuraQuantityList();
		}
        clone.quantityList = (ArrayList) quantityList.clone();
		return clone;
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


	public boolean greaterThan(AuraQuantityList list) {
		for(EnumAura aura: EnumAura.values()){
			if(get(aura) < list.get(aura)){
				return false;
			}
		}
		return true;
	}
}
