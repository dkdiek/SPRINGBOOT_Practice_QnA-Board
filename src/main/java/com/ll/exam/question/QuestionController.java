package com.ll.exam.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor// 생성자 주입 final을 자동으로 autowired시킴
//컨트롤러는 repository가 있는지 몰라야한다
//서비스는 브라우저라는 것이 이 세상에 존재하는지 몰라야 한다.
//리포지터리는 서비스가 이 세상이 있는지 몰라야 한다.
//서비스는 컨트롤러를 몰라야 한다.
//db는 리포지터리를 몰라야 한다.
//SPRING DATA JPA는 MySQL을 몰라야 한다.
//SPRING DATA JPA(리포지터리) -> JPA ->하이버네이트 ->jdbc -> mysql driver -> mysql
//단방향 하나를 건너 뛰고 처리하는건 안된다
public class QuestionController {
    //@AutoWired 필드 주입
    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }

}
