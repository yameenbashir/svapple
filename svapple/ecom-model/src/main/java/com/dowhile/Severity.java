package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Severity generated by hbm2java
 */
@Entity
@Table(name="severity"
    ,catalog="ecom"
)
public class Severity  implements java.io.Serializable {


    private Integer severityId;
    private String severityDescription;
    private Set<Ticket> tickets = new HashSet<Ticket>(0);
    private Set<ActivityDetail> activityDetails = new HashSet<ActivityDetail>(0);
    private Set<WebActivityDetail> webActivityDetails = new HashSet<WebActivityDetail>(0);

   public Severity() {
   }

	
   public Severity(String severityDescription) {
       this.severityDescription = severityDescription;
   }
   public Severity(String severityDescription, Set<Ticket> tickets, Set<ActivityDetail> activityDetails, Set<WebActivityDetail> webActivityDetails) {
      this.severityDescription = severityDescription;
      this.tickets = tickets;
      this.activityDetails = activityDetails;
      this.webActivityDetails = webActivityDetails;
   }
  
    @Id @GeneratedValue(strategy=IDENTITY)

   
   @Column(name="SEVERITY_ID", unique=true, nullable=false)
   public Integer getSeverityId() {
       return this.severityId;
   }
   
   public void setSeverityId(Integer severityId) {
       this.severityId = severityId;
   }

   
   @Column(name="SEVERITY_DESCRIPTION", nullable=false, length=256)
   public String getSeverityDescription() {
       return this.severityDescription;
   }
   
   public void setSeverityDescription(String severityDescription) {
       this.severityDescription = severityDescription;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="severity")
   public Set<Ticket> getTickets() {
       return this.tickets;
   }
   
   public void setTickets(Set<Ticket> tickets) {
       this.tickets = tickets;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="severity")
   public Set<ActivityDetail> getActivityDetails() {
       return this.activityDetails;
   }
   
   public void setActivityDetails(Set<ActivityDetail> activityDetails) {
       this.activityDetails = activityDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="severity")
   public Set<WebActivityDetail> getWebActivityDetails() {
       return this.webActivityDetails;
   }
   
   public void setWebActivityDetails(Set<WebActivityDetail> webActivityDetails) {
       this.webActivityDetails = webActivityDetails;
   }




}

