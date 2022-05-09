package ruangong.root.service_xiao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.service_xiao.TemplateService;


@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;


}
