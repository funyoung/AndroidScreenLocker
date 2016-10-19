package com.github.funyoung.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangfeng on 16-10-19.
 */

public class ArticleCharModel {
    private final List<Integer> uniqCharList = new ArrayList<>();
    private final Map<Integer, List<Integer>> indexMapByChar = new HashMap<>();

    public void reset() {
        uniqCharList.clear();
        indexMapByChar.clear();
    }

    public void add(int index, Integer charValue) {
        List<Integer> indexList = indexMapByChar.get(charValue);
        if (null == indexList) {
            indexList = new ArrayList<>();
            indexMapByChar.put(charValue, indexList);
            uniqCharList.add(charValue);
        }

        assert (!indexList.contains(index));
        indexList.add(index);
    }

    public String charList() {
        Integer[] byteArray = uniqCharList.toArray(new Integer[uniqCharList.size()]);
        return String.valueOf(byteArray);
    }

    public String indexInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        int total = 0;
        for (int value : uniqCharList) {
            int count = indexMapByChar.get(value).size();
            stringBuffer.append((char)value).append(count);
            total += count;
        }
        stringBuffer.append(":").append(total);
        return stringBuffer.toString();
    }
}
