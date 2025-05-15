package com.kun;

import com.alibaba.fastjson2.JSON;
import com.kun.utils.MongoUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessTest {

//    @Autowired
//    private MongoRepository mongoRepository;

    @Autowired
    private MongoUtil mongoUtil;
    @Test
    public void test() {
        User user = new User();
        user.setId(1L);
        user.setUser_id(1111L);
        user.setAge(10);
        User saveUser = mongoUtil.save(user, "user");
        log.info("新增user: {}", JSON.toJSONString(saveUser));

        Query query = new Query();
        Criteria criteria = new Criteria("user_id");
        criteria.is(user.getUser_id());
        query.addCriteria(criteria);

        user.setAge(18);
        Update update = new Update();
        update.set("age", 20);
        mongoUtil.updateMulti(query, update, User.class, "user");
        User one = mongoUtil.findOne(query, User.class, "user");
//        User one = mongoRepository.findOne(query, User.class, "user");
        log.info("one: {}", JSON.toJSONString(one));
    }

    @Data
    static class User {
        private Long id;
        private Long user_id;
        private Integer age;
    }
}
