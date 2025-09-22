package fsm.oned.comm.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("commonAbstractDAO")
public abstract class CommonAbstractDAO  extends EgovAbstractDAO {

 @Override
@Resource(name="sqlMapClient")
 public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
  super.setSuperSqlMapClient(sqlMapClient);
    }
}
