package br.com.senai;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Código Adaptado para Teste de Segurança (Capacidade 7 e 11)
 * Simulação de falha de autenticação.
 */
public class AppColetorERROR {
    public static void main(String[] args) {
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "JavaClient_Erro_Teste_" + System.currentTimeMillis();

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            
            // --- SIMULAÇÃO DE ERRO DE CREDENCIAIS ---
            // Inserindo dados deliberadamente incorretos para testar a segurança
            options.setUserName("usuario_nao_autorizado");
            options.setPassword("senha_errada_123".toCharArray());
            options.setCleanSession(true);

            System.out.println("Iniciando Teste de Segurança...");
            System.out.println("Tentando conectar ao Broker com credenciais inválidas...");
            
            client.connect(options); // A tentativa de conexão ocorre aqui

            // Se as credenciais fossem aceitas, o código continuaria aqui:
            System.out.println("Conectado inesperadamente (Verifique as configurações do Broker).");

        } catch (MqttException e) {
            // Captura do erro e exibição de evidências para o relatório
            System.err.println("\n[EVIDÊNCIA DE TESTE - ACESSO NEGADO]");
            System.err.println("Causa da falha: " + e.getMessage());
            System.err.println("Código do Motivo (Reason Code): " + e.getReasonCode());
            
            // Explicação técnica do erro 4 ou 5
            if (e.getReasonCode() == 4 || e.getReasonCode() == 5) {
                System.err.println("Diagnóstico: O Broker recusou a conexão por falha de autenticação.");
            }
        }
    }
}
