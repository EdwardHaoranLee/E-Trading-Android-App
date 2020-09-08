package com.example.phase2.comparator;

import java.util.Comparator;

public class IntegersComparator implements Comparator {


    @Override
    /**
     * Compares two integers.
     */
    public int compare(Object o1, Object o2) {
        if(o1.equals(o2)){
            return 0;
        }else if(((Integer) o1) > ((Integer) o2)){
            return 1;
        }
        return -1;
    }
}
