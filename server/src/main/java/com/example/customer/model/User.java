package com.example.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "T_USER", uniqueConstraints = {
		@UniqueConstraint(name = "idx_unique_user", columnNames = { "tx_login_name" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_key")
	private Long userKey;

	@Column(name = "tx_name")
	private String name;

	@Column(name = "tx_login_name")
	private String loginName;

	@Column(name = "tx_password")
	private String password;

	@Column(name = "is_disabled")
	private boolean isDisabled;

	@Column(name = "is_login_allowed")
	private boolean isAllowLogin;

	@Column(name = "id_user_mod_key")
	private Integer userModKey;

	@Column(name = "dtt_mod")
	private Date dateModified;

	@Column(name = "tx_roles")
	private String roles;

	@Column(name = "tx_comment", length = 1024)
	private String comment;
}
