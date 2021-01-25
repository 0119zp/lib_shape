# lib-shape
自定义TextViewShape、LinearLayoutShape、RelativeLayoutShape、lineShape、ConstraintLayoutShape控件，从此不再写shape、selector资源文件。清爽了很多！ 

### 感谢
首先要感谢 [SuperButton](https://github.com/ansnail/SuperButton) 的作者，该控件是在其基础上做得改动。
### 概述
该自定义TextViewShape、LinearLayoutShape、RelativeLayoutShape、lineShape、ConstraintLayoutShape，覆盖了大部分shape背景使用场景。
### 示例图

<img src="https://github.com/0119zp/feat_superbutton/blob/master/SuperButton%E7%A4%BA%E4%BE%8B.png" width="375" hight="750" alt="ShapeButton示例图">

### 示例代码 - 只需要配置其相应的属性就好
~~~
            <zpan.lib.shape.TextViewShape
                android:layout_width="80dp"
                android:layout_height="40dp"
                app:color_normal="#e74d4d"
                app:corner="8dp"
                app:text="按钮"
                app:textColor="#ffffff"
                app:textSize="16sp" />
~~~
