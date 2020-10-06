<#macro titul>
    <!-- titul page   -->
    <div class="titul">
        <div class="center">
            <img class="logo" src="clean/logo.png">
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
                      ${student_name} <#-- to change -->
                    </i>
                </div>
                <div class="assistant">
                    <p>
                        Принял
                    </p>
                    <i class="name">
                        ${assistant_name} <#-- to change -->
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
</#macro>
