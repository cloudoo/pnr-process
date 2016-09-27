package com.csair.loong.service;

import java.util.List;

/**
 * Created by cloudoo on 2016/9/21.
 */
public interface ObjectFactory<T> {
    public T gen(String line);
}
