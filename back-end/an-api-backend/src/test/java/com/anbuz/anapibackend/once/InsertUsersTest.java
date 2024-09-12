package com.anbuz.anapibackend.once;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class InsertUsersTest {

    @Resource
    InsertUsers insertUsers;

    @Test
    public void testInsertUsers() {
//        insertUsers.insertBySave();
        insertUsers.insertBySaveBatch();
        insertUsers.insertByConcurrencySaveBatch();
    }

}
