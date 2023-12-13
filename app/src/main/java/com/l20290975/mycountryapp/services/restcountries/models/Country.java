package com.l20290975.mycountryapp.services.restcountries.models;

import java.util.List;

public class Country {
    private Flags flags;
    private Name name;
    private String cca3;
    private List<String> capital;
    private String flag;
    private long population;

    public Flags getFlags() { return flags; }
    public void setFlags(Flags value) { this.flags = value; }

    public Name getName() { return name; }
    public void setName(Name value) { this.name = value; }

    public String getCca3() { return cca3; }
    public void setCca3(String value) { this.cca3 = value; }

    public List<String> getCapital() { return capital; }
    public void setCapital(List<String> value) { this.capital = value; }

    public String getFlag() { return flag; }
    public void setFlag(String value) { this.flag = value; }

    public long getPopulation() { return population; }
    public void setPopulation(long value) { this.population = value; }
}
