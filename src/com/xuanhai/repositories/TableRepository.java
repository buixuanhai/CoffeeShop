/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.Ban;
import com.xuanhai.models.NhanVien;
import com.xuanhai.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Admin
 */
public class TableRepository {

    public List<Ban> get() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<Ban> results = null;

        results = s.createCriteria(Ban.class).list();

        s.getTransaction().commit();
        return results;
    }

    public void create(int initial, int count) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.createSQLQuery("truncate table Ban").executeUpdate();

        for (int i = 0; i < count; i++) {
            Ban b = new Ban(Integer.toString(initial + i), true);
            s.save(b);
        }

        s.getTransaction().commit();
    }

    public int count() {
        Session s = HibernateUtil.getSessionFactory().openSession();

        s.beginTransaction();

        Number count = (Number) s.createCriteria(Ban.class).setProjection(Projections.rowCount()).uniqueResult();

        s.getTransaction().commit();

        return count.intValue();
    }

    public int getFirstTableId() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        Ban b = (Ban) s.createCriteria(Ban.class).setMaxResults(1).list().get(0);

        s.getTransaction().commit();

        return Integer.parseInt(b.getSoBan());
    }

    public int getLastTableId() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Ban b = (Ban) s.createCriteria(Ban.class).addOrder(Order.desc("banId")).setMaxResults(1).list().get(0);

        s.getTransaction().commit();

        return Integer.parseInt(b.getSoBan());
    }

    /**
     * Hàm delete tham khảo
     *
     * @return
     */
    public int delete() {

        String hql = "delete from Ban";

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        Query query = s.createQuery(hql);
        int rowsUpdated = query.executeUpdate();

        s.getTransaction().commit();

        return rowsUpdated;
    }
}
