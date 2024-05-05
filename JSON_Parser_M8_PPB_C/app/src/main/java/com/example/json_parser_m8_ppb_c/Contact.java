package com.example.json_parser_m8_ppb_c;

public class Contact {
    private String id;
    private String name;
    private String email;
    private String mobile;

    public Contact(String id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
