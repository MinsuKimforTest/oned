package fsm.oned.organicCert.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrganicCertVO {

    private final String CERT_ID;
    private final String USER_GUBUN;
    private final String CERT_GUBUN;
    private final String CERT_NO;
}
