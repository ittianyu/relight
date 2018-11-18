
![](./images/mvvm.png)

## 优势 ##

#### 轻量 ####
整个lib只有十几个类，仅依赖 lifecycle 和 support lib.

#### 简单 ####
作为初学者，你几乎不需要学习框架api就可以开始使用。
下面是最贴近原来开发习惯的方式。

UserLayout
```
class UserLayout extends AndroidWidget<View> {
    private User user;

    private TextView tvId;
    private TextView tvName;

    public UserLayout(Context context, Lifecycle lifecycle, User user) {
        super(context, lifecycle);
        this.user = user;
    }

    @Override
    public View createView(Context context) {
        return View.inflate(context, R.layout.a_activity_user, null);
    }

    @Override
    public void initView(View view) {
        tvId = view.findViewById(R.id.tv_id);
        tvName = view.findViewById(R.id.tv_name);
    }

    @Override
    public void updateView(View view) {
        tvId.setText(user.getId() + "");
        tvName.setText(user.getName());
    }
}
```

UserActivity
```
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(WidgetUtils.render(this, UserLayout.class, new User(1, "tomcat")));
    }

}
```

在 Activity 中，调用工具类直接对我们写的 UserLayout 进行渲染。
也就是说，主要的工作已经完全转移到了 UserLayout 中。
而 UserLayout 并不会让人感到陌生，仅仅是继承了 AndroidWidget，
并重写了 `createView`、`initView`、`updateView`这几个方法，相信具体在里面干什么操作你应该很清楚了。

#### 强大 ####
MVVM的强大之处在于，你不需要关注数据“方向”，你只需要拿着数据去渲染UI即可。

就比如上面那个例子，数据是外面传进来的，只需要在 `updateView` 中做出相应的变化即可。

但如果想更新UI呢？
当然你可以直接操作View来设置新的数据，但这违背了MVVM框架的设计原则。
你可以直接对数据进行修改，然后就会触发一次重新渲染，并不需要担心性能问题，因为默认情况下，原来的 View 并不会被抛弃掉，仅仅会触发一次 update 操作，对应于上面的就是 `updateView` 里面的操作。
```
class UserLayout extends AndroidWidget<View> {
...
        @Override
        public void bindEvent(View view) {
            view.setOnClickListener(v -> setState(() -> {
                user = UserModel.getInstance().getUser();
            }));
        }
...
}
```
这里对 view 设置了一个点击事件，点击后更新数据，进而触发 UI 的更新。
其实就是调用了 `setState` 方法来触发更新，类似于 react 和 flutter，更新数据的操作需要放到该方法中，否则不会触发更新。
当然，AndroidWidget 里面并不具备这个方法，因为它是一个原子性的控件，并没有所谓的"状态"。具体用法会在后面讲到。


#### 高复用 ####

框架的设计思想是所有的东西都视为控件，各个控件之间保持独立，容器控件可以组合一个或多个控件。每个控件都有独立的生命周期。
因此，控件的复用性大大提高。

#### 异步处理和生命周期管理 ####

对于客户端编程来说，最麻烦的是各种异步调用和状态同步。
多线程编程很难，稍有不慎，轻则内存泄漏，重则直接蹦溃。
本框架内部做了处理异步请求，并在 onDestroy 时，自动取消子线程的操作，防止内存泄漏 或者 异步导致的空指针问题。
修改数据时，各位开发者可自行选择合适的方法。

* `setState`：同步执行数据修改操作(不需要切换线程，性能高)
* `setStateAsync`：异步执行的数据修改操作，并在UI销毁时自动停止异步线程


## 快速开始 ##

#### 引入库 ####
添加 java8 支持
```
android {
...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
...
}
```

添加 maven 仓库
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

添加依赖
```
def support_version = '28.0.0'
def lifecycle_version = '1.1.1'

implementation 'com.github.ittianyu:relight:master-SNAPSHOT'
implementation "com.android.support:appcompat-v7:$support_version"
implementation "com.android.support:design:$support_version"

//     Support library depends on this lightweight import
implementation "android.arch.lifecycle:runtime:$lifecycle_version"
// alternately - if using Java8, use the following instead of compiler
implementation "android.arch.lifecycle:common-java8:$lifecycle_version"
```

#### hello relight ####

下面将介绍使用框架来构建一个显示 id 和 name 的 activity，点击之后，切换数据。

