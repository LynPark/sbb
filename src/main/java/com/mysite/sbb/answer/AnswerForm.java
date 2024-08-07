package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @Size(min = 5, message = "내용은 5자 이상입니다.")
    private String content;
}
