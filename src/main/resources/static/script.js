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

function showToast(message, type) {
    let toastContainer = document.querySelector('.position-fixed.bottom-0.end-0.p-3');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.classList.add('position-fixed', 'bottom-0', 'end-0', 'p-3');
        document.body.appendChild(toastContainer);
    }

    const toast = document.createElement('div');
    toast.classList.add('toast', `bg-${type}`);
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
        allowClear: true,
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
    if(text === null || id === null) {
        $('#branchSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#branchSelect2').trigger('change');
    }
}