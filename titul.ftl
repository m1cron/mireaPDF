<!DOCTYPE html>
<html>
<head>
    <title>Отчет</title>
    <meta charset="utf-8" />
    <style>
        .center-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 30px;
        }

        .left-content {
            margin-left: 50px;
        }

        body {
            font-family: "Times New Roman", Times, serif;
        }

        .content-margin {
            margin-top: 30px;
            align-items: center;
        }

        .content-margin p {
            margin-top: 5px;
            align-items: center;
        }
    </style>
</head>
<body>
<div class="center-content">
    <img class="titul_logo" src="data:image/jpeg;base64,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"/>
    <p>Федеральное государственное бюджетное образовательное учреждение высшего образования</p>
    <b style="margin-top: 10px;">«МИРЭА – Российский технологический университет»</b>
    <h2 style="margin-top: 6px;">РТУ МИРЭА</h2>
    <hr style="width: 55%; border-style: double;"/>
    <h1 style="margin-top: 5px;">Институт информационных</h1>
    <h1 style="margin-top: 20px;">технологий</h1>
    <p>КАФЕДРА ИНСТРУМЕНТАЛЬНОГО И ПРИКЛАДНОГО</p>
    <p style="margin-top: 10px;">ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ (ИиППО)</p>
    <hr style="width: 55%;"/>
    <p style="margin-top: 60px;">ПРАКТИЧЕСКИЕ РАБОТЫ</p>
    <p style="margin-top: 10px;">ПО ДИСЦИПЛИНЕ «Программирование на языке Джава»</p>
</div>
<div class="left-content">
    <p style="margin-top: 100px;">Выполнил студент группы ${group} ${student}</p>
    <p style="margin-top: 40px;">Принял&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>${teacher}</i></p>
    <p style="margin-top: 40px;">Практические работы работа выполнены «___»_______${year}г</p>
</div>
<div class="center-content">
    <p style="margin-top: 10px;">«Зачтено» «___»_______${year}г.</p>
    <p style="margin-top: 70px;">Москва ${year}</p>
</div>
<strong>Практическая работа ${prac_number}</strong> <br/>
<div class="content-margin">
    <strong>Цель работы</strong>
    <p>${target_content}</p>
    <strong>Теоретическое введение</strong>
    <p>${teor_content}</p>
    <strong>Ход работы</strong>
    <p>${step_by_step}</p>
    <strong>Код</strong>
    <pre>${all_code}</pre>
    <strong>Выводы по работе</strong>
    <p>${conclusion_content}</p>
    <strong>Используемая литература</strong>
    <p>${literature_content}</p>
    </div>
</body>
</html>
