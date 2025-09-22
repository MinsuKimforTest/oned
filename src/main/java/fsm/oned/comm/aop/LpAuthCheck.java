package fsm.oned.comm.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LpAuthCheck {

    boolean isLoginChk() default true; /*로그인체크 여부*/
    boolean isAuthChk() default true; /*권한체크 여부 */
}