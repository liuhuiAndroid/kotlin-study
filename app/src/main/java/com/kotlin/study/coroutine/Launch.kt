package com.kotlin.study.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedReceiveChannelException

/**
 * 演示协程使用更小的内存
 * 如何正确的在 Android 上使用协程 ？: https://zhuanlan.zhihu.com/p/79224185
 */
fun main(args: Array<String>) {
//    fun1()
//    fun2()
//    fun3()
//    fun4()
//    fun5()
//    fun6()
//    fun7()
//    fun8()
//    fun9()
//    fun10()
    // ==================
//    fun11()
//    fun12()
//    fun13()
//    fun14()
//    fun15()
    // ==================
//    fun16()
//    fun17()
    fun18()
}

fun fun18() {

}

fun fun17() = runBlocking {
    val channel = Channel<Int>()  //定义一个通道
    launch(Dispatchers.Default) {
        repeat(5) {
                i ->
            delay(200)
            channel.send((i+1) * (i+1))
            if (i==2) {  // 发送3次后关闭
                channel.close()
            }
        }
    }
    launch(Dispatchers.Default) {
        repeat(5) {
            try {
                println(channel.receive())
            }catch (e: ClosedReceiveChannelException){
                println("There is a ClosedReceiveChannelException.") // channel 异常则打印
            }
        }
    }
    delay(2000)
    println("Receive Done!")
}

fun fun16() = runBlocking {
    val channel = Channel<Int>()  //定义一个通道
    launch(Dispatchers.Default) {
        repeat(5) { // 重复5次发送消息，相当于 for(int i=0;i<5;i++)
                i ->
            delay(200)
            channel.send((i + 1) * (i + 1))
        }
    }
    launch(Dispatchers.Default) {
        repeat(5) {
            println(channel.receive())
        }
    }
    delay(2000)
    println("Receive Done!")
}

fun fun15() {
    try {
        GlobalScope.launch {
            val job: Deferred<String> = GlobalScope.async {
                throw RuntimeException("this is an exception")
                "doSomething..."
            }
            job.await()
        }
    } catch (e:Exception) {

    }
    Thread.sleep(5000)
}

fun fun14() = runBlocking<Unit> {
    val job = Job() // 创建一个 job 对象来管理生命周期
    launch(coroutineContext+job)  {
        delay(500)
        println("job1 is done")
    }
    launch(coroutineContext+job) {
        delay(1000)
        println("job2 is done")
    }
    launch(Dispatchers.Default+job) {
        delay(1500)
        println("job3 is done")
    }
    launch(Dispatchers.Default+job) {
        delay(2000)
        println("job4 is done")
    }
    launch(Dispatchers.Default+job) {
        delay(2500)
        println("job5 is done")
    }
    delay(1800)
    println("Cancelling the job!")
    job.cancel() // 取消任务
}

fun fun13() {
    val job = GlobalScope.launch {
        val childJob = GlobalScope.launch(Dispatchers.Default  + coroutineContext) {
            println("childJob: I am a child of the request coroutine, but with a different dispatcher")
            delay(1000)
            println("childJob: I will not execute this line if job is cancelled")
        }
        childJob.join()
    }
    Thread.sleep(500)
    job.cancel() // 取消请求
    Thread.sleep(2000)
}

fun fun12()= runBlocking {
    val job = launch {
        // 子协程
        val job1 = launch(coroutineContext)  {
            println("job1 is running")
            delay(1000)
            println("job1 is done")
        }
        // 子协程
        val job2 = launch(coroutineContext) {
            println("job2 is running")
            delay(1500)
            println("job2 is done")
        }
        // 子协程
        val job3 = launch(coroutineContext) {
            println("job3 is running")
            delay(2000)
            println("job3 is done")
        }
        job1.join()
        job2.join()
        job3.join()
    }
    job.join()
    println("all the jobs is complete")
}

