package com.dowhile.frontend.mapping.bean;

import java.util.List;

public class OrganizationGraph {
	
	private String name;
	private String title;
	private String className;
	private List<Organization> children;
	/**
	 * 
	 */
	public OrganizationGraph() {
	}
	/**
	 * @param name
	 * @param title
	 * @param className
	 * @param children
	 */
	public OrganizationGraph(String name, String title, String className,
			List<Organization> children) {
		this.name = name;
		this.title = title;
		this.className = className;
		this.children = children;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the children
	 */
	public List<Organization> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Organization> children) {
		this.children = children;
	}
}