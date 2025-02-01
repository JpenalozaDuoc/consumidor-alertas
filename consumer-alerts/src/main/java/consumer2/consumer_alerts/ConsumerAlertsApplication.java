package consumer2.consumer_alerts;

//import com.rabbitmq.client.*;

//import java.io.IOException;
//import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableRabbit
public class ConsumerAlertsApplication {

	private final static String QUEUE_NAME = "myQueue";

	@Autowired
    private AlertaPacientesRepository alertaPacientesRepository;

	public static void main(String[] args) {
        SpringApplication.run(ConsumerAlertsApplication.class, args);
    }

	/*
    public static void main(String[] argv) throws IOException, TimeoutException {
        // Establecer conexión y canal a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declarar la cola de la cual consumir con la propiedad durable
        boolean durable = true;

        // Declarar la cola de la cual consumir
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Definir la función de callback para el consumidor
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        // Consumir mensajes de la cola
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
	*/

    // Método que se ejecuta cada vez que un mensaje es recibido desde la cola de RabbitMQ
    @RabbitListener(queues = QUEUE_NAME)
    @Transactional  // Asegura que el mensaje se guarda de forma transaccional en la base de datos
    public void receiveMessage(String message) {
        System.out.println(" [x] Received '" + message + "'");

        // Crear un nuevo objeto AlertaPacientes y guardarlo en la base de datos
        AlertaPacientes alerta = AlertaPacientes.builder()
            .messagge(message)
            .build();

        // Guardar la alerta en la base de datos
        alertaPacientesRepository.save(alerta);
        System.out.println(" [x] Message saved to database: " + message);
    }



}
