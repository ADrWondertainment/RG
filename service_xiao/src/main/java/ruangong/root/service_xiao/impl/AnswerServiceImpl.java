package ruangong.root.service_xiao.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.dao.UserMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.CuserService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
    public Result collectAnswerAndUpdate(JsonBeanSurvey jsonBeanSurvey) {

        int sheetId = jsonBeanSurvey.getSheetId();

        Result result1 = sheetService.getSheetById(sheetId);

        Sheet sheet1 = JSONUtil.toBean((String) result1.getData(), Sheet.class);

        JsonBeanTemplate jsonBeanTemplate = JSONUtil.toBean(sheet1.getLocation(), JsonBeanTemplate.class);

        int length = jsonBeanTemplate.getContentLength();

        List<JsonBeanTemplateContentsContent> content = jsonBeanTemplate.getContent();

        List<JsonBeanSurveysAnswers> answers = jsonBeanSurvey.getAnswers();

//        for (int i = 0; i < length; i++) {
//
//            JsonBeanTemplateContentsContent tempContent = content.get(i);
//            JsonBeanSurveysAnswers tempAnswer = answers.get(i);
//
//            String index = tempAnswer.getValue();
//            Map<String, Integer> value = tempContent.getValue();
//            Integer count = value.get(index) + 1;
//            value.put(index, count);
//
//        }

        sheet1.setLocation(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));

        result = sheetService.updateSheet(sheet1);


        return result;
    }

    @Override
    public Result insertAnswer(Answer answer, User user) {


        if (!checkAnswerTime(answer)) {
            throw new FrontException(ErrorCode.ILLEGAL_ANSWER_TIME, "该问卷回答时间已结束");
        }

        if (!checkAnswerStatus(answer)) {
            throw new FrontException(ErrorCode.ANSWER_ALREADY_DONE, "已提交该问卷的答案");
        }


        boolean insert = saveOrUpdate(answer);
        Integer id = answer.getId();
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

        boolean b = closeAnswer(answer);


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
        query.eq("sid", sheetId);
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
    public boolean checkAnswerTime(Answer answer) {

        Result sheetById = sheetService.getSheetById(answer.getSid());
        Sheet beanFromData = ResultUtil.getBeanFromData(sheetById, Sheet.class);
        Date date = new Date();
        return beanFromData.getEnd().getTime() > date.getTime();


    }

    @Override
    public boolean closeAnswer(Answer answer) {

        UpdateWrapper<Answer> update = Wrappers.update();
        update.eq("id", answer.getId()).set("done", 1);
        int update1 = answerMapper.update(null, update);
        return update1 == 1;
    }

    @Override
    public Result saveTempAnswer(Answer answer) {
        if (!checkAnswerTime(answer)) {
            throw new FrontException(ErrorCode.ILLEGAL_ANSWER_TIME, "该问卷回答时间已结束");
        }

        checkAnswerStatus(answer);

        boolean insert = saveOrUpdate(answer);
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
        if (answer.getDone() != null)
            return answer.getDone() == 1;
        return true;
    }

    @Override
    public boolean checkUserStatus(HttpServletRequest httpServletRequest) {
        Object email = httpServletRequest.getSession().getAttribute("email");
        if (email == null) {
            throw new FrontException(ErrorCode.USER_ILLEGAL_ACCESS, "必须登录以填写问卷");
        }

        return true;
    }

    @Override
    public Result getAnswersByUserID(Integer id, Integer pageNum, Integer size) {

        IPage<Answer> page = new Page(pageNum, size);

        QueryWrapper<Answer> query = Wrappers.query();
        query.eq("uid", id);
        IPage<Answer> answerIPage = answerMapper.selectPage(page, query);
        if (answerIPage == null) {
            throw new BackException(ErrorCode.TEMPLATE_SELECT_FAILURE, "分页数据查询失败");
        }

        List<Answer> records = answerIPage.getRecords();
        JSONArray jsonArray = JSONUtil.parseArray(records);

        System.out.println(JSONUtil.toJsonPrettyStr(jsonArray));

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(jsonArray)
        );


        return result;
    }

    @Override
    public boolean checkUserCompany(User user, Integer sheetID) {
        Result sheetById = sheetService.getSheetById(sheetID);
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
            if (user.getType() == null)
                return false;
            Cuser cuserByTypeID = cuserService.getCuserByTypeID(user.getType());
            return cuserByTypeID.getCid() == cid;
        } else {
            if (user.getType() == null)
                return false;
            Cuser cuserByTypeID = cuserService.getCuserByTypeID(user.getType());
            return cuserByTypeID.getCid() == cid && cuserByTypeID.getDid() == did;
        }

    }

    @Override
    public Result selectAnswerByAnswerID(int id) {
        Answer answer = answerMapper.selectById(id);
        if (answer == null) {
            throw new BackException(ErrorCode.ANSWER_SELECT_FAILURE, "未找到对应id的答案");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "成功找到答案",
                JSONUtil.toJsonStr(answer)
        );

        return result;
    }
}
