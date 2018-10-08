

## Widget ##

#### 在 Android 中渲染 ####

外部通过调用 render 方法，获得一个 View，进行渲染

## StatelessWidget ##
承 Widget，无状态控件
需要实现一个 Widget<T> build() 方法，来完成 Widget 的构建

## StatefulWidget ##

继承 Widget，内部带有一个 State
需要实现一个 State<T> createState(Context context) 方法 来构建一个 State 对象

## AndroidWidget ##

承 Widget，带有 Android 常用的构建方法 和 生命周期

#### 构建过程 ####

构造方法 -> createView

render(first call) -> bind lifecycle

onStart -> initView -> bindEvent -> initData -> updateView

#### 生命周期 ####
通过 Activity 或 Fragment 中绑定 Lifecycle 来让 Widget 获得完整的生命周期

onStart
onResume
onPause
onStop
onDestroy

#### BaseAndroidWidget ####

initView -> initProps -> updateProps

#### ViewGroupWidget ####

构造方法 -> addChildren -> updateChildrenProps && updateProps 

updaetView 中，调用 children 的 updateView 或 setState 或 update 方法



## LifecycleStatelessWidget ##

带有生命周期的 StatelessWidget

## LifecycleStatefulWidget ##

带有生命周期的 StatelfulWidget

## 容器 ##

Widget 类似于 View，是一个原子性的控件，并不能包容其他Widget，比如 BaseAndroidWidget。
而我们一般使用的都是 LifecycleStatefulWidget 或 LifecycleStatelessWidget，他里面可以组合一个或多个 Widget

