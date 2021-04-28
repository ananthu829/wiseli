package uk.ac.tees.mad.w9501736.models;

public class AvailableUserList {

    String  caption;
    Boolean isSelected;

    public AvailableUserList(String caption,Boolean isSelected) {
        this.caption = caption;
        this.isSelected =isSelected;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }


}
