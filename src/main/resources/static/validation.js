var contextPath = "/minions-dd/admin"
var countError = 0;
function previewImage(event, imageId) {
    var reader = new FileReader();
    reader.onload = function () {
        var output = document.getElementById(imageId);
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}

function validateAndUpload(imageId, allowedExtensions) {
    const fileInputs = document.querySelectorAll('.fileInput');

    fileInputs.forEach(fileInput => {
        const file = fileInput.files[0];
        const toastContainer = document.createElement('div');
        toastContainer.classList.add('position-fixed', 'bottom-0', 'end-0', 'p-3');
        document.body.appendChild(toastContainer);
        if (!file) {
            if (languageSecondExample == 'eng') addText($(fileInputs),'No file selected')
            else if (languageSecondExample == 'ru')addText($(fileInputs), 'Файл не вибран')
            else addText($(fileInputs), 'Файл не вибрано')
            fileInput.value = '';
            return false;
        }

        if (file.size > 20 * 1024 * 1024) {
            if (languageSecondExample == 'eng') addText($(fileInputs),'file more more than 20MB')
            else if (languageSecondExample == 'ru')addText($(fileInputs),'Файл превышает 20 МБ')
            else addText($(fileInputs),'Файл перевищує 20 МБ')
            fileInput.value = '';
            return false;
        }

        const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
        const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;

        if (!allowedExtensions.includes(lowerCaseExtension)) {
            if (languageSecondExample == 'eng') addText($(fileInputs),`Unsupported file type The following types are available: ${allowedExtensions.join(', ')}`)
            else if (languageSecondExample == 'ru')addText($(fileInputs),`Неподдерживаемый тип файла доступны следующие типы: ${allowedExtensions.join(', ')}`)
            else addText($(fileInputs),`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`)
            fileInput.value = '';
            return false;
        }
        if (imageId != null && imageId.length > 1) previewImage(event, imageId);
        return true;
    });
}
function validateOneFile(file, allowedExtensions){
    if (!file) {
        if (languageSecondExample == 'eng') addText($(file),'No file selected')
        else if (languageSecondExample == 'ru')addText($(file),'Файл не вибран')
        else addText($(file),'Файл не вибрано')
        return false;
    }

    if (file.size > 20 * 1024 * 1024) {
        if (languageSecondExample == 'eng') addText($(file),'file more more than 20MB')
        else if (languageSecondExample == 'ru')addText($(file),'Файл превышает 20 МБ')
        else addText($(file),'Файл перевищує 20 МБ')
        return false;
    }
    const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
    const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;
    if (!allowedExtensions.includes(lowerCaseExtension)) {
        if (languageSecondExample == 'eng') addText($(file),`Unsupported file type The following types are available: ${allowedExtensions.join(', ')}`)
        else if (languageSecondExample == 'ru')addText($(file),`Неподдерживаемый тип файла доступны следующие типы: ${allowedExtensions.join(', ')}`)
        else addText($(file),`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`)
        return false;
    }

    return true;
}

function validateFile(imageId, allowedExtensions) {
    const fileInput = document.getElementById(imageId);
    var file = fileInput.files[0]
    const toastContainer = document.createElement('div');
    toastContainer.classList.add('position-fixed', 'bottom-0', 'end-0', 'p-3');
    document.body.appendChild(toastContainer);

    if (!file) {
        if (languageSecondExample == 'eng') addText($(fileInput),'No file selected')
        else if (languageSecondExample == 'ru')addText($(fileInput),'Файл не вибран')
        else addText($(fileInput),'Файл не вибрано')
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }

    if (file.size > 20 * 1024 * 1024) {
        if (languageSecondExample == 'eng') addText($(fileInput),'file more more than 20MB')
        else if (languageSecondExample == 'ru')addText($(fileInput),'Файл превышает 20 МБ')
        else addText($(fileInput),'Файл перевищує 20 МБ')
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }

    const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
    const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;

    if (!allowedExtensions.includes(lowerCaseExtension)) {
        if (languageSecondExample == 'eng') addText($(fileInput),`Unsupported file type The following types are available: ${allowedExtensions.join(', ')}`)
        else if (languageSecondExample == 'ru')addText($(fileInput),`Неподдерживаемый тип файла доступны следующие типы: ${allowedExtensions.join(', ')}`)
        else addText($(fileInput),`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`)
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }
    return true;
}

function validateNumber(min, max, number) {
    if (number.val() === '') {
        if (languageSecondExample == 'eng') addText(number,`The number must be specified`)
        else if (languageSecondExample == 'ru')addText(number,`Число должно быть указано`)
        else addText(number,"Число повинно бути вказане")
        number.css("border", "1px solid #ff0000");
        scrollToElement(number)
        return false;
    }
    var result = number.val() >= min && number.val() <= max
    if (result === false) {
        scrollToElement(number)
        number.css("border", "1px solid #ff0000");
        if (languageSecondExample == 'eng') {
            addText(number,`The number must be in the range from ${min} to ${max}`);
        } else if (languageSecondExample == 'ru') {
            addText(number,`Число должно быть в диапазоне от ${min} до ${max}`);
        } else {
            addText(number,`Число повинно бути в діапазоні від ${min} до ${max}`);
        }
    }
    return result
}

function cleanInputs() {
    $('.text-for-validating').remove()
    var elements = document.querySelectorAll('input, select, textarea');
    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];
        element.style.borderColor = '';
    }
    var select2Selects = document.querySelectorAll('.select2-selection');
    for (var i = 0; i < select2Selects.length; i++) {
        var select2Select = select2Selects[i];
        select2Select.style.borderColor = '';
    }
}

