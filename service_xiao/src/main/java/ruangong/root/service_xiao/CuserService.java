package ruangong.root.service_xiao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Cuser;

/**
 * @author pangx
 */
@Service
@Repository
public interface CuserService {

    /**
     * 通过用户的type信息获取用户的企业信息
     *
     * @param id typeId
     * @return 企业级user的相关信息
     */
    public Cuser getCuserByTypeId(int id);


}
