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
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.dto.WillAnalysisData;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.utils.TreeUtil;
import com.cloud.gds.cleaning.api.vo.*;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.*;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.springframework.beans.BeanUtils;
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

	private final DataFieldValueMapper dataFieldValueMapper;
	private final CalculateService calculateService;
	private final DataFieldService dataFieldService;
	private final DataRuleService dataRuleService;
	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	AnalysisResultService analysisResultService;

	@Autowired
	public DataFieldValueServiceImpl(CalculateService calculateService, DataFieldService dataFieldService,
									 DataRuleService dataRuleService, DataFieldValueMapper dataFieldValueMapper) {
		this.calculateService = calculateService;
		this.dataFieldService = dataFieldService;
		this.dataRuleService = dataRuleService;
		this.dataFieldValueMapper = dataFieldValueMapper;
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
			e.like("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
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
			e.like("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		Page<DataFieldValue> page = this.selectPage(p, e);

		// 查询当前清洗池信息
		DataField dataField = dataFieldService.selectById(Long.valueOf(String.valueOf(params.get("fieldId"))));

		// 获取规则中百分比最高的字段
		DataSetVo resultSet = dataRuleService.gainUpperPower(dataField.getRuleId());
		// todo 规则未选择不处理 2019-1-10 15:36:32
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
			e.like("field_id", SpecialStringUtil.escapeExprSpecialWord(fieldId));
		}
		e.eq("is_deleted", DataCleanConstant.FALSE);
		Page<DataFieldValue> page = this.selectPage(p, e);

		// 查询当前清洗池信息
		DataField dataField = dataFieldService.selectById(Long.valueOf(String.valueOf(params.get("fieldId"))));

		// 获取规则中百分比最高的字段
		DataSetVo resultSet = dataRuleService.gainUpperPower(dataField.getRuleId());

		// 重新构造赋值分页信息
		Page<BaseVo> page2 = new Page<>();
		BeanUtils.copyProperties(page, page2);
		List<BaseVo> baseVos = new ArrayList<>();

		List<DataPoolVo> dataPool = DataPoolUtils.listEntity2Vo(page.getRecords());
		// 组件前端动态数据
		// todo 规则未选择不处理 2019-1-10 15:36:32
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
	public List<CenterData> gainCleanData(Long fieldId) {
		// field_value需要取其中比例"较高"的一项,因此需要重新组装中心数据回显
		List<CenterData> list = dataFieldValueMapper.gainCleanData(fieldId);

		// 获取规则中比较最高的一项
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(dataFieldService.selectById(fieldId).getRuleId());

		// 取field_value值,先判断list是否有值
		if (list != null && list.size() != 0) {
			for (CenterData centerData : list) {
				// field_value,字符串转map取要的数据
				com.alibaba.fastjson.JSONObject myJson = com.alibaba.fastjson.JSONObject.parseObject(centerData.getFieldValue());
				Map<String, Object> map = (Map<String, Object>) myJson;
				centerData.setFieldValue(map.get(dataSetVo.getProp()).toString());
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
			darVo.setSimilarity(darVo.getSimilarity() * 100);
			darVos.add(darVo);
		}
		return darVos;
	}

	@Override
	public List<DataFieldValue> selectByfieldId(Long fieldId) {
		return this.selectList(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.FALSE));
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
		assert SecurityUtils.getUser() != null;
		dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
		return this.insert(dataFieldValue);
	}

	@Override
	public Boolean deleteById(Long id) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setId(id);
		assert SecurityUtils.getUser() != null;
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultService.deleteAllById(id);
		return this.updateById(dataFieldValue);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {

		DataFieldValue dataFieldValue = new DataFieldValue();
		assert SecurityUtils.getUser() != null;
		dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
		dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
		dataFieldValue.setModifiedTime(LocalDateTime.now());
		// 删除数据时需要删除分析结果表中相关数据
		analysisResultService.deleteAllByIds(ids);
		return this.update(dataFieldValue, new EntityWrapper<DataFieldValue>().in("id", ids));
	}

	@Override
	public Boolean save(Long fieldId, com.alibaba.fastjson.JSONObject params) {
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setFieldId(fieldId);
		dataFieldValue.setFieldValue(JSON.toJSONString(params));
		assert SecurityUtils.getUser() != null;
		dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
		dataFieldValue.setCreateTime(LocalDateTime.now());
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
			assert SecurityUtils.getUser() != null;
			dataFieldValue.setCreateUser(SecurityUtils.getUser().getId());
			// 添加数据
//			this.insert(dataFieldValue);
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getAnalysisData(Long fieldId, Float threshold) {

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
		for (DataFieldValue dataFieldValue : willAnalysisList) {
			if (JSONUtil.isJsonObj(dataFieldValue.getFieldValue())) {
				JSONObject jsonObj = JSONUtil.parseObj(dataFieldValue.getFieldValue());
				//如果原数据含字段id，删除之
				jsonObj.remove("id");
				//删除权重为0的字段
				for (String needDeleteField : needDeleteFields) {
					jsonObj.remove(needDeleteField);
				}
				//添加id字段
				jsonObj.putOnce("id", dataFieldValue.getId());

				objList.add(jsonObj);
			}
		}
		willAnalysisData.setData(objList);

		//写入文件
		String resultPath = fileSavePath + "/" + fieldId + ".json";
		FileWriter fileWriter = new FileWriter(resultPath);
		fileWriter.write(JSONUtil.toJsonStr(willAnalysisData));

		//返回文件路径
		return resultPath;
	}

	@Override
	public boolean cleanDate(List<Map<String, Object>> params) {

		List<DataFieldValue> list = new ArrayList<>();
		// 拼装参数进行清洗
		for (Map<String, Object> map : params) {
			// 组装value中要清洗的相应数据信息
			DataFieldValue dataFieldValue = new DataFieldValue();
			dataFieldValue.setId(Long.valueOf(String.valueOf(map.get("cleanId"))));
			dataFieldValue.setBeCleanedId(Long.valueOf(String.valueOf(map.get("baseId"))));
			// 由于数据被清洗了,对数据进行删除状态的更新
			dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
			assert SecurityUtils.getUser() != null;
			dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
			dataFieldValue.setModifiedTime(LocalDateTime.now());
			list.add(dataFieldValue);

			// 如数据被清洗,分析结果中相应的记录需要删除
			AnalysisResult q = new AnalysisResult();
			q.setBaseId(Long.valueOf(String.valueOf(map.get("baseId"))));
			q.setCompareId(Long.valueOf(String.valueOf(map.get("cleanId"))));
			analysisResultService.delete(new EntityWrapper<>(q));
		}
		// 清洗数据
		return this.updateBatchById(list);
	}

	@Override
	public Boolean clear(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
		DataField field = new DataField();
		field.setId(fieldId);
		field.setAnalyseState(DataCleanConstant.NO_ANALYSIS);
		dataFieldService.update(field);
		return this.delete(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId));
	}

	@Override
	public Boolean clearBuffer(Long fieldId) {
		// 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除->清缓冲
		return this.delete(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.TRUE));
	}

	@Override
	public List<CleanItem> cleaningItem(Long beCleanedId) {
		// 查询规则比较高的项
		Long fieldId = dataFieldValueMapper.selectById(beCleanedId).getFieldId();
		Long ruleId = dataFieldService.selectById(fieldId).getRuleId();
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(ruleId);

		// 查询被清洗掉的数据
		List<DataFieldValue> baseDate = dataFieldValueMapper.selectList(new EntityWrapper<DataFieldValue>().eq("be_cleaned_id", beCleanedId));

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
			List<DataFieldValue> leafs = dataFieldValueMapper.selectList(new EntityWrapper<DataFieldValue>().eq("be_cleaned_id", result.getId()));
			b.setIsLeaf(!!leafs.isEmpty());

			baseVos.add(b);
		}
		return baseVos;
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
			.eq("is_deleted", DataCleanConstant.FALSE);

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
			.eq("is_deleted", DataCleanConstant.FALSE).eq("state", 0);

		List<DataFieldValue> needAnalysisList = baseMapper.selectList(wrapper);
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setState(1);
		baseMapper.update(dataFieldValue, wrapper);

		return needAnalysisList;
	}

	@Override
	public void jsonapi(List<Map<String, Object>> params) {
		for (Map<String, Object> param : params) {
			String string = JSON.toJSON(param).toString();
			System.out.println(string);
		}
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
		String simResult = calculateService.analysisSimilarity(DataCleanConstant.QUICK_ANALYSIS, "dddd");
		System.out.println(simResult);
	}

}
