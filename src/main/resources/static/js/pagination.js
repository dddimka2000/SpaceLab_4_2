var currentPage;
var isFilter;
function updatePagination(currentPage, totalButtons, container, isFilter) {
    var pagination = document.getElementById(container);
    this.currentPage = currentPage
    this.isFilter = isFilter
    pagination.innerHTML = '';
    var li, link;
    if (totalButtons <= 7) {
        for (var i = 1; i <= totalButtons; i++) {
            li = createPaginationItem(i);
            pagination.appendChild(li);
        }
    } else {
        li = createPaginationItem(1);
        pagination.appendChild(li);
        if (currentPage <= 4) {
            for (var i = 2; i <= 5; i++) {
                li = createPaginationItem(i);
                pagination.appendChild(li);
            }
            li = createEllipsisItem();
            pagination.appendChild(li);
        } else if (parseInt(currentPage) >= parseInt(totalButtons) - 3) {

            li = createEllipsisItem();
            pagination.appendChild(li);
            for (var i = totalButtons-4; i <= totalButtons-1; i++) {
                li = createPaginationItem(i);
                pagination.appendChild(li);
            }
        } else {
            li = createEllipsisItem();
            pagination.appendChild(li);

            for (var i = currentPage - 1; i <= parseInt(currentPage) + 1; i++) {
                li = createPaginationItem(i);
                pagination.appendChild(li);
            }
            li = createEllipsisItem();
            pagination.appendChild(li);
        }
        li = createPaginationItem(totalButtons);
        pagination.appendChild(li);
    }
}

function createPaginationItem(pageNumber) {
    var li = document.createElement('li');
    li.classList.add('page-item');
    if (parseInt(pageNumber) === parseInt(currentPage)) {
        li.classList.add('active');
    }
    var button = document.createElement('button');
    button.classList.add('page-link');
    button.innerText = pageNumber;
    li.appendChild(button);

    button.addEventListener('click', function() {
        if(isFilter) getPageWithFilter(pageNumber)
        else getPage(pageNumber)
    });


    return li;
}

function createEllipsisItem() {
    var li = document.createElement('li');
    li.classList.add('page-item');
    var link = document.createElement('a');
    link.classList.add('page-link');
    link.innerText = '...';
    li.appendChild(link);
    return li;
}
