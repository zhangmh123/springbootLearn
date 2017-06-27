package com.qf.rabbitmq.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.qf.rabbitmq.AppConstants;
import com.qf.rabbitmq.util.ApplicationContextUtil;
import com.rabbitmq.client.AMQP;

public class AsyncMQSenderThread implements Runnable {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String message;

	public AsyncMQSenderThread(String message) {
		this.message = message;
	}

	public void run() {
		Date sendTime = new Date();
		String correlationId = UUID.randomUUID().toString();

		MessagePropertiesConverter messagePropertiesConverter = (MessagePropertiesConverter) ApplicationContextUtil
				.getBean("messagePropertiesConverter");
		AsyncRabbitTemplate rabbitTemplate = (AsyncRabbitTemplate) ApplicationContextUtil
				.getBean("asyncRabbitTemplate");
		AMQP.BasicProperties props = new AMQP.BasicProperties("text/plain",
				"UTF-8", null, 2, 0, correlationId,
				AppConstants.REPLY_EXCHANGE_NAME, null, null, sendTime, null,
				null, "SpringProducer", null);

		MessageProperties sendMessageProperties = messagePropertiesConverter
				.toMessageProperties(props, null, "UTF-8");
		sendMessageProperties.setReceivedExchange(AppConstants.REPLY_EXCHANGE_NAME);
		sendMessageProperties.setReceivedRoutingKey(AppConstants.ASYNC_REPLY_ROUTINGKEY);
		sendMessageProperties.setRedelivered(true);
		Message sendMessage = MessageBuilder.withBody(message.getBytes()).andProperties(sendMessageProperties).build();

		logger.info("Send message to consumer");

		AsyncRabbitTemplate.RabbitMessageFuture replyMessageFuture = rabbitTemplate
				.sendAndReceive(AppConstants.SEND_EXCHANGE_NAME, AppConstants.SEND_ASYNC_MESSAGE_KEY,sendMessage);

		replyMessageFuture.addCallback(new ListenableFutureCallback<Message>() {
			public void onFailure(Throwable ex) {
				logger.info("The reply message fail:" + ex);
			}

			public void onSuccess(Message result) {
				//logger.info("Received the reply message success!");
				try {
					String replyMessageContent = new String(result.getBody(),
							"UTF-8");
					logger.info("The reply message is:" + replyMessageContent);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		logger.info("The following operation");
	}
}