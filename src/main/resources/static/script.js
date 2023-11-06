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
console.log(lowerCaseExtension)
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

function showToast(message, type) {
    let toastContainer = document.querySelector('.position-fixed.top-0.end-0.p-3');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.classList.add('position-fixed', 'top-0', 'end-0', 'p-3');
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }

    const toast = document.createElement('div');
    toast.classList.add('toast', `bg-${type}`);
    toast.style.transform = 'translateY(0)';
    toast.innerHTML = `
        <div class="toast-body text-white">
            <button type="button" class="btn-close" style="margin-left: 93%" data-bs-dismiss="toast" aria-label="Close"></button>
            ${message}
        </div>`;

    toastContainer.appendChild(toast);

    const toastInstance = new bootstrap.Toast(toast, {
        delay: 2000
    });
    toastInstance.show();

    toast.querySelector('.btn-close').addEventListener('click', function () {
        toastInstance.hide();
    });
}




function branchSelect2(id, text) {
    $('#branchSelect2').select2({
        placeholder: "Філіал",
        ajax: {
            url: contextPath + '/branches/for/select',
            dataType: 'json',
            delay: 1500,
            data: function (params) {
                var number = params.page > 0 ? params.page-1 : 0;
                return {
                    query: params.term || '',
                    page: number,
                    size: 10
                };
            },
            processResults: function(data, params) {
                var results = data.content.map(function(item) {
                    return { id: item.id, text: item.name };
                });
                var hasMore = data.totalPages > data.number;
                return {
                    results: results,
                    pagination: {
                        more: hasMore
                    }
                };
            },
            cache: true
        }
    }).on('select2:select', function (e) {
        var selectedBranchId = e.params.data.id;
        var input = document.querySelector('select[name="branch"]');
        input.value=selectedBranchId;
    })
    if(text !== undefined && id !== undefined) {
        $('#branchSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#branchSelect2').trigger('change');
    }
}
function realtorSelect2(id, text) {
    $('#realtorSelect2').select2({
        placeholder: "Риелтор",
        ajax: {
            url: contextPath + '/realtors/for/select',
            dataType: 'json',
            delay: 1500,
            data: function (params) {
                var number = params.page > 0 ? params.page-1 : 0;
                return {
                    query: params.term || '',
                    page: number,
                    size: 10
                };
            },
            processResults: function(data, params) {
                var results = data.content.map(function(item) {
                    return { id: item.id, text: item.name };
                });
                var hasMore = data.totalPages > data.number;
                return {
                    results: results,
                    pagination: {
                        more: hasMore
                    }
                };
            },
            cache: true
        }
    }).on('select2:select', function (e) {
        var selectedRealtorId = e.params.data.id;
        var input = document.querySelector('select[name="realtor"]');
        input.value=selectedRealtorId;
    })
    if(text !== undefined && id !== undefined) {
        $('#realtorSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#realtorSelect2').trigger('change');
    }
}

function roleSelect2(text, id){
    $('#roleSelect2').select2({
        minimumResultsForSearch: -1,
        placeholder: "Адмін",
        ajax: {
            url: contextPath+'/enum/role',
            dataType: 'json',
            processResults: function(data) {
                var index = 0;
                var results = data.map(function(data) {
                    return { id: data, text: data };
                });
                return {
                    results: results
                };
            },
        }
    })
    if(text !== undefined && id !== undefined) {
        $('#roleSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#roleSelect2').trigger('change');
    }
}
function informationSourceSelect2(text, id){
    var selectName='#informationSourceSelect2'
    var url= '/enum/informationSource'

    $(selectName).select2({
        minimumResultsForSearch: -1,
        placeholder: "Адмін",
        ajax: {
            url: contextPath+url,
            dataType: 'json',
            processResults: function(data) {
                var index = 0;
                var results = data.map(function(data) {
                    return { id: data, text: data };
                });
                return {
                    results: results
                };
            },
        }
    })
    if(text !== undefined && id !== undefined) {
        $(selectName).append(new Option(text.toString(), id.toString(), true, true));
        $(selectName).trigger('change');
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
function getLastDigitFromPath(relativePath) {
    var pathSegments = relativePath.split("/");
    var lastSegment = pathSegments[pathSegments.length - 1];
    if (/^\d+$/.test(lastSegment)) {
        return parseInt(lastSegment, 10);
    } else {
        return null;
    }
}

function textForTable(str, length) {
    if (str.toString().length > length) {
        return str.substring(0, length-3) + '...';
    }
    return str;
}

