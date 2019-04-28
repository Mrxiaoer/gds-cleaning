package com.cloud.gds.gms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.vo.GeneralVO;
import com.cloud.gds.gms.api.vo.GovAnalyseVo;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
public interface GovPolicyGeneralService extends IService<GovPolicyGeneral> {


	/**
	 * 根据用户条件查询信息
	 *
	 * @param query
	 * @return
	 */
	Page<GeneralVO> selectAllPage(Query query);

	/**
	 * 根据用户自定义条件获取查询信息
	 *
	 * @param params
	 * @return
	 */
	List<GovAnalyseVo> gainAll(Map<String, Object> params);

	/**
	 * 根据用户自定义条件查询信息的id
	 *
	 * @param params
	 * @return
	 */
	List<Long> gainList(Map<String, Object> params);

	List<GovPolicyGeneral> queryByInfos(List<Long> ids);
}
