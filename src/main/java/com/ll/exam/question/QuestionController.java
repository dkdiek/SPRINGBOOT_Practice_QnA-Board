package com.ll.exam.question;

import com.ll.exam.answer.AnswerForm;
import com.ll.exam.user.SiteUser;
import com.ll.exam.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

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
@RequestMapping("/question")
public class QuestionController {
    //@AutoWired 필드 주입
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(HttpSession session, Model model, @RequestParam(defaultValue = "0") int page) {
        Object o = session.getAttribute("SPRING_SECURITY_CONTEXT");
        System.out.println(o);

        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable int id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);

        model.addAttribute("question",question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()") // 실행하기 전에 권한 체크
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(Principal principal, @Valid QuestionForm questionForm, BindingResult bindingResult) {
        SiteUser siteUser = userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

}
