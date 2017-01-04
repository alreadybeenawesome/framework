package com.centryOf22th.framework.utils;

import com.centryOf22th.framework.exception.WithoutAnnotationException;
import com.centryOf22th.framework.orm.assist.DbAssistant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by louis on 16-11-2.
 */
public class AssertUtil {
    private static Logger logger = LogManager.getLogger(AssertUtil.class);


    /**
     *
     * @param annotation
     * @param clzss the entity of class
     * might throw a WithoutAnnotationException if the annotation is null
     */
    public static void isWithAnnotation(Object annotation, Class clzss) {
        if (annotation == null) {
            logger.error(clzss.getName() + " can not without config annotation at the related place");
            throw new WithoutAnnotationException(clzss.getName() + " can not without config annotation at the related place");
        }
    }
}
