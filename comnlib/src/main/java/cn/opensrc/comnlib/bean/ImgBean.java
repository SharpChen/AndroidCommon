package cn.opensrc.comnlib.bean;

/**
 * Author:       sharp
 * Created on:   8/11/16 4:09 PM
 * Description:  图片信息
 * Revisions:
 */
public class ImgBean {

    // 图片唯一标识
    private String imgId;

    // 图片类型,分网络图片和本地图片
    private String imgType;

    // 图片路径
    private String imgPath;

    // 图片名
    private String imgName;

    // 图片所在目录
    private String imgParentDirName;

    // 图片所在目录共有多少图片
    private String imgParentDirChildImgCount;

    // 图片是否被选择了（选择本地图片时）
    private boolean isSelected;

    // 图片是否被删除了（当图片在浏览大图时被删除后,为该图片打上删除标志,以更新其它界面）
    private boolean isDeleted;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgParentDirName() {
        return imgParentDirName;
    }

    public void setImgParentDirName(String imgParentDirName) {
        this.imgParentDirName = imgParentDirName;
    }

    public String getImgParentDirChildImgCount() {
        return imgParentDirChildImgCount;
    }

    public void setImgParentDirChildImgCount(String imgParentDirChildImgCount) {
        this.imgParentDirChildImgCount = imgParentDirChildImgCount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
