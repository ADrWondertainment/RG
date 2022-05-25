package ruangong.root.service_xiao.impl;

import org.springframework.stereotype.Service;
import ruangong.root.bean.Cuser;
import ruangong.root.dao.CuserMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.CuserService;

import javax.annotation.Resource;

@Service
public class CuserServiceImpl implements CuserService {

    @Resource
    private CuserMapper cuserMapper;

    @Override
    public Cuser getCuserByTypeId(int id) {

        Cuser cuser = cuserMapper.selectById(id);
        if (cuser == null)
            throw new BackException(ErrorCode.CUSER_ID_UNREGISTERED, "没有找到对应id的企业用户");
        return cuser;

    }
}
