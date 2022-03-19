package com.example.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "T_CUSTOMER_AUDIT")
public class CustomerAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_customer_audit_key")
	private Long customerAuditKey;

	@Column(name = "id_customer_key")
	private Long customerKey;

	@Column(name = "id_customer_ver")
	private Long customerVer;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "tx_name")
	private String name;

	@Column(name = "tx_email")
	private String email;

	@Column(name = "tx_phone")
	private String phone;

	@Column(name = "tx_login_name")
	private String loginName;

	@Column(name = "tx_password")
	private String password;

	@Column(name = "id_user_mod_key")
	private Integer userModKey;

	@Column(name = "dtt_mod")
	private Date dateModified;

	@PreUpdate
	private void onUpdate() {
		dateModified = new Date();
	}

	@PrePersist
	private void onInsert() {
		dateModified = new Date();
	}
}
