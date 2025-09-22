package fsm.oned.comm;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInfoVO {
    private String userId;  //ID
    private String userNm;  //이름
    private String passWd;  //비밀번호
    private String userIp;  //사용자IP

    private String bizRegNo;  //사업자번호 (수입수산물이력 시스템)
    private String compNmKor;  //업체명 (수입수산물이력 시스템)
    private boolean intgrMngr;  //통합관리자 여부
    private String sceSysCd; // 시스템 구분

    @Builder
    public UserInfoVO(String userId, String userNm, String passWd,String userIp,String bizRegNo,String compNmKor, boolean intgrMngr, String sceSysCd) {
        this.userId = userId;
        this.userNm = userNm;
        this.passWd = passWd;
        this.userIp = userIp;
        this.bizRegNo = bizRegNo;
        this.compNmKor = compNmKor;
        this.intgrMngr = intgrMngr;
        this.sceSysCd = sceSysCd;
    }

    /**
     * 사용자 정보 여부
     * @return
     */
    public boolean isUserInfo(){
        if(userId == null){
            return false;
        }else{
            return true;
        }
    }
}
