# kotlin-study
重新学Kotlin

#### 开发环境搭建

Kotlin官网：<https://kotlinlang.org/>

Gradle是什么？

Gradle是一个灵活高效且支持多语音多平台的构建工具

gradlew gradlew.bat是Gradle Wrapper 启动脚本

#### 内置类型

#### 类型初步

#### 表达式

#### 函数进阶

#### 类型进阶

#### Kotlin泛型

#### Kotlin反射

#### Kotlin注解

#### 协程初步

1. 协程是什么

   协程是可以由程序自行控制挂起、恢复的程序

   协程可以用来实现多任务的协作执行

   协程可以用来解决异步任务控制流的灵活转移

2. 协程的作用

   协程可以让异步逻辑可以用同步代码的形式写出

   协程可以降低异步程序的设计复杂度

3. 协程的基本要素

   1. 挂起函数：以suspend修饰的函数
   2. 挂起函数只能在其他挂起函数或协程中调用
   3. 挂起函数的调用处称为挂起点
   4. 真正的挂起必须异步调用resume
   5. 将回调转写成挂起函数：使用suspendCoroutine获取挂起函数的Continuation
   6. 回调成功的分支使用Continuation.resume(value)
   7. 回调失败则使用Continuation.resumeWithException(e)

4. 协程的创建

   1. 协程是一段可执行的程序
   2. 协程的创建通常需要一个函数：suspend function
   3. 协程的创建也需要一个API：createCoroutine startCoroutine 

5. 协程上下文

   1. 协程执行过程中需要携带数据
   2. 索引是 CoroutineContext.Key
   3. 元素是 CoroutineContext.Element

6. 拦截器

   1. 拦截器 ContinuationInterceptor 是一类协程上下文元素
   2. 可以对协程上下文所在协程的 Continuation 进行拦截

#### 协程进阶

#### 协程应用