<!DOCTYPE html>
<html>
<head>
    <title>Отчет</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="./templates/clean/style.css">
</head>
<body>
    <<div class="page">
        <div class="content">
            <!-- titul page   -->
            <div class="titul">
                <div class="center">
                    <img class="logo" src="./templates/clean/logo.png">
                    <p class="str1">
                        МИНИСТЕРСТВО НАУКИ И ВЫСШЕГО ОБРАЗОВАНИЯ РОССИЙСКОЙ ФЕДЕРАЦИИ
                    </p>
                    <p class="str2">
                        Федеральное государственное бюджетное образовательное учреждение <br> высшего образования
                    </p>
                    <p class="str3">
                        "МИРЭА - Российский технологический университет"
                    </p>
                    <p class="str4">
                        РТУ МИРЭА
                    </p>
                    <hr class="hr1"/>
                    <p class="str5" >
                        Институт информационных технологий (ИТ)
                    </p>
                    <p class="str6">
                        Кафедра инструментального и прикладного <br> программного обеспечения (ИиППО)
                    </p>
                    <hr class="hr2"/>
                </div>
                <div class="container">
                    <p class="str7">
                        <b>ПРАКТИЧЕСКИЕ РАБОТЫ</b>  <br> <b>по дисциплине</b> <br> «Программирование на языке Джава»
                    </p>
                    <div class="names">
                        <div class="student">
                            <p>
                                Выполнил студент группы <span>_________________</span>
                            </p>
                            <i class="name">
                                ${student} <#-- to change -->
                            </i>
                        </div>
                        <div class="assistant">
                            <p>
                                Принял
                            </p>
                            <i class="name">
                                ${teacher} <#-- to change -->
                            </i>
                        </div>
                    </div>
                    <div class="bottom">
                        <p class="str1">
                        <span class="item1">
                            Практические работы выполнены
                        </span>
                            <span>
                            «___»_______2020г
                        </span>
                        </p>
                        <p class="str2">
                        <span class="item1">
                            «Зачтено»
                        </span>
                            <span>
                            «___»_______2020г.
                        </span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <p class="running-title">
            Москва 2020
        </p>
    </div>

    <<div class="page">
        <div class="content">
            <h1 class="h1">
                ${prac_number} <#-- to change -->
            </h1>
            <div>
                <h2 class="h2">
                    Цель работы
                </h2>
                <p class="text">
                    ${target_content} <#-- to change -->
                </p>

                <h2 class="h2">
                    Теоретическое введение
                </h2>
                <p class="text">
                    ${teor_content} <#-- to change -->
                </p>

                <h2 class="h2">
                    Ход работы
                </h2>
                <p class="text">
                    ${step_by_step} <#-- to change -->
                </p>
            </div>
        </div>
        <p class="running-title">
            Москва 2020
        </p>
    </div>

    <<div class="page">
        <div class="content">
            <h2 class="h2">
                Код
            </h2>
            <pre class="code">
                ${all_code} <#-- to change -->
            </pre>
        </div>
        <p class="running-title">
            Москва 2020
        </p>
    </div>

    <<div class="page">
        <div class="content">
            <h2 class="h2">
                Выводы по работе
            </h2>
            <p class="text">
                ${conclusion_content} <#-- to change -->
            </p>

            <h2 class="h2">
                Используемая литература
            </h2>
            <p class="text">
                ${literature_content} <#-- to change -->
            </p>
        </div>
        <p class="running-title">
            Москва 2020
        </p>
    </div>

</body>
</html>
