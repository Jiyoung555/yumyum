package com.example.yumyum.controller;
import com.example.yumyum.dto.ArticleForm;
import com.example.yumyum.entity.Article;
import com.example.yumyum.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @GetMapping("/articles")
    public String index(Model model){
        Iterable<Article> articleList = articleRepository.findAll();
        model.addAttribute("articles", articleList);

        model.addAttribute("msg", "안녕하세요, 반갑습니다.");
        return "articles/index";
    }

    @GetMapping("/articles/new")
    public String letsgo(){
        return "articles/new";
    }

    //@PostMapping("/articles")
    //public String create(ArticleForm form){
    //    log.info(form.toString());
    //    return "redirect:/articles";
    //}

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "articles/show";
    }

    @GetMapping("/articles/init")
    public String init(){
        List<Article> articleList = new ArrayList();

        for(int i = 0; i < 3; i ++){
            articleList.add( new Article(null, "title" + i, "contents" + i) );
        }

        articleRepository.saveAll(articleList);

        return "redirect:/articles";
    }

    @GetMapping("/articles/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Article entity = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 Article이 없습니다.")
        );

        model.addAttribute("article", entity);

        return "articles/edit";
    }
}
