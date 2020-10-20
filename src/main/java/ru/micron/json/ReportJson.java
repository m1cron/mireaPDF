package ru.micron.json;

public class ReportJson {
    public String target, theory, step_by_step,conclusion, literature;

    public ReportJson(String target, String theory, String step_by_step, String conclusion, String literature) {
        this.target = target;
        this.theory = theory;
        this.step_by_step = step_by_step;
        this.conclusion = conclusion;
        this.literature = literature;
    }

    public String getTarget() {
        return target;
    }

    public String getTheory() {
        return theory;
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
