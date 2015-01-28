2. Single Threaed Execution Pattern
	只要有一个线程进入临界区间，其他的线程就不能进入，而被请求等待。
3. Guarded Suspension Pattern(被阻挡而暂时中断执行)
	线程要不要等待，则是依警戒条件决定的，"有条件的 synchronized"
	想要在一定的时间后中断操作时，我们可以在调用wait方法时，在参数里面指定终止(timeout)的时间。
	详见Balking Pattern(第四章)的进阶说明