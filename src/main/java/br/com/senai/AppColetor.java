package br.com.senai;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class AppColetor {
    public static void main(String[] args) {
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "JavaClient_Aluno_" + System.currentTimeMillis(); 
        String topic = "senai/dados/temperatura";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            System.out.println("Conectando ao Broker....");
            client.connect(options);
            System.out.println("Conectado com sucesso!");

            // Assinando o tópico
            client.subscribe(topic, (t, msg) -> {
                String payload = new String(msg.getPayload());
                double temp = Double.parseDouble(payload);
                
                System.out.println("\n----------------------------");
                System.out.println("DADO RECEBIDO DA AUTOMAÇÃO:");
                System.out.println("Temperatura: " + temp + "°C");

                // Lógica do Desafio Extra
                if (temp > 30.0) {
                    System.err.println("STATUS: [ALERTA] Temperatura Crítica!");
                } else {
                    System.out.println("STATUS: [NORMAL] Operação estável.");
                }
            });

        } catch (MqttException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
}