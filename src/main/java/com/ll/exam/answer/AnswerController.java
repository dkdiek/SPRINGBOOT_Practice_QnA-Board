package com.ll.exam.answer;

import com.ll.exam.question.Question;
import com.ll.exam.question.QuestionService;
import com.ll.exam.user.SiteUser;
import com.ll.exam.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;


    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createAnswer(Principal principal, @PathVariable int id, Model model, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        SiteUser siteUser = userService.getUser(principal.getName());

        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
