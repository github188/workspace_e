package com.nari.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeNode implements Comparable<TreeNode> {
	/* 树节点ID */
	private String id;
	/* 树节点名称（已丢弃） */
	@Deprecated
	private String name;

	/* 树节点标题 */
	private String text;

	/* 是否叶子节点 */
	private boolean leaf;

	/* 树节点图标 */
	private String iconCls;
	private String cls;

	private String qtip;

	/* 其他属性 */
	private String type;
	private Object attributes;

	/* 子节点列表 */
	private List<TreeNode> children;

	/* 自定义标志 */
	private String dataType;

	/**
	 * 自定义排序
	 */
	@Override
	public int compareTo(TreeNode o) {
		Pattern pattern = Pattern.compile("^[F0-9]+");
		Matcher m1 = pattern.matcher(this.getText().trim());
		Matcher m2 = pattern.matcher(o.getText().trim());
		if (m1.find()) {
			if (m2.find()) {
				Integer i1 = Integer.parseInt(m1.group(), 16);
				Integer i2 = Integer.parseInt(m2.group(), 16);
				return i1 - i2;
			} else {
				return -1;
			}
		} else {
			return 1;
		}
	}

	// getters and setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

}
