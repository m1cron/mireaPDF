package ru.micron.json;

public class ReportJson {
    private final String target;
    private final String theory;
    private final String step_by_step;
    private final String code;
    private final String conclusion;
    private final String literature;

    public ReportJson(String target, String theory, String step_by_step, String code, String conclusion, String literature) {
        this.target = target;
        this.theory = theory;
        this.step_by_step = step_by_step;
        this.code = code;
        this.conclusion = conclusion;
        this.literature = literature;
    }

    public String getTarget() {
        return target;
    }

    public String getTheory() {
        return theory;
    }

    public String getCode() {
        return code;
    }

    public String getStep_by_step() {
        return step_by_step;
    }

    public String getConclusion() {
        return conclusion;
    }

    public String getLiterature() {
        return literature;
    }

    @Override
    public String toString() {
        return "ReportJson{" +
                "target='" + target + '\'' +
                ", theory='" + theory + '\'' +
                ", step_by_step='" + step_by_step + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", literature='" + literature + '\'' +
                '}';
    }
}
