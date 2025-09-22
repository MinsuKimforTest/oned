package fsm.oned.comm;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;

public class MyBatisBaseMap extends ListOrderedMap {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7700790403928325865L;

    @Override
    public Object put(Object key, Object value) {
        return super.put(StringUtils.upperCase((String) key), value == null ? "" : value);
    }
}
