package com.example.laba11;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация интерфейса
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Фиксируем переменные на уровне метода или класса
        final int[] array = {1, 2, 3, 0, 4, 5, 7, 0, 7, 8}; // Массив должен быть final или effectively final

        calculateButton.setOnClickListener(v -> {
            // Создаем два потока для выполнения расчетов

            // Фиксируем переменную product перед лямбда-выражением
            final int product = calculateProduct(array);

            Thread productThread = new Thread(() -> {
                // Используем final переменную
                new Handler(Looper.getMainLooper()).post(() -> {
                    resultTextView.append("\nПроизведение четных индексов: " + product);
                });
            });

            // Фиксируем переменную sumBetweenZeros перед лямбда-выражением
            final int sumBetweenZeros = calculateSumBetweenZeros(array);

            Thread sumBetweenZerosThread = new Thread(() -> {
                // Используем final переменную
                new Handler(Looper.getMainLooper()).post(() -> {
                    resultTextView.append("\nСумма между первым и последним нулевыми: " + sumBetweenZeros);
                });
            });

            // Запускаем оба потока
            productThread.start();
            sumBetweenZerosThread.start();
        });
    }

    private int calculateProduct(int[] array) {
        int product = 1;
        for (int i = 0; i < array.length; i += 2) {
            product *= array[i];
        }
        return product;
    }

    private int calculateSumBetweenZeros(int[] array) {
        int firstZeroIndex = -1;
        int lastZeroIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                if (firstZeroIndex == -1) {
                    firstZeroIndex = i;
                }
                lastZeroIndex = i;
            }
        }

        int sum = 0;
        if (firstZeroIndex != -1 && lastZeroIndex > firstZeroIndex) {
            for (int i = firstZeroIndex + 1; i < lastZeroIndex; i++) {
                sum += array[i];
            }
        }

        return sum;
    }
}