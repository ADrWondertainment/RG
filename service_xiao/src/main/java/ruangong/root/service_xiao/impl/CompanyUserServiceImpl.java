package ruangong.root.service_xiao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Cuser;
import ruangong.root.bean.CuserView;
import ruangong.root.dao.CuserMapper;
import ruangong.root.dao.CuserViewMapper;
import ruangong.root.service_xiao.CompanyUserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pangx
 */
@Service
@Transactional
public class CompanyUserServiceImpl implements CompanyUserService {

    @Resource
    private CuserMapper cuserMapper;

    @Resource
    private CuserViewMapper cuserViewMapper;

    @Override
    public List<Cuser> getApproverByCompanyId(Integer id) {
        return cuserMapper.selectList(new QueryWrapper<Cuser>().eq("id", id).eq("level", 2));
    }

    @Override
    public List<CuserView> getCuserView() {
        return cuserViewMapper.selectList(null);
    }
}
