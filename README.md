# DiffUtilsDemo
利用support包新增的DiffUtils优雅的刷新RecycleView的数据

优雅的更新List数据

DiffUtil

官方文档：DiffUtil

参考文章：【Android】新的工具类DiffUtil，让RecyclerView上天

- 解决了无脑调用notifyItemChanged()（效率低下，无脑将所有数据全部刷新，不会自动调用默认动画）方法

- DiffUtils可以通过实现的Callback方法来实现寻找两个List的数据差别，粒度甚至可以小到Bean类的一个成员变量。。（但是这个必须结合RecycleView使用），然后会自动调用notifyItemXXXXXX()方法，隐藏了大部分的实现细节。吊吊哒。

- 并且不止是可以和Recycleview结合使用，dispatch方法中还可以传入ListUpdateCallback，可以在这个Callback中获取到合并之后的最新数据。

- 需要注意的问题是

	如果数据有在旧数据中间有增量或者删减变换，那么会存在新旧数据数量的不一致，就很有可能导致对象成员变量的比较，因为已经错位了。这个问题我遇到了。暂时只想到同步数量这个办法。。不太优雅。
