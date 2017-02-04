package com.example.pragya.ilovezappos;

import java.util.List;

/**
 * Created by pragya on 2/4/2017.
 */

public class ZapposItemList {
    List<ZapposItem> results;

    int currentResultCount;

    int totalResultCount;

    @Override
    public String toString() {
        return "" + totalResultCount + currentResultCount;
    }
}
