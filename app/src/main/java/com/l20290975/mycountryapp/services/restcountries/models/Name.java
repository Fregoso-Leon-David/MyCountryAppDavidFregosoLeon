package com.l20290975.mycountryapp.services.restcountries.models;

import java.util.Map;

public class Name {
    private String common;
    private String official;
    private Map<String, NativeName> nativeName;

    public String getCommon() { return common; }
    public void setCommon(String value) { this.common = value; }

    public String getOfficial() { return official; }
    public void setOfficial(String value) { this.official = value; }

    public Map<String, NativeName> getNativeName() { return nativeName; }
    public void setNativeName(Map<String, NativeName> value) { this.nativeName = value; }
}
