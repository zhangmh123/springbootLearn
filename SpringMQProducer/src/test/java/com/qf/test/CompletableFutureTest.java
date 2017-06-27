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
	//CompletableFuture��Future��get()���ö�������
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
	//ע������һ���ص�����ȥ�������� 
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
	 //���Կ���������CompletableFutureע��Ļص�������ִ�������ύ�������ִ����ͬһ���߳���ɵġ��������ͬһ���߳�����ɻص�����������������
	 	@Test
		public void testCompletableCallback2()  {
			    CompletableFuture<String> resultCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {  
			        public String get() {  
			            try {  
			                TimeUnit.SECONDS.sleep(3);  
			                System.out.println("��ǰ�߳���1��"+Thread.currentThread().getName());  
			            } catch (InterruptedException e) {  
			                e.printStackTrace();  
			            }  
			            return "hello";  
			        }  
			    }, executor);  
			    resultCompletableFuture.thenAcceptAsync(new Consumer<String>() {  
			        public void accept(String t) {  
			        System.out.println("�ص������"+t);  
			        System.out.println("��ǰ�߳���2��"+Thread.currentThread().getName());  
			        }  
			    }, executor);  
			    System.out.println(123); 
		}
	 //���Զ�ͨ��completeExceptionally ������CompletableFuture�����쳣֪ͨ��
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
	 	//��thenCompose()��������һ��futureʱ������һ��thenCombine�������Ƕ����֮��ͽ������������futures
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
	 	//�ȴ����е� CompletableFutures ���
	 	//������ǲ����µ�CompletableFuture�������������������ֻ��ϣ�������ʱ�õ�֪ͨ��
	 	//���ǿ���ʹ�� thenAcceptBoth()/runAfterBoth()ϵ�еķ���
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
	 	//�ȴ���һ�� CompletableFuture ���������
	 	//�ȴ���һ�����������෴����ɵ�future������������ͬ��������Ľ��ʱ���Ե÷ǳ����㣬
	 	//ֻҪ������Ӧʱ������ˣ�û���ĸ����������ȵġ�
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