package pixlepix.auracascade.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import pixlepix.auracascade.registry.ITTinkererBlock;
import pixlepix.auracascade.registry.ThaumicTinkererRecipe;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/5/15.
 */
public class BlockExplosionContainer extends Block implements ITTinkererBlock {

    String type;


    public BlockExplosionContainer() {
        super(Material.rock);
        //Same as obby
        setResistance(2000F);
        type = "Dirt";
    }

    public BlockExplosionContainer(String s) {
        this();
        type = s;
    }


    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public double getChanceToResist() {
        if (type.equals("Dirt")) {
            return .1;
        }
        if (type.equals("Wood")) {
            return .25;
        }
        if (type.equals("Glass")) {
            return .25;
        }
        if (type.equals("Cobblestone")) {
            return .4;
        }
        if (type.equals("Stone")) {
            return .25;
        }
        if (type.equals("Obsidian")) {
            return .9;
        }
        return 0;
    }

    public double getChanceToRepair() {
        if (type.equals("Dirt")) {
            return .25;
        }
        if (type.equals("Wood")) {
            return .9;
        }
        if (type.equals("Glass")) {
            return .75;
        }
        if (type.equals("Cobblestone")) {
            return .75;
        }
        if (type.equals("Stone")) {
            return .5;
        }
        if (type.equals("Obsidian")) {
            return .1;
        }
        return 0;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon("aura:fortifiedCobblestone");
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        // TODO Auto-generated method stub
        ArrayList result = new ArrayList<Object>();
        result.add("Wood");
        result.add("Glass");
        result.add("Cobblestone");
        result.add("Stone");
        result.add("Obsidian");
        return result;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return super.getRenderType();
    }

    @Override
    public String getBlockName() {
        return "fortified" + type;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public int getCreativeTabPriority() {
        return 23;
    }
}
