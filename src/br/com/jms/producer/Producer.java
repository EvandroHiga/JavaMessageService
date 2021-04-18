package br.com.jms.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Producer {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		System.out.println("### Producer: conexao estabelecida ###");
		
		Destination fila = (Destination) initialContext.lookup("FilaTestDestination");
		
		/*
		 * false: define se queremos usar explicitamente o tratamento da transação.
		 * Session.AUTO_ACKNOWLEDGE: confirmacao automatica do recebimento da msg JMS
		 */
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer msgProducer = session.createProducer(fila);
		
		for(int i=0; i<=1000; i++) {
			msgProducer.send(session.createTextMessage("<pedido><id> " + i + " </id></pedido>"));
		}
		
		connection.close();
		initialContext.close();
	}

}
