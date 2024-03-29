package com.ll.exam.question;

import com.ll.exam.answer.AnswerForm;
import com.ll.exam.user.SiteUser;
import com.ll.exam.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String list(String kw, Model model, @RequestParam(defaultValue = "0") int page) {


        Page<Question> paging = questionService.getList(kw, page);
        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

}
