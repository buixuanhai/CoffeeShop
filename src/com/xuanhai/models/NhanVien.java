package com.xuanhai.models;
// Generated Oct 11, 2016 5:24:08 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * NhanVien generated by hbm2java
 */
public class NhanVien  implements java.io.Serializable {


     private int nhanVienId;
     private String hoTen;
     private Date ngaySinh;
     private Date ngayVaoLam;

    public NhanVien() {
    }

	
    public NhanVien(int nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
    public NhanVien(int nhanVienId, String hoTen, Date ngaySinh, Date ngayVaoLam) {
       this.nhanVienId = nhanVienId;
       this.hoTen = hoTen;
       this.ngaySinh = ngaySinh;
       this.ngayVaoLam = ngayVaoLam;
    }
   
    public int getNhanVienId() {
        return this.nhanVienId;
    }
    
    public void setNhanVienId(int nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
    public String getHoTen() {
        return this.hoTen;
    }
    
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public Date getNgaySinh() {
        return this.ngaySinh;
    }
    
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public Date getNgayVaoLam() {
        return this.ngayVaoLam;
    }
    
    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }




}


