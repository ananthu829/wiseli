package uk.ac.tees.mad.w9501736.ui.helper;

public interface AdapterInterface {
    void onItemClicked(String title, Integer circleID);

    void onDeleteCtaClicked(Integer id);

    void setEditableText(Integer id, String name);
}
