package ruangong.root.service_tao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.*;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.utils.ResultUtil;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private CuserMapper cuserMapper;

    @Autowired
    private Result result;

    @Autowired
    private UserData userData;

    @Autowired
    private Cuser cuser;

    @Autowired
    private Cuser root_cuser;

    @Autowired
    private User root_user;

    @Autowired
    private List<CompanyUser> list_user;

    @Autowired
    private Role role_user;

    @Autowired
    private Dept dept_user;

    @Override
    public Result register(User user) {

        String username = user.getEmail();

        Result temp_result = GetUserByEmail(username);
        User user_result = (User) temp_result.getData();

        //重名出错
        if (user_result != null) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_NAME_DUPLICATED,
                    "用户名重复，请重命名",
                    null
            );
            return result;
        }

        //注册功能
        int insert = userMapper.insert(user);

        if (insert != 1) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_INSERT_FAILURE,
                    "注册失败，请稍后再试",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_INSERT_SUCCESS,
                "注册成功",
                1
        );
        return result;
    }

    @Override
    public Result login(User user) {
        String username = user.getEmail();
        String password = user.getPass();

        Result temp_result = GetUserByEmail(username);
        User user_result = (User) temp_result.getData();

        if (user_result == null) {
            return temp_result;
        }
        String real_pass = user_result.getPass();
        if (!real_pass.equals(password)) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_PASSWORD_UNMATCH,
                    "密码错误，请重新输入",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_LOGIN_SUCCESS,
                "登录成功",
                1
        );
        return result;
    }

    @Override
    public Result GetUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user_result = userMapper.selectOne(wrapper);
        if (user_result == null) {
            throw new BackException(ErrorCode.USER_NAME_UNFINDED, "该用户不存在");
        }
