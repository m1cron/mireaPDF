package ru.micron.json;

public class ReportJson {
    private String target;
    private String theory;
    private String step_by_step;
    private String code;
    private String conclusion;
    private String literature;

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

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    public void setStep_by_step(String step_by_step) {
        this.step_by_step = step_by_step;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public void setLiterature(String literature) {
        this.literature = literature;
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
