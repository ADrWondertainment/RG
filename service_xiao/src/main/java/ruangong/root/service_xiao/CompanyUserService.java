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

    public List<Cuser> getApproverByCompanyId(Integer id);

    public List<CuserView> getCuserView();

}
