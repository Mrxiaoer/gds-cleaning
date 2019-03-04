package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.utils.TreeUtil;
import com.cloud.gds.cleaning.api.vo.*;
import com.cloud.gds.cleaning.mapper.AnalysisResultMapper;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据池接口实现类
 *
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Service
public class DataFieldValueServiceImpl extends ServiceImpl<DataFieldValueMapper, DataFieldValue> implements
	DataFieldValueService {

	private final DataRuleMapper dataRuleMapper;
	private final DataFieldValueMapper dataFieldValueMapper;
	private final AnalysisResultMapper analysisResultMapper;
	private final DataFieldMapper dataFieldMapper;
	private final DataRuleService dataRuleService;

	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	public DataFieldValueServiceImpl(DataFieldMapper dataFieldMapper, DataRuleService dataRuleService,
									 DataFieldValueMapper dataFieldValueMapper, DataRuleMapper dataRuleMapper,
									 AnalysisResultMapper analysisResultMapper) {
		this.dataFieldMapper = dataFieldMapper;
		this.dataRuleService = dataRuleService;
		this.dataFieldValueMapper = dataFieldValueMapper;
		this.dataRuleMapper = dataRuleMapper;
		this.analysisResultMapper = analysisResultMapper;
	}

	@Override
	public Page<DataPoolVo> queryPage(Map<String, Object> params) {
		boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if (StrUtil.isNotBlank(fieldId)) {
			e.eq("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		e.eq("is_deleted", DataCleanConstant.FALSE);
		Page<DataFieldValue> page1 = this.selectPage(p, e);
		Page<DataPoolVo> page2 = new Page<>();
		BeanUtils.copyProperties(page1, page2);
		page2.setRecords(DataPoolUtils.listEntity2Vo(page1.getRecords()));

		return page2;
	}

	@Override
	public Page<BaseVo> contrastBeforePage(Map<String, Object> params) {
		// 获取分页信息
		boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if (StrUtil.isNotBlank(fieldId)) {
			e.eq("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		Page<DataFieldValue> page = this.selectPage(p, e);

		// 查询当前清洗池信息
		DataField dataField = dataFieldMapper.selectById(Long.valueOf(String.valueOf(params.get("fieldId"))));

		// 获取规则中百分比最高的字段
		DataSetVo resultSet = dataRuleService.gainUpperPower(dataField.getRuleId());
		if (resultSet.getProp() == null) {
			return null;
		}

		// 重新构造赋值分页信息
		Page<BaseVo> page2 = new Page<>();
		BeanUtils.copyProperties(page, page2);
		List<BaseVo> baseVos = new ArrayList<>();

		List<DataPoolVo> dataPool = DataPoolUtils.listEntity2Vo(page.getRecords());
		for (DataPoolVo dataPoolVo : dataPool) {
			BaseVo baseVo = new BaseVo();
			baseVo.setId(dataPoolVo.getId());
			Map<String, Object> dataPoolMap = dataPoolVo.getFieldValue();
			baseVo.setName(dataPoolMap.get(resultSet.getProp()).toString());

			baseVos.add(baseVo);
		}
		page2.setRecords(baseVos);

		return page2;
	}

	@Override
	public Page<BaseVo> contrastAfterPage(Map<String, Object> params) {
		// 获取分页信息
		boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if (StrUtil.isNotBlank(fieldId)) {
			e.eq("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		e.eq("is_deleted", DataCleanConstant.FALSE);
		Page<DataFieldValue> page = this.selectPage(p, e);

		// 查询当前清洗池信息
		DataField dataField = dataFieldMapper.selectById(Long.valueOf(String.valueOf(params.get("fieldId"))));

		// 获取规则中百分比最高的字段
		DataSetVo resultSet = dataRuleService.gainUpperPower(dataField.getRuleId());

		// 重新构造赋值分页信息
		Page<BaseVo> page2 = new Page<>();
		BeanUtils.copyProperties(page, page2);
		List<BaseVo> baseVos = new ArrayList<>();

		List<DataPoolVo> dataPool = DataPoolUtils.listEntity2Vo(page.getRecords());
		// 组件前端动态数据
		if (resultSet.getProp() == null) {
			return null;
		}
		for (DataPoolVo vo : dataPool) {
			BaseVo baseVo = new BaseVo();
			baseVo.setId(vo.getId());
			Map<String, Object> dataPoolMap = vo.getFieldValue();
			baseVo.setName(dataPoolMap.get(resultSet.getProp()).toString());

			baseVos.add(baseVo);
		}
		page2.setRecords(baseVos);

		return page2;
	}

	@Override
	public Page<DataPoolVo> queryRecycleBinPage(Map<String, Object> params) {
		boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p = new Page<>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e = new EntityWrapper<>();
		String fieldId = params.getOrDefault("fieldId", "").toString();
		if (StrUtil.isNotBlank(fieldId)) {
			e.eq("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		e.eq("is_deleted", DataCleanConstant.TRUE);
		Page<DataFieldValue> page1 = this.selectPage(p, e);
		Page<DataPoolVo> page2 = new Page<>();
		BeanUtils.copyProperties(page1, page2);
		page2.setRecords(DataPoolUtils.listEntity2Vo(page1.getRecords()));

		return page2;
	}

	@Override
	public List<CenterData> gainCleanData(Long fieldId) {
		// field_value需要取其中比例"较高"的一项,因此需要重新组装中心数据回显
		List<CenterData> list = dataFieldValueMapper.gainCleanData(fieldId);

		// 获取规则中比较最高的一项
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(dataFieldMapper.selectById(fieldId).getRuleId());

		// 取field_value值,先判断list是否有值
		if (list != null && list.size() != 0) {
			for (CenterData centerData : list) {
				// field_value,字符串转map取要的数据
				com.alibaba.fastjson.JSONObject myJson = com.alibaba.fastjson.JSONObject
					.parseObject(centerData.getFieldValue());
				Map<String, Object> map = (Map<String, Object>) myJson;
				centerData.setFieldValue(map.get(dataSetVo.getProp()) != null ? map.get(dataSetVo.getProp()).toString() : ">无最高权重字段值喔~<");
			}
		}
		return list;
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
	public List<DARVo> centerToSatellite(Long centerId) {
		// 根据中心Id->卫星数据信息
		List<DataPoolAnalysis> list = dataFieldValueMapper.selectDataPool(centerId);
		List<DARVo> darVos = new ArrayList<>();

		// DataPoolAnalysis 转 DARVo
		for (DataPoolAnalysis result : list) {
			DARVo darVo = new DARVo();
			BeanUtils.copyProperties(result, darVo);
			darVo.setFieldValue(com.alibaba.fastjson.JSONObject.parseObject(result.getFieldValue()));
			// 百分比转换
			DecimalFormat df = new DecimalFormat(".00");
			darVo.setSimilarity(Double.parseDouble(df.format(darVo.getSimilarity() * 100)));
			darVos.add(darVo);
		}
		return darVos;
	}

	@Override
	public Boolean updateJson(Long id, Map<String, Object> map) {
		// 修改->先删再增
		DataFieldValue q = this.selectById(id);
		this.deleteById(id);

		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setFieldId(q.getFieldId());
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
		dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultDelete(id);
		return this.updateById(dataFieldValue);
	}

	/**
	 * 数据池数据删除 触发分析结果表中的数据进行删除
	 * 由于未对结果池中查看是否存在此id,因此删除的条数->boolean 可能是false
	 *
	 * @param ids
	 * @return
	 */
	private boolean analysisResultDeletes(Set<Long> ids) {
		return SqlHelper.delBool(analysisResultMapper.delete(new EntityWrapper<AnalysisResult>().in("base_id", ids)))
			&& SqlHelper
			.delBool(analysisResultMapper.delete(new EntityWrapper<AnalysisResult>().in("compare_id", ids)));

	}

	/**
	 * 数据池数据删除 触发分析结果表中的数据进行删除
	 * 由于未对结果池中查看是否存在此id,因此删除的条数->boolean 可能是false
	 *
	 * @param id
	 * @return
	 */
	private boolean analysisResultDelete(Long id) {
		AnalysisResult before1 = new AnalysisResult();
		before1.setBaseId(id);
		AnalysisResult before2 = new AnalysisResult();
		before2.setCompareId(id);
		return SqlHelper.delBool(analysisResultMapper.delete(new EntityWrapper<>(before1))) && SqlHelper
			.delBool(analysisResultMapper.delete(new EntityWrapper<>(before2)));
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {

		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultDeletes(ids);
		return this.update(dataFieldValue, new EntityWrapper<DataFieldValue>().in("id", ids));
	}

	@Override
	public Boolean save(Long fieldId, com.alibaba.fastjson.JSONObject params) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setFieldId(fieldId);
		dataFieldValue.setFieldValue(JSON.toJSONString(params));
		dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
		dataFieldValue.setCreateTime(LocalDateTime.now());
		// 添加数据需将清洗池中的分析状态更换成最原先的状态
		DataField dataField = dataFieldMapper.selectById(fieldId);
		if (!DataCleanConstant.FALSE.equals(dataField.getAnalyseState())) {
			DataField q = new DataField();
			q.setId(fieldId);
			q.setAnalyseState(DataCleanConstant.FALSE);
			assert SecurityUtils.getUser() != null;
			q.setModifiedUser(SecurityUtils.getUser().getId());
			q.setModifiedTime(LocalDateTime.now());
			dataFieldMapper.updateById(q);
		}
		return this.insert(dataFieldValue);
	}

	@Override
	//	@Transactional(rollbackFor = Exception.class)
	public void saveAll(Long fieldId, List<Map<String, Object>> fieldValues) {
		// 循环插入数据库相关信息
		List<DataFieldValue> list = new ArrayList<>();
		LocalDateTime localDateTime = LocalDateTime.now();
		// todo 判空处理 2019-1-9 19:25:24
		for (Map<String, Object> map : fieldValues) {
			DataFieldValue dataFieldValue = new DataFieldValue();
			// 组装
			dataFieldValue.setFieldId(fieldId);
			dataFieldValue.setCreateTime(localDateTime);
			dataFieldValue.setFieldValue(JSON.toJSONString(map));
			dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
			// 添加数据
			list.add(dataFieldValue);
		}
		this.insertBatch(list);
	}

	@Override
	public List<DataFieldValueTree> compareDifference(Long id) {
		DataFieldValue dfv = new DataFieldValue();
		dfv.setFieldId(id);
		dfv.setIsDeleted(DataCleanConstant.FALSE);
		List<DataFieldValue> allData = baseMapper.selectList(new EntityWrapper<>(dfv));

		List<DataFieldValueTree> treeList = allData.stream().map(value -> {
			DataFieldValueTree node = new DataFieldValueTree();
			node.setId(value.getId());
			node.setPid(value.getBeCleanedId());
			return node;
		}).collect(Collectors.toList());
		return TreeUtil.buildByRecursive(treeList, 0L);
	}

	// @Override
	// @Transactional(rollbackFor = Exception.class)
	// public String getAnalysisData(Long fieldId, Float threshold) {
	//
	// 	DataField dataField = new DataField();
	// 	dataField.setId(fieldId);
	// 	dataField = dataFieldService.selectById(dataField);
	//
	// 	DataRule dataRule = new DataRule();
	// 	dataRule.setId(dataField.getRuleId());
	// 	dataRule = dataRuleService.selectById(dataRule);
	//
	// 	WillAnalysisData willAnalysisData = new WillAnalysisData();
	// 	//设置阀值
	// 	willAnalysisData.setThreshold(threshold);
	// 	//设置字段名，权重，字段是否近似
	// 	List<DataSetVo> list = JSONUtil.parseArray(dataRule.getParams()).toList(DataSetVo.class);
	// 	List<String> params = new ArrayList<>(4);
	// 	List<Float> weights = new ArrayList<>(4);
	// 	List<Integer> approximates = new ArrayList<>(4);
	// 	List<String> needDeleteFields = new ArrayList<>();
	// 	for (DataSetVo dataSetVo : list) {
	// 		//如果权重为零，删除该字段
	// 		if (dataSetVo.getWeight() > 0) {
	// 			params.add(dataSetVo.getProp());
	// 			weights.add(dataSetVo.getWeight());
	// 			approximates.add(dataSetVo.getIsSynonymous());
	// 		} else {
	// 			needDeleteFields.add(dataSetVo.getProp());
	// 		}
	// 	}
	// 	willAnalysisData.setParams(params);
	// 	willAnalysisData.setWeights(weights);
	// 	willAnalysisData.setApproximates(approximates);
	//
	// 	willAnalysisData.setNeedReanalysis(dataField.getNeedReanalysis());
	// 	//设置待分析数据
	// 	List<DataFieldValue> willAnalysisList = firstAnalysisList(fieldId);
	// 	List<JSONObject> objList = new ArrayList<>();
	// 	for (DataFieldValue dataFieldValue : willAnalysisList) {
	// 		if (JSONUtil.isJsonObj(dataFieldValue.getFieldValue())) {
	// 			JSONObject jsonObj = JSONUtil.parseObj(dataFieldValue.getFieldValue());
	// 			//如果原数据含字段id，删除之
	// 			jsonObj.remove("id");
	// 			//删除权重为0的字段
	// 			for (String needDeleteField : needDeleteFields) {
	// 				jsonObj.remove(needDeleteField);
	// 			}
	// 			for (String needField : params) {
	// 				if (!jsonObj.containsKey(needField)) {
	// 					jsonObj.put(needField, "");
	// 				}
	// 			}
	// 			//添加id字段
	// 			jsonObj.putOnce("id", dataFieldValue.getId());
	//
	// 			objList.add(jsonObj);
	// 		}
	// 	}
	// 	willAnalysisData.setData(objList);
	//
	// 	//写入文件
	// 	String resultPath = fileSavePath + "/" + fieldId + ".json";
	// 	FileWriter fileWriter = new FileWriter(resultPath);
	// 	fileWriter.write(JSONUtil.toJsonStr(willAnalysisData));
	//
	// 	//返回文件路径
	// 	return resultPath;
	// }

	@Override
	public boolean cleanDate(List<Map<String, Object>> params) {

		List<DataFieldValue> list = new ArrayList<>();
		Set<Long> ids = new HashSet<>();
		// 拼装参数进行清洗
		for (Map<String, Object> map : params) {
			// 组装value中要清洗的相应数据信息
			DataFieldValue dataFieldValue = new DataFieldValue();
			Long cleanId = Long.valueOf(String.valueOf(map.get("cleanId")));
			dataFieldValue.setId(cleanId);
			dataFieldValue.setBeCleanedId(Long.valueOf(String.valueOf(map.get("baseId"))));
			// 由于数据被清洗了,对数据进行删除状态的更新
			dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
			dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
			dataFieldValue.setModifiedTime(LocalDateTime.now());
			list.add(dataFieldValue);
			ids.add(cleanId);
			// 如数据被清洗,分析结果中相应的记录需要删除
			AnalysisResult q = new AnalysisResult();
			q.setBaseId(Long.valueOf(String.valueOf(map.get("baseId"))));
			q.setCompareId(Long.valueOf(String.valueOf(map.get("cleanId"))));
			analysisResultMapper.delete(new EntityWrapper<>(q));
		}
		// 清洗数据
		System.out.println(ids);
		analysisResultMapper.relevanceDelete(ids);
		return this.updateBatchById(list);
	}

	@Override
	public Boolean clear(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
		DataField field = new DataField();
		field.setId(fieldId);
		field.setAnalyseState(DataCleanConstant.NO_ANALYSIS);
		field.setModifiedUser(SecurityUtils.getUser().getId());
		field.setModifiedTime(LocalDateTime.now());
		dataFieldMapper.updateById(field);
		return this.delete(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId));
	}

	@Override
	public Boolean clearBuffer(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除->清缓冲
		return this.delete(
			new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.TRUE));
	}

	@Override
	public List<CleanItem> cleaningItem(Long beCleanedId) {
		// 查询规则比较高的项
		Long fieldId = dataFieldValueMapper.selectById(beCleanedId).getFieldId();
		Long ruleId = dataFieldMapper.selectById(fieldId).getRuleId();
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(ruleId);

		// 查询被清洗掉的数据
		List<DataFieldValue> baseDate = dataFieldValueMapper
			.selectList(new EntityWrapper<DataFieldValue>().eq("be_cleaned_id", beCleanedId));

		// po 转vo主要是把string字段转成json字段
		List<DataPoolVo> dataPools = DataPoolUtils.listEntity2Vo(baseDate);

		// 组装返回结果集
		List<CleanItem> baseVos = new ArrayList<>();
		for (DataPoolVo result : dataPools) {
			CleanItem b = new CleanItem();
			b.setId(result.getId());
			Map<String, Object> map = result.getFieldValue();
			b.setLabel(map.get(dataSetVo.getProp()).toString());
			// 查询是否存在子叶
			List<DataFieldValue> leafs = dataFieldValueMapper
				.selectList(new EntityWrapper<DataFieldValue>().eq("be_cleaned_id", result.getId()));
			b.setLeaf(leafs.size() <= 0);

			baseVos.add(b);
		}
		return baseVos;
	}

	// /**
	//  * 未分析或需要重新分析的数据
	//  *
	//  * @param fieldId
	//  * @return List<DataFieldValue>
	//  */
	// @Transactional(rollbackFor = Exception.class)
	// public List<DataFieldValue> firstAnalysisList(Long fieldId) {
	// 	Wrapper<DataFieldValue> wrapper = new EntityWrapper<DataFieldValue>().eq("field_id", fieldId)
	// 		.eq("is_deleted", DataCleanConstant.FALSE);
	//
	// 	List<DataFieldValue> needAnalysisList = baseMapper.selectList(wrapper);
	// 	DataFieldValue dataFieldValue = new DataFieldValue();
	// 	dataFieldValue.setState(1);
	// 	baseMapper.update(dataFieldValue, wrapper);
	//
	// 	return needAnalysisList;
	// }

	// /**
	//  * 多次分析的数据(已弃用，所有分析统一使用firstAnalysisList方法)
	//  *
	//  * @param fieldId
	//  * @return List<DataFieldValue>
	//  */
	// @Deprecated
	// @Transactional(rollbackFor = Exception.class)
	// public List<DataFieldValue> notFirstAnalysisList(Long fieldId) {
	// 	Wrapper<DataFieldValue> wrapper = new EntityWrapper<DataFieldValue>().eq("field_id", fieldId)
	// 		.eq("is_deleted", DataCleanConstant.FALSE).eq("state", 0);
	//
	// 	List<DataFieldValue> needAnalysisList = baseMapper.selectList(wrapper);
	// 	DataFieldValue dataFieldValue = new DataFieldValue();
	// 	dataFieldValue.setState(1);
	// 	baseMapper.update(dataFieldValue, wrapper);
	//
	// 	return needAnalysisList;
	// }

	@Override
	public JSONArray dataJsonInput(long fieldId, JSONArray jsonArray) throws NullPointerException {
		Long startTime = System.currentTimeMillis();
		//获取规则
		String jsonParams;
		try {
			jsonParams = dataRuleMapper.selectRuleByFieldId(fieldId).getParams();
		} catch (NullPointerException npe) {
			throw new NullPointerException("未找到对应规则！");
		}

		JSONArray paramArray = JSONArray.parseArray(jsonParams);

		List<DataSetVo> dl = paramArray.toJavaList(DataSetVo.class);
		//删除与规则字段不匹配的数据
		Iterator<Object> iterator = jsonArray.iterator();
		JSONArray array = new JSONArray();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			JSONObject jsonData = JSONUtil.parseObj(obj, false);
			if (!checkJsonParams(dl, jsonData)) {
				array.add(jsonData);
				iterator.remove();
			}
		}
		Long beforeSave = System.currentTimeMillis();
		//存储正确数据
		if (!saveAllJson(fieldId, jsonArray)) {
			throw new RuntimeException("数据保存失败！");
		}
		Long afterSave = System.currentTimeMillis();
		System.out.println("校验耗时" + (beforeSave - startTime));
		System.out.println("保存耗时" + (afterSave - beforeSave));
		//返回错误数据
		return array;
	}

	@Override
	public boolean saveAllMap(long fieldId, List<Map<String, String>> mapList) {
		// 循环插入数据库相关信息
		List<DataFieldValue> list = new ArrayList<>();
		LocalDateTime nowTime = LocalDateTime.now();
		for (Map<String,String> map : mapList) {
			DataFieldValue value = new DataFieldValue();
			value.setFieldId(fieldId);
			value.setCreateTime(nowTime);
			value.setFieldValue(JSON.toJSONString(map));
			if (SecurityUtils.getUser() != null) {
				value.setCreateUser(SecurityUtils.getUser().getId());
			} else {
				value.setCreateUser(0);
			}
			list.add(value);
		}

		//批量导入
		if (list.isEmpty()) {
			return true;
		}
		return batchSave(list, 100);
	}

	/**
	 * 检查json数据，与规则字段匹配则返回true
	 *
	 * @param dl       规则字段列表
	 * @param jsonData json数据
	 * @return
	 */
	private Boolean checkJsonParams(List<DataSetVo> dl, JSONObject jsonData) {
		boolean flag = true;
		List<String> dataKeys = new ArrayList<>();
		//判断是否包含所有规则中的字段
		for (DataSetVo dsv : dl) {
			if (!jsonData.containsKey(dsv.getProp())) {
				flag = false;
			}
			dataKeys.add(dsv.getProp());
		}
		//判断是否还包含包含规则以外的字段,有则删除
		Iterator<String> iterator = jsonData.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!dataKeys.contains(key)) {
				// flag = false;
				iterator.remove();
			}
		}
		// for (String key : jsonData.keySet()) {
		// 	if (!dataKeys.contains(key)) {
		// 		// flag = false;
		// 		jsonData.remove(key);
		// 	}
		// }

		return flag;
	}

	/**
	 * 返回结果只说明导入操作是否完成，不保证是否有数据被导入
	 *
	 * @param fieldId
	 * @param jsonArray
	 * @return
	 */
	private boolean saveAllJson(long fieldId, JSONArray jsonArray) {
		// 循环插入数据库相关信息
		List<DataFieldValue> list = new ArrayList<>();
		LocalDateTime nowTime = LocalDateTime.now();
		for (Object obj : jsonArray) {
			DataFieldValue dataFieldValue = new DataFieldValue();
			dataFieldValue.setFieldId(fieldId);
			dataFieldValue.setCreateTime(nowTime);
			dataFieldValue.setFieldValue(JSON.toJSONString(obj));
			if (SecurityUtils.getUser() != null) {
				dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
			} else {
				dataFieldValue.setCreateUser(0);
			}
			list.add(dataFieldValue);
		}

		//批量导入
		if (list.isEmpty()) {
			return true;
		}
		return batchSave(list, 100);
	}

	/**
	 * 批量分段插入
	 *
	 * @param list
	 * @param oneSize
	 * @return
	 */
	@Override
	public boolean batchSave(List<DataFieldValue> list, int oneSize) {
		boolean flag = true;
		List<DataFieldValue> subList;
		int currentNum = 0;
		while (flag) {
			if (list.size() > oneSize * (currentNum + 1)) {
				subList = list.subList(currentNum * oneSize, oneSize * (currentNum + 1));
			} else {
				subList = list.subList(currentNum * oneSize, list.size());
				flag = false;
			}
			dataFieldValueMapper.insertAll(subList);
			currentNum++;
		}
		return true;
	}

	@Override
	public boolean reductionById(Long id) {
		assert SecurityUtils.getUser() != null;
		Integer userId = SecurityUtils.getUser().getId();
		return SqlHelper.retBool(dataFieldValueMapper.reductionById(id, userId));
	}

	@Override
	public boolean reductionByIds(Set<Long> ids) {
		assert SecurityUtils.getUser() != null;
		Integer userId = SecurityUtils.getUser().getId();
		return SqlHelper.retBool(dataFieldValueMapper.reductionByIds(ids, userId));
	}

	@Override
	public boolean oneKeyReduction(Long fieldId) {
		assert SecurityUtils.getUser() != null;
		Integer userId = SecurityUtils.getUser().getId();
		return SqlHelper.retBool(dataFieldValueMapper.oneKeyReduction(fieldId, userId));
	}

	@Override
	public boolean dataPoolDelete(Long id) {
		return SqlHelper.retBool(dataFieldValueMapper.deleteById(id));
	}

	@Override
	public boolean dataPoolDeletes(Set<Long> ids) {
		return SqlHelper.retBool(dataFieldValueMapper.recyclingBinClear(ids));
	}

}
