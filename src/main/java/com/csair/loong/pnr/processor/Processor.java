package com.csair.loong.pnr.processor;

/**
 * Created by cloudoo on 2016/9/19.
 */
public interface Processor<T,K> {
    public  T doit(K k);
}
