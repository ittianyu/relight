
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
        public void initEvent(View view) {
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

【可选】 添加 java8 支持
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

#### 混淆 ####

使用了 xml 支持，必须加入混淆，未使用的可以不加。
```
-keep class * extends com.ittianyu.relight.widget.Widget {*;}
```


## 入门教程 ##

#### [1. AndroidWidget](./docs/base/1.AndroidWidget.md) ####

目的：学习 AndroidWidget 的简单用法。

#### [2. StatefulWidget](./docs/base/2.StatefulWidget.md) ####

目的：学习 StatefulWidget 的简单用法。

#### [3. TextWidget](./docs/base/3.TextWidget.md) ####

目的：学习 TextWidget 的简单用法，熟悉非 xml 的方式写界面。

#### [4. LinearWidget](./docs/base/4.LinearWidget.md) ####

目的：学习 LinearWidget 的简单用法。

#### [5. FrameWidget](./docs/base/5.FrameWidget.md) ####

目的：学习 FrameWidget 的简单用法

#### [6. RelativeWidget](./docs/base/6.RelativeWidget.md) ####

目的：学习 RelativeWidget 的简单用法

#### [7. setStateAsync](./docs/base/7.setStateAsync.md) ####

目的：学习 setStateAsync 的使用。

## 进阶教程 ##

#### [1. LceeWidget](./docs/medium/1.LceeWidget.md) ####

目的：学习 LceeWidget 的使用。

#### [2. UpdateStrategy](./docs/medium/2.UpdateStrategy.md) ####

目的：学习 AsyncState 的更新策略。

#### [3. LceermWidget](./docs/medium/3.LceermWidget.md) ####

目的：学习 LceermWidget 的使用。


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
* StatefulWidget：带有生命周期的 StatefulWidget
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
render(first call) -> build -> widget.render -> initWidget -> update
```

#### state ####

```
setState -> willUpdate -> update -> didUpdate

onDestroy -> dispose
```

#### StatefulWidget ####
需要实现一个 State<T> createState(Context context) 方法 来构建一个 State 对象

```
render(first call) -> createState -> state.init -> state.build -> widget.render -> initWidget -> update

state.setState -> state.update -> widget.update
```

#### AndroidWidget ####

```
构造方法 -> createView
```

```
render(first call) -> initView -> initEvent -> initData -> update
```

#### Lifecycle ####
带有生命周期的 Widget

* 调用顺序

```
render(first call) -> bind lifecycle
```

需要注意的是，`bind lifecycle` 在控件初始化完之后才调用

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
带有常用 View 属性设置的 native widget

```
initView -> initProps
```

```
onStart -> updateProps(when has LayoutParams)
```

initView 是在 render 之后触发的

#### ViewGroupWidget ####

```
render(first call) -> children.render -> super.render(render self) -> add children to ViewGroup
```

```
addChildren -> updateChildrenProps -> updateProps 
```


## To Do List ##

#### 框架 ####

- [x] 基础框架
- [x] 异步支持
- [x] 重试支持
- [x] 过滤支持
- [x] 缓存支持
- [ ] 完善 BaseAndroidWidget 基础属性 和 api
- [x] startActivity 支持
- [x] xml 支持
- [ ] 单元测试支持
- [ ] CoroutineState(kotlin 协程)
- [ ] Android Studio 模版

#### 基础控件 ####

- [x] FrameWidget
- [x] LinearWidget
- [x] RelativeWidget
- [x] RecyclerWidget
- [x] TextWidget
- [x] ImageWidget
- [x] SwipeRefreshWidget
- [x] ButtonWidget
- [ ] ToolBarWidget
- [ ] EditWidget
- [ ] FloatingActionButtonWidget
- [ ] DrawerWidget

#### 高级控件 ####

- [x] LceeWidget
- [x] LceermWidget
- [x] RmWidget
- [ ] List
- [ ] Route 和 Navigator

#### 主题控件 ####

- [ ] material


#### 文档 ####

- [x] 优势
- [x] 快速开始
- [x] 入门教程
- [ ] 进阶教程
- [x] Widgets
- [x] 异步线程策略
- [x] 内部结构
- [ ] 目录
- [ ] 英文版
- [x] To Do List

