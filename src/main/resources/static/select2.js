var contextPath = "/ProminadaDD"

function branchSelect2(id, text) {
    $('#branchSelect2').select2({
        // placeholder: "Філіал",
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
    })
    if(text !== undefined && id !== undefined) {
        $('#branchSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#branchSelect2').trigger('change');
    }
}
function realtorSelect2(id, text) {
    $('#realtorSelect2').select2({
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
    });
    if(text !== undefined && id !== undefined) {
        $('#realtorSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#realtorSelect2').trigger('change');
    }
}


function roleSelect2(text, id){
    $('#roleSelect2').select2({
        minimumResultsForSearch: -1,
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
        // placeholder: "Адмін",
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
function forSelect2(id, text, selectName, url){
    $(selectName).select2({
        minimumResultsForSearch: -1,
        // placeholder: "Елемент",
        ajax: {
            url: contextPath+url,
            dataType: 'json',
            processResults: function(data) {
                var index = 0;
                var results = data.map(function(data) {
                    if(data.id)return { id: data.id, text: data.name };
                    return { id: data, text: data };
                });
                return {
                    results: results
                };
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log('AJAX error: ', textStatus, errorThrown);
            }
        }
    })
    if(text !== undefined && id !== undefined) {
        $(selectName).append(new Option(text.toString(), id.toString(), true, true));
        $(selectName).trigger('change');
    }
}
function forSelect2WithSearch(id, text, selectName, url, name) {
    $(selectName).select2({
        // placeholder: "element",
        ajax: {
            url: contextPath + url,
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
    })
    if(text !== undefined && id !== undefined) {
        $(selectName).append(new Option(text.toString(), id.toString(), true, true));
        $(selectName).trigger('change');
    }
}