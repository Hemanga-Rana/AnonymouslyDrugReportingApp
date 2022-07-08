package com.smartindiahackathon2022.SAKHAM.Hindi;

public class UserHelperHindiClass {

    String drugplace, drugReport;

    public UserHelperHindiClass() {
    }

    public UserHelperHindiClass(String drugplace, String drugReport) {
        this.drugplace = drugplace;
        this.drugReport = drugReport;
    }

    public String getDrugplace() {
        return drugplace;
    }

    public void setDrugplace(String drugplace) {
        this.drugplace = drugplace;
    }

    public String getDrugReport() {
        return drugReport;
    }

    public void setDrugReport(String drugReport) {
        this.drugReport = drugReport;
    }
}
