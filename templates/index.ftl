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
            ${practice_number} <#-- to change -->
        </h1>
        <div>
            <h2 class="h2">
                Цель работы
            </h2>
            <p class="text">
                ${work_goal} <#-- to change -->
            </p>

            <h2 class="h2">
                Теоретическое введение
            </h2>
            <p class="text">
                abstract void yourMethod();
                ${theoretical_intro} <#-- to change -->
            </p>

            <h2 class="h2">
                Ход работы
            </h2>
            <p class="text">
                ${work_plan} <#-- to change -->
            </p>
        </div>
    </@page.page>

    <@page.page>
        <h2 class="h2">
            Код
        </h2>
        <pre class="code">
<strong>
    ${source_path} <#-- to change -->
</strong>
${code} <#-- to change -->
            </pre>
    </@page.page>

    <@page.page>
        <h2 class="h2">
            Выводы по работе
        </h2>
        <p class="text">
            ${conclusions} <#-- to change -->
        </p>

        <h2 class="h2">
            Используемая литература
        </h2>
        <p class="text">
            ${literature} <#-- to change -->
        </p>
    </@page.page>
</@base.base>