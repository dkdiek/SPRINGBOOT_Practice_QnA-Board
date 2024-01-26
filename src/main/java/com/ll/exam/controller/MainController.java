package com.ll.exam.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private int increaseNo = -1;

    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("성공");
        return "abc";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <h1>안녕하세요</h1>
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이 입력" />
                    <input type="submit" value="page2로 POST이동" />
                </form>
                """;
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPageGet(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이:%d</h1>
                <h1>get방식으로 왔습니다</h1>
                """.formatted(age);
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPagePost(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이:%d</h1>
                <h1>post방식으로 왔습니다</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, int b) {
        return a + b;
    }

//    @GetMapping("/plus2") 기존 서블릿 방식
//    @ResponseBody
//    public void showPlus2(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        int a = Integer.parseInt(req.getParameter("a"));
//        int b = Integer.parseInt(req.getParameter("b"));
//        res.getWriter().append(a+b+"");
//    }

    @GetMapping("/increase")
    @ResponseBody
    public int increaseNumber() {
        increaseNo++;
        return increaseNo;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(Integer dan, Integer limit) {
        if (dan == null) {
            dan = 9;
        }
        if (limit == null) {
            limit = 9;
        }

        final Integer finalDan = dan;
        //스트림 사용 시 외부 객체 형태 타입은 Final선언이 필요함

        return IntStream.rangeClosed(1, limit)//stream 반복문
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));//br기준으로 하나의 스트림으로 생성
    }

    @GetMapping("/mbti/{name}") // ?말고 /로 받으려면 PathVariable 부트가능
    @ResponseBody
    public String showMbti(@PathVariable String name) {
       return switch (name) {
            case "홍길순" -> {
                yield  "INFJ"; //안쪽 return = yield
            }
            case "임꺽정" -> "INFJ";
            case "홍길동", "임연수" -> "INFP"; //name을 묶어서 처리 가능
            default -> "모름";
        };

    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s로 설정되었습니다".formatted(name, value);
    }
    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String saveSession(@PathVariable String name, HttpSession session) { //req말고 session으로 바로 뺄수있음

        //req => 쿠키생성 => JSESSIONID => 세션을 얻을수있다
        String value= (String)session.getAttribute(name);

        return "세션변수 %s의 값은 %s입니다".formatted(name, value);
    }


    private List<Article> articles = new ArrayList<>(
            Arrays.asList(
                    new Article("제목","내용"),
                    new Article("제목","내용")
            )
    );


    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) { //req말고 session으로 바로 뺄수있음

        Article article = new Article(title, body);
        articles.add(article);

        return "%d번 게시물이 생성되었습니다".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticles(@PathVariable int id) { //req말고 session으로 바로 뺄수있음

        Article article = articles //id가 1번이 게시물이 앞에서 3번째 있으면 3번만 실행
                    .stream()
                    .filter(a -> a.getId() == id)
                    .findFirst() //찾은 것 중에 첫번째 것
                    .orElse(null); //못찾으면 null을 넣어줌 .get()은 오류남

        return article;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body) { //req말고 session으로 바로 뺄수있음

        Article article = articles //id가 1번이 게시물이 앞에서 3번째 있으면 3번만 실행
                    .stream()
                    .filter(a -> a.getId() == id)
                    .findFirst() //찾은 것 중에 첫번째 것
                    .orElse(null); //못찾으면 null을 넣어줌 .get()은 오류남

        if(article == null){
            return "%d번 게시물은 존재하지 않습니다".formatted(id);
        }

        article.setTitle(title);
        article.setBody(body);

        return "%d번 게시물을 수정하였습니다".formatted(id);
    }

    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id, String title, String body) { //req말고 session으로 바로 뺄수있음

        Article article = articles //id가 1번이 게시물이 앞에서 3번째 있으면 3번만 실행
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst() //찾은 것 중에 첫번째 것
                .orElse(null); //못찾으면 null을 넣어줌 .get()은 오류남

        if(article == null){
            return "%d번 게시물은 존재하지 않습니다".formatted(id);
        }

        articles.remove(article);
        

        return "%d번 게시물을 삭제하였습니다".formatted(id);
    }

    @AllArgsConstructor //모든 항목 생성자 alt+7 생성자 보기
    @Getter
    @Setter
    public class Article {

        private static int lastId=0;
        private int id;
        private String title;
        private String body;

        public Article(String title, String body){
            this(++lastId, title, body);
        }
    }


    @GetMapping("/addPersonOldWay")
    @ResponseBody
    public Person addPersonOldWay(int id, int age, String name) {
        Person p = new Person(id, age, name);
        return p;
    }

    @GetMapping("/addPerson") //위에 것이랑 똑같음 param으로 값 넣어주면 된다
    @ResponseBody
    public Person addPerson(Person p) {
        return p;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    class Person{
        private int id;
        private int age;
        private String name;
    }

}
