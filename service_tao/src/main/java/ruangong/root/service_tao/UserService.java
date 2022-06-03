package ruangong.root.service_tao;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.*;

import java.util.List;
import java.util.Map;

/**
 * @author tao
 */
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

    public Result GetUserByEmail(String email);

    public Result changePassword(User user,String newpass);

    public Result SelectByName(String name);

    public Result SelectByInvite(String invite);

    public Result CompanyRegister(Company company);

    public Result JoinCompany(String invite,Integer id);

    public Result RemoveCompanyUser(Integer id);

    public UserData GetAllData(Integer id);

    public Result SelectByUid(Integer uid);

    public Result UpdateRole(Integer uid,Integer cid,String role);

    public Result CreateDept(Integer cid,String department,Integer fid);

    public Result UpdateDept(Integer did,String department);

    public Result RemoveDept(Integer did,Integer fid);

    public Result UpdateLevel(Integer uid,Integer level);

    public Map<Dept,Integer> GetCompanyUserList(Integer cid,Integer did);

    public List<CompanyUser> GetComanyUserByDepartment(Integer cid,Integer did);

    public List<Dept> GetAllDept(Integer cid,Integer did);

    //----------------------------------------------------------------------------
    public UserData GetAllData(String email);

    public User getUserByEmail(String email);



}
