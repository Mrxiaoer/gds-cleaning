package com.cloud.gds.gms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.gms.vo.GeneralVO;
import com.cloud.gds.gms.entity.GovPolicyGeneral;

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

}
