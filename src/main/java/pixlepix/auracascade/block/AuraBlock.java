package pixlepix.auracascade.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.SideOnly;
import javafx.geometry.Side;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pixlepix.auracascade.block.tile.*;
import pixlepix.auracascade.data.EnumAura;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

public class AuraBlock extends Block implements ITTinkererBlock, ITileEntityProvider {

	public AuraBlock(String type){
		super(Material.glass);
		this.type = type;
	}

	public AuraBlock(){
		this("");
	}

	//"" is default
	//"pump" is AuraTilePump\
	//"black" is AuraTileBlack etc
	String type;

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.addChatComponentMessage(new ChatComponentText("Aura:"));

			if(world.getTileEntity(x, y, z) instanceof AuraTileCapacitor && player.isSneaking()){
				AuraTileCapacitor capacitor = (AuraTileCapacitor) world.getTileEntity(x, y, z);
				capacitor.storageValueIndex = (capacitor.storageValueIndex + 1) % capacitor.storageValues.length;
				player.addChatComponentMessage(new ChatComponentText("Max Storage: " + capacitor.storageValues[capacitor.storageValueIndex]));
				world.markBlockForUpdate(x, y, z);

			}else {
				for (EnumAura aura : EnumAura.values()) {
					if (((AuraTile) world.getTileEntity(x, y, z)).storage.get(aura) != 0) {
						player.addChatComponentMessage(new ChatComponentText(aura.name + " Aura: " + ((AuraTile) world.getTileEntity(x, y, z)).storage.get(aura)));
					}
				}
				if (world.getTileEntity(x, y, z) instanceof AuraTilePump) {

					player.addChatComponentMessage(new ChatComponentText("Power: " + ((AuraTilePump) world.getTileEntity(x, y, z)).pumpPower));
				}
			}
		}
		return false;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int meta) {
		if(world.getTileEntity(x, y, z) instanceof AuraTileCapacitor){
			AuraTileCapacitor capacitor = (AuraTileCapacitor) world.getTileEntity(x, y, z);
			return capacitor.aboutToBurst ? 15 : 0;
		}
		return 0;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		// TODO Auto-generated method stub
		ArrayList result = new ArrayList<Object>();
		result.add("pump");
		result.add("black");
		result.add("conserve");
		result.add("capacitor");
		return result;
	}

	public static String name = "auraNode";

	@Override
	public String getBlockName() {
		// TODO Auto-generated method stub
		return name+type;
	}

	@Override
	public boolean shouldRegister() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends TileEntity> getTileEntity() {

		if(type.equals("pump")){
			return AuraTilePump.class;
		}
		if(type.equals("black")) {
			return AuraTileBlack.class;
		}
		if(type.equals("conserve")){
			return AuraTileConserve.class;
		}
		if(type.equals("capacitor")){
			return AuraTileCapacitor.class;
		}
		return AuraTile.class;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		try {
			return getTileEntity().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
