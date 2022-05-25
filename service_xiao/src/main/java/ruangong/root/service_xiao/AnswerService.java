package ruangong.root.service_xiao;

import org.springframework.stereotype.Service;
import ruangong.root.bean.Answer;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pangx
 */
@Service
public interface AnswerService {

    /**
     * 通过answer的ID寻找answer
     *
     * @param id 找寻answer的id
     * @return data:Answer对象的json字符串
     */
    Result selectAnswerByAnswerId(int id);

    /**
     * 通过answer和user插入answer
     *
     * @param answer 准备插入的answer
     * @param user   插入answer的用户
     * @return data: 1 代表成功
     */
    Result insertAnswer(Answer answer, User user);

    /**
     * 通过sheetId查询所有该sheet的answer并以List的形式返回
     *
     * @param sheetId 查询answer的sheet的id
     * @return 该sheet的answers组成的列表
     */
    Result getAnswersBySheetId(Integer sheetId);

    /**
     * 通过用户id查询所有回答过的answer并以分页形式返回
     *
     * @param uid       user's id
     * @param pageIndex 要查询第几页
     * @param size      一页多少条数据
     * @return 以分页形式返回该用户的所有answer
     */
    Result getAnswerInPagesByUserId(Integer uid, Integer pageIndex, Integer size);

    /**
     * 通过ids传入查询所需信息，通过columns传入查询的列名，获得分页形式的answers数据
     *
     * @param ids       需要查询的id信息
     * @param pageIndex 查询第几页
     * @param size      一页多少数据
     * @param columns   查询的列名
     * @return 分页形式的answer数据
     */
    Result getCheckingAnswers(Integer[] ids, Integer pageIndex, Integer size, String[] columns);

    /**
     * 检查该answer是否超过回答时间
     *
     * @param answer 检查的answer
     * @return ture for valid, false for invalid
     */
    boolean checkAnswerTime(Answer answer);

    /**
     * 关闭该answer的提交权限
     *
     * @param answer 关闭的answer
     */
    void closeAnswer(Answer answer);

    /**
     * 存储临时answer数据，不关闭answer提交权限
     *
     * @param answer 临时储存的answer对象
     * @param user   进行存储的user对象
     * @return data: 1 for success
     */
    Result saveTempAnswer(Answer answer, User user);

    /**
     * 检查answer是否有提交权限
     *
     * @param answer 检查的answer
     * @return true for valid, false for invalid
     */
    boolean checkAnswerStatus(Answer answer);

    /**
     * 检查用户是否登录（考虑将该接口移植到UserService中）
     *
     * @param httpServletRequest httpServletRequest
     */
    void checkUserStatus(HttpServletRequest httpServletRequest);

    /**
     * 通过用户id以分页形式查询answers
     *
     * @param id      用户id
     * @param pageNum 查询第几页
     * @param size    一页多少条数据
     * @return IPage<Answer>.getRecords()的Json字符串形式
     */
    Result getAnswersByUserId(Integer id, Integer pageNum, Integer size);

    /**
     * 检查用户是否满足企业级sheet要求；若sheet非企业级，返回true；若为企业级，只有user与sheet的企业信息匹配才返回true
     *
     * @param user    检查的用户
     * @param sheetId 欲填写的sheetId
     * @return 若sheet非企业级，返回true；若为企业级，只有user与sheet的企业信息匹配才返回true
     */
    boolean checkUserCompany(User user, Integer sheetId);
}
