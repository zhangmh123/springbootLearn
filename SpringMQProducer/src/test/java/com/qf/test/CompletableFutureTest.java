package com.qf.test;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class CompletableFutureTest {
	private static final Logger logger = LoggerFactory
			.getLogger(CompletableFutureTest.class);
	private static ExecutorService executor = Executors.newFixedThreadPool(5);  
	//CompletableFuture和Future的get()调用都会阻塞
	//@Test
	public void testFuture() throws InterruptedException, ExecutionException {
		 Future<String> result = executor.submit(new Callable<String>() {

			public String call() throws Exception {
				 TimeUnit.SECONDS.sleep(3);  
				  return "hello";  
			}
		});  
	    logger.debug(result.get()); 
	}
	 
	/// @Test
	public void testCompletableFuture() throws InterruptedException, ExecutionException {
		    CompletableFuture<String> resultCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {  
		        public String get() {  
		            try {  
		                TimeUnit.SECONDS.sleep(3);  
		            } catch (InterruptedException e) {  
		                e.printStackTrace();  
		            }  
		            return "hello";  
		        }  
		    }, executor);  
	    logger.debug(resultCompletableFuture.get()); 
	}
	//注册类似一个回调函数去处理结果： 
	// @Test
	public void testCompletableCallback() {
		    CompletableFuture<String> resultCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {  
		        public String get() {  
		            try {  
		                TimeUnit.SECONDS.sleep(3);  
		                System.out.println(Thread.currentThread().getName());  
		            } catch (InterruptedException e) {  
		                e.printStackTrace();  
		            }  
		            return "hello";  
		        }  
		    }, executor);  
		    System.out.println(resultCompletableFuture.thenAccept(new Consumer<String>(){  
		        public void accept(String t) {  
		        	System.out.println(t);  
		        	System.out.println(Thread.currentThread().getName());  
		        }  
		    }));     
		    System.out.println(123); 
	}
	 //可以看出来，在CompletableFuture注册的回调函数的执行与其提交的任务的执行是同一个线程完成的。如果不想同一个线程来完成回调函数，可以这样：
	 	@Test
		public void testCompletableCallback2()  {
			    CompletableFuture<String> resultCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {  
			        public String get() {  
			            try {  
			                TimeUnit.SECONDS.sleep(3);  
			                System.out.println("当前线程名1："+Thread.currentThread().getName());  
			            } catch (InterruptedException e) {  
			                e.printStackTrace();  
			            }  
			            return "hello";  
			        }  
			    }, executor);  
			    resultCompletableFuture.thenAcceptAsync(new Consumer<String>() {  
			        public void accept(String t) {  
			        System.out.println("回调结果："+t);  
			        System.out.println("当前线程名2："+Thread.currentThread().getName());  
			        }  
			    }, executor);  
			    System.out.println(123); 
		}
	 //可以对通过completeExceptionally 函数对CompletableFuture发出异常通知：
	 	@Test
		public void testCompletableException()  {
	 	    CompletableFuture<String> resultCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {  
	 	        public String get() {  
	 	        try {  
	 	            TimeUnit.SECONDS.sleep(3);  
	 	            System.out.println("run--" + Thread.currentThread().getName());  
	 	        } catch (InterruptedException e) {  
	 	            e.printStackTrace();  
	 	        }  
	 	        return "hello";  
	 	        }  
	 	    }, executor);  
	 	    resultCompletableFuture.thenAccept(new Consumer<String>() {  
	 	        public void accept(String t) {  
	 	        System.out.println("accept--" + t);  
	 	        System.out.println(Thread.currentThread().getName());  
	 	        }  
	 	    }).exceptionally(new Function<Throwable, Void>(){  
	 	        public Void apply(Throwable t) {  
	 	        System.out.println(t.getMessage());  
	 	        return null;  
	 	        }  
	 	    });  
	 	    resultCompletableFuture.completeExceptionally(new Exception("error"));  
	 	    System.out.println("over");  
		}
	 	@Test
	 	public void testthenApply() throws InterruptedException, ExecutionException{
	 		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new Supplier<String>() {

				public String get() {
					return "zero";
				}
			}, executor);  
	 		  
	 		CompletableFuture<Integer> f2 = f1.thenApply(new Function<String, Integer>() {  
	 		  
	 		    public Integer apply(String t) {  
	 		        System.out.println(t);  
	 		        return Integer.valueOf(t.length());  
	 		    }  
	 		});  
	 		  
	 		CompletableFuture<Integer> f3 = f2.thenApply(new Function<Integer, Integer>() {

				public Integer apply(Integer t) {
					// TODO Auto-generated method stub
					return (int) (t*2.0);
				}

				
			});  
	 		System.out.println(f3.get());  
	 	}
	 	//当thenCompose()用于链接一个future时依赖另一个thenCombine，当他们都完成之后就结合两个独立的futures
	 	@Test
	 	public void testThenCombine() throws InterruptedException, ExecutionException{
	 		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new Supplier<String>() {

				public String get() {
					return "zero";
				}
			}, executor);  
	 		  
	 		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new Supplier<String>() {

				public String get() {
					return "hello";
				}
			}, executor);  
	 		  
	 		CompletableFuture<String> reslutFuture =  f1.thenCombine(f2,new BiFunction<String,String,String>(){

				public String apply(String t, String u) {
					// TODO Auto-generated method stub
					 return t.concat(u);  
				}	 			
	 		});
	 		System.out.println(reslutFuture.get());  
	 	}
	 	//等待所有的 CompletableFutures 完成
	 	//如果不是产生新的CompletableFuture连接这两个结果，我们只是希望当完成时得到通知，
	 	//我们可以使用 thenAcceptBoth()/runAfterBoth()系列的方法
	 	public void testThenAcceptBoth() throws InterruptedException, ExecutionException{
	 		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(new Supplier<String>() {

				public String get() {
					return "zero";
				}
			}, executor);  
	 		  
	 		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(new Supplier<String>() {

				public String get() {
					return "hello";
				}
			}, executor);  
	 		  
	 		CompletableFuture<Void> reslutFuture =  f1.thenAcceptBoth(f2,new BiConsumer<String,String>(){


				public void accept(String t, String u) {
					  System.out.println(t + " over");  
				      System.out.println(u + " over");  
				}
	 		});
	 		System.out.println(reslutFuture.get());  
	 	}
	 	//等待第一个 CompletableFuture 来完成任务
	 	//等待第一个（与所有相反）完成的future。当有两个相同类型任务的结果时就显得非常方便，
	 	//只要关心响应时间就行了，没有哪个任务是优先的。
	 	 private static void testRunAfterEither() throws InterruptedException, ExecutionException {  
	         System.out.println("CompletableFuture");  
	         CompletableFuture<Void> futureA = CompletableFuture.runAsync(new Runnable() {
				public void run() {
					work("A");
				}
			}, executor);  
	         CompletableFuture<Void> futureB = CompletableFuture.runAsync(new Runnable() {
					public void run() {
						work("B");
					}
				}, executor);  
	         futureA.runAfterEither(futureB, new Runnable() {
				public void run() {
					work("C");
				}
			}).get();  
	     }  
	   
	     public static Void work(String name) {  
	    	  Random random = new Random();  
	         System.out.println(name + " starts at " + LocalTime.now());  
	         try {  
	             TimeUnit.SECONDS.sleep(random.nextInt(10));  
	         } catch (InterruptedException e) {  
	         }  
	         System.out.println(name + " ends at " + LocalTime.now());  
	         return null;  
	     }  
	 public static void main(String[] args) throws InterruptedException, ExecutionException {
		 CompletableFutureTest test = new CompletableFutureTest();
		 //test.testCompletableCallback2();
		 //test.testCompletableException();
		 //test.testthenApply();
		 //test.testThenCombine();
		 //test.testThenAcceptBoth();
		 CompletableFutureTest.testRunAfterEither();
	}
}