package com.qf.rabbitmq.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.qf.rabbitmq.service.AsyncMQSenderThread;
import com.qf.rabbitmq.service.BatchMQSenderThread;
import com.qf.rabbitmq.service.HandlerMQSenderThread;
import com.qf.rabbitmq.service.MQSenderSupplier;
import com.qf.rabbitmq.service.SendMessageService;

@Service("sendMessageService")
public class SendMessageServiceImpl implements SendMessageService {
	@Autowired
	private ThreadPoolTaskExecutor executor;

	public String sendMessage(String message) {
		CompletableFuture<String> resultCompletableFuture = CompletableFuture
				.supplyAsync(new MQSenderSupplier(message), executor);
		try {
			String result = resultCompletableFuture.get();
			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public void sendAsyncMessage(String message) {
		CompletableFuture<Void> resultCompletableFuture = CompletableFuture
				.runAsync(new AsyncMQSenderThread(message), executor);
		

	}

	public void sendBatchMessage(List<String> messages) {
		// TODO Auto-generated method stub
		CompletableFuture.runAsync(new BatchMQSenderThread(messages), executor);  
	}

	public void sendObjectMessage(Object message) {
		// TODO Auto-generated method stub
		CompletableFuture.runAsync(new HandlerMQSenderThread(message), executor);  
	}
}
