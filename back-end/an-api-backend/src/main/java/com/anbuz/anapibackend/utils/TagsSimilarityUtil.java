package com.anbuz.anapibackend.utils;

import java.util.List;

public class TagsSimilarityUtil {

    /**
     * 基于标签共现的相似度计算
     */
    public static Integer TagCoOccurrence1(List<String> tags1, List<String> tags2) {
        Integer tagCoOccurrence = 0;
        for (String tag : tags1) {
            if (tags2.contains(tag)) {
                tagCoOccurrence++;
            }
        }
        return tagCoOccurrence;
    }
}
