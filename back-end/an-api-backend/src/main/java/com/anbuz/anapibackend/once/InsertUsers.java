package com.anbuz.anapibackend.once;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.anbuz.anapibackend.model.entity.User;
import com.anbuz.anapibackend.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {

    @Resource
    UserService userService;

    public void insertBySave(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i=0; i<=100000; ++i) {
            User user = new User();
            user.setUsername("fakeAnbuz");
            user.setUserAccount("271544");
            user.setAvatarUrl("https://anbuz.oss-cn-guangzhou.aliyuncs.com/123ffd3c-f299-42ef-8bb9-5ad27dacfc68.jpg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123412");
            user.setEmail("123121@qq.com");
            user.setUserStatus(0);
            user.setUserRole(1);
            user.setTags("[\"python\", \"java\", \"男\", \"大一\"]");
            userService.save(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    public void insertBySaveBatch(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<User> users = new ArrayList<User>();

        for (int i=0; i<=100000; ++i) {
            User user = new User();
            user.setUsername("fakeAnbuz");
            user.setUserAccount("271544");
            user.setAvatarUrl("https://anbuz.oss-cn-guangzhou.aliyuncs.com/123ffd3c-f299-42ef-8bb9-5ad27dacfc68.jpg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123412");
            user.setEmail("123121@qq.com");
            user.setUserStatus(0);
            user.setUserRole(1);
            user.setTags("[\"python\", \"java\", \"男\", \"大一\"]");
            users.add(user);
        }
        userService.saveBatch(users, 10000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    public void insertByConcurrencySaveBatch(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<CompletableFuture<Void>> futureList = new ArrayList<>();

        int j = 0;
        for (int i=0; i<=10; ++i) {
            List<User> users = new ArrayList<User>();
            while (true) {
                j++;
                User user = new User();
                user.setUsername("fakeAnbuz");
                user.setUserAccount("271544");
                user.setAvatarUrl("https://anbuz.oss-cn-guangzhou.aliyuncs.com/123ffd3c-f299-42ef-8bb9-5ad27dacfc68.jpg");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("123412");
                user.setEmail("123121@qq.com");
                user.setUserStatus(0);
                user.setUserRole(1);
                user.setTags("[\"python\", \"java\", \"男\", \"大一\"]");
                users.add(user);
                if (j % 10000 == 0) break;
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(users, 10000);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}
