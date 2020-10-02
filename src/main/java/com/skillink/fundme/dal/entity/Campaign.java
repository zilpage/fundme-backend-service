package com.skillink.fundme.dal.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tbl_campaign")
public class Campaign implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long userId;
	@Column(name="goal", columnDefinition="Decimal(20,2) default '00.00'")
	private double goal;
    private String title;
    private String ownership;
    private String category;
    private String raiseAs;
    private String coverPhoto;
    private String story;
    private boolean enabled;
    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;
    @Column(name = "modified_at")
    private java.sql.Timestamp modifiedAt;

    
    @PrePersist
    public void prePersist() {
    	createdAt = new Timestamp(new Date().getTime());
    }
 
    @PreUpdate
    public void preUpdate() {
    	modifiedAt = new Timestamp(new Date().getTime());
    }
    
	
	
	
}
