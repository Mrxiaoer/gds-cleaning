package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.utils.TreeUtil;
import com.cloud.gds.cleaning.api.vo.DataFieldValueTree;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据池接口实现类
 *
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Service("dataFieldValueService")
public class DataFieldValueServiceImpl extends ServiceImpl<DataFieldValueMapper, DataFieldValue> implements
	DataFieldValueService {

    private final CalculateService calculateService;

    @Autowired
    public DataFieldValueServiceImpl(CalculateService calculateService) {
        this.calculateService = calculateService;
    }


    @Override
    public Boolean update(DataFieldValue dataFieldValue) {
        dataFieldValue.setModifiedTime(LocalDateTime.now());
        //		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
        return this.updateById(dataFieldValue);
    }

    @Override
    public Boolean deleteById(Long id) {
        DataFieldValue dataFieldValue = new DataFieldValue();
        dataFieldValue.setId(id);
        //		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
        dataFieldValue.setIsDeleted(DataCleanConstant.YES);
        dataFieldValue.setModifiedTime(LocalDateTime.now());
        return this.updateById(dataFieldValue);
    }

    @Override
    public Boolean deleteByIds(Set<Long> ids) {
        for (Long id : ids) {
            this.deleteById(id);
        }
        return true;
    }

    @Override
    public Boolean save(Long id, JSONObject params) {
        DataFieldValue dataFieldValue = new DataFieldValue();
        dataFieldValue.setFieldId(id);
        dataFieldValue.setFieldValue(params.toJSONString());
        //        dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
        dataFieldValue.setCreateTime(LocalDateTime.now());
        return this.insert(dataFieldValue);
    }

    @Override
    @Transactional
    public void saveAll(Long fieldId, List<Map<String, Object>> maps) {

        // 循环插入数据库相关信息
        for (Map<String, Object> map : maps) {
            DataFieldValue dataFieldValue = new DataFieldValue();
            // 组装
            dataFieldValue.setFieldId(fieldId);
            dataFieldValue.setFieldValue(map.toString());
            dataFieldValue.setCreateTime(LocalDateTime.now());
            dataFieldValue.setModifiedTime(LocalDateTime.now());
            // 添加数据
            this.insert(dataFieldValue);
        }

    }

    @Override
    public Page pagePo2Vo(Page page) {
        // 转化page中数据变成前端可用的vo
        List<DataPoolVo> vo = DataPoolUtils.listEntity2Vo(page.getRecords());
        page.setRecords(vo);

        return page;
    }

    @Override
    public List<DataFieldValueTree> compareDifference(Long id) {
        DataFieldValue dfv = new DataFieldValue();
        dfv.setFieldId(id);
        dfv.setIsDeleted(DataCleanConstant.NO);
        List<DataFieldValue> allData = baseMapper.selectList(new EntityWrapper<>(dfv));

        List<DataFieldValueTree> treeList = allData.stream().map(value -> {
            DataFieldValueTree node = new DataFieldValueTree();
            node.setId(value.getId());
            node.setPid(value.getBeCleanedId());
            return node;
        }).collect(Collectors.toList());
        return TreeUtil.buildByRecursive(treeList, 0L);
    }

	@Override
	public String getAnalysisData(Long id) {
		//第一次分析

		//多次分析

		return null;
	}

	/**
     * 调用清洗相似度计算
     */
    public void interfaceTest() {
        String str = "{\"threshold\":0.6,\"Params\":[\"length\",\"type\",\"nameEn\",\"nameCn\"],\"weights\":[0.1,0.2,"
                + "0.3,0.4],\"approximates\":[0,0,1,1],\"standard\":{\"length\":10,\"type\":1,\"nameEn\":\"mc\","
                + "\"nameCn\":\"名称\"},\"similarity\":{\"nameEn\":{\"xm\":0.5,\"mz\":0.8},\"nameCn\":{\"姓名\":0.6,"
                + "\"名字\":0.8}},\"data\":[{\"id\":1,\"length\":10,\"type\":1,\"nameEn\":\"xm\",\"nameCn\":\"姓名\"},"
                + "{\"id\":2,\"length\":18,\"type\":2,\"nameEn\":\"sfz\",\"nameCn\":\"身份证\"},{\"id\":3,\"length\":1,"
                + "\"type\":3,\"nameEn\":\"sex\",\"nameCn\":\"性别\"}]}";
        String simResult = calculateService.Similarity(str);
        System.out.println(simResult);
    }

}