function validString(minLength, maxLength, inputString) {
    try {
        var length = inputString.val().length
        var result = length >= minLength && length <= maxLength && inputString.val().replace(/\s/g, '').length >= minLength
    } catch (e) {
        result = false
    }
    if (!result) {
        scrollToElement(inputString)
        if (languageSecondExample == 'eng') {
            addText(inputString, `The field must contain between ${minLength} and ${maxLength} characters`);
        } else if (languageSecondExample == 'ru') {
            addText(inputString, `Поле должно содержать от ${minLength} до ${maxLength} символов`);
        } else {
            addText(inputString, `Поле повино містити від ${minLength} до ${maxLength} символів`);
        }
        inputString.css("border", "1px solid #ff0000");
    }
    return result
}
function validatePhoneNumber(input) {
    const phoneNumberRegex = /^\+380\d{9}$/;
    var regExp = /^\+380(31|32|33|34|35|36|38|39|41|43|44|45|46|20|89|94|92|91|67|68|96|97|98|70|90|91|67|68|96|97|98|70|87|89|50|66|95|99|93)\d{7}$/;
    var result = phoneNumberRegex.test(input.val().replace(/\s/g, '')) && regExp.test(input.val().replace(/\s/g, ''));
    if (!result) {
        scrollToElement(input)
        if (languageSecondExample == 'eng') {
            addText(input, "The phone must be in the format +380_________. And have existing prefixes (96), (68), (98), and others.");
        } else if (languageSecondExample == 'ru') {
            addText(input, "Телефон должен быть в формате +380_________. Иметь существующие префиксы (96), (68), (98) и другие.");
        } else {
            addText(input, "Телефон повинен бути в форматі +380_________. Та мати існуючий префікс(96), (68), (98) та інші.");
        }
        input.css("border", "1px solid #ff0000");
    }
    return result
}
function validatePhoneNumberWithAjax(input) {
    return new Promise((resolve, reject) => {
        const phoneNumberRegex = /^\+380\d{9}$/;
        var regExp = /^\+380(31|32|33|34|35|36|38|39|41|43|44|45|46|20|89|94|92|91|67|68|96|97|98|70|90|91|67|68|96|97|98|70|87|89|50|66|95|99|93)\d{7}$/;
        var result = phoneNumberRegex.test(input.val().replace(/\s/g, '')) && regExp.test(input.val().replace(/\s/g, ''));
        if (!result) {
            scrollToElement(input);
            if (languageSecondExample == 'eng') {
                addText(input, "The phone must be in the format +380_________. And have an existing prefix");
            } else if (languageSecondExample == 'ru') {
                addText(input, "Телефон должен быть в формате +380_________. Иметь существующий префикс");
            } else {
                addText(input, "Телефон повинен бути в форматі +380_________. Та мати існуючий префікс");
            }
            input.css("border", "1px solid #ff0000");
            reject(false);
        }
        $.ajax({
            type: 'GET',
            url: contextPath + '/valid/phone',
            data: { phone: input.val() },
            success: function (resultFromAjax) {
                if (resultFromAjax && !getLastDigitFromPath(window.location.pathname)) {
                    scrollToElement(input);
                    input.css("border", "1px solid #ff0000")
                    if (languageSecondExample == 'eng') {
                        addText(input, "The phone already exists");
                    } else if (languageSecondExample == 'ru') {
                        addText(input, "Телефон уже существует");
                    } else {
                        addText(input, "Телефон вже існує");
                    }
                    result = false;
                    reject(false);
                }
                resolve(result);
            },
            error: function () {
                reject(false);
            }
        });
    });
}
function validateEmail(input) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var result = emailRegex.test(input.val());
    if (!result) {
        scrollToElement(input)
        if (languageSecondExample == 'eng') {
            addText(input, "Email is not valid");
        } else if (languageSecondExample == 'ru') {
            addText(input, "Email некорректен");
        } else {
            addText(input, "Email не коректний");
        }
        input.css("border", "1px solid #ff0000");
    }
    return result
}

