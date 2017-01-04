package com.centryOf22th.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by louis on 16-10-13.
 */
public class PropertyReader {


    /**
     * the second time will
     * loaded in cache
     */
    private static final Map<String, Map<String, String>> cache = new ConcurrentHashMap<String, Map<String, String>>();

    private static final String path = "/config/";


    /**
     *
     * @param key the key of the file of property's key
     * @param fileName the name of file
     * @return
     */
    public static String get(String key, String fileName) {
        if (cache.containsKey(fileName)) {
            Map<String, String> file = cache.get(fileName);
            return file.get(key);
        } else {
            InputStream in = PropertyReader.class.getResourceAsStream(path + fileName);
            Properties prop = new Properties();
            if (in != null) {
                try {
                    prop.load(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            HashMap<String, String> map = new HashMap<String, String>();
            Set set = prop.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String k = (String) entry.getKey();
                String v = entry.getValue() == null ? null : entry.getValue().toString();
                map.put(k, v);


            }
            cache.put(fileName,map);
            return map.get(key);
        }

    }

}
