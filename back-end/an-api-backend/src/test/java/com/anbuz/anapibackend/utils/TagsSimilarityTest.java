package com.anbuz.anapibackend.utils;

import com.anbuz.anapicommon.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TagsSimilarityTest {

    @Test
    public void testTagsSimilarity() {

        List<String> tags1 = Arrays.asList("大一", "男", "python", "dqwiu", "wduoq", "7861y", "gd1", "37g1d", "bdvt831vd3");
        List<String> tags2 = Arrays.asList("python", "男", "大一", "df38yv1", "udg13", "613f8vy1f");

        Long start = System.currentTimeMillis();
        Integer tagCoOccurrence = 0;
        for (int i = 0; i < 10000000; i++) {
            tagCoOccurrence = TagsSimilarityUtil.TagCoOccurrence1(tags1, tags2);
        }
        Long end1 = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            tagCoOccurrence = TagsSimilarityUtil.TagCoOccurrence2(tags1, tags2);
//        }
//        Long end2 = System.currentTimeMillis();
        System.out.println(end1 - start);
//        System.out.println(end2 - end1);
        System.out.println(tagCoOccurrence);
    }

    @Test
    public void testGson() {
        Gson gson = new Gson();
        String tags = "[\"大一\", \"python\", \"java\"]";
        TypeToken<List<String>> token = new TypeToken<List<String>>() {};
        List<String> list = gson.fromJson(tags, token);
        System.out.println(list);
        System.out.println(list.get(1));
    }

    @Test
    public void test() {
        Gson gson = new Gson();
        String tags = "[\"女\", \"研二\", \"产品\"]";
        String tags1 = "[\"python\", \"java\", \"女\", \"大一\"]";
        String tags2 = "[\"女\", \"研二\", \"产品\", \"大二\"]";
        User user1 = new User();
        user1.setTags(tags1);
        user1.setId(1L);
        User user2 = new User();
        user2.setTags(tags2);
        user2.setId(2L);
        ArrayList<User> userList = new ArrayList<>(Arrays.asList(user1, user2));
        TypeToken<List<String>> token = new TypeToken<List<String>>() {};
        List<String> tagList = gson.fromJson(tags, token);

        Comparator<Integer> userIndexComparator = (a, b) -> {
            List<String> tagListA = gson.fromJson(userList.get(a).getTags(), token);
            List<String> tagListB = gson.fromJson(userList.get(b).getTags(), token);
            Integer scoreA = TagsSimilarityUtil.TagCoOccurrence1(tagList, tagListA);
            Integer scoreB = TagsSimilarityUtil.TagCoOccurrence1(tagList, tagListB);
            return Integer.compare(scoreA, scoreB);
        };

        System.out.println(userIndexComparator.compare(0, 1));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(2, userIndexComparator);
        minHeap.add(0);
        minHeap.add(1);
        System.out.println(minHeap);

        List<Long> idList = Arrays.stream(minHeap.toArray()).map(
                (a)->userList.get(((Integer)a)).getId()).collect(Collectors.toList());
        Collections.reverse(idList);
        System.out.println(idList);
    }


}