function validateDate(input) {
    var regex = /^\d{4}\/\d{2}\/\d{2}$/;
    if (!regex.test(input.val())) {
        scrollToElement(input)
        if (languageSecondExample == 'eng') {
            addText(input, "The date must be specified");
        } else if (languageSecondExample == 'ru') {
            addText(input, "Дата должна быть указана");
        } else {
            addText(input, "Дата повина бути вказана");
        }
        input.css("border", "1px solid #ff0000");
        return false;
    }
    var parts = input.val().split('/');
    var year = parseInt(parts[0], 10);
    var month = parseInt(parts[1], 10) - 1;
    var day = parseInt(parts[2], 10);
    var inputDate = new Date(year, month, day);
    var currentDate = new Date();
    var hundredYearsAgo = new Date();
    hundredYearsAgo.setFullYear(currentDate.getFullYear() - 100);
    if (inputDate > hundredYearsAgo && inputDate < currentDate) {
        return true;
    }
    input.css("border", "1px solid #ff0000");
    if (languageSecondExample == 'eng') {
        addText(input, "The date is specified incorrectly");
    } else if (languageSecondExample == 'ru') {
        addText(input, "Дата указана неверно");
    } else {
        addText(input, "Дата вказана не коректно");
    }
    return false;
}

function validSelect2(select) {
    if (!select.val()  ||  (Array.isArray(select.val()) && select.val().length===0)) {
        scrollToElement(select)
        select.next().find(".select2-selection").css("border", "1px solid #ff0000");
        if (languageSecondExample == 'eng') {
            addText(select.next().find(".select2-selection"),"The item must be selected");
        } else if (languageSecondExample == 'ru') {
            addText(select.next().find(".select2-selection"),"Элемент должен быть выбран");
        } else {
            addText(select.next().find(".select2-selection"),"Елемент має бути вибрано");
        }
        return false;
    }
    return true
}

function activateListItem(element) {
    var $button = $(element);
    $button.click()
    var $list = $button.closest('ul');
    var listItem = $button.closest('li');
    listItem.addClass('active');
    $list.find('.active').removeClass('active');
    $button.addClass('active');
    if ($button) {
        $button.addClass('active');
    }
}
function scrollToElement($element) {
    if(countError !== 0)return
    countError++
    var $container = $element.closest('[aria-labelledby]');
    if ($container.length>0) activateListItem('#' + $container.attr('aria-labelledby'))
    if ($element.length>0) {
        var windowHeight = $(window).height();
        var targetOffset = $element.offset().top - windowHeight / 4;

        $('html, body').animate({
            scrollTop: targetOffset
        }, 100);
    }
}
function addText(inputId, message) {
    var icon = $('<p class="text-for-validating" style="color: #ff0000;">'+message+'</p>')
    icon.tooltip({
        content: message,
        position: { my: "left+15 center", at: "right center" }
    })
    inputId.after(icon);
    inputId.css("border-color", "#ff0000")
}
function validAllNumberInput(){
    var elements = $('.onlyNumber');
    var isValid = true
    for (var i = 0; i < elements.length; i++) {
        var inputValue = $(elements[i]).val();
        var containsOnlyNumbers = /^\d+$/.test(inputValue.replace(/\s/g, ''));
        if (!containsOnlyNumbers) {
            scrollToElement($(elements[i]))
            $(elements[i]).css("border-color", "#ff0000")
            if (languageSecondExample == 'eng') {
                addText($(elements[i]), "Must be a number");
            } else if (languageSecondExample == 'ru') {
                addText($(elements[i]), "Должно быть числом");
            } else {
                addText($(elements[i]), "Має бути числом");
            }
            isValid = false
        }
    }
    return isValid
}
function validDataFromResponse(errors){
    for (var fieldName in errors) {
        if (errors.hasOwnProperty(fieldName)) {
            var errorMessage = errors[fieldName];
            scrollToElement($('#' + fieldName.toString()));
            addText($('#'+fieldName.toString()), errorMessage)
            $('#'+fieldName.toString()).css("border", "1px solid #ff0000")
        }
    }
    countError=0
}