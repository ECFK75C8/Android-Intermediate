package com.francis.gitme;

public class Developer {

    private String username;
    private String image_url;
    private String developer_url;

    Developer(String user, String img_url, String dev_url){
        username = user;
        image_url = img_url;
        developer_url = dev_url;
    }

    public String getUsername() {
        return username;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDeveloper_url() {
        return developer_url;
    }
}
