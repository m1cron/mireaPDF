package ru.micron.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportJson {
    private String prac_number;
    private String target;
    private String theory;
    private String step_by_step;
    private String code;
    private String conclusion;
    private String literature;

    public ReportJson() {
        this.prac_number = "№";
        this.target = "Цель работы";
        this.theory = "Теоретическое введение";
        this.step_by_step = "Ход работы";
        this.code = "Код с GitHub или ссылка";
        this.conclusion = "Вывод";
        this.literature = "Используемая литература";
    }

}