package uk.ac.tees.mad.w9501736.ui.helper;

public interface AdapterInterface {
    void onItemClicked(String title, Integer circleID);

    void onDeleteCtaClicked(String id);

    void setEditableText(String id,String name);
}
