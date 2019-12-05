package com.example.myapplicat.data;

public class Edititem {
    private int imageId;
    private String topView;
    private String BottomView;

    public Edititem(int imageId, String topView, String bottomView) {
        this.imageId = imageId;
        this.topView = topView;
        BottomView = bottomView;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTopView() {
        return topView;
    }

    public void setTopView(String topView) {
        this.topView = topView;
    }

    public String getBottomView() {
        return BottomView;
    }

    public void setBottomView(String bottomView) {
        BottomView = bottomView;
    }
}
