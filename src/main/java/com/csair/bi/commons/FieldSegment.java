package com.csair.bi.commons;

/**
 * Created by cloudoo on 2015/5/4.
 */
public interface FieldSegment {
    public static String separator =",";
    /**
     * 待分割的文本，默认数组第一个存放文本原数据
     * @param words
     * @return
     */
    public String segment(String... words);


}
