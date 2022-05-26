package com.example.dusTmq.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//TODO: 반드시 QueryDsl에서 쿼리문 작성할때 반드시 설정해주고 넘어가야함.
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return  new JPAQueryFactory(em);
    }
}
