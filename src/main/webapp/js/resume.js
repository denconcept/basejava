function addListItem(typeName) {
    let container = document.getElementById(typeName + '_list');
    container.insertAdjacentHTML('beforeend', `
        <dd><input type="text" name="${typeName}" size="200"></dd>
        <br>
    `);
}

function addCompany(typeName) {
    let container = document.getElementById(typeName + '_companies');
    container.insertAdjacentHTML('beforeend', `
        <dd>
            <br>
            <input type="text" name="${typeName}_companyTitle" size="100" placeholder="Название"><br>
            <input type="text" name="${typeName}_companyUrl" size="100" placeholder="Ссылка">
            <input type="hidden" name="${typeName}_periodCount" value="0">
            <dl></dl>
            <button type="button" onclick="addPeriod(this, '${typeName}')">Добавить период</button>
        </dd>
    `);
}

function addPeriod(button, typeName) {
    let parent = button.parentNode;
    let dl = parent.querySelector('dl');
    let periodCount = parent.querySelector(`input[name="${typeName}_periodCount"]`);
    dl.insertAdjacentHTML('beforeend', `
        <dd>
            <input type="text" name="${typeName}_periodStart" size="20" placeholder="Начало, ММ/ГГГГ"><br>
            <input type="text" name="${typeName}_periodEnd" size="20" placeholder="Окончание, ММ/ГГГГ"><br>
            <input type="text" name="${typeName}_periodTitle" size="100" placeholder="Заголовок"><br>
            <input type="text" name="${typeName}_periodDescription" size="200" placeholder="Описание"><br>
        </dd>
    `);
    periodCount.value = Number(periodCount.value) + 1;
}