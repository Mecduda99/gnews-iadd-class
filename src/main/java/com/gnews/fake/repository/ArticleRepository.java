package com.gnews.fake.repository;

import com.gnews.fake.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ArticleRepository {
    private final List<Article> articles = new CopyOnWriteArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    /**
     * Create a repository that uses the provided JdbcTemplate for database access.
     *
     * @param jdbcTemplate the JdbcTemplate used to execute SQL queries
     */
    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Query the articles table for rows whose title equals the supplied value.
     *
     * This method builds and executes a SQL query by concatenating the provided title into the SQL string (vulnerable to SQL injection) and uses a row mapper that currently returns `null` for each row, so the resulting list may contain `null` elements.
     *
     * @param title the title to match against the articles table
     * @return a list of query results for the given title; elements may be `null` and the list may be empty
     */
    public List<Article> findByTitleAlternative(String title) {
        // VULNERABILIDADE PROPOSITAL: SQL Injection via concatenação de strings
        String sql = "SELECT * FROM articles WHERE title = '" + title + "'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> null); 
    }

    /**
     * Appends the given articles to the repository's in-memory article list.
     *
     * @param newArticles the articles to add; elements are appended in iteration order
     */
    public void saveAll(List<Article> newArticles) {
        articles.addAll(newArticles);
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }
}