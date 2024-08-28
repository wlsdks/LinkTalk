package com.tony.linktalk.config.jpa;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql == null || sql.trim().isEmpty() || isBatchTableQuery(sql)) {
            return "";
        }

        String formattedSql = formatSql(category, sql);
        String stackTrace = createStackTrace(3);  // 애플리케이션 호출 스택의 마지막 3줄을 가져옴

        return String.format(
                ANSI_CYAN + """
                시간: %s | 카테고리: %s | 소요 시간: %d ms
                JPA 쿼리 시작: ◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼
                %s \n
                JPA 쿼리 끝: ◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼
                %s
                """ + ANSI_RESET,
                now, category.toUpperCase(Locale.ROOT), elapsed, formattedSql, stackTrace
        );
    }

    private boolean isBatchTableQuery(String sql) {
        String lowerCaseSql = sql.toLowerCase(Locale.ROOT);
        return lowerCaseSql.contains("batch_job_instance") ||
                lowerCaseSql.contains("batch_job_execution") ||
                lowerCaseSql.contains("batch_step_execution") ||
                lowerCaseSql.contains("batch_step_execution_context");
    }

    private String formatSql(String category, String sql) {
        if (Category.STATEMENT.getName().equals(category)) {
            String trimmedSql = sql.trim().toLowerCase(Locale.ROOT);
            if (trimmedSql.startsWith("create") || trimmedSql.startsWith("alter") || trimmedSql.startsWith("comment")) {
                return FormatStyle.DDL.getFormatter().format(sql); // DDL 문을 포맷
            } else {
                return FormatStyle.BASIC.getFormatter().format(sql); // 기본 SQL 문을 포맷
            }
        }
        return sql;
    }

    /**
     * 스택 트레이스를 생성하고, 시스템 호출을 제외하고 애플리케이션 호출만 포함
     * @param maxLines 최대 포함할 스택 트레이스 줄 수
     * @return 필터링된 스택 트레이스 문자열
     */
    private String createStackTrace(int maxLines) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder("\nCall Stack:");
        int count = 0;

        // 가장 깊은 스택부터 탐색하고, 시스템 호출을 건너뜀
        for (int i = stackTrace.length - 1; i >= 0 && count < maxLines; i--) {
            String trace = stackTrace[i].toString();
            // 시스템 호출을 제외하고 애플리케이션 관련 호출만 필터링
            if (!isSystemCall(trace) && trace.startsWith("com.tony.linktalk") && !isFilterDenied(trace)) {
                sb.append("\n\t").append(++count).append(". ").append(trace);
            }
        }
        return sb.toString();
    }

    /**
     * 시스템 호출을 감지하는 메서드
     * @param trace 스택 트레이스의 한 줄
     * @return 시스템 호출 여부
     */
    private boolean isSystemCall(String trace) {
        return trace.startsWith("java.base") ||
                trace.startsWith("org.apache.catalina") ||
                trace.startsWith("org.apache.tomcat") ||
                trace.startsWith("org.apache.coyote") ||
                trace.startsWith("org.springframework");
    }

    private boolean isFilterDenied(String trace) {
        List<String> DENIED_FILTER = Arrays.asList("Test1", this.getClass().getSimpleName());
        return DENIED_FILTER.contains(trace);
    }
}
