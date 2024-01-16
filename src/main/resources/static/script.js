var contextPath = "/minions-dd/admin"


function showToast(message, type) {
    let toastContainer = document.querySelector('.position-fixed.top-0.end-0.p-3');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.classList.add('position-fixed', 'top-0', 'end-0', 'p-3');
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }
    if(message == 'saveObj'  ||  message == 'deleteObj'  ||  message == 'editObj')
        message = translateValue(message)
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
function showToastWithTranslate(message, translate, type) {
    if(message.includes("ERROR"))type="danger"
    let toastContainer = document.querySelector('.position-fixed.top-0.end-0.p-3');

    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.classList.add('position-fixed', 'top-0', 'end-0', 'p-3');
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }
    if(message == 'saveObj'  ||  message == 'deleteObj'  ||  message == 'editObj')
        message = translateValue(message)
    const toast = document.createElement('div');
    toast.classList.add('toast', `bg-${type}`);
    toast.style.transform = 'translateY(0)';
    toast.innerHTML = `
        <div class="toast-body text-white">
            <button type="button" class="btn-close" style="margin-left: 93%" data-bs-dismiss="toast" aria-label="Close"></button>
            <span data-translate=${translate} >${message}</span>
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
function getLastDigitFromPath(relativePath) {
    if(!relativePath)relativePath=window.location.pathname
    var pathSegments = relativePath.split("/");
    var lastSegment = pathSegments[pathSegments.length - 1];
    lastSegment.replace('#','')
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

function createStatusIcon(dateString) {
    const inputDate = new Date(dateString);
    const currentDate = new Date();
    const timeDiff = inputDate - currentDate;
    const daysDiff = Math.ceil(timeDiff / (1000 * 60 * 60 * 24)) * -1;
    let color = "red";
    if (daysDiff <= 3) {
        color = "green";
    } else if (daysDiff <= 7) {
        color = "blue";
    } else if (daysDiff <= 21) {
        color = "orange";
    }
    const icon = document.createElement("div");
    icon.className = "d-flex";
    icon.innerHTML = `<i class="fas fa-info-circle" style="color: ${color}"></i>`;
    return icon;
}
function printTable(headerRow, bodyRows) {
    console.log(bodyRows)
    if (!headerRow || !bodyRows || !Array.isArray(bodyRows)) {
        console.error('Не вказано необхідні параметри або bodyRows не є масивом.');
        return;
    }
    var tableHtml = '<table style="width: 100%; border: 1px solid black;">' + headerRow;
    bodyRows.forEach(function(row) {
        tableHtml += '<tr style="border: 1px solid black;">' + row + '</tr>';
    });

    tableHtml += '</table>';
    var printDiv = document.createElement('div');
    printDiv.innerHTML = tableHtml;
    var bodyElements = document.body.children;
    for (var i = 0; i < bodyElements.length; i++) {
        if (bodyElements[i] !== printDiv) {
            bodyElements[i].style.display = 'none';
        }
    }
    document.body.appendChild(printDiv);
    window.print();
    for (var i = 0; i < bodyElements.length; i++) {
        bodyElements[i].style.display = '';
    }
    document.body.removeChild(printDiv);
}
function removeBeforeFirstDot(input) {
    const indexOfDot = input.indexOf('.');
    if (indexOfDot !== -1) {
        return input.substring(indexOfDot + 1);
    } else {
        return input;
    }
}
$(document).ready(function () {
    $(".phone").each(function(index, element) {
        new Cleave("#" + element.id, {
            blocks: [13],
            numericOnly: true,
            prefix: "+380"
        })
    });
    $(".for-filter").each(function(index, element) {
        var maxLength = 20;
        $(element).attr("maxlength", maxLength);
        $(element).on("input", function() {
            if ($(this).val().length > maxLength) {
                $(this).val($(this).val().substring(0, maxLength));
            }
        });
    });
    $('.onlyNumber').on('input', function () {
        $(this).val(function (_, value) {
            return value.replace(/[^\d.]+/g, '').replace(/^(\d*\.\d*)\..*$/, '$1');
        });
    });
})