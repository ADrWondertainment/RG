package ruangong.root.service_xiao;

import org.springframework.stereotype.Service;
import ruangong.root.bean.Cuser;
import ruangong.root.bean.CuserView;

import java.util.List;

/**
 * @author pangx
 */
@Service
public interface CompanyUserService {

    /**
     * 通过公司id获得所有审批人员
     * @param id 公司id
     * @return 以列表的形式返回所有审批人员
     */
    public List<Cuser> getApproverByCompanyId(Integer id);

    /**
     * 获取所有公司用户的视图，包含了公司用户的所有关键信息
     * @return 以列表的形式返回所有公司用户的视图
     */
    public List<CuserView> getCuserView();

}
