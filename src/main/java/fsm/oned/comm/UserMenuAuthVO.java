package fsm.oned.comm;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserMenuAuthVO {
    private String authRegYn = "N"; //등록
    private String authUpdYn = "N"; //수정
    private String authDelYn = "N"; //삭제
    private String authAdmYn = "N"; //관리자

    public UserMenuAuthVO(Map currentMenu){
        if(currentMenu != null) {
            authRegYn = currentMenu.get("AUTH_REG_YN").toString();
            authUpdYn = currentMenu.get("AUTH_UPD_YN").toString();
            authDelYn = currentMenu.get("AUTH_DEL_YN").toString();
            authAdmYn = currentMenu.get("AUTH_ADM_YN").toString();
        }
    }

}
