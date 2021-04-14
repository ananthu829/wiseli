package uk.ac.tees.mad.w9501736.models;

public class Comment {

    private String comment;
    private String userName;
    private String imgUrl;

    public Comment(String comment, String userName, String imgUrl) {
        this.comment = comment;
        this.userName = userName;
        this.imgUrl = imgUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
