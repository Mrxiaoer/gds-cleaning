package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.WillAnalysisData;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.utils.TreeUtil;
import com.cloud.gds.cleaning.api.vo.DataFieldValueTree;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private final DataFieldService dataFieldService;
	private final DataRuleService dataRuleService;
	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	public DataFieldValueServiceImpl(CalculateService calculateService, DataFieldService dataFieldService,
		DataRuleService dataRuleService) {
		this.calculateService = calculateService;
		this.dataFieldService = dataFieldService;
		this.dataRuleService = dataRuleService;
	}

	@Override
	public Boolean updateJson(Long id, Map<String, Object> map) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setId(id);
		dataFieldValue.setFieldValue(JSON.toJSONString(map));
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		return this.updateById(dataFieldValue);
	}

	@Override
	public Boolean update(DataFieldValue dataFieldValue) {
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		return this.updateById(dataFieldValue);
	}

	@Override
	public Boolean deleteById(Long id) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setId(id);
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
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
		dataFieldValue.setFieldValue(JSON.toJSONString(params));
		dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
		dataFieldValue.setCreateTime(LocalDateTime.now());
		return this.insert(dataFieldValue);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAll(Long fieldId, List<Map<String, Object>> maps) {

		// 循环插入数据库相关信息
		for (Map<String, Object> map : maps) {
			DataFieldValue dataFieldValue = new DataFieldValue();
			// 组装
			dataFieldValue.setFieldId(fieldId);
			dataFieldValue.setFieldValue(map.toString());
			dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
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
	@Transactional(rollbackFor = Exception.class)
	public String getAnalysisData(Long fieldId) {

		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField = dataFieldService.selectById(dataField);

		DataRule dataRule = new DataRule();
		dataRule.setId(dataField.getRuleId());
		dataRule = dataRuleService.selectById(dataRule);

		WillAnalysisData willAnalysisData = new WillAnalysisData();
		//设置阀值
		willAnalysisData.setThreshold(0.8F);
		//设置字段名，权重，字段是否近似
		List<DataSetVo> list = JSONUtil.parseArray(dataRule.getParams()).toList(DataSetVo.class);
		List<String> params = new ArrayList<>(4);
		List<Float> weights = new ArrayList<>(4);
		List<Integer> approximates = new ArrayList<>(4);
		List<String> needDeleteFields = new ArrayList<>();
		for (DataSetVo dataSetVo : list) {
			//如果权重为零，删除该字段
			if (dataSetVo.getWeight() > 0) {
				params.add(dataSetVo.getProp());
				weights.add(dataSetVo.getWeight());
				approximates.add(dataSetVo.getIsSynonymous());
			} else {
				needDeleteFields.add(dataSetVo.getProp());
			}
		}
		willAnalysisData.setParams(params);
		willAnalysisData.setWeights(weights);
		willAnalysisData.setApproximates(approximates);

		willAnalysisData.setNeedReanalysis(dataField.getNeedReanalysis());
		//设置待分析数据
		List<DataFieldValue> willAnalysisList = firstAnalysisList(fieldId);
		List<JSONObject> objList = new ArrayList<>();
		// if (StrUtil.isBlank(dataField.getMatrixFile())) {
		// 	//第一次分析
		// 	willAnalysisList = firstAnalysisList(fieldId);
		// } else {
		// 	//非首次分析
		// 	willAnalysisList = notFirstAnalysisList(fieldId);
		//
		// 	DataFieldValue dfv = new DataFieldValue();
		// 	dfv.setFieldId(fieldId);
		// 	List<Long> needAnalysisIdList = baseMapper.selectNeedAnalysisIdList(dfv);
		// 	willAnalysisData.setNeedAnalysisDataId(needAnalysisIdList);
		// }
		for (DataFieldValue dataFieldValue : willAnalysisList) {
			if (JSONUtil.isJsonObj(dataFieldValue.getFieldValue())) {
				JSONObject jsonObj = JSONUtil.parseObj(dataFieldValue.getFieldValue());
				//如果原数据含字段id，删除之
				jsonObj.remove("id");
				//添加id字段
				jsonObj.putOnce("id", dataFieldValue.getId());
				//删除权重为0的字段
				for (String needDeleteField : needDeleteFields) {
					jsonObj.remove(needDeleteField);
				}

				objList.add(jsonObj);
			}
		}
		willAnalysisData.setData(objList);

		//写入文件
		String resultPath = fileSavePath + "/"+fieldId+".text";
		FileWriter fileWriter = new FileWriter(resultPath);
		fileWriter.write(JSONUtil.toJsonStr(willAnalysisData));

		//返回文件路径
		return resultPath;
	}

	/**
	 * 未分析或需要重新分析的数据
	 *
	 * @param fieldId
	 * @return List<DataFieldValue>
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<DataFieldValue> firstAnalysisList(Long fieldId) {
		Wrapper<DataFieldValue> wrapper = new EntityWrapper<DataFieldValue>().eq("field_id", fieldId)
			.eq("is_deleted", DataCleanConstant.NO);

		List<DataFieldValue> needAnalysisList = baseMapper.selectList(wrapper);
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setState(1);
		baseMapper.update(dataFieldValue, wrapper);

		return needAnalysisList;
	}

	/**
	 * 多次分析的数据
	 *
	 * @param fieldId
	 * @return List<DataFieldValue>
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<DataFieldValue> notFirstAnalysisList(Long fieldId) {
		Wrapper<DataFieldValue> wrapper = new EntityWrapper<DataFieldValue>().eq("field_id", fieldId)
			.eq("is_deleted", DataCleanConstant.NO).eq("state", 0);

		List<DataFieldValue> needAnalysisList = baseMapper.selectList(wrapper);
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setState(1);
		baseMapper.update(dataFieldValue, wrapper);

		return needAnalysisList;
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
