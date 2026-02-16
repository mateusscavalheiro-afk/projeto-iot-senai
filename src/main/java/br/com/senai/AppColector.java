package br.com.senai;

import java.util.Random;

@SuppressWarnings("all")

/*
  PROJETO: Coletor de dados de Automação
  OBJETIVO: Exemplificar a : - Coleta;
                             - Tratamento; e
                             - Validação de dados Indústriais.
 */

public class AppColector {
    public static void main(String[] args) {

        //Exibição do cabeçalho: Simula a inicialização de um sistema de informação
        System.out.println("==========================================");
        System.out.println("== Industrial Monitoring System -- V0.1 ==");
        System.out.println("==========================================");

        //Criação de um laço (loop) para repetição da coleta 5x
        //OBS: Isso é uma simulação, na vida real, isso rodaria infinitamente para monitorar a máquina

        for (int i = 1; i <= 5; i++) {
            System.out.println("\nReading process data.. - Cicle N° " + i + ".");

            //1.Coleta de Dados
            double value_temp = read_sensor("Temperature_OVEN_01");

            //2/3.Tratamento de Dados e Validação
            validate_data_Sec("Temperature", value_temp, 20.0, 80.0);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("| ERR0R: ERROR IN TIME! |");
            }
        }

        System.out.println("\n");
        System.out.println("===================");
        System.out.println("== COLLECT ENDED ==");
        System.out.println("===================");
    }

    //Métodos Especiais (1)
    public static double read_sensor(String tag) {
        Random rand = new Random();
        //Gerar de fato um número entre 10.0 - 100.00 para simular a variação de temperatura real do processo
        double read_value = 10 + (100 - 10) * rand.nextDouble();
        return read_value;
    }

    //Métodos Especiais (2)
    public static void validate_data_Sec(String sensor_name, Double value_sensor, Double min, Double max) {
        //Exibir valor formatado com duas casas decimais
        System.out.printf("Sensor: %s | Actual Value: %.2f°C", sensor_name, value_sensor);

        //Lógica de Programação
        if (value_sensor >= min && value_sensor <= max) {
            System.out.println("\n >> Normal Operation << ");
        } else {
            System.out.println("\n >> Outside of Security levels << ");
        }
    }
}