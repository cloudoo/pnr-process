package com.csair.loong.pnr.processor;

/**
 * Created by cloudoo on 2016/9/19.
 */
public abstract class Processor<T,K> {
    public Processor processor;
    public abstract T doit(K k);
    public  void setNext(Processor processor){
        this.processor = processor;
    };
}
