package cn.myweb01.money01.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*线程池工具类
* 使用枚举实现单例
* */
public enum ContexThredPool {
    INSTANCE;

    public ThreadPoolExecutor getThreadPool() {
        return ThreadPoolHolder.pool;
    }

    private static class ThreadPoolHolder {
        /**
         * 阻塞队列的容量
         */
        private final static int CAPACITY = 500;

        private static ThreadPoolExecutor pool;
        /**
         * 获取处理器数目
         */
        private static int availableProcessors = Runtime.getRuntime().availableProcessors();

        /**
         * 基于LinkedBlockingQueue的容量为{@link}
         */
        private static BlockingQueue queue = new LinkedBlockingQueue(CAPACITY);

        static {
            pool = new ThreadPoolExecutor(
                    availableProcessors * 2,
                    availableProcessors * 4 + 1,
                    0,
                    TimeUnit.MILLISECONDS,
                    queue,
                    new ThreadFactory() {
                        private AtomicInteger count = new AtomicInteger(0);

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread thread = new Thread(r);
                            //String threadName = EnvirmentThreadPool.class.getSimpleName() + "-thread-" + count.addAndGet(1);
                            thread.setName("我的线程池");
                            return thread;
                        }
                    },
                    //自定义线程池FULL时的策略,新的任务阻塞在队列外面;
                    (r, executor) -> {
                        try {
                            queue.put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }


    }
}
