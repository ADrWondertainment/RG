package ruangong.root.service_xiao.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.dao.UserMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.CuserService;
import ruangong.root.service_xiao.PageUtil;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author pangx
 */
@Service
@Transactional
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    @Resource
    private Result result;

    @Resource
    private UserMapper userMapper;

    @Resource
    private SheetService sheetService;

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private CuserService cuserService;


    @Override
    public Result insertAnswer(Answer answer, User user) {


        if (checkAnswerTime(answer)) {
            throw new FrontException(ErrorCode.ILLEGAL_ANSWER_TIME, "该问卷回答时间已结束");
        }

        if (!checkAnswerStatus(answer)) {
            throw new FrontException(ErrorCode.ANSWER_ALREADY_DONE, "已提交该问卷的答案");
        }


        boolean insert;
        int insert1 = 0;
        int insert2 = 0;
        if (0 == answerMapper.selectCount(new QueryWrapper<Answer>().eq("uid", user.getId()).eq("sid", answer.getSid()))) {
            insert2 = answerMapper.insert(answer);
        } else {
            insert1 = answerMapper.update(answer, new UpdateWrapper<Answer>().eq("uid", user.getId()).eq("sid", answer.getSid()));
        }

        insert = (insert1 + insert2 != 0);
        Answer answer1 = answerMapper.selectOne(new QueryWrapper<Answer>().eq("sid", answer.getSid()).eq("uid", user.getId()));
        Integer id = answer1.getId();
        String submittedAnswers = user.getSheets();
        if (submittedAnswers != null) {
            List<String> strings = JSONUtil.parseArray(submittedAnswers).toList(String.class);
            strings.add(Integer.toString(id));
            JSONArray objects = JSONUtil.parseArray(strings);
            String newString = JSONUtil.toJsonStr(objects);
            user.setSheets(newString);
        } else {
            JSONArray array = JSONUtil.createArray();
            array.add(Integer.toString(id));
            user.setSheets(JSONUtil.toJsonStr(array));
        }

        userMapper.updateById(user);


        if (!insert) {
            throw new BackException(ErrorCode.ANSWER_INSERT_FAILURE, "回答插入失败");
        }

        closeAnswer(answer1);


        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "答案提交成功",
                1
        );

        return result;
    }

    @Override
    public Result getAnswersBySheetId(Integer sheetId) {

        QueryWrapper<Answer> query = Wrappers.query();
        query.eq("sid", sheetId).eq("done", 1);
        List<Answer> answers = answerMapper.selectList(query);

        if (answers.size() == 0) {
            throw new BackException(ErrorCode.ANSWER_SELECT_FAILURE, "回答分页查找失败或者无人回答");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "成功获取调查信息",
                JSONUtil.toJsonStr(answers)
        );

        return result;
    }

    @Override
    public Result getAnswerInPagesByUserId(Integer uid, Integer pageIndex, Integer sizePerPage) {

        JSONArray recordsById = PageUtil.getPageRecordsById(uid, pageIndex, sizePerPage,
                "uid", Answer.class, answerMapper);

        for (Object object : recordsById) {
            JSONObject temp = (JSONObject) object;
            Object data = temp.get("data");
            JSONArray objects = JSONUtil.parseArray(data);
            temp.set("data", objects);
            Integer sid = temp.get("sid", Integer.class);
            Sheet sheetFromData = ResultUtil.getBeanFromData(sheetService.getSheetById(sid), Sheet.class);
            temp.putOnce("name", sheetFromData.getName());
        }


        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "成功获取调查信息",
                recordsById
        );

        return result;
    }

    @Override
    public Result getCheckingAnswers(Integer[] ids, Integer pageIndex, Integer size, String[] columns) {

        JSONArray pageRecordsById = PageUtil.getPageRecordsById(ids, pageIndex, size, columns, Answer.class, answerMapper);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonStr(pageRecordsById)
        );
        return result;
    }

    @Override
    public boolean checkAnswerTime(Answer answer) {

        Result sheetById = sheetService.getSheetById(answer.getSid());
        Sheet beanFromData = ResultUtil.getBeanFromData(sheetById, Sheet.class);
        Date date = new Date();
        return beanFromData.getEnd().getTime() <= date.getTime();


    }

    @Override
    public void closeAnswer(Answer answer) {

        UpdateWrapper<Answer> update = Wrappers.update();
        update.eq("id", answer.getId()).set("done", 1);
        int update1 = answerMapper.update(null, update);
    }

    @Override
    public Result saveTempAnswer(Answer answer, User user) {


        if (checkAnswerTime(answer)) {
            throw new FrontException(ErrorCode.ILLEGAL_ANSWER_TIME, "该问卷回答时间已结束");
        }

        if (!checkAnswerStatus(answer)) {
            throw new FrontException(ErrorCode.ANSWER_ALREADY_DONE, "已提交该问卷的答案");
        }

        boolean insert;
        int insert1 = 0;
        int insert2 = 0;
        if (0 == answerMapper.selectCount(new QueryWrapper<Answer>().eq("uid", user.getId()).eq("sid", answer.getSid()))) {
            insert2 = answerMapper.insert(answer);
        } else {
            insert1 = answerMapper.update(answer, new UpdateWrapper<Answer>().eq("uid", user.getId()).eq("sid", answer.getSid()));
        }


        insert = (insert1 + insert2 != 0);

        Answer answer1 = answerMapper.selectOne(new QueryWrapper<Answer>().eq("sid", answer.getSid()).eq("uid", user.getId()));

        Integer id = answer1.getId();
        String submittedAnswers = user.getSheets();
        if (submittedAnswers != null) {
            List<String> strings = JSONUtil.parseArray(submittedAnswers).toList(String.class);
            if (!strings.contains(Integer.toString(id))) {
                strings.add(Integer.toString(id));
                JSONArray objects = JSONUtil.parseArray(strings);
                String newString = JSONUtil.toJsonStr(objects);
                user.setSheets(newString);
            }
        } else {
            JSONArray array = JSONUtil.createArray();
            array.add(Integer.toString(id));
            user.setSheets(JSONUtil.toJsonStr(array));
        }

        userMapper.updateById(user);


        if (!insert) {
            throw new BackException(ErrorCode.ANSWER_INSERT_FAILURE, "回答插入失败");
        }


        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "答案提交成功",
                1
        );

        return result;


    }

    @Override
    public boolean checkAnswerStatus(Answer answer) {
        if (answer.getDone() != null) {
            return answer.getDone() != 1;
        }
        return true;
    }

    @Override
    public void checkUserStatus(HttpServletRequest httpServletRequest) {
        Object email = httpServletRequest.getSession().getAttribute("email");
        if (email == null) {
            throw new FrontException(ErrorCode.USER_ILLEGAL_ACCESS, "必须登录以填写问卷");
        }

    }

    @Override
    public Result getAnswersByUserId(Integer id, Integer pageNum, Integer size) {

        JSONArray recordsById = PageUtil.getPageRecordsById(id, pageNum, size, "uid", Answer.class, answerMapper);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(recordsById)
        );


        return result;
    }

    @Override
    public boolean checkUserCompany(User user, Integer sheetId) {
        Result sheetById = sheetService.getSheetById(sheetId);
        Sheet sheetFromData = ResultUtil.getBeanFromData(sheetById, Sheet.class);
        Integer cid = sheetFromData.getCid();
        Integer did = sheetFromData.getDid();
        boolean cCheck, dCheck;
        cCheck = cid != null && cid != 0;
        dCheck = did != null && did != 0;

        if (!cCheck && !dCheck) {
            return true;
        } else if (!cCheck) {
            throw new BackException(ErrorCode.SHEET_STRUCTURE_CONTAMINATED, "发布sheet结构异常");
        } else if (!dCheck) {
            if (user.getType() == null) {
                return false;
            }
            Cuser cuserByTypeID = cuserService.getCuserByTypeId(user.getType());
            return cuserByTypeID.getCid().equals(cid);
        } else {
            if (user.getType() == null) {
                return false;
            }
            Cuser cuserByTypeID = cuserService.getCuserByTypeId(user.getType());
            return cuserByTypeID.getCid().equals(cid) && cuserByTypeID.getDid().equals(did);
        }

    }

    @Override
    public Result selectAnswerByAnswerId(int id) {
        Answer answer = answerMapper.selectById(id);
        if (answer == null) {
            throw new BackException(ErrorCode.ANSWER_SELECT_FAILURE, "未找到对应id的答案");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "成功找到答案",
                answer
        );

        return result;
    }
}
