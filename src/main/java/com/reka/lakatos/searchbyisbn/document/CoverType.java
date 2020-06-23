package com.reka.lakatos.searchbyisbn.document;

public enum CoverType {
    HARDCORE("kötött"),
    PAPERBACK("fűzött"),
    DIGITAL("elektronikus dokumentum"),
    SOUND_RECORD("hangfelvétel"),
    MAP("kartográfiai dokumentum"),
    SHEET_MUSIC("nyomtatott kotta");

    private String hunName;

    CoverType(String hunName) {
        this.hunName = hunName;
    }

    public String getHunName() {
        return hunName;
    }

    public static CoverType findTypeByName(String hunName){
        if (hunName.isBlank()) {
            throw new NullPointerException("Cover type can't be empty!");
        }

        for(CoverType v : values()){
            if( v.getHunName().equals(hunName)){
                return v;
            }
        }
        return null;
    }
}
