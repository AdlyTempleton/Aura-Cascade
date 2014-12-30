package pixlepix.auracascade.main;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Created by pixlepix on 12/26/14.
 */
public class NEIAuraConfig implements IConfigureNEI{


    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new PylonRecipeHandler());
    }

    @Override
    public String getName() {
        return ConstantMod.modName;
    }

    @Override
    public String getVersion() {
        return ConstantMod.version;
    }
}
