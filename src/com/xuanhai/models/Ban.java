package com.xuanhai.models;
// Generated Oct 11, 2016 5:24:08 PM by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Ban generated by hbm2java
 */
@Entity
@Table(name = "ban", catalog = "coffee_shop")
public class Ban implements java.io.Serializable {

    private int banId;
    private String soBan;
    private boolean conTrong;

    public Ban() {
    }

    public Ban(String soBan, boolean trangThai) {
        this.soBan = soBan;
        this.conTrong = trangThai;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id", unique = true, nullable = false)
    public int getBanId() {
        return this.banId;
    }

    public void setBanId(int banId) {
        this.banId = banId;
    }

    @Column(name = "so_ban", unique = true, nullable = false)
    public String getSoBan() {
        return this.soBan;
    }

    public void setSoBan(String soBan) {
        this.soBan = soBan;
    }

    @Column(name = "con_trong", unique = false, nullable = false)
    @Type(type = "yes_no")
    public boolean getConTrong() {
        return this.conTrong;
    }

    public void setConTrong(boolean trangThai) {
        this.conTrong = trangThai;
    }

    @Override
    public String toString() {
//        return !conTrong ? soBan : soBan + " (còn trống)";

        if (conTrong) {
            return soBan + " (còn trống)";
        } else {
            return soBan;
        }
    }

}
