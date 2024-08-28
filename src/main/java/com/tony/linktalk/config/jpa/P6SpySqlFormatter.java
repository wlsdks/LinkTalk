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
import java.util.regex.Pattern;

/**
 * P6Spy SQL 포맷터
 */
@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {

    public static final int LOG_BOUND_BLOCK_ICON_COUNT = 150;
    public static final int MAX_STACK_TRACE_DEPTH = 3;

    // ANSI 컬러 코드를 사용하여 콘솔 출력에 색상을 추가합니다.
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static final Pattern SYSTEM_CALL_PATTERN = Pattern.compile(
            "^(java.base|org.apache.catalina|org.apache.tomcat|org.apache.coyote|org.springframework)");
    private static final List<String> DENIED_FILTER = Arrays.asList("Test1", P6SpySqlFormatter.class.getSimpleName());


    // 빈이 초기화된 후 P6Spy의 로그 메시지 포맷을 설정합니다.
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }


    /**
     * @param connectionId 연결 ID
     * @param now          현재 시간
     * @param elapsed      작업 소요 시간
     * @param category     로그 카테고리
     * @param prepared     준비된 SQL 문
     * @param sql          SQL 쿼리
     * @param url          데이터베이스 URL
     * @return 포맷된 로그 메시지
     * @apiNote SQL 로그 메시지를 커스텀 형식과 스택 트레이스 필터링을 적용하여 포맷합니다.
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        // SQL이 비어있거나 배치 작업과 관련된 경우 로그를 출력하지 않습니다.
        if (sql == null || sql.trim().isEmpty() || isBatchTableQuery(sql)) {
            return "";
        }

        String formattedSql = formatSql(category, sql);
        String stackTrace = createStackTrace(MAX_STACK_TRACE_DEPTH); // 애플리케이션 호출 스택의 마지막 3줄을 가져옵니다.
        String border = "◼".repeat(LOG_BOUND_BLOCK_ICON_COUNT); // 로그 구분을 위한 반복 경계 문자열을 정의합니다.

        // 포맷된 로그 메시지를 생성하고 반환합니다.
        return String.format(
                ANSI_CYAN + """
                        시간: %s | 카테고리: %s | 소요 시간: %d ms
                        쿼리 시작: %s
                        """ + ANSI_GREEN + "%s\n" + ANSI_CYAN + """
                        \n쿼리 끝: %s
                        %s
                        """ + ANSI_RESET + "\nCall Stack 설명 끝: %s",
                now, category.toUpperCase(Locale.ROOT), elapsed, border, formattedSql, border, stackTrace, border
        );
    }


    /**
     * @param sql 확인할 SQL 쿼리
     * @return 배치 처리와 관련된 SQL인지 여부
     * @apiNote SQL 쿼리가 배치 처리 테이블과 관련된지 확인하여, 로그에서 제외할지 여부를 결정합니다.
     */
    private boolean isBatchTableQuery(String sql) {
        String lowerCaseSql = sql.toLowerCase(Locale.ROOT);
        return lowerCaseSql.contains("batch_job_instance") ||
                lowerCaseSql.contains("batch_job_execution") ||
                lowerCaseSql.contains("batch_step_execution") ||
                lowerCaseSql.contains("batch_step_execution_context");
    }


    /**
     * @param category SQL 문의 카테고리
     * @param sql      포맷할 SQL 쿼리
     * @return 포맷된 SQL 문자열
     * @apiNote SQL 문의 카테고리에 따라 SQL 문을 포맷합니다 (예: DDL 또는 DML).
     */
    private String formatSql(String category, String sql) {
        if (Category.STATEMENT.getName().equals(category)) {
            return formatStatement(sql);
        }

        // 카테고리가 'STATEMENT'가 아닌 경우 원본 SQL 반환
        return sql;
    }


    /***
     * @apiNote SQL 문의 카테고리에 따라 SQL 문을 포맷합니다 (예: DDL 또는 DML).
     * @param sql 포맷할 SQL 쿼리
     * @return 포맷된 SQL 문자열
     */
    private String formatStatement(String sql) {
        String trimmedSql = sql.trim().toLowerCase(Locale.ROOT);

        if (trimmedSql.startsWith("create") || trimmedSql.startsWith("alter") || trimmedSql.startsWith("comment")) {
            return FormatStyle.DDL.getFormatter().format(sql); // DDL 문을 포맷
        }

        return FormatStyle.BASIC.getFormatter().format(sql); // DML 또는 기타 기본 SQL 문을 포맷
    }


    /**
     * @param maxLines 포함할 스택 트레이스의 최대 줄 수
     * @return 필터링된 스택 트레이스 문자열
     * @apiNote 필터링된 스택 트레이스를 생성하여 시스템 호출을 제외하고 애플리케이션 호출만 포함합니다.
     */
    private String createStackTrace(int maxLines) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder("\nCall Stack:");
        int count = 0;

        for (int i = stackTrace.length - 1; i >= 0 && count < maxLines; i--) {
            String trace = stackTrace[i].toString();

            if (!isSystemCall(trace) && trace.startsWith("com.tony.linktalk") && !isFilterDenied(trace)) {
                sb.append("\n\t").append(++count).append(". ").append(trace);
            }
        }

        return sb.toString();
    }


    /**
     * @param trace 스택 트레이스의 한 줄
     * @return 시스템 호출 여부
     * @apiNote 시스템 호출을 감지하는 메서드
     */
    private boolean isSystemCall(String trace) {
        return SYSTEM_CALL_PATTERN.matcher(trace).find();
    }


    /**
     * @param trace 스택 트레이스의 한 줄
     * @return 필터링된 목록에 포함 여부
     * @apiNote 특정 호출이 필터링된 목록에 포함되어 있는지 확인하는 메서드
     */
    private boolean isFilterDenied(String trace) {
        return DENIED_FILTER.contains(trace);
    }

}
