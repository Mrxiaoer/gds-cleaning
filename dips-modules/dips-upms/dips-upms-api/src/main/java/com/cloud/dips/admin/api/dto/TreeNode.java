package com.cloud.dips.admin.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author RCG
 * @date 2017年11月19日23:33:45
 */
@Data
public class TreeNode {
	protected int id;
	protected int parentId;
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
