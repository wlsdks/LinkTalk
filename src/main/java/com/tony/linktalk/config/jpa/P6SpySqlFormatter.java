package com.tony.linktalk.config.jpa;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {

    @PostConstruct
    public void setLogMessageFormat() {
        // Set the custom formatter for P6Spy
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql == null || sql.trim().isEmpty() || isBatchTableQuery(sql)) {
            return "";
        }

        String formattedSql = formatSql(category, sql);

        return String.format(
                """
                Time: %s | Category: %s | Elapsed: %d ms
                JPA QUERY START: ◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼
                %s \n
                JPA QUERY END: ◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼
                """,
                now, category.toUpperCase(Locale.ROOT), elapsed, formattedSql
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
                return FormatStyle.DDL.getFormatter().format(sql); // Format DDL statements
            } else {
                return FormatStyle.BASIC.getFormatter().format(sql); // Format basic SQL statements
            }
        }
        return sql;
    }

}
