package fsm.oned.comm;


import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;


public class PropMsg {

    private static MessageSourceAccessor msgAccessor = null;

    public void setMessageSourceAccessor(MessageSourceAccessor msgAccessor) {
        PropMsg.msgAccessor = msgAccessor;
    }

    public static String getMessage(String key) {
        return msgAccessor.getMessage(key, Locale.getDefault());
    }

    public static String getMessage(String key, Object[] objs) {
        return msgAccessor.getMessage(key, objs, Locale.getDefault());
    }


}

