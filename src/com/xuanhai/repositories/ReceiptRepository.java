/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.HoaDon;
import com.xuanhai.models.NhanVien;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class ReceiptRepository implements IRepository<HoaDon> {

    @Override
    public List<HoaDon> get() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<HoaDon> results = null;

        results = s.createCriteria(HoaDon.class).list();

        s.getTransaction().commit();
        return results;
    }

    @Override
    public HoaDon get(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<HoaDon> receipts = s.createCriteria(HoaDon.class).add(Restrictions.eq("hoaDonId", id)).list();

        s.getTransaction().commit();

        if (receipts.isEmpty()) {
            return null;
        } else {
            return receipts.get(0);
        }
    }
    

    @Override
    public int create(HoaDon object) {
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
        HoaDon hoaDon = this.get(id);
        if (hoaDon == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(hoaDon);
            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public int update(HoaDon object) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(object);
            s.getTransaction().commit();

            return object.getHoaDonId();
        } catch (HibernateException e) {
            return -1;
        }
    }

}
