/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "ban", catalog = "coffee_shop", schema = "")
@NamedQueries({
    @NamedQuery(name = "Ban.findAll", query = "SELECT b FROM Ban b")
    , @NamedQuery(name = "Ban.findByBanId", query = "SELECT b FROM Ban b WHERE b.banId = :banId")
    , @NamedQuery(name = "Ban.findBySoBan", query = "SELECT b FROM Ban b WHERE b.soBan = :soBan")
    , @NamedQuery(name = "Ban.findByTrangThai", query = "SELECT b FROM Ban b WHERE b.trangThai = :trangThai")})
public class Ban implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ban_id")
    private Integer banId;
    @Column(name = "so_ban")
    private String soBan;
    @Column(name = "trang_thai")
    private String trangThai;

    public Ban() {
    }

    public Ban(Integer banId) {
        this.banId = banId;
    }

    public Integer getBanId() {
        return banId;
    }

    public void setBanId(Integer banId) {
        Integer oldBanId = this.banId;
        this.banId = banId;
        changeSupport.firePropertyChange("banId", oldBanId, banId);
    }

    public String getSoBan() {
        return soBan;
    }

    public void setSoBan(String soBan) {
        String oldSoBan = this.soBan;
        this.soBan = soBan;
        changeSupport.firePropertyChange("soBan", oldSoBan, soBan);
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        String oldTrangThai = this.trangThai;
        this.trangThai = trangThai;
        changeSupport.firePropertyChange("trangThai", oldTrangThai, trangThai);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banId != null ? banId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ban)) {
            return false;
        }
        Ban other = (Ban) object;
        if ((this.banId == null && other.banId != null) || (this.banId != null && !this.banId.equals(other.banId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.xuanhai.ui.Ban[ banId=" + banId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
