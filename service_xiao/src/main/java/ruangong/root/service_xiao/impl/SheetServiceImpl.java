package ruangong.root.service_xiao.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Sheet;
import ruangong.root.dao.SheetMapper;
import ruangong.root.service_xiao.SheetService;

@Service
@Transactional
public class SheetServiceImpl extends ServiceImpl<SheetMapper, Sheet> implements SheetService {




}
