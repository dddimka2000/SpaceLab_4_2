var contextPath = "/MinionsDD"
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
            showToast('Файл не вибрано', 'danger');
            fileInput.value = '';
            return false;
        }

        if (file.size > 20 * 1024 * 1024) {
            showToast('Файл перевищує 20 МБ', 'danger');
            fileInput.value = '';
            return false;
        }

        const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
        const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;

        if (!allowedExtensions.includes(lowerCaseExtension)) {
            showToast(`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`, 'danger');
            fileInput.value = '';
            return false;
        }

        showToast('Файл успішно завантажено', 'success');
        if (imageId != null && imageId.length > 1) previewImage(event, imageId);
        return true;
    });
}
function validateOneFile(file, allowedExtensions){
    if (!file) {
        showToast('Файл не вибрано', 'danger');
        return false;
    }

    if (file.size > 20 * 1024 * 1024) {
        showToast('Файл перевищує 20 МБ', 'danger');
        return false;
    }
    const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
    const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;
    if (!allowedExtensions.includes(lowerCaseExtension)) {
        showToast(`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`, 'danger');
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
        showToast('Файл не вибрано', 'danger');
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }

    if (file.size > 20 * 1024 * 1024) {
        showToast('Файл перевищує 20 МБ', 'danger');
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }

    const fileExtension = file.name.slice(((file.name.lastIndexOf(".") - 1) >>> 0) + 2);
    const lowerCaseExtension = `.${fileExtension.toLowerCase()}`;

    if (!allowedExtensions.includes(lowerCaseExtension)) {
        showToast(`Непідтримуваний тип файлу доступні такі типи: ${allowedExtensions.join(', ')}`, 'danger');
        fileInput.style.borderColor = '#ff0000'
        fileInput.value = '';
        return false;
    }
    return true;
}

function validateNumber(min, max, number) {
    if (number.val() === '') {
        showToast("Число повинно бути вказане", "danger")
        number.css("border", "1px solid #ff0000");
        scrollToElement(number)
        return false;
    }
    var result = number.val() >= min && number.val() <= max
    if (result === false) {
        scrollToElement(number)
        number.css("border", "1px solid #ff0000");
        showToast("Число повинно бути в діапазоні від " + min + " до " + max, "danger")
    }
    return result
}

function cleanInputs() {
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
        var length = inputString.val().replace(/\s/g, '').length
        var result = length >= minLength && length <= maxLength
    } catch (e) {
        result = false
    }
    if (!result) {
        scrollToElement(inputString)
        showToast("Поле повино містити від " + minLength + " до " + maxLength + " символів", "danger")
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
        showToast("Телефон повинен бути в форматі +380_________. Та мати існуючий префікс(96), (68), (98) та інші.", "danger")
        input.css("border", "1px solid #ff0000");
    }
    return result
}

function validateEmail(input) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var result = emailRegex.test(input.val());
    if (!result) {
        scrollToElement(input)
        showToast("Email не коректний", "danger")
        input.css("border", "1px solid #ff0000");
    }
    return result
}

function validateDate(input) {
    var regex = /^\d{4}\/\d{2}\/\d{2}$/;
    if (!regex.test(input.val())) {
        scrollToElement(input)
        showToast("Дата повина бути вказана", "danger")
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
    showToast("Дата вказана не коректно", "danger")
    return false;
}

function validSelect2(select) {
    console.log(select.val())
    if (!select.val()  ||  (Array.isArray(select.val()) && select.val().length===0)) {
        scrollToElement(select)
        showToast("Елемент має бути вибрано", "danger");
        select.next().find(".select2-selection").css("border", "1px solid #ff0000");
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