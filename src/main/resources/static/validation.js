var contextPath = "/ProminadaDD"
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
    return true;
}

function validateNumber(min, max, number) {
    if(number === ''){
        showToast("Число повинно бути вказане", "danger")
        return false;
    }
    var result = number >= min && number <= max
    if(result === false)showToast("Число повинно бути в діапазоні від "+min+" до "+max, "danger")
    return result
}
function cleanInputs(){
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
    var length = inputString.val().length
    var result = length >= minLength && length <= maxLength
    if(!result) {
        showToast("Поле повино містити від " + minLength + " до " + maxLength + " символів", "danger")
        inputString.css("border", "1px solid #ff0000");
    }
    return result
}

function validatePhoneNumber(input) {
    const phoneNumberRegex = /^\+380\d{9}$/;
    var result = phoneNumberRegex.test(input.val().replace(/\s/g, ''));
    if(!result) {
        showToast("Телефон повинен бути в форматі +380_________", "danger")
        input.css("border", "1px solid #ff0000");
    }
    return result
}

function validateEmail(input) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var result = emailRegex.test(input.val());
    if(!result) {
        showToast("Email не коректний", "danger")
        input.css("border", "1px solid #ff0000");
    }
    return result
}
function validateDate(input) {
    var regex = /^\d{4}\/\d{2}\/\d{2}$/;
    if (!regex.test(input.val())) {
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
function validSelect2(select){
    if (select.val() === null) {
        showToast("Елемент має бути вибрано", "danger");
        select.next().find(".select2-selection").css("border", "1px solid #ff0000");
        return false;
    }
    return true
}