package bwie.mylibrary;

/**
 * Created by zjj on 2016/11/8.
 */
public interface MyOnCreate {

// /**回车
    /**
     *
     * @return 布局文件

     */
    int bindlayout();

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 初始化数据
     */
    void initdata();

    /**
     * 加载数据 activity自动调用
     */
    void loaddata();
}
