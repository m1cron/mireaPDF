<#import "parts/base.ftl" as base>
<#import "parts/page.ftl" as page>
<#import "titul.ftl" as titul>
<@base.base>
    <@page.page>
        <@titul.titul>
        </@titul.titul>
    </@page.page>

    <@page.page>
        <h1 class="h1">
            Практическая работа 2
        </h1>
        <div>
            <h2 class="h2">
                Цель работы
            </h2>
            <p class="text">
                Освоить на практике работу с абстрактными классами и наследованием на Java.
            </p>

            <h2 class="h2">
                Теоретическое введение
            </h2>
            <p class="text">
                abstract void yourMethod();
                Класс, содержащий абстрактные методы, называется абстрактным классом. Такие классы при определении помечаются ключевым словом abstract.
                Абстрактный метод внутри абстрактного класса не имеет тела, только прототип. Он состоит только из объявления и не имеет тела:
                По сути, мы создаём шаблон метода. Например, можно создать абстрактный метод для вычисления площади фигуры в абстрактном классе Фигура. А все другие производные классы от главного класса могут уже реализовать свой код для готового метода. Ведь площадь у прямоугольника и треугольника вычисляется по разным алгоритмам и универсального метода не существует.
                Если вы объявляете класс, производный от абстрактного класса, но хотите иметь возможность создания объектов нового типа, вам придётся предоставить определения для всех абстрактных методов базового класса. Если этого не сделать, производный класс тоже останется абстрактным, и компилятор заставит пометить новый класс ключевым словом abstract.
                Абстрактный класс не может содержать какие-либо объекты, а также абстрактные конструкторы и абстрактные статические методы. Любой подкласс абстрактного класса должен либо реализовать все абстрактные методы суперкласса, либо сам быть объявлен абстрактным.
            </p>

            <h2 class="h2">
                Ход работы
            </h2>
            <p class="text">
                test
            </p>
        </div>
    </@page.page>

    <@page.page>
        <h2 class="h2">
            Код
        </h2>
        <pre class="code">
<strong>src/ru/micron/task1/Main.java</strong>

package ru.micron.task1;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void num3(){
        int[] arr = {1,2,3,4};
        int res = 0;
        for (int i = 0; i < arr.length; i++)
            res += arr[i];
        System.out.println(res);
    }

    public static void num4(String[] args) {
        for (int i = 0; i < args.length; i++)
            System.out.println(args[i]);
    }

    public static void num5() {
        for (int i = 1; i <= 10; i++){
            System.out.printf("%.2f\t", (float)1 / i);
        }
    }

    public static void num6(){
        int[] arr = new int[10];
        for (int i = 0; i < 10; i++)
            arr[i] = (int)(Math.random() * 100);
        for(int i = 0; i < 10; i++)
            System.out.println(arr[i]);
        Arrays.sort(arr);
        for(int i = 0; i < 10; i++)
            System.out.println(arr[i]);
        Random ran = new Random(100);
        for(int i = 0; i < 10; i++)
            arr[i] = ran.nextInt(100);
        Arrays.sort(arr);
        for(int i = 0; i < 10; i++)
            System.out.println(arr[i]);
    }
    public static void num7(int fac){
        if (fac == 0){
            System.out.println(1);
            return;
        }
        long res = 1;
        for(int i = 1; i <= fac; i++)
            res *= i;
        System.out.println(res);
    }

    public static void main(String[] args) {
        //num3();
        //num4(args);
        //num5();
        //num6();
        num7(5);
    }
}
            </pre>
    </@page.page>

    <@page.page>
        <h2 class="h2">
            Выводы по работе
        </h2>
        <p class="text">
            Я научился работать с абстрактными классами. Появилось понимание наследования в Java.
        </p>

        <h2 class="h2">
            Используемая литература
        </h2>
        <p class="text">
            1.	Конспект лекций по дисциплине “Программирование на языке Java”, РТУ МИРЭА. Лектор – Зорина Н.В.
        </p>
    </@page.page>
</@base.base>