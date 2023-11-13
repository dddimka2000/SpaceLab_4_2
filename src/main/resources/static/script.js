var contextPath = "/ProminadaDD"


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