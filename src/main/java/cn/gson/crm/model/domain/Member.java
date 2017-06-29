package cn.gson.crm.model.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "crm_member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 64, nullable = false, unique = true, updatable = false)
	private String userName;

	@Column(length = 128, nullable = false)
	private String password;

	private Boolean status = true;

	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name="crm_member_role",
	joinColumns={
			@JoinColumn(name="member_id")
	},inverseJoinColumns={
			@JoinColumn(name="role_id")
	})
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", userName=" + userName + ", password=" + password + "]";
	}
}
