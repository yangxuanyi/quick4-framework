package org.quick4.framework.test;

import org.quick4.framework.utils.ClassUtil;

import java.util.Set;

public class PackageTest {


    public static void main(String [] args){
        Set classSet = ClassUtil.getClassSet("org.quick4.framework.utils");

        System.out.println(classSet);

    }
}
