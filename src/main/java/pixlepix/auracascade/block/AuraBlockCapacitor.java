package pixlepix.auracascade.block;

import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

import java.util.ArrayList;
import java.util.Collections;

// Extend so we can use blockstates without forcing them on all the other blocks >.>
public class AuraBlockCapacitor extends AuraBlock {

    public static final PropertyBool BURSTING = PropertyBool.create("bursting");

    public AuraBlockCapacitor() {
        super("capacitor");
        setDefaultState(blockState.getBaseState().withProperty(BURSTING, false));
    }

    @Override
    public BlockState createBlockState() {
        return new BlockState(this, BURSTING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BURSTING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BURSTING, meta == 1);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return Lists.<Object>newArrayList("capacitor");
    }
}
