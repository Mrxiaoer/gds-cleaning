package com.cloud.dips.tag.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTagType;
import com.cloud.dips.tag.api.vo.GovTagTypeVO;

/**
 * @author ZB
 */
public interface GovTagTypeService extends IService<GovTagType> {
	/**
	 * 获取一级分类
	 * @return
	 */
	public List<GovTagTypeVO> selectParents();
	/**
	 * 分页获取分类
	 * @param query
	 * @return
	 */
	public Page<GovTagTypeVO> selectTypeVoPage(Query<GovTagTypeVO> query);

}

