
![](./images/mvvm.png)

## 优势 ##

#### 轻量 ####
- 最少依赖：仅依赖 [lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle) 和 [support lib](https://developer.android.google.cn/topic/libraries/support-library/features).
- 实现精简：只有几十个类

提示：这两个依赖库在 Android Studio 新建的项目里几乎都包含，也就是几乎 0 依赖。

#### 接入成本低 ####
- 侵入性低：不需要修改任何现有代码
- 无缝嵌入：可间接当做 View 使用，无论之前使用 MVP 还是 MVC，往里面加一个 View 根本不影响你的结构。

#### 简单 ####
- 对原生开发友好：你几乎不需要学习框架 api 就可以开始使用。
- 熟悉 react 和 flutter 的非常容易上手

具体可往下滑，查看基础教程。

#### 解耦 ####
MVVM 的强大之处在于 UI 和 逻辑 分离，处理逻辑时不需要关心 UI，写 UI 时不需要管数据从哪获取。

要更新时，你直接对数据进行修改，就会自动触发重新渲染。 并不需要担心性能问题，因为默认情况下，原来的 View 并不会被抛弃掉，仅仅会触发一次 update 操作。
```
public class StatefulUserWidget extends StatefulWidget<View, UserWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<UserWidget> createState(Context context) {
        return StateUtils.create(new UserWidget(context, lifecycle, user));
    }

    @Override
    public void initWidget(UserWidget widget) {
        widget.setOnClickListener(v -> setState(() -> {
            user = UserDataSource.getInstance().getUser();
        }));
    }

    @Override
    public void update() {
        super.update();
        widget.setUser(user);
    }
}
```
在 `initWidget` 方法中对 `widget` 设置了一个点击事件，点击后重新获取数据，自动触发 UI 的更新。
其实就是调用了 `setState` 方法来触发更新，类似于 [react](http://react-china.org) 和 [flutter](https://flutterchina.club)，更新数据的操作需要放到该方法中，否则不会触发更新。

#### 高复用 ####

本框架的设计思想类似于 `flutter` 的 "Everything's a Widget"，即把所有的东西都视为控件。
各个控件之间保持独立，容器控件可以组合一个或多个控件，每个控件都有独立的生命周期。
因此，控件的复用性大大提高。

#### 便捷的生命周期 ####

得益于谷歌新引进的 [lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle)，让每个 widget 都可以拥有完整的生命周期，甚至数据也可以拥有生命周期。

#### 异步支持 (同步发请求) ####

对于客户端编程来说，最麻烦的是各种异步调用和状态同步。
多线程编程很难，稍有不慎，轻则内存泄漏，重则直接蹦溃。
本框架内部做了处理异步请求，并在 `onDestroy` 时，自动取消子线程的操作，防止内存泄漏 或者 异步导致的空指针问题。

本库提供了如下方法支持数据修改，各位开发者可自行选择合适的方法。

* `setState`：同步执行数据修改操作(适用于非耗时的数据修改操作，无线程切换性能消耗)
* `setStateAsync`：异步执行的数据修改操作，并在UI销毁时自动停止异步线程
* `setStateAsyncWithCache`：类似于 `setStateAsync` ，对缓存提供支持。

有了它，你可以同步的方式去发网络请求。 合并多个请求的数据变得异常轻松（比如 先请求a，在请求b，合并结果变成c）。

#### 缓存支持 ####

<s>生活</s> 缓存很难。 一千个应用有一千种缓存。
我见过网上很多缓存方案非常粗糙，大部分是直接在网络层通过拦截器来做。 因为这样不用侵入到业务代码。 但是，这样做的弊端也很大，不够灵活。 虽然像 okhttp 这样的网络库提供了对缓存的支持，比如可以设置只使用缓存，或者只使用网络，但这依然不够灵活。

如果想精准控制缓存，那就不得不自己在代码里为每一个请求都加上缓存的逻辑。 你会发现这就导致相同的缓存逻辑写了无数遍，这简直是噩梦。

不过因为本库有异步支持，所以处理缓存也变得简单多了。 至于你想怎么使用缓存，交给你自己判断吧，我们提供了一个策略接口，你只需要实现它即可。 

#### 页面状态管理 ####

无数据页面、 错误页面、 加载中页面、 下拉刷新、 加载更多 在应用中很常见。

实现起来却不方便了，常见的做法是 BaseActivity BaseFragment，但我表示不希望看见它们，曾今我觉得 base 是很好的逻辑抽象和封装，后来发现自从有了 base，迁移和复用几乎变成了 0。 base 使得它们紧紧的耦合在一起。 如果你不明白我在说什么，我给你举个例子：

我想从项目 A 中抽出一个页面和逻辑差不多的 Activity，以便于在项目 B 中使用，这个时候最常见的就是 复制 XxxActivity.java 到 B 项目，然后后面你懂的。

但本库对这几种页面状态提供了高度的封装，你不必再依赖于 `Base`。
不仅仅是 activity，甚至一个 button，你都可以让他拥有如上的这几种状态。

具体用法参考 进阶教程 1 3 4

#### 请求过滤 ####

不知道你是否烦恼过，产品跟你说，用户可能狂按按钮，让你加个判断，减少不必要的请求。
听起来需求很简单，防止重复点击就行，但可达鸭眉头一皱，发现事情并不简单。
一个按钮防重复点击也就几行代码，但几十个几百个按钮呢？ 
你说可以抽出一个 BaseButton？ 那点击的如果是个 text 或 fab 这样的控件呢？
确实 base 可以解决很多重复代码，但相应的你要把对应的控件全部换成 base，工作量也很大。

本库贴心的为大家提供了请求过滤器，默认就过滤重复的请求，虽然不是在 UI 上过滤，但同一个 task 的请求是不会重复执行的，这点可以放心。 如果你有其他过滤需求，还可以自定义实现一个过滤器。

#### 重试支持 ####

请求失败重试也是很常见的需求，但实现并不简单，基本有2种做法：
- 如果在代码层面做，就需要在请求失败的回调里重新发起一次，还要记录次数，很是麻烦。
- 如果在网络层做，你就得对网络层进行一次封装，提供一个方法设置重试次数。 然而，这种方法弊端很大，不能和业务很好的联系。 因为网络层并不知道什么时候应该重试，网络请求失败就重试？ 还是返回内容里面标识不成功就重试呢？

本库同样提供了重试支持，因为有了异步支持，重试对框架来说，就是一个循环，然而这个循环框架都帮你写好了，你只需告诉框架重试次数和什么时候应该重试就可以了。


#### 动态属性设置能力 ####

动态换肤或样式修改也是一个很常见的需求，然而为了实现这样的需求，往往需要开发者在代码里提前写好根据配置修改的UI的代码。

本库同样提供了支持，你可以通过一个 json 来对 wiget 进行属性修改。
所以换个皮肤或改个样式都是分分钟的事啦。

#### 单页面应用(测试) ####

搞前端的应该很清楚这是什么，就是所有渲染都是在一个页面上展示，页面跳转都是通过前端路由来控制。 对应到客户端，就是所有 UI 都在一个 activity 中展示。

这样做有什么意义？ 安卓插件化最大的问题是四大组件需要提前在 `manifest` 中注册，虽然目前有一些开源项目通过底层 hook 方式解决了这个问题，但是以后的安卓版本就不清楚会不会把这个限制了。
而且目前的插件化都需要对资源进行合并，这就使得成功率下降。

如果是单页面应用，动态下发字节码执行也变得有可能。 而且这样成功率理论上接近 100%。 打算有时间尝试一下。

我的意思就是像前端那样具有随时更新的能力，不知道会不会被封杀，逃。。。

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

// Support library depends on this lightweight import
implementation "android.arch.lifecycle:runtime:$lifecycle_version"
```

如果开启了 java8
```
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

#### [3.LceermWidget](./docs/medium/3.LceermWidget.md) ####

目的：学习 LceermWidget 的使用。

#### [4.RmWidget](./docs/medium/4.RmWidget.md) ####

目的：学习 RmWidget 的使用。

#### [5.Retryable](./docs/medium/5.Retryable.md) ####

目的：学习请求重试api。

#### [6.Cache](./docs/medium/6.Cache.md) ####

目的：学习缓存机制。

#### [7.StartActivity](./docs/medium/7.StartActivity.md) ####

目的：学习 Widget 中 startActivity 的用法。

#### [8.xml](./docs/medium/8.xml.md) ####

目的：了解 Widget xml 可视化支持


## Widgets ##

前面说过本框架的设计思想是 "Everything's a Widget"，`Widget` 是带有生命周期的原子性控件，
大致分为三类：native、stateful、stateless。

#### native ####
底层 widget。 直接涉及原生 view 的渲染。

* AndroidWidget：所有 native 控件的基类，含有生命周期和 native 构建方法
* BaseAndroidWidget：继承 AndroidWidget，封装了常用的 native 的属性和设置方法
* ViewGroupWidget:继承 BaseAndroidWidget，类似于 ViewGroup，用于包容其他 AndroidWidget
* FrameWidget：封装 FrameLayout
* LinearWidget：封装 LinearLayout
* RelativeWidget：封装 RelativeLayout
* BaseTextWidget：封装了继承自 TextView 的所有 View  的常用属性和设置方法
* TextWidget：封装 TextView
* ImageWidget：封装 ImageView
* ButtonWidget：封装 Button
* EditWidget：封装 EditText
* RecyclerWidget：封装 RecyclerView
* SwipeRefreshWidget：封装 SwipeRefreshLayout

#### stateful ####

带有状态的控件。 

* StatefulWidget：所有 stateful 控件的基类，带有生命周期
* LceeWidget：封装了 Loading、Content、Empty、Error 四种常见状态的控件
* RmWidget：封装了 Refresh、LoadMore 两种常见状态的控件
* LceermWidget：封装了 Loading、Content、Empty、Error、Refresh、LoadMore 六种常见状态的控件

#### stateless ####

无状态的控件。

* StatelessWidget：所有 stateless 控件的基类，带有生命周期

## 异步线程策略 ##

框架内部采用线程池来执行异步操作，考虑到不同的应用有不同的需求，
所以，开发者可以自行设置相应的线程池策略(建议在初始化时设置)。
```
ThreadPool.set(executorService);
```

默认使用的是 `Executors.newCachedThreadPool()`，也就是一段时间内没有异步任务时，
自动释放内部的线程，符合大部分应用的需求。

## 核心方法调用顺序 ##

#### Widget ####

外部通过调用 `render` 方法，获得一个 View，进行渲染

#### StatelessWidget ####
需要实现一个 `Widget<T> build()` 方法，来完成 `Widget` 的构建

```
render(first call) -> build -> widget.render -> initWidget -> update
```

#### state ####

```
setState -> willUpdate -> update -> didUpdate

onDestroy -> dispose
```

#### StatefulWidget ####
需要实现一个 `State<T> createState(Context context)` 方法 来构建一个 `State` 对象

```
render(first call) -> createState -> state.init -> state.build -> widget.render -> initWidget -> update

state.setState -> state.update -> widget.update
```

#### AndroidWidget ####

```
构造方法 -> createView

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

通过绑定 `Lifecycle` 来让 `Widget` 获得完整的生命周期
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

onStart -> updateProps(when has LayoutParams)
```

`initView` 是在 `render` 之后触发的

#### ViewGroupWidget ####

```
render(first call) -> children.render -> super.render(render self) -> add children to ViewGroup

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
- [ ] 应用状态管理(类似 redux mobx)
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
- [x] EditWidget
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
- [x] 进阶教程
- [x] Widgets
- [x] 异步线程策略
- [x] 内部结构
- [ ] 目录
- [ ] 英文版
- [x] To Do List

