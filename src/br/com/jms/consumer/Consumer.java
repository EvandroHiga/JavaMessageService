package br.com.jms.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class Consumer {
	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		System.out.println("### conexao estabelecida ###");
		
		Destination fila = (Destination) initialContext.lookup("FilaTestDestination");
		
		/*
		 * false: define se queremos usar explicitamente o tratamento da transação.
		 * Session.AUTO_ACKNOWLEDGE: confirmacao automatica do recebimento da msg JMS
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer msgConsumer = session.createConsumer(fila);
		
		msgConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("[ " + textMessage.getText() + " ]");
					System.out.println("### pressione qualquer tecla para encerrar ###");
				} catch (JMSException e) {
					e.printStackTrace();
				}
							
			}
			
		});
		
		System.out.println("### message listener funcionando ###");
		new Scanner(System.in).nextLine();
		
		connection.close();
		initialContext.close();
	}
}
