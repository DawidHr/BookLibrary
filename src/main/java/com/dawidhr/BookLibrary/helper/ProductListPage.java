package com.dawidhr.BookLibrary.helper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ProductListPage {

    public static Integer[] listSizesAvailable = {2, 5, 10, 15, 20};
    public static final Integer DEFAULT_PER_PAGE = 5;

    public static boolean isPageSizeAvailable(int listSize) {
        return Arrays.asList(listSizesAvailable).contains(listSize);
    }

    public static List<Integer> preparePagination(long productsCount, long productSizeList) {
        List<Integer> pagination = new LinkedList<>();
        if (productsCount>0) {
            int pages = (int) Math.ceil((double) productsCount/ (double) productSizeList);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
        return pagination;
    }
}
