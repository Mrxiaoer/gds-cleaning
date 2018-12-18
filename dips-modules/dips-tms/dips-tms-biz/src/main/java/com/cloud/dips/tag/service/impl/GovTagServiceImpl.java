package com.cloud.dips.tag.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagFunction;
import com.cloud.dips.tag.api.entity.GovTagModificationRecord;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.api.vo.MapVO;
import com.cloud.dips.tag.mapper.GovTagMapper;
import com.cloud.dips.tag.service.GovTagFunctionService;
import com.cloud.dips.tag.service.GovTagModificationRecordService;
import com.cloud.dips.tag.service.GovTagService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagServiceImpl extends ServiceImpl<GovTagMapper, GovTag>
		implements GovTagService {

	@Autowired
	private GovTagMapper mapper;
	@Autowired
	private GovTagFunctionService govTagFunctionService;
	@Autowired
	private GovTagModificationRecordService recordService;
	

	/**
	 * 分页查询标签
	 *
	 * @param query 查询条件
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<GovTagVO> selectAllPage(Query query) {
		Object name = query.getCondition().get("name");
		Object typeid = query.getCondition().get("typeid");
		Object levelid = query.getCondition().get("levelid");
		Object status = query.getCondition().get("status");
		Object enable = query.getCondition().get("enable");
		Object fob = query.getCondition().get("fob");
		String tagname="";
		if(name!=null){
			tagname=escapeExprSpecialWord(name.toString());
		}
		query.setRecords(mapper.selectGovTagVoPage(query, tagname,typeid,levelid,status,enable,fob));
		return query;
	}
	
public String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotEmpty(keyword)) {
            String[] fbsArr = { "\\","$","(",")","*","+",".","[", "]","?","^","{","}","|","'","%" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
	
	/**
	 * 通过ID查询标签
	 *
	 * @param ID
	 * 
	 * @return 标签
	 */
	@Override
	public GovTagVO selectGovTagVoById(Integer id) {
		return mapper.selectGovTagVoById(id);
	}


	/**
	 * 删除标签
	 *
	 * @param govTag 标签
	 *            
	 * @return Boolean
	 */
	@Override
	public Boolean deleteGovTagById(GovTag govTag) {
		this.deleteById(govTag.getTagId());
		return Boolean.TRUE;
	}

	/**
	 * 保存标签
	 * 
	 * @param govTag
	 * 
	 * @return
	 */
	@Override
	public GovTag save(GovTag govTag) {
		govTag.setSystem(CommonConstant.SYSTEM_NAME);
		GovTagFunction govTagFunction=govTagFunctionService.getByNumber("tagReview");
		if(govTagFunction!=null && govTagFunction.getEnable()==1){
			govTag.setStatus(0);
		}
		this.insert(govTag);
		GovTagModificationRecord record=new GovTagModificationRecord();
		record.setCreatorId(govTag.getCreatorId());
		record.setTagId(govTag.getTagId());
		record.setDescription("创建了标签“"+govTag.getName()+"”");
		recordService.insert(record);
		return govTag;
	}
	
	/**
	 * 根据标签名称查标签
	 * @param tagName
	 * @return
	 */
	@Override
	public Integer findByGovTagName(String tagName){
		return mapper.findByGovTagName(tagName);
	}

	/**
	 * 根据标签分类统计标签
	 * @return
	 */
	@Override
	public List<MapVO> coutnByType() {
		return mapper.coutnByType();
	}
	
	/**
	 * 根据日期统计标签
	 * @param date
	 * @return
	 */
	@Override
	public List<MapVO> coutnByDate(Date date) {
		return mapper.coutnByDate(date);
	}

	@Override
	public List<GovTagVO> getAllTag() {
		return mapper.getAllTag();
	}

}
