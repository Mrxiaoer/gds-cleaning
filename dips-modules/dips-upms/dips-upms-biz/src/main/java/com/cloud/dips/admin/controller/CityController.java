package com.cloud.dips.admin.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.dto.CityTree;
import com.cloud.dips.admin.api.entity.SysCity;
import com.cloud.dips.admin.service.SysCityService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.R;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;


/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:31
 */
@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {
    @Autowired
    private SysCityService govCityService;

    
    /**
	 * 返回城市树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	@ApiOperation(value = "返回城市树形菜单集合", notes = "返回城市树形菜单集合", httpMethod = "GET")
	public List<CityTree> getTree() {
		SysCity condition = new SysCity();
		condition.setIsDeleted(CommonConstant.STATUS_NORMAL);
		return govCityService.selectListTree(new EntityWrapper<>(condition));
	}


    /**
     * 通过ID查询
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询城市详细", notes = "根据ID查询城市详细: params{城市ID: id}", httpMethod = "GET")
    public SysCity info(@PathVariable("id") Integer id){
			return govCityService.selectById(id);
    }

    /**
	 * 添加
	 *
	 * @param sysCity 实体
	 * @return success/false
	 */
	@PostMapping("/create")
	@ApiOperation(value = "添加城市", notes = "添加城市", httpMethod = "POST")
	@PreAuthorize("@pms.hasPermission('sys_city_add')")
	public R<Boolean> add(@Valid @RequestBody SysCity sysCity) {
		//设置城市的缺省值
		sysCity.applyDefaultValue();
		return new R<>(govCityService.insertCity(sysCity));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@PostMapping("/delete/{id}")
	@ApiOperation(value = "删除城市", notes = "根据ID删除城市: params{类型ID: id}", httpMethod = "POST")
	@PreAuthorize("@pms.hasPermission('sys_city_del')")
	public R<Boolean> delete(@PathVariable Integer id) {
		return new R<>(govCityService.deleteCityById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysCity 实体
	 * @return success/false
	 */
	@PostMapping("/update")
	@ApiOperation(value = "更新城市", notes = "更新城市", httpMethod = "POST")
	@PreAuthorize("@pms.hasPermission('sys_city_edit')")
	public Boolean edit(@Valid @RequestBody SysCity sysCity) {
		sysCity.setModifiedTime(LocalDateTime.now());
		return govCityService.updateCityById(sysCity);
	}
}
