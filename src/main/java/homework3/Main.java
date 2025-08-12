package homework3;


import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MyThreadPool pool = new MyThreadPool(4);

        for (int i =1; i<=4; i++){
            int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " started by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored){}
                System.out.println("Task " + taskId + " finished");
                    });
        }
        pool.shutdown();

        // попытка добавить еще одно задание после shutdown
        pool.execute(() -> {
            System.out.println("Task started by " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored){}
            System.out.println("Task  finished");
        });
        pool.awaitTermination();

        System.out.println("End");

    }

    static class MyThreadPool {
        private final List<Worker> workers = new LinkedList<>();

        private final LinkedList<Runnable> taskQueue = new LinkedList<>();

        private volatile boolean isRunning = true;

        public MyThreadPool(int poolSize) {
           for (int i = 1; i<= poolSize; i++){
               Worker worker = new Worker("Worker № " + i);
               workers.add(worker);
               worker.start();

           }
        }

        public void execute(Runnable task){
            synchronized (taskQueue){

                // добавил вызов исключения если попробовать добавить задачу после вызова shutdown, который сменил значение isRunning
                if (!isRunning) throw new IllegalStateException();
                taskQueue.add(task);
                taskQueue.notify();
            }
        }

        public void shutdown(){
            isRunning = false;
            synchronized (taskQueue){
                taskQueue.notifyAll();
            }
        }

        public void awaitTermination(){
            for (Worker worker : workers){
                try {
                    worker.join();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }

        private class Worker extends Thread{
            public Worker(String name) {
                super(name);

            }

            @Override
            public void run() {
               while (isRunning || !taskQueue.isEmpty()){
                  Runnable task;
                    synchronized (taskQueue){
                        while (taskQueue.isEmpty()){
                            if (!isRunning) return;
                            try {
                                taskQueue.wait();
                            }catch (InterruptedException e){
                                return;
                            }
                        }

                        task = taskQueue.removeFirst();
                    }
                    try {
                        task.run();
                    }catch (Exception e){
                        System.err.println(getName() + " error: " + e.getMessage());
                    }
                }
            }
        }

    }
}
