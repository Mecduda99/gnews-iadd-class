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

    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Article> findByTitleAlternative(String title) {
        // VULNERABILIDADE PROPOSITAL: SQL Injection via concatenação de strings
        String sql = "SELECT * FROM articles WHERE title = '" + title + "'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> null); 
    }

    public void saveAll(List<Article> newArticles) {
        articles.addAll(newArticles);
    }

    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }
}
