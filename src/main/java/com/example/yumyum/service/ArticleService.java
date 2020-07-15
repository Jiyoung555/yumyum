package com.example.yumyum.service;
import com.example.yumyum.dto.ArticleForm;
import com.example.yumyum.entity.Article;
import com.example.yumyum.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Article update(Long id, ArticleForm form) {
        // db에서 id로 뒤져서 원래 데이터 소환하기 (엔티티)
        Article target = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 Article이 없습니다.")
        );

        // 소환한 데이터를 -> 수정된 form 데이터로 -> 엔티티값 수정하기
        target.rewrite(form.getTitle(), form.getContent());
        log.info("target: " + target.toString());

        // 수정한 엔티티값을 -> db에 다시 저장하기
        Article saved = articleRepository.save(target);
        log.info("saved: " + saved.toString());

        return saved;
    }
}

