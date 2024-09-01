package com.tony.linktalk.adapter.out.persistence.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostDslRepository {

    private final JPAQueryFactory queryFactory;

}
