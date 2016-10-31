/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import com.xuanhai.models.Ban;
import com.xuanhai.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class TableRepository {

    public void create(int initial, int count) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.createSQLQuery("truncate table Ban").executeUpdate();

        for (int i = 0; i < count; i++) {
            Ban b = new Ban(Integer.toString(initial + i), Integer.toString(1));
            s.save(b);
        }

        s.getTransaction().commit();
    }

    /**
     * Hàm delete tham khảo
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
