# lib-shape
自定义SuperButton，覆盖80%以上的按钮类型，从此不再写shape、selector资源文件。清爽了很多！ 

### 感谢
首先要感谢 [SuperButton](https://github.com/ansnail/SuperButton) 的作者，该控件是在其基础上做得改动。
### 概述
该自定义SuperButton，覆盖了差不多80%的按钮使用场景。以往我们遇到这样的按钮样式，实现方式都是shape、selector。有了这个控件以后就再也不会有写shape的烦恼了。这里的SuperButton提供了Java和Kotlin两种。
### 示例图

<img src="https://github.com/0119zp/feat_superbutton/blob/master/SuperButton%E7%A4%BA%E4%BE%8B.png" width="375" hight="750" alt="ShapeButton示例图">

### 示例代码 - 只需要配置其相应的属性就好
~~~
<zpan.example.shape.ShapeButtonKotlin
                android:layout_width="80dp"
                android:layout_height="40dp"
                app:color_normal="#e74d4d"
                app:corner="8dp"
                app:text="按钮"
                app:textColor="#ffffff"
                app:textSize="16sp" />
~~~
