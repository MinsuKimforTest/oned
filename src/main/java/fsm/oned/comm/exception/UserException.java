package fsm.oned.comm.exception;

import fsm.oned.comm.PropMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserException extends RuntimeException{

 /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger Logger = LoggerFactory.getLogger(UserException.class);

    public UserException(String errorCode, Object[] messageParam){
  super(PropMsg.getMessage(errorCode,messageParam));
   Logger.info(PropMsg.getMessage(errorCode,messageParam));
 }

 public UserException(String msg){
  super(msg);
       Logger.info(msg);
 }

 public UserException(Throwable throwable, String errorCode){
  this(throwable, throwable.getMessage(), errorCode);
 }
 public UserException(Throwable throwable, String msg, String errorCode){
  super(msg, throwable);
 }

}