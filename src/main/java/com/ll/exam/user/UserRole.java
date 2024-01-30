package com.ll.exam.user;

import lombok.Getter;


    //enum은 객체를 싱글톤으로 쓰고싶다. 객체의 객수가 무조건 정해져 있을때 사용, 잘못 입력을 방지
    @Getter
    public enum UserRole {
        ADMIN("ROLE_ADMIN"), //변수랑 똑같다
        USER("ROLE_USER");

        UserRole(String value) {
            this.value = value;
        }

        private String value;

}
