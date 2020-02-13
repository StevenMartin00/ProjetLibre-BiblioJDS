package fr.polytech.bibliothequejds.model;

public class Category
{
    private String categoryName;

    public Category()
    {
        this.categoryName = "";
    }

    public Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
