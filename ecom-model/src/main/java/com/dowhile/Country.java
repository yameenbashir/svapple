package com.dowhile;

// Generated Dec 5, 2017 5:08:49 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Country generated by hbm2java
 */
@Entity
@Table(name = "country")
public class Country implements java.io.Serializable {

	private Integer countryId;
	private String countryName;
	private boolean activeIndicator;
	private Date createdDate;
	private Date lastUpdated;
	private Integer createdBy;
	private Integer updatedBy;
	private Set<ContactGroup> contactGroups = new HashSet<ContactGroup>(0);
	private Set<Address> addresses = new HashSet<Address>(0);

	public Country() {
	}

	public Country(String countryName, boolean activeIndicator,
			Date createdDate, Date lastUpdated) {
		this.countryName = countryName;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
	}

	public Country(String countryName, boolean activeIndicator,
			Date createdDate, Date lastUpdated, Integer createdBy,
			Integer updatedBy, Set<ContactGroup> contactGroups,
			Set<Address> addresses) {
		this.countryName = countryName;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.contactGroups = contactGroups;
		this.addresses = addresses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "COUNTRY_ID", unique = true, nullable = false)
	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Column(name = "COUNTRY_NAME", nullable = false, length = 200)
	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "ACTIVE_INDICATOR", nullable = false)
	public boolean isActiveIndicator() {
		return this.activeIndicator;
	}

	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", nullable = false, length = 19)
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY")
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<ContactGroup> getContactGroups() {
		return this.contactGroups;
	}

	public void setContactGroups(Set<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

}