为了展示数据，先建一个 User 实体类
```
public class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

... getter setter toString
}
```

然后先把数据源准备好，这里用随机数字和name来模拟的。
```
public class UserModel {
    private static UserModel instance;

    public static UserModel getInstance() {
        if (null == instance)
            instance = new UserModel();
        return instance;
    }

    public int randomId() {
        Random random = new Random();
        return random.nextInt(100000);
    }

    public String randomName() {
        String[] names = {"tom", "ketty", "marry", "jone", "bob", "jackson", "cat"};
        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }

    public User getUser() {
        int id = randomId();
        String name = randomName();
        return new User(id, name);
    }
}
```

下面就是重点了，你需要记住的是，一切都是控件。
为了渲染需求中的 activity，需要一个 Widget，并且能修改数据，所以新建一个类 UserLayoutStateful  继承  LifecycleStatefulWidget
```
public class UserLayoutStateful extends LifecycleStatefulWidget<View> {
    public UserLayoutStateful(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected AsyncState<View> createState(Context context) {
        return null;
    }
}
```

实现的方法需要返回一个 AsyncState。
当然我们可以直接 new 一个 State，但已经有更方便的工具类可以节省代码。
```
    @Override
    protected AsyncState<View> createState(Context context) {
        return StateUtils.create(new UserLayout(context, lifecycle, new User(0, "")));
    }
```

该方法需要一个 AndroidWidget，所以还需要一个类，为了方便使用，直接定义为内部类。
因为后面需要使用 user，所以让外面传进来。
createView 则直接使用我们最熟悉的 inflate xml 方式来创建。

```
class UserLayout extends AndroidWidget<View> {
    private User user;

    public UserLayout(Context context, Lifecycle lifecycle, User user) {
        super(context, lifecycle);
        this.user = user;
    }

    @Override
    public View createView(Context context) {
        return View.inflate(context, R.layout.a_activity_user, null);
    }
}
```

a_activity_user.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:id="@+id/v_root"
    android:gravity="center"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/tv_id"
        android:gravity="center"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <TextView
        android:id="@+id/tv_name"
        android:gravity="center"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```


为了渲染数据，重写里面的 `initView` `updateView`方法，这里都是常规操作。
```
class UserLayout extends AndroidWidget<View> {
    private User user;

    private TextView tvId;
    private TextView tvName;

    public UserLayout(Context context, Lifecycle lifecycle, User user) {
        super(context, lifecycle);
        this.user = user;
    }

    @Override
    public View createView(Context context) {
        return View.inflate(context, R.layout.a_activity_user, null);
    }

    @Override
    public void initView(View view) {
        tvId = view.findViewById(R.id.tv_id);
        tvName = view.findViewById(R.id.tv_name);
    }

    @Override
    public void updateView(View view) {
        tvId.setText(user.getId() + "");
        tvName.setText(user.getName());
    }
}
```

然后添加一个点击事件，点击之后触发数据更新
```
    @Override
    public void bindEvent(View view) {
        view.setOnClickListener(v -> setState(() -> {
            user = UserModel.getInstance().getUser();
        }));
    }
