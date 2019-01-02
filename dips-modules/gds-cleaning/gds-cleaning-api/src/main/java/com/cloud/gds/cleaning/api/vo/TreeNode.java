package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RCG
 * @date 2017年11月19日23:33:45
 */
@Data
public class TreeNode {
	protected Long id;
	protected Long pid;
	protected List<TreeNode> children = new ArrayList<>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
