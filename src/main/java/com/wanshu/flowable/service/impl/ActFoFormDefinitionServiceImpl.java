package com.wanshu.flowable.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.flowable.mapper.ActFoFormDefinitionMapper;
import com.wanshu.flowable.domain.ActFoFormDefinition;
import com.wanshu.flowable.service.ActFoFormDefinitionService;
@Service
public class ActFoFormDefinitionServiceImpl extends ServiceImpl<ActFoFormDefinitionMapper, ActFoFormDefinition> implements ActFoFormDefinitionService{

}
