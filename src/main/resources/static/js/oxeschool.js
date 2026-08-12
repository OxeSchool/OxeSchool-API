/* OxeSchool — utilidades front-end */

document.addEventListener('DOMContentLoaded', function () {

    /* Filtro de galeria: categoria + busca */
    var filtroCategoria = document.getElementById('filtroCategoria');
    var busca = document.getElementById('buscaCurso');

    if (filtroCategoria || busca) {
        var cards = document.querySelectorAll('[data-categoria]');

        function aplicarFiltro() {
            var categoria = filtroCategoria ? filtroCategoria.value : 'todas';
            var termo = busca ? busca.value.toLowerCase().trim() : '';

            cards.forEach(function (card) {
                var mostraCategoria = categoria === 'todas' || card.dataset.categoria === categoria;
                var mostraBusca = !termo || (card.dataset.nome || '').toLowerCase().indexOf(termo) !== -1;
                card.classList.toggle('d-none', !(mostraCategoria && mostraBusca));
            });
        }

        if (filtroCategoria) filtroCategoria.addEventListener('change', aplicarFiltro);
        if (busca) busca.addEventListener('input', aplicarFiltro);
        aplicarFiltro();
    }

});