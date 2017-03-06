package com.csair.loong.service;

import com.csair.loong.pnr.processor.Processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloudoo on 2016/9/22.
 */
public abstract class TxtFormaterService<T> {

    private Processor<List<T>,T> processor;
    private ObjectFactory<T> objectFactory;
    private List<T> objects;

    public abstract boolean isTitle(String str);

    public abstract boolean isTail(String str);

    public abstract boolean isSegStart(String str);

    public void process(String line) {
        T object = objectFactory.gen(line);
        if (isSegStart(line) || isTail(line)) {

            processor.doit(objects);

        } else {
            objects = new ArrayList<>();
            objects.add(object);
        }
    }
}
