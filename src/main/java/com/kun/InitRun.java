package com.kun;

import com.kun.common.exception.BusinessException;
import com.kun.utils.RedisUtil;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Component
public class InitRun implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    // 项目启动时执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("项目启动成功");
        try {
            // 获取数据库链接
            dataSource.getConnection();
            log.info("链接的数据库名称：{}", dataSource.getConnection().getMetaData().getDatabaseProductName());
            // Redis链接测试
            if (redisUtil.hasKey("test")) {
                log.info("Redis链接成功");
            }
            // Mongo链接测试
            if (mongoTemplate.collectionExists(mongoTemplate.getDb().getName())) {
                log.info("Mongo链接成功：链接的数据库名称：{}", mongoTemplate.getDb().getName());
            }
        } catch (SQLException e) {
            log.error("数据库链接失败");
            throw new BusinessException(400, "数据库链接失败");
        } catch (RedisException e) {
            log.error("Redis链接失败");
            throw new BusinessException(400, "Redis链接失败");
        } catch (Exception e) {
            log.error("未知错误");
        }
    }
}
