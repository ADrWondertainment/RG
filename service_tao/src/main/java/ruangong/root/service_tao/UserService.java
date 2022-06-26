package ruangong.root.service_tao;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.*;

import java.util.List;
import java.util.Map;

/**
 * @author tao
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Service
public interface UserService extends IService<User> {
    /**
     * 普通用户的注册，不涉及企业
     * @param user 通过前端传入的数据构建新的User对象
     * @return 返回前后端通信协议Result
     */
    public Result register(User user);

    /**
     * 登录功能
     * @param user 通过前端传入的数据构建新的User对象
     * @return 返回前后端通信协议Result
     */
    public Result login(User user);

    /**
     * 通过eamil查询用户信息
     * @param email 用户名
     * @return data:user类型的数据
     */
    public Result GetUserByEmail(String email);

    /**
     * 改密码
     * @param user user类型的数据
     * @param newpass 新密码
     * @return data:1表示成功
     */
    public Result changePassword(User user,String newpass);

    /**
     * 改名
     * @param email 用户名
     * @param name 新用户名
     * @return data:1表示成功
     */
    public Result changeName(String email,String name);

    /**
     * 根据企业名返回企业信息
     * @param name 企业名
     * @return data:company类型的数据
     */
    public Result SelectByName(String name);

    /**
     * 根据邀请码返回企业信息
     * @param invite 企业邀请码
     * @return data:company类型的数据
     */
    public Result SelectByInvite(String invite);

    /**
     * 企业注册
     * @param company company类型的数据
     * @return data:1表示成功
     */
    public Result CompanyRegister(Company company);

    /**
     * 加入企业
     * @param invite 企业邀请码
     * @param id 用户id
     * @return data:1表示成功
     */
    public Result JoinCompany(String invite,Integer id);

    /**
     * 移除企业用户
     * @param id 用户id
     * @return data:1表示成功
     */
    public Result RemoveCompanyUser(Integer id);

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return UserData类型的数据
     */
    public UserData GetAllData(Integer id);

    /**
     * 根据uid获取cuser信息
     * @param uid 用户uid
     * @return data:cuser类型的数据
     */
    public Result SelectByUid(Integer uid);

    /**
     * 更新角色
     * @param uid 用户id
     * @param cid 企业id
     * @param role 角色名
     * @return data:1表示成功
     */
    public Result UpdateRole(Integer uid,Integer cid,String role);

    /**
     * 设置部门
     * @param uid 用户id
     * @param name 部门名
     * @param cid 企业名
     * @return data:1表示成功
     */
    public Result SetDept(Integer uid,String name,Integer cid);

    /**
     * 创建企业
     * @param cid 企业id
     * @param department 部门名
     * @param fid 父部门id
     * @return data:1表示成功
     */
    public Result CreateDept(Integer cid,String department,Integer fid);

    /**
     * 更新部门名
     * @param did 部门id
     * @param department 部门名
     * @return data:1表示成功
     */
    public Result UpdateDept(Integer did,String department);

    /**
     * 移除部门
     * @param did 部门id
     * @param fid 父部门id
     * @return data:1表示成功
     */
    public Result RemoveDept(Integer did,Integer fid);

    /**
     * 改变权限
     * @param uid 用户id
     * @param level 新权限
     * @return data:1表示成功
     */
    public Result UpdateLevel(Integer uid,Integer level);

    /**
     * 返回部门下子部门信息
     * @param cid 企业id
     * @param did 部门id
     * @return 包含了dept类型的部门信息和integer类型的部门人数的json字符串
     */
    public Result GetCompanyUserList(Integer cid,Integer did);

    /**
     * 根据部门返回部门下人员信息
     * @param cid 企业id
     * @param did 部门id
     * @return CompanyUser类型的list
     */
    public List<CompanyUser> GetComanyUserByDepartment(Integer cid,Integer did);

    /**
     * 显示部门用户信息
     * @param cid 企业id
     * @param did 部门id
     * @return data:CompanyUser类型的list
     */
    public Result ShowCuser(Integer cid,Integer did);

    /**
     * 获取所有部门
     * @param cid 企业id
     * @param did 部门id
     * @return Dept类型的list
     */
    public List<Dept> GetAllDept(Integer cid,Integer did);

    /**
     * 根据email获取单个用户的信息
     * @param email 用户名
     * @return data:UserData类型的数据
     */
    public UserData GetAllData(String email);

    /**
     * 根据用户名获取用户信息
     * @param email 用户名
     * @return data:User类型的数据
     */
    public User getUserByEmail(String email);

    /**
     * 根据level获取cuser信息
     * @param cid 企业id
     * @return data:cuser类型的数据
     */
    public Result GetCuserByLevel(Integer cid);

    /**
     * 返回所有子部门人数
     * @param cid 企业id
     * @param did 部门id
     * @return Integer类型的list
     */
    public List<Integer> GetAllSon(Integer cid ,Integer did);
}
