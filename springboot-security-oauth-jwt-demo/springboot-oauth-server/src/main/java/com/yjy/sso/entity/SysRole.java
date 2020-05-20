package com.yjy.sso.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = -7136537864183138269L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;
    private String roleCode;
    private String roleDescription;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="sysRoles")
	private List<SysUser> sysUsers = new ArrayList<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sys_role_permission", 
		joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, 
		inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
	private List<SysPermission> sysPermissions = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	public List<SysUser> getSysUsers() {
		return sysUsers;
	}
	
	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}
	
	public List<SysPermission> getSysPermissions() {
		return sysPermissions;
	}
	
	public void setSysPermissions(List<SysPermission> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}
}
