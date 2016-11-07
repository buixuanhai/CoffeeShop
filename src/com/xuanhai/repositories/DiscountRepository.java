/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.GiamGia;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Admin
 */
public class DiscountRepository implements IRepository<GiamGia> {

    @Override
    public List<GiamGia> get() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<GiamGia> results = null;

        results = s.createCriteria(GiamGia.class).list();

        s.getTransaction().commit();
        return results;
    }

    @Override
    public GiamGia get(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<GiamGia> discounts = s.createCriteria(GiamGia.class).add(Restrictions.eq("giamGiaId", id)).list();

        s.getTransaction().commit();

        if (discounts.isEmpty()) {
            return null;
        } else {
            return discounts.get(0);
        }
    }

    @Override
    public int create(GiamGia object) {
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
        GiamGia discount = this.get(id);
        if (discount == null) {
            return -1;
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.delete(discount);
            s.getTransaction().commit();
            return id;
        }
    }

    @Override
    public int update(GiamGia object) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.update(object);
            s.getTransaction().commit();

            return object.getGiamGiaId();
        } catch (HibernateException e) {
            return -1;
        }
    }

}
