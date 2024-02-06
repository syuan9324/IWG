package com.iisi.pccdeploy;

public enum WarFileEnum {
    PWC_WEB("pwc-web.war"),
    PWC_WEB_TEST("pwc-web-test.war"),
    PWC_REST("pwc-rest.war"),
    PWC_BATCH("pwc-batch.war");

    private final String warName;

    WarFileEnum(String warName) {
        this.warName = warName;
    }

    public String getWarName() {
        return warName;
    }
}
