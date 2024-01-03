var contextPath = "/minions-dd/admin"

async function translateAllSelect2() {
    $(".select2-selection__choice").each(async function (index, element) {
        try {
            $(element).text($(element).text().replace('×',''))
            const originalName = await getOriginalValue($(element).text());
            $(element).text(translateValue(originalName));
        } catch (error) {
            console.error('Caught an error:', error);
        }
    });

    $("span.select2-selection__rendered:not(:has(span))").each(async function (index, element) {
        if ($(element).text()) {
            try {
                const originalName = await getOriginalValue($(element).text());
                $(element).text(translateValue(originalName));
            } catch (error) {
                console.error('Caught an error:', error);
            }
        }
    });
}

function branchSelect2(id, text) {
    $('#branchSelect2').select2({
        // placeholder: "Філіал",
        ajax: {
            url: contextPath + '/branches/for/select',
            dataType: 'json',
            delay: 1500,
            data: function (params) {
                var number = params.page > 0 ? params.page - 1 : 0;
                return {
                    query: params.term || '',
                    page: number,
                    size: 10
                };
            },
            processResults: function (data, params) {
                var results = data.content.map(function (item) {
                    return {id: item.id, text: item.name};
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
    if (text && id) {
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
                var number = params.page > 0 ? params.page - 1 : 0;
                return {
                    query: params.term || '',
                    page: number,
                    size: 10
                };
            },
            processResults: function (data, params) {
                var results = data.content.map(function (item) {
                    return {id: item.id, text: item.name};
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
    if (text && id) {
        $('#realtorSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#realtorSelect2').trigger('change');
    }
}


function roleSelect2(text, id) {
    $('#roleSelect2').select2({
        minimumResultsForSearch: -1,
        ajax: {
            url: contextPath + '/enum/role',
            dataType: 'json',
            processResults: function (data) {

                var index = 0;
                var results = data.map(function (data) {
                    return {id: data, text: translateValue(data)};
                });
                return {
                    results: results
                };
            },
        }
    })
    if (text && id) {
        $('#roleSelect2').append(new Option(text.toString(), id.toString(), true, true));
        $('#roleSelect2').trigger('change');
    }
}

function informationSourceSelect2(text, id) {
    var selectName = '#informationSourceSelect2'
    var url = '/enum/informationSource'

    $(selectName).select2({
        minimumResultsForSearch: -1,
        // placeholder: "Адмін",
        ajax: {
            url: contextPath + url,
            dataType: 'json',
            processResults: function (data) {
                var index = 0;
                var results = data.map(function (data) {
                    return {id: data, text: translateValue(data)};
                });
                return {
                    results: results
                };
            },
        }
    })
    if (text && id) {
        $(selectName).append(new Option(text.toString(), id.toString(), true, true));
        $(selectName).trigger('change');
    }
}

function forSelect2(id, text, selectName, url) {
    if (translationsSecondExample === undefined) {
        $.ajax({
            url: '/minions-dd/admin/api/translations?language=' + languageSecondExample,
            dataType: 'json',
            success: function (newTranslations) {
                translationsSecondExample = newTranslations;
                $(selectName).select2({
                    minimumResultsForSearch: -1,
                    ajax: {
                        url: contextPath + url,
                        dataType: 'json',
                        processResults: function (data) {
                            var results = data.map(function (data) {
                                if (data.id) return {id: data.id, text: translateValue(data.name)};
                                return {id: data, text: translateValue(data)};
                            });
                            return {
                                results: results
                            };
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log('AJAX error: ', textStatus, errorThrown);
                        }
                    }
                })
                if (text && id) {
                    $(selectName).append(new Option(translateValue(text.toString()), id.toString(), true, true));
                    $(selectName).trigger('change');
                }
            },
        })
    } else {
        $(selectName).select2({
            minimumResultsForSearch: -1,
            // placeholder: "Елемент",
            ajax: {
                url: contextPath + url,
                dataType: 'json',
                processResults: function (data) {
                    var results = data.map(function (data) {
                        if (data.id) return {id: data.id, text: translateValue(data.name)};
                        return {id: data, text: translateValue(data)};
                    });
                    return {
                        results: results
                    };
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log('AJAX error: ', textStatus, errorThrown);
                }
            }
        })
        if (text && id) {
            $(selectName).append(new Option(translateValue(text.toString()), id.toString(), true, true));
            $(selectName).trigger('change');
        }
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
                var number = params.page > 0 ? params.page - 1 : 0;
                return {
                    query: params.term || '',
                    page: number,
                    size: 10
                };
            },
            processResults: function (data, params) {
                var results = data.content.map(function (item) {
                    return {id: item.id, text: item.name};
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
    if (text && id) {
        $(selectName).append(new Option(text.toString(), id.toString(), true, true));
        $(selectName).trigger('change');
    }
}