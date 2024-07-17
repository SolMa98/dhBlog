let template = function (e) {
    let templateInit = () => {
        let scrollPos = 0;
        const mainNav = document.getElementById('mainNav');
        const headerHeight = mainNav.clientHeight;
        window.addEventListener('scroll', function() {
            const currentTop = document.body.getBoundingClientRect().top * -1;
            if ( currentTop < scrollPos) {
                // Scrolling Up
                if (currentTop > 0 && mainNav.classList.contains('is-fixed')) {
                    mainNav.classList.add('is-visible');
                } else {
                    mainNav.classList.remove('is-visible', 'is-fixed');
                }
            } else {
                // Scrolling Down
                mainNav.classList.remove('is-visible');
                if (currentTop > headerHeight && !mainNav.classList.contains('is-fixed')) {
                    mainNav.classList.add('is-fixed');
                }
            }
            scrollPos = currentTop;
        });

        $(document).on('click', '.navbar-toggler', function(e){
            let target = e.target;
            if(!e.target.classList.contains("navbar-toggler")){
                target = e.target.closest(".navbar-toggler");
            }

            let toggleOption = target.dataset.toggle;

            if(target.classList.contains(toggleOption)){
                target.classList.remove(toggleOption);
                document.getElementById("navbarResponsive").classList.remove("show");
            }else{
                target.classList.add(toggleOption);
                document.getElementById("navbarResponsive").classList.add("show");
            }
        });
    }

    return {
        init : function() {
            templateInit()
        },
    }
}();

$(function() {
    template.init();
})