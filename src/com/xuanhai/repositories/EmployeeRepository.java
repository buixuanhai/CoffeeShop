/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.NhanVien;
import com.xuanhai.models.SanPham;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EmployeeRepository implements IEmployeeRepository<NhanVien> {

    @Override
    public List<NhanVien> get() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<NhanVien> results = null;

        results = s.createCriteria(NhanVien.class).list();

        s.getTransaction().commit();
        return results;
    }

    @Override
    public NhanVien get(int id) {
       Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<NhanVien> employees = s.createCriteria(NhanVien.class).add(Restrictions.eq("nhanVienId", id)).list();

        s.getTransaction().commit();

        if (employees.isEmpty()) {
            return null;
        } else {
            return employees.get(0);
        }
    }

    @Override
    public int create(NhanVien object) {
        if (object == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            int id = (int) s.save(object);
            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public int delete(int id) {
         NhanVien nv = this.get(id);
        if (nv == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(nv);
            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public int update(NhanVien object) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(object);
            s.getTransaction().commit();

            return object.getNhanVienId();
        } catch (HibernateException e) {
            return -1;
        }
    }

}