```

这里从数据源获取数据并不是什么耗时操作，所以，这里直接使用 `setState` 来更新数据。
但如果这个数据源从网络获取数据，只需要把 换成 `setStateAsync` 来更新数据即可。


最后一步是，让 activity 直接渲染这个 widget
```
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(WidgetUtils.render(this, UserLayoutStateful.class));
    }

}
```

## 入门教程 ##

#### [1. AndroidWidget](./docs/1.AndroidWidget.md) ####

目的：学习 AndroidWidget 的简单用法。

#### [2. LifecycleStatefulWidget](./docs/2.LifecycleStatefulWidget.md) ####

目的：学习 LifecycleStatefulWidget 的简单用法。

#### [3. TextWidget](./docs/3.TextWidget.md) ####

目的：学习 TextWidget 的简单用法，熟悉非 xml 的方式写界面

#### 4. LinearWidget ####

目的：学习 LinearWidget 的简单用法
计划：往 LinearWidget 中加入 2 个 TextWidget。

#### 5. FrameWidget ####

目的：学习 FrameWidget 的简单用法
计划：往 FrameWidget 中加入 2 个 TextWidget。

#### 6. RelativeWidget ####

目的：学习 RelativeWidget 的简单用法
计划：往 RelativeWidget 中加入 3 个 TextWidget。

#### 7. setStateAsync ####

目的：学习 在 setStateAsync 的使用。
计划：在第2篇的基础上，把数据源改成耗时操作，setState 改成 setStateAsync。

#### 8. LceeWidget ####

目的：学习 LceeWidget 的使用。
计划：创建一个 widget 继承 LceeWidget，分别渲染 4中状态的页面，点击内容 Widget 触发重新加载数据。


## Widgets ##

本框架将 Widget 大致分为 3 大类。

#### native ####
底层 Widget。 直接涉及原生 view 的渲染。

* AndroidWidget：所有 native 控件的基类，含有 生命周期和 navite 构建方法
* BaseAndroidWidget：继承 AndroidWidget，封装了常用的 native 的属性和设置方法
* ViewGroupWidget:继承 BaseAndroidWidget，类似于 ViewGroup，用于包容其他 AndroidWidget
* FrameWidget：封装 FrameLayout
* LinearWidget：封装 LinearLayout
* RelativeWidget：封装 RelativeLayout
* TextWidget：封装 TextView
* RecyclerWidget：封装 RecyclerView

#### stateful ####

带有状态的控件。 

* StatefulWidget：所有 stateful 控件的基类
* LifecycleStatefulWidget：带有生命周期的 StatefulWidget
* LceeWidget：封装了 Loading Content Empty Error 四种常见状态的控件

#### stateless ####

无状态的控件。

* StatelessWidget：所有 stateless 控件的基类
* LifecycleStatelessWidget：带有生命周期的 StatelessWidget


## 异步线程策略 ##

框架内部采用线程池来执行异步操作，考虑到不同的应用有不同的需求，所以，开发者可以自行设置相应的线程池策略(建议在初始化时设置)。
```
ThreadPool.set(executorService);
```

默认使用的是 `Executors.newCachedThreadPool()`
也就是一段时间内没有异步任务时，自动释放内部的线程，符合大部分应用的需求。

## 内部结构 ##

#### Widget ####

外部通过调用 render 方法，获得一个 View，进行渲染

#### StatelessWidget ####
需要实现一个 Widget<T> build() 方法，来完成 Widget 的构建

```
render -> build -> initWidget -> widget.render
```

#### state ####

```
setState -> willUpdate -> update -> didUpdate

onDestroy -> dispose
```

#### StatefulWidget ####
需要实现一个 State<T> createState(Context context) 方法 来构建一个 State 对象

```
render -> createState -> state.init -> state.build -> initWidget -> widget.render

state.setState -> state.update -> update -> updateWidget
```

#### AndroidWidget ####
带有 Android 常用的构建方法 和 生命周期

* 调用顺序

```
构造方法 -> createView

render(first call) -> bind lifecycle

onStart -> initView -> bindEvent -> initData -> updateView
```

* 生命周期

通过绑定 Lifecycle 来让 Widget 获得完整的生命周期
```
onStart
onResume
onPause
onStop
onDestroy
```

#### BaseAndroidWidget ####

initView -> initProps -> updateProps

#### ViewGroupWidget ####

构造方法 -> addChildren -> updateChildrenProps -> updateProps 

updaetView 中，调用 children 的 updateView 或 setState 或 update 方法

## To Do List ##

#### 框架 ####

- [x] 基础框架
- [x] 异步支持
- [ ] 完善 BaseAndroidWidget 基础属性 和 api
- [ ] startActivity 支持
- [ ] anko 支持(开发时可视化支持)
- [ ] Android Studio 模版

#### 基础控件 ####

- [x] FrameWidget
- [x] LinearWidget
- [x] RelativeWidget
- [x] RecyclerWidget
- [x] TextWidget
- [ ] ButtonWidget
- [ ] ToolBarWidget
- [ ] ImageWidget
- [ ] EditWidget
- [ ] FloatingActionButtonWidget
- [ ] DrawerWidget

#### 高级控件 ####

- [x] LceeWidget
- [ ] List
- [ ] Route 和 Navigator


#### 文档 ####

- [x] 优势
- [x] 快速开始
- [ ] 入门教程
- [x] Widgets
- [x] 异步线程策略
- [x] 内部结构
- [ ] 高级教程
- [ ] 目录
- [ ] 英文版
- [x] To Do List
