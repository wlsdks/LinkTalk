package com.tony.linktalk.config.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 스프링 부트 애플리케이션이 시작된 후 데이터베이스 연결을 확인하는 클래스
 */
@Slf4j
@Component
public class DatabaseConnectionChecker implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseConnectionChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 데이터베이스 연결 확인
            entityManager.createQuery("SELECT 1").getSingleResult();
            log.info("Database connection is OK!");

            // 데이터베이스 연결 풀 상태 확인
            DataSource dataSource = jdbcTemplate.getDataSource();
            if (dataSource != null) {
                log.info("DataSource class: {}", dataSource.getClass().getName());
            } else {
                log.warn("DataSource not found!");
            }

//            // Redis 연결 확인
//            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
//            log.info("Redis connection is OK! Response: " + pong);

        } catch (Exception e) {
            log.error("Error occurred during startup checks!", e);
        }
    }

}
