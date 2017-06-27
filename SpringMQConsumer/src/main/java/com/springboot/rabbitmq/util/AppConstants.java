package com.springboot.rabbitmq.util;

public class AppConstants {
	
	public final static String SEND_MESSAGE_KEY = "springMessage";
	
	public final static String SEND_QUEUE_NAME = "springMessageQueue";
	
	public final static String SEND_EXCHANGE_NAME = "springMessageExchange";
	
	public final static String REPLY_QUEUE_NAME = "springReplyMessageQueue";
	
	public final static String REPLY_EXCHANGE_NAME = "springReplyMessageExchange";
	
	public final static String REPLY_MESSAGE_KEY = "springMessageReply";
	
	public final static String SEND_ASYNC_MESSAGE_KEY = "springAsyncMessage";
	
	public final static String REPLY_ASYNC_QUEUE_NAME = "springAsyncReplyMessageQueue";
	
	public static final String ASYNC_REPLY_ROUTINGKEY = "springAsyncReplyMessage"; 
	
	public final static String SEND_BATCH_QUEUE_NAME = "springBatchMessageQueue";
	
	public final static String REPLY_BATCH_QUEUE_NAME = "springBatchReplyMessageQueue";
	
	public final static String SEND_BATCH_EXCHANGE_NAME = "springBatchMessageExchange";
	
	public final static String SEND_BATCH_MESSAGE_KEY = "springBatchMessage";
	
	public final static String REPLY_BATCH_MESSAGE_KEY = "springBatchMessageReply";
	
}
