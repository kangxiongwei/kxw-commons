package org.kxw.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Create by kangxiongwei on 2017/11/25 上午9:56
 */
public class CollectionUtilsTest {

    @Test
    public void testDifferenceList() {
        List list = CollectionUtils.differenceList(Arrays.asList(1,2,3), Arrays.asList(1,3,5));
        Assert.assertArrayEquals(new Integer[] {2}, list.toArray());
    }


}
