package com.centryOf22th.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by louis on 16-10-31.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/applicationContext-core-test.xml",
        "classpath*:/applicationContext-resource-test.xml"})
public  class BaseTest {
}
