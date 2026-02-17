package br.com.senai;

// Importação da biblioteca Eclipse Paho (gerenciada via Maven no pom.xml)
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Classe principal do sistema de monitoramento (Capacidade 6 e 8)
 * Utiliza o paradigma de Orientação a Objetos para facilitar a manutenção e escalabilidade.
 */
public class AppColetor {
    public static void main(String[] args) {
        // Definição dos parâmetros de rede e protocolo
        String broker = "tcp://broker.hivemq.com:1883"; // Endereço do servidor Broker
        String clientId = "JavaClient_Mateus" + System.currentTimeMillis(); // ID único para evitar conflitos
        String topic = "senai/mateus/temperatura"; // Tópico subscrito (mesmo definido no ESP32)

        try {
            // Instanciação do cliente MQTT (Objeto da biblioteca Paho)
            MqttClient client = new MqttClient(broker, clientId);
            
            // Configurações de conexão (Capacidade 2)
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true); // Garante que sessões antigas não interfiram

            System.out.println("Conectando ao Broker..");
            client.connect(options); // Estabelece o handshake com o servidor
            System.out.println("Conectado com sucesso!");

            // Assinando o tópico e definindo a Interface de Resposta (IMqttMessageListener)
            // O software "escuta" o tópico e reage instantaneamente quando um dado chega.
            client.subscribe(topic, (t, msg) -> {
                // Conversão da carga útil (Payload) de Bytes para String
                String payload = new String(msg.getPayload());
                
                // Conversão do dado (Parsing) de texto para número real (Double)
                double temp = Double.parseDouble(payload);
                
                System.out.println("\n----------------------------");
                System.out.println("DADO RECEBIDO DA AUTOMAÇÃO:");
                System.out.println("Temperatura: " + temp + "°C");

                // Lógica de Atuação e Segurança (Desafio Extra)
                // Se a temperatura ultrapassar 30°C, o sistema sinaliza estado crítico.
                if (temp > 30.0) {
                    System.err.println("STATUS: [ALERTA] Temperatura Crítica!");
                } else {
                    System.out.println("STATUS: [NORMAL] Operação estável.");
                }
            });

        } catch (MqttException e) {
            // Tratamento de exceções para garantir a resiliência do software
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }
}