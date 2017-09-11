# 阳光浙理light版


### 算是吐槽吧
“阳光浙理”（工程名SunSmile）是我大学（浙江理工大学）在校期间给学校师生开发的一款集合了查询和社交等多种功能于一体的校园型App。
从大二开始自学Android并跟随学长着手进行了第一版的开发，大三自己配合后台以及UI的同学开发了第二版，大四在校最后一年着手进行了第三版的开发，
并作为自己的毕业设计得到了优秀以及校百优。毕业后将源码以及开发重任交给了学弟学妹，到现在毕业一年多，也不见更新。。。。。

### 立个flag
今年（2017）是浙江理工大学建校120周年，在校经历点滴难忘，尤其是持续做了三年的“阳光浙理”，所以打算在校庆前做点东西出来献给母校
（学校也不稀罕这个，哈哈~~）。这次的是“阳光浙理light版”（工程名Sunshine），主要也是为了自己学习MVVM+DataBinding模式而做的，打算暂时去除
社交功能，专注于各个实用查询功能的开发，如果有能力有可能的话尽量也能给其他高校方便的对接使用。

## 需要记录的文档吧
暂时还没有写完，我会一篇一篇的写，写完的会添上博客连接。
### 自定义控件
这些东西其实不能叫自定义，充其量是个半自定义，但是其中的坑真真是要记录出来的。  
* [TabLayout的TabItem自定义][1]
* [AlertDialog自定义][2]

### VectorDrawable
VectorDrawable 的特点就是它不会因为图像的缩放而失真，这样在开发过程中就不需要为不同分辨率的设备定义不同大小的图片资源了，还能有效减小apk体积。

### MVVM+DataBinding
这种模式也是我第一次使用，一边学习一边实战，记录的也是自己的踩坑历程，绝不是那种MVVM模式使用的教程，如果文章中有对模式使用不对
的地方欢迎大家指正。
* DataBinding基本使用
* [DataBinding中include布局使用][5]

## 依赖的第三方库及其他工具
* 日志打印[orhanobut/logger](https://github.com/orhanobut/logger)
* ORM数据库[greenrobot/greenDAO](https://github.com/greenrobot/greenDAO)
* 网络请求库[square/okhttp](https://github.com/square/okhttp)
* 图片处理库[square/picasso](https://github.com/square/picasso)
* 客服[美洽](https://meiqia.com/)  
* 模拟数据[EasyMock](https://github.com/easy-mock/easy-mock)
  
  
[1]: http://blog.csdn.net/u010976213/article/details/77712180
[2]: http://blog.csdn.net/u010976213/article/details/77715311
[5]: http://blog.csdn.net/u010976213/article/details/77746315



