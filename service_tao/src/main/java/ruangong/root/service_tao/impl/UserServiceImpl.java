package ruangong.root.service_tao.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.*;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_tao.UserService;
import ruangong.root.utils.ResultUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_NAME_UNFINDED,
                    "该用户不存在",
                    null
            );
            return result;
        }
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
            throw new FrontException(ErrorCode.USER_PASSWORD_UNMATCH, "密码错误，请重新输入");
        }

        user_result.setPass(newpass);
        int update = userMapper.updateById(user_result);

        if (update != 1) {
            throw new BackException(ErrorCode.USER_CHANGEPASS_FAILURE, "修改密码失败，请稍后再试");
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
    public Result changeName(String email, String name) {
        UpdateWrapper<User> wrap = new UpdateWrapper<>();
        wrap.eq("email", email).set("email", name);
        userMapper.update(null,wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_CHANGENAME_SUCCESS,
                "修改名字成功",
                1
        );
        return result;
    }

    @Override
    public Result SelectByName(String name) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Company company_result = companyMapper.selectOne(wrapper);
        if (company_result == null) {
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
        if (company_result == null) {
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
        return result;
    }

    @Override
    public Result CompanyRegister(Company company) {
        String name = company.getName();

        Result name_result = SelectByName(name);

        if (name_result.getData() != null) {
            throw new FrontException(ErrorCode.COMPANY_NAME_DUPLICATED, "公司名重复，请重新输入");

        }

        String invite = IdUtil.simpleUUID();

        company.setInvite(invite);

        int insert = companyMapper.insert(company);
        if (insert != 1) {
            throw new BackException(ErrorCode.COMPANY_REGISTER_FAILURE, "注册失败，请重试");

        }

        root_user.setEmail(name + "_root");
        root_user.setPass(invite);
        userMapper.insert(root_user);
        root_cuser.setUid(root_user.getId());
        root_cuser.setCid(company.getId());
        root_cuser.setLevel(0);
        root_cuser.setDid(0);
        root_cuser.setRid(0);
        cuserMapper.insert(root_cuser);
        UpdateWrapper<User> wp = new UpdateWrapper<>();
        wp.set("type", root_cuser.getId()).eq("id", root_user.getId());
        userMapper.update(null, wp);
        ResultUtil.quickSet(
                result,
                ErrorCode.COMPANY_REGISTER_SUCCESS,
                "注册成功，用户输入邀请码即可加入企业",
                company.getInvite()
        );
        return result;


    }

    @Override
    public Result JoinCompany(String invite, Integer id) {
        Result invite_result = SelectByInvite(invite);
        if (invite_result.getData() == null) {
            return invite_result;
        }
        Company company = (Company) invite_result.getData();
        Integer cid = company.getId();
        //增加cuser表
        cuser.setCid(cid);
        cuser.setLevel(2);
        cuser.setUid(id);
        cuser.setDid(0);
        cuser.setRid(0);
        int insert = cuserMapper.insert(cuser);
        Integer id1 = cuserMapper.selectOne(new QueryWrapper<Cuser>().eq("uid", id)).getId();
        //修改user表
        UpdateWrapper<User> wrap = new UpdateWrapper<>();
        wrap.eq("id", id).set("type", id1);
        int update = userMapper.update(null, wrap);

        if (update == 0 || insert == 0) {

            throw new BackException(ErrorCode.JOIN_COMPANY_FAILURE, "加入公司失败");

        }
        ResultUtil.quickSet(
                result,
                ErrorCode.JOIN_COMPANY_FAILURE,
                "加入公司成功",
                1
        );
        return result;
    }

    @Override
    public Result RemoveCompanyUser(Integer id) {
        UpdateWrapper<User> wp = new UpdateWrapper<>();
        wp.set("type", null).eq("id", id);
        userMapper.update(null, wp);
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.eq("uid", id);
        cuserMapper.delete(wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.REMOVE_COMPANY_USER,
                "删除成员成功",
                1
        );
        return result;
    }

    @Override
    public UserData GetAllData(Integer id) {
        UserData userData1 = new UserData();
        User user = userMapper.selectById(id);
        userData1.setId(id);
        userData1.setEmail(user.getEmail());

        Integer typeId = user.getType();
        if (typeId == null) {
            return userData1;
        }
        userData1.setTypeId(typeId);
        Integer cid = cuserMapper.selectById(typeId).getCid();
        userData1.setCid(cid);
        Company company = companyMapper.selectById(cid);
        userData1.setCompany(company.getName());

        Cuser cuser_result = (Cuser) SelectByUid(id).getData();
        Integer rid = cuser_result.getRid();
        Integer did = cuser_result.getDid();
        userData1.setRid(rid);
        userData1.setDid(did);
        userData1.setLevel(cuser_result.getLevel());
        if (did != 0) {
            userData1.setDepartment(deptMapper.selectById(did).getName());
        }
        if (rid != 0) {
            userData1.setRole(roleMapper.selectById(rid).getName());
        }else {
            userData1.setRole("无");
        }
        return userData1;
    }

    @Override
    public UserData GetAllData(String email) {
        UserData userData1 = new UserData();
        User user = getUserByEmail(email);
        Integer id = user.getId();
        userData1.setId(user.getId());
        userData1.setEmail(user.getEmail());

        Integer typeId = user.getType();
        if (typeId == null) {
            return userData1;
        }
        userData1.setTypeId(typeId);
        Integer cid = cuserMapper.selectById(typeId).getCid();
        userData1.setCid(cid);
        Company company = companyMapper.selectById(cid);
        userData1.setCompany(company.getName());

        Cuser cuser_result = (Cuser) SelectByUid(id).getData();
        Integer rid = cuser_result.getRid();
        Integer did = cuser_result.getDid();
        userData1.setRid(rid);
        userData1.setDid(did);
        userData1.setLevel(cuser_result.getLevel());
        int didValue, ridValue;

        didValue = did == null ? 0 : did;
        ridValue = rid == null ? 0 : rid;

        if (didValue != 0) {
            userData1.setDepartment(deptMapper.selectById(didValue).getName());
        }
        if (ridValue != 0) {
            userData1.setRole(roleMapper.selectById(ridValue).getName());
        }else{
            userData1.setRole("无");
        }
        return userData1;

    }

    @Override
    public User getUserByEmail(String email) {

        QueryWrapper<User> query = Wrappers.query();
        query.eq("email", email);

        return userMapper.selectOne(query);
    }

    @Override
    public Result GetCuserByLevel(Integer cid) {
        List<CompanyUser> companyUsers = new ArrayList<>();
        QueryWrapper<Cuser> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid).eq("level", 1);
        List<Cuser> cuserList = cuserMapper.selectList(wrapper);
        if(cuserList ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.FAIL,
                    "无权限为1的人员",
                    null
            );
            return result;
        }
        for (Cuser tmp_user :
                cuserList) {
            companyUsers.add(GetAllData(tmp_user.getUid()));
        }
        JSONObject obj = JSONUtil.createObj();
        for (Integer i = 0; i < companyUsers.size(); i++) {
            CompanyUser tmp_cuser = companyUsers.get(i);
            obj.putOnce(i.toString(), JSONUtil.parseObj(tmp_cuser));
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成员成功",
                obj
        );
        return result;
    }

    @Override
    public List<Integer> GetAllSon(Integer cid ,Integer did) {
        List<Integer> son_list = new ArrayList<>();
        son_list.add(did);
        List<Integer> while_list = new ArrayList<>();
        while_list.add(did);
        while(true){
            if(while_list.isEmpty()){
                break;
            }
            Integer tmp_did = while_list.get(0);
            while_list.remove(0);
            List<Dept> tmp_depts = GetAllDept(cid, tmp_did);
            if(tmp_depts.isEmpty()){
                continue;
            }
            for (Dept tmp_dept:
                 tmp_depts) {
                Integer son_did = tmp_dept.getId();
                son_list.add(son_did);
                while_list.add(son_did);
            }
        }
        return son_list;
    }

    @Override
    public Result SelectByUid(Integer uid) {
        QueryWrapper<Cuser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        Cuser cuser_result = cuserMapper.selectOne(wrapper);
        if (cuser_result == null) {
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
    public Result UpdateRole(Integer uid, Integer cid, String role) {
        role_user.setCid(cid);
        role_user.setName(role);
        roleMapper.insert(role_user);
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("rid", role_user.getId()).eq("uid", uid);
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
    public Result SetDept(Integer uid, String name, Integer cid) {
        if(Objects.equals(name, "空")){
            UpdateWrapper<Cuser> wp = new UpdateWrapper<>();
            wp.set("did", 0).eq("uid", uid);
            cuserMapper.update(null, wp);
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_SET_DEPARTMENT,
                    "部门调整成功",
                    1
            );
            return result;
        }
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name).eq("cid", cid);
        Dept company_result = deptMapper.selectOne(wrapper);
        if (company_result == null) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.UNFINDED_DEPARTMENT,
                    "不存在该部门",
                    0
            );
            return result;
        }
        Integer did = company_result.getId();
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("did", did).eq("uid", uid);
        cuserMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_SET_DEPARTMENT,
                "部门调整成功",
                1
        );
        return result;
    }

    @Override
    public Result CreateDept(Integer cid, String department, Integer fid) {
        dept_user.setCid(cid);
        dept_user.setName(department);
        dept_user.setFid(fid);
        deptMapper.insert(dept_user);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_CREATE_DEPARTMENT,
                "部门创建成功",
                1
        );
        return result;
    }

    @Override
    public Result UpdateDept(Integer did, String department) {
        UpdateWrapper<Dept> wrap = new UpdateWrapper<>();
        wrap.set("name", department).eq("id", did);
        deptMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_UPDATE_DEPARTMENT,
                "部门更新成功",
                1
        );
        return result;
    }

    @Override
    public Result RemoveDept(Integer did, Integer fid) {
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("did", fid).eq("did", did);
        cuserMapper.update(null, wrap);

        Dept updatemessage =new Dept();
        updatemessage.setFid(fid);
        UpdateWrapper<Dept> dwrap = new UpdateWrapper<>();
        dwrap.eq("fid", did);
        deptMapper.update(updatemessage, dwrap);
        deptMapper.deleteById(did);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_DELETE_DEPARTMENT,
                "部门删除成功",
                1
        );
        return result;
    }

    @Override
    public Result UpdateLevel(Integer uid, Integer level) {
        UpdateWrapper<Cuser> wrap = new UpdateWrapper<>();
        wrap.set("level", level).eq("uid", uid);
        cuserMapper.update(null, wrap);
        ResultUtil.quickSet(
                result,
                ErrorCode.USER_UPDATE_LEVEL,
                "权限更新成功",
                1
        );
        return result;
    }

    @Override
    public Result GetCompanyUserList(Integer cid, Integer did) {
        List<Dept> all_dept = GetAllDept(cid, did);
        if(all_dept ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.FAIL,
                    "无部门",
                    null
            );
        }
        JSONObject obj = JSONUtil.createObj();
        if (!all_dept.isEmpty()) {
            for (Integer i = 0; i < all_dept.size(); i++) {
                Dept tmp_dept = all_dept.get(i);
//                List<CompanyUser> tmp_companyUsers = GetComanyUserByDepartment(cid, tmp_dept.getId());
                List<Integer> tmp_sons = GetAllSon(cid, tmp_dept.getId());
                int tmp_count = 0;
                for (Integer tmp_did:
                     tmp_sons) {
                    int size = GetComanyUserByDepartment(cid, tmp_did).size();
                    tmp_count +=size;
                }
                obj.putOnce(i.toString(), JSONUtil.parseObj(tmp_dept).putOnce("num", tmp_count));
            }

        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询部门成功",
                obj
        );
        return result;
    }

    @Override
    public List<CompanyUser> GetComanyUserByDepartment(Integer cid, Integer did) {
        List<CompanyUser> companyUsers = new ArrayList<>();
        QueryWrapper<Cuser> wrapper = new QueryWrapper<>();
        wrapper.eq("did", did).eq("cid", cid);
        List<Cuser> cuserList = cuserMapper.selectList(wrapper);
        for (Cuser tmp_user :
                cuserList) {
            companyUsers.add(GetAllData(tmp_user.getUid()));
        }
        return companyUsers;
    }

    @Override
    public Result ShowCuser(Integer cid, Integer did) {
        List<CompanyUser> tmp_cusers = GetComanyUserByDepartment(cid, did);
        if(tmp_cusers ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.FAIL,
                    "无成员",
                    null
            );
            return result;
        }
        JSONObject obj = JSONUtil.createObj();
        for (Integer i = 0; i < tmp_cusers.size(); i++) {
            CompanyUser tmp_cuser = tmp_cusers.get(i);
            obj.putOnce(i.toString(), JSONUtil.parseObj(tmp_cuser));
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成员成功",
                obj
        );
        return result;
    }

    @Override
    public List<Dept> GetAllDept(Integer cid, Integer did) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid).eq("fid", did);
        return deptMapper.selectList(wrapper);
    }
}
