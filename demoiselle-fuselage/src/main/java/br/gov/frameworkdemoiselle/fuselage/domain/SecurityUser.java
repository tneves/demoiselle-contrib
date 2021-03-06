package br.gov.frameworkdemoiselle.fuselage.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "SECURITYUSER")
public class SecurityUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ATIUS_ID", sequenceName = "SQ_ATIUS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ATIUS_ID")
	private Long id;

	@Column
	@NotBlank(message = "Especifique melhor o login")
	@Size(min = 3, max = 255, message = "Especifique melhor o login")
	private String login;

	@Column
	@NotBlank(message = "Especifique melhor o nome")
	@Size(min = 3, max = 255, message = "Especifique melhor o nome")
	private String name;

	@Column
	@Size(max = 255, message = "Especifique melhor a senha")
	private String passwordhash;

	@Transient
	@Size(max = 255, message = "Especifique melhor a senha")
	private String password;

	@Transient
	@Size(max = 255, message = "Especifique melhor a senha")
	private String passwordrepeat;

	@Column
	@Size(max = 255, message = "Especifique melhor a organização")
	private String orgunit;

	@Column
	@Size(max = 255, message = "Especifique melhor a descrição")
	private String description;

	@Column
	@NotNull
	private Integer available;

	@ManyToMany
	@JoinTable(name = "SECURITYUSER_PROFILE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "PROFILE_ID") })
	private List<SecurityProfile> profiles;

	public SecurityUser() {

	}

	public SecurityUser(String login, String name, String password) {
		this.login = login;
		this.name = name;
		this.available = 1;
		this.password = password;
		setPasswordhash();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || id == null || getClass() != obj.getClass())
			return false;
		if (id.equals(((SecurityUser) obj).id))
			return true;
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPassword(String password) {
		if (passwordhash != null && passwordhash.equals(DigestUtils.sha512Hex(password)))
			return true;
		return false;
	}

	public void setPasswordhash() {
		this.passwordhash = DigestUtils.sha512Hex(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordrepeat() {
		return passwordrepeat;
	}

	public void setPasswordrepeat(String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}

	public List<SecurityProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<SecurityProfile> profiles) {
		this.profiles = profiles;
	}

	public String getOrgunit() {
		return orgunit;
	}

	public void setOrgunit(String orgunit) {
		this.orgunit = orgunit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public boolean isEnabled() {
		if (this.available != null && this.available.intValue() == 1)
			return true;
		return false;
	}

}
