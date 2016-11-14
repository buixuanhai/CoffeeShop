/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.ChiTietHoaDon;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class ReceiptDetailRepository implements IRepository<ChiTietHoaDon> {

    @Override
    public List<ChiTietHoaDon> get() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<ChiTietHoaDon> results = null;

        results = s.createCriteria(ChiTietHoaDon.class).list();

        s.getTransaction().commit();
        return results;
    }

    public List<ChiTietHoaDon> getByReceiptId(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<ChiTietHoaDon> receiptDetails = s.createCriteria(ChiTietHoaDon.class).add(Restrictions.eq("hoaDonId", id)).list();

        s.getTransaction().commit();

        if (receiptDetails.isEmpty()) {
            return null;
        } else {
            return receiptDetails;
        }
    }

    @Override
    public ChiTietHoaDon get(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<ChiTietHoaDon> receiptDetails = s.createCriteria(ChiTietHoaDon.class).add(Restrictions.eq("chiTietHoaDonId", id)).list();

        s.getTransaction().commit();

        if (receiptDetails.isEmpty()) {
            return null;
        } else {
            return receiptDetails.get(0);
        }
    }

    @Override
    public int create(ChiTietHoaDon object) {
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
        ChiTietHoaDon nv = this.get(id);
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
    public int update(ChiTietHoaDon object) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(object);
            s.getTransaction().commit();

            return object.getChiTietHoaDonId();
        } catch (HibernateException e) {
            return -1;
        }
    }

}