fun fun11() {
    // 创建一个协程，并在内部再创建两个协程
    val job = GlobalScope.launch {
        // job1 采用全局作用域来创建协程
        val job1 = GlobalScope.launch {
            println("job1: I have my own context and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the job")
        }
        // 第二个继承父级上下文
        val job2 = launch(coroutineContext) {
            println("job2: I am a child of the job coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent job is cancelled")
        }
        job1.join()
        job2.join()
    }
    Thread.sleep(500)
    job.cancel() // 取消job
    Thread.sleep(2000)
}

fun fun10() = runBlocking {
    val jobs = ArrayList<Job>()
    jobs += launch(Dispatchers.Unconfined) { // 无限制，在当前默认的协程中运行
        println("'Unconfined': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) { // 使用父级的上下文，也就是 runBlocking 的上下文
        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        println("'Dispatchers.Default': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs += launch {
        println("'default': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs += launch(newSingleThreadContext("MyThread")) { // 创建自己的新线程
        println("'MyThread': I'm working in thread ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}

fun fun9() {
    GlobalScope.launch {
        val result1 = withContext(Dispatchers.Default) {
            delay(2000)
            1
        }
        val  result2 = coroutineScope {
            delay(1000)
            2
        }
        val  result = result1 + result2
        println(result)
    }
    Thread.sleep(5000)
}

fun fun8() {
    val job =  GlobalScope.launch {
        withContext(NonCancellable) {
            delay(2000)
            println("this code can not be cancel")
        }
    }
    job.cancel()
    Thread.sleep(5000)
}

fun fun7() {
    GlobalScope.launch {
        val result1 = withContext(Dispatchers.Default) {
            delay(2000)
            1
        }
        val  result2 = withContext(Dispatchers.IO) {
            delay(1000)
            2
        }
        val  result = result1 + result2
        println(result)
    }
    Thread.sleep(5000)
}

fun fun6() = runBlocking {
    val job1 = launch {
        println(1)
        yield()
        println(3)
        yield()
        println(5)
    }
    val job2 = launch {
        println(2)
        yield()
        println(4)
        yield()
        println(6)
    }
    println(0)
    // 无论是否调用以下两句，上面两个协程都会运行
//    job1.join()
//    job2.join()
}

fun fun5() = runBlocking{
    println("1: current thread is ${Thread.currentThread().name}")
    GlobalScope.launch {
        println("3: current thread is ${Thread.currentThread().name}")
        // delay() 是最常见的挂起函数，类似于线程的 sleep() 函数。但 delay() 并不会阻塞线程
        delay(1000L)
        println("4: current thread is ${Thread.currentThread().name}")
    }
    println("2: current thread is ${Thread.currentThread().name}")
    Thread.sleep(2000L)
    println("5: current thread is ${Thread.currentThread().name}")
}

/**
 * runBlocking 创建的协程直接运行在当前线程上，同时阻塞当前线程直到结束。
 */
fun fun4()= runBlocking {
    launch {
        delay(1000)
        println("Hello World!")
    }
    delay(2000)
}

fun fun3() {
    GlobalScope.launch {
        val  result = async{
            delay(2000)
            1
        }
        result.invokeOnCompletion {
            if (it!=null){
                println("exception: ${it.message}")
            } else {
                println("result is complete")
            }
        }
        // 取消任务
        result.cancelAndJoin()
        println(result.await())
    }
    Thread.sleep(5000)
}

/**
 * 使用 async 创建协程
 */
fun fun2() {
    GlobalScope.launch {
        // 使用 async 会返回一个 Deferred 对象
        val result1: Deferred<Int> = async {
            delay(2000)
            1
        }
        val result2: Deferred<Int> = async {
            delay(1000)
            2
        }
        // 通过 await() 方法将值返回
        val result = result1.await() + result2.await()
        println(result)
    }
    Thread.sleep(5000)
}

/**
 * 使用 launch 创建协程
 */
fun fun1() {
    val job = GlobalScope.launch {
        delay(1000)
        println("Hello Coroutines!")
    }
    Thread.sleep(2000)
}
