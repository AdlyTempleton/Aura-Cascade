package pixlepix.auracascade.registry;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/9/14.
 */
public interface ITTinkererItem extends ITTinkererRegisterable {

    ArrayList<Object> getSpecialParameters();

    String getItemName();

    boolean shouldRegister();

    boolean shouldDisplayInTab();

}
