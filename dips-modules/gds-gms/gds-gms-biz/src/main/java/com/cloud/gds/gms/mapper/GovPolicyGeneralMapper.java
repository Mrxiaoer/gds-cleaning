package com.cloud.gds.gms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.vo.GeneralVO;
import com.cloud.gds.gms.api.vo.GovAnalyseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

@Mapper
public interface GovPolicyGeneralMapper extends BaseMapper<GovPolicyGeneral> {


	/**
	 * 根据条件查询
	 *
	 * @param query
	 * @param map
	 * @return
	 */
	List<GeneralVO> selectAll(Query query, Map<String, Object> map);

	/**
	 * 政策分析查询列表
	 *
	 * @param map
	 * @return
	 */
	List<GovAnalyseVo> queryAll(Map<String, Object> map);

	/**
	 * 根据查询条件获取信息的id
	 *
	 * @param params
	 * @return
	 */
	List<Long> gainList(Map<String, Object> params);

	List<GovPolicyGeneral> queryByInfos(@Param("ids") List<Long> ids);
}