//        if (user_result == null) {
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.USER_NAME_UNFINDED,
//                    "该用户不存在",
//                    null
//            );
//            return result;
//        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成功",
                user_result
        );
        return result;
    }

    @Override
    public Result changePassword(User user, String newpass) {
        String username = user.getEmail();
        String oldpass = user.getPass();

        Result temp_result = GetUserByEmail(username);
        User user_result = (User) temp_result.getData();

        if (user_result == null) {
            return temp_result;
        }

        if (!oldpass.equals(user_result.getPass())) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_PASSWORD_UNMATCH,
                    "密码错误，请重新输入",
                    null
            );
            return result;
        }

        user_result.setPass(newpass);
        int update = userMapper.updateById(user_result);

        if (update != 1) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_CHANGEPASS_FAILURE,
                    "修改密码失败，请稍后再试",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_CHANGEPASS_SUCCESS,
                "修改密码成功",
                1
        );
        return result;
    }

    @Override
    public Result SelectByName(String name) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Company company_result = companyMapper.selectOne(wrapper);
        if(company_result ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_NAME_UNFINDED,
                    "公司不存在",
                    null
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成功",
                company_result
        );
        return result;
    }

    @Override
    public Result SelectByInvite(String invite) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("invite", invite);
        Company company_result = companyMapper.selectOne(wrapper);
        if(company_result ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_INVITE_UNFINDED,
                    "邀请码不存在",
                    null
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成功",
                company_result
        );
        return result;    }

    @Override
    public Result CompanyRegister(Company company) {
        String name =company.getName();
        String invite =company.getInvite();

        Result name_result =SelectByName(name);
        Result invite_result =SelectByInvite(invite);

        if(name_result.getData()!=null && invite_result.getData()!=null) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_NAME_INVITE_DUPLICATED,
                    "公司名、邀请码均重复，请重新输入",
                    null
            );
            return result;
        }
        if(name_result.getData()!=null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_NAME_DUPLICATED,
                    "公司名重复，请重新输入",
                    null
            );
            return result;
        }
        if(invite_result.getData()!=null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_INVITE_DUPLICATED,
                    "邀请码重复，请重新输入",
                    null
            );
            return result;
        }
        int insert = companyMapper.insert(company);
        if(insert == 1){
            root_user.setEmail(name+"_root");
            root_user.setPass(invite);
            root_user.setType(company.getId());
            userMapper.insert(root_user);
            root_cuser.setUid(root_user.getId());
            root_cuser.setCid(company.getId());
            root_cuser.setLevel(0);
            cuserMapper.insert(root_cuser);
            ResultUtil.quickSet(
                    result,
                    ErrorCode.COMPANY_REGISTER_SUCCESS,
                    "注册成功，用户输入邀请码即可加入企业",
                    1
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.COMPANY_REGISTER_FAILURE,
                "注册失败，请重试",
                1
        );
        return result;
    }

    @Override
    public Result JoinCompany(String invite,Integer id) {
        Result invite_result =SelectByInvite(invite);
        if(invite_result.getData()==null){
            return result;
        }
        Company company =(Company) invite_result.getData();
        Integer cid = company.getId();
        //修改user表
        UpdateWrapper<User> wrap = new UpdateWrapper<>();
        wrap.set("type",cid).eq("id",id);
        int update = userMapper.update(null, wrap);
        //增加cuser表
        cuser.setCid(cid);
        cuser.setLevel(1);
        cuser.setUid(id);
        int insert = cuserMapper.insert(cuser);

        if(update!=0 && insert!=0){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.JOIN_COMPANY_SUCCESS,
                    "加入公司成功",
                    1
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.JOIN_COMPANY_FAILURE,
                "加入公司失败",
                null
        );
        return result;
    }

    @Override
    public UserData GetAllData(Integer id) {
        User user =userMapper.selectById(id);
        userData.setId(id);
        userData.setEmail(user.getEmail());

        Integer company_id =user.getType();
        if(company_id==null){
            return userData;
        }
        userData.setCid(company_id);
        Company company = companyMapper.selectById(company_id);
        userData.setCompany(company.getName());

        Cuser cuser_result = (Cuser) SelectByUid(id).getData();
        Integer rid =cuser_result.getRid();
        Integer did =cuser_result.getDid();
        userData.setRid(rid);
        userData.setDid(did);
        userData.setLevel(cuser_result.getLevel());
        userData.setDepartment(deptMapper.selectById(did).getName());
        userData.setRole(roleMapper.selectById(rid).getName());

        return userData;
    }

    @Override
    public Result SelectByUid(Integer uid) {
        QueryWrapper<Cuser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        Cuser cuser_result =cuserMapper.selectOne(wrapper);
        if(cuser_result ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_UNJOIN,
                    "该用户未加入公司",
                    null
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成功",
                cuser_result
        );
        return result;
    }

    @Override
    public List<CompanyUser> GetAllCompanyUser(Integer cid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("type", cid);
        List<User> users = userMapper.selectList(wrapper);
        for (User tmp_user:
             users) {
            list_user.add(GetAllData(tmp_user.getId()));
        }
        return list_user;
    }

    @Override
    public Result UpdateRole(Integer uid, Integer cid, String role) {
        role_user.setCid(cid);
        role_user.setName(role);
        roleMapper.insert(role_user);
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("rid",role_user.getId()).eq("uid",uid);
        cuserMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_UPDATE_ROLE,
                "角色更新成功",
                1
        );
        return result;
    }

    @Override
    public Result UpdateDept(Integer uid, Integer cid, String department) {
        dept_user.setCid(cid);
        dept_user.setName(department);
        deptMapper.insert(dept_user);
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("did",dept_user.getId()).eq("uid",uid);
        cuserMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_UPDATE_DEPARTMENT,
                "部门更新成功",
                1
        );
        return result;
    }

    @Override
    public Result UpdateLevel(Integer uid, Integer level) {
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("level",level).eq("uid",uid);
        cuserMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_UPDATE_LEVEL,
                "权限更新成功",
                1
        );
        return result;
    }
}
