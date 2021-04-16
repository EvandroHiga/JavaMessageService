package br.com.jms.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
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
		
		Message message = msgConsumer.receive();
		System.out.println("### MSG RECEBIDA: " + message + "###");
		
		connection.close();
		initialContext.close();
	}
}
