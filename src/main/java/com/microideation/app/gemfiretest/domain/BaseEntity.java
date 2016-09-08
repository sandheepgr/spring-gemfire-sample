package com.microideation.app.gemfiretest.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by sandheepgr on 31/5/16.
 */
@MappedSuperclass
public class BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    protected Long id;

    @Column(name = "VERSION")
    @Version
    private Long version;

    @Column(name = "UID", unique = true, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String uid;

    @Column(name = "PASSIVE", nullable = false, columnDefinition = "boolean default false")
    private boolean passive;

    @Column(name = "created_at",updatable =  false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Size(max = 20)
    @Column(name = "created_by", length = 20,updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Size(max = 20)
    @Column(name = "updated_by", length = 20)
    private String updatedBy;


    @PrePersist
    public void init() {
        this.uid = UUID.randomUUID().toString();
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
