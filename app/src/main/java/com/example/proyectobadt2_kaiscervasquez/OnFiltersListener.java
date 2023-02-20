package com.example.proyectobadt2_kaiscervasquez;

public interface OnFiltersListener {

    public void onFiltersMonth(String filterMonth);
    public void onFiltersYear(String filterYear);
    public void onFiltersCountry(String filterCountry);

    public void onFilterMonthYear(String filterMonth, String filterYear);
    public void onFilterMonthCountry(String filterMonth, String filterCountry);
    public void onFilterYearCountry(String filterYear, String filterCountry);

    public void onFilterMonthYearCountry(String filterMonth, String filterYear, String filterCountry);

}