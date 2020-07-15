package com.example.yumyum.api;
import com.example.yumyum.dto.ArticleForm;
import com.example.yumyum.entity.Article;
import com.example.yumyum.repository.ArticleRepository;
import com.example.yumyum.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public Long create(@RequestBody ArticleForm form){
        log.info(form.toString());

        Article article = form.toEntity();

        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return saved.getId();
    }

    @GetMapping("/api/articles/{id}")
    public ArticleForm getArticle(@PathVariable Long id){
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 Article이 없습니다.")
        );

        ArticleForm dto = new ArticleForm(article);

        return dto;
    }

    @GetMapping("/api/articles")
    public List<ArticleForm> getArticles(){
        Iterable<Article> articles= articleRepository.findAll();

        List<ArticleForm> articleFormList = new ArrayList();

        for(Article article : articles){
            articleFormList.add( new ArticleForm(article) );
        }

        return articleFormList;
    }

    @PutMapping("/api/articles/{id}")
    public Long update(@PathVariable Long id, @RequestBody ArticleForm form){
        // 수정 폼 한번 로그로 확인
        log.info("form: " + form.toString());

        Article saved = articleService.update(id, form);

        return saved.getId();
    }



}
