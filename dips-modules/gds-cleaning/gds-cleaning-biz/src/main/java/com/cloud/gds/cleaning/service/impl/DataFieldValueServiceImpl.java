package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.WillAnalysisData;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.utils.TreeUtil;
import com.cloud.gds.cleaning.api.vo.DataFieldValueTree;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.*;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	private final DataFieldService dataFieldService;
	private final DataRuleService dataRuleService;
	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	DataFieldValueMapper dataFieldValueMapper;

	@Autowired
	AnalysisResultService analysisResultService;

	@Autowired
	public DataFieldValueServiceImpl(CalculateService calculateService, DataFieldService dataFieldService,
		DataRuleService dataRuleService) {
		this.calculateService = calculateService;
		this.dataFieldService = dataFieldService;
		this.dataRuleService = dataRuleService;
	}

	@Override
	public Page<DataFieldValue> queryPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<DataFieldValue>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<DataFieldValue>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if(StrUtil.isNotBlank(fieldId)){
			e.like("field_id",  SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		e.eq("is_deleted", DataCleanConstant.NO);
		return this.selectPage(p,e);
	}

	@Override
	public Page contrastBeforePage(Map<String, Object> params) {
		// 获取分页信息
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<DataFieldValue>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<DataFieldValue>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if(StrUtil.isNotBlank(fieldId)){
			e.like("field_id",  SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		// 查询当前清洗池信息
		DataField dataField = dataFieldService.selectById((Long) params.get("fieldId"));
		// 获取规则中百分比
		DataRuleVo dataRuleVo = dataRuleService.queryById(dataField.getRuleId());

		return null;
	}

	@Override
	public List<DataFieldValue> gainCleanData(Long fieldId) {
		return dataFieldValueMapper.gainCleanData(fieldId);
	}

	@Override
	public DataPoolVo queryById(Long id) {
		DataFieldValue dataFieldValue = this.selectById(id);
		return DataPoolUtils.entity2Vo(dataFieldValue);
	}

	@Override
	public List<DataFieldValue> gainDetails(Long id) {
		return dataFieldValueMapper.gainDetails(id);
	}

	@Override
	public List<DataFieldValue> selectByfieldId(Long fieldId) {
		return this.selectList(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.NO));
	}

	@Override
	public Boolean updateJson(Long id, Map<String, Object> map) {
		// 修改->先删再增
		this.deleteById(id);

		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setFieldValue(JSON.toJSONString(map));
		dataFieldValue.setCreateTime(LocalDateTime.now());
		dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
		return this.insert(dataFieldValue);
	}

	@Override
	public Boolean deleteById(Long id) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setId(id);
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		dataFieldValue.setIsDeleted(DataCleanConstant.YES);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultService.deleteAllById(id);
		return this.updateById(dataFieldValue);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {

		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		dataFieldValue.setIsDeleted(DataCleanConstant.YES);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultService.deleteAllByIds(ids);
		return this.update(dataFieldValue, new EntityWrapper<DataFieldValue>().in("id",ids ));
	}

	@Override
	public Boolean save(Long fieldId, JSONObject params) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setFieldId(fieldId);
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
			dataFieldValue.setCreateTime(LocalDateTime.now());
			dataFieldValue.setModifiedTime(LocalDateTime.now());
			dataFieldValue.setFieldValue(map.toString());
			dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
			// 添加数据
			this.insert(dataFieldValue);
		}
//		this.insertBatch();
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
	public String getAnalysisData(Long fieldId,Float threshold) {

		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField = dataFieldService.selectById(dataField);

		DataRule dataRule = new DataRule();
		dataRule.setId(dataField.getRuleId());
		dataRule = dataRuleService.selectById(dataRule);

		WillAnalysisData willAnalysisData = new WillAnalysisData();
		//设置阀值
		willAnalysisData.setThreshold(threshold);
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
		String resultPath = fileSavePath + "/"+fieldId+".txt";
		FileWriter fileWriter = new FileWriter(resultPath);
		fileWriter.write(JSONUtil.toJsonStr(willAnalysisData));

		//返回文件路径
		return resultPath;
	}

	@Override
	public Boolean clear(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
		return this.delete(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId));
	}

	@Override
	public Boolean clearBuffer(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除->清缓冲
		return this.delete(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.YES));
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
		String simResult = calculateService.Similarity(DataCleanConstant.QUICK_ANALYSIS,"dddd");
		System.out.println(simResult);
	}

}
