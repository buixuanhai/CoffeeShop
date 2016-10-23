/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xuanhai.repositories;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface IRepository<T> {

    List<T> get();

    T get(int id);

    int create(T object);

    int delete(int id);

    int update(T object);
}
