$(function () {
    //slick của sub nav
    $('.slick-slider').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        infinite: false,
        prevArrow: "<button type='button' class='slick-prev pull-left'><i class='fa fa-angle-left' aria-hidden='true'></i></button>",
        nextArrow: "<button type='button' class='slick-next pull-right'><i class='fa fa-angle-right' aria-hidden='true'></i></button>",
    });



    // lấy tên file hiện tại
    var currentFile = window.location.pathname.split("/").pop();
//        alert(currentFile);
    //    alert("helelo ae");
    //kiểm tra đường dẫn
    if (currentFile === '') { //&& window.innerWidth > 739
        // thay đổi backgroup trên header nếu là file index.html
        $(window).scroll(function (event) { //scrollTop là cuộn chuột
            var location = $(window).scrollTop();
            var headerElements = document.querySelectorAll('.header--white'); //lấy tất cả các class
            var headerElements2 = document.querySelectorAll('.header--white--black');
            if (window.innerWidth > 739) { // nếu màn hình lớn hơn 739 thì mới thay đổi thanh header
                if (location > 0) { // cuộn thanh kéo và kích thước màn hình > 739px
                    $('#logo').attr('src', '/fe/img/logo.svg');
                    headerElements.forEach(function (element) {// xóa class cũ và thêm
                        element.classList.replace('header--white', 'header--white--black');
                    });
                } else {
                    $('#logo').attr('src', '/fe/img/logo_white.svg');
                    //                    $('#logo').attr('src', /*[[@{/assets/img/logo_white.svg}]]*/ '');
                    headerElements2.forEach(function (element) {
                        element.classList.replace('header--white--black', 'header--white');
                    });
                }
            }
            else {
                $('#logo').attr('src', '/fe/img/logo.svg');

            }
        })

        // giữ nguyên trạng thái hover của header
        $(".header").hover(
            function () {
                $('#logo').attr('src', '/fe/img/logo.svg');
                var headerElements = document.querySelectorAll('.header--white'); //lấy tất cả các class
                headerElements.forEach(function (element) {// xóa và thêm
                    element.classList.replace('header--white', 'header--white--black');
                });
            },
        );

        // kiểm tra kích thước màn hình hiện tại
        $(window).resize(function () {
            var width = $(window).width();
            // màn cho Mobile (kích thước nhỏ hơn 739px)
            if (width < 739) { // mobile
                $('#logo').attr('src', '/fe/img/logo.svg');
                var index = 0;
                const items = $('.product-camera-security .product_list'); // tìm tất cả các thẻ div có class product_list
                function showNextItem() {
                    items.removeClass('active');
                    items.eq(index).addClass('active'); //thêm class active

                    if (index === items.length - 1) {
                        index = 0; //quay lại phần tử đầu tiên
                    } else {
                        index++;
                    }
                }
                // Gọi hàm showNextItem mỗi 3 giây
                showNextItem(); // Hiển thị thẻ div đầu tiên ngay khi trang load
                setInterval(showNextItem, 5000);

            } else {
                $('#logo').attr('src', '/fe/img/logo_white.svg');
            }
        });

    } else {// nếu k phải file index thì thay đổi màu
        $('#logo').attr('src', '/fe/img/logo.svg');
        var headerElements = document.querySelectorAll('.header--white'); //lấy tất cả các class
        headerElements.forEach(function (element) {// xóa và thêm
            element.classList.replace('header--white', 'header--white--black');
        });
    }



    // tạo thanh lướt lên top
    $(window).scroll(function (event) {
        var location = $(window).scrollTop();
        if (location > 1200) {
            $('.backtop').css('opacity', 1); //
        } else {
            $('.backtop').css('opacity', 0);
        }
    });
    $('.backtop').click(function () {
        $('html, body').animate({
            scrollTop: 0
        }, 100);
    });

    // click sub nav next prev ẩn hiện
    $(".slick-prev").hide();// ẩn click prev trước
    //đếm số lần click
    var clickCount = 0; // Biến đếm số lần click
    var maxClicks = 2;  // Số lần click tối đa trước khi ẩn

    // click đủ 2 lần thì tự động ẩn phần click số 2 đó
    $(".slick-next").click(function () {
        $(".slick-prev").show(); // hiện click prev
        clickCount++; //reset lại click next
        if (clickCount >= maxClicks) {
            $(".slick-next").hide(); // ẩn phần tử
            clickCount = 0;
        }
    });

    $(".slick-prev").click(function () {
        $(".slick-next").show(); //hiện cick next
        clickCount++; //reset lại click prev
        if (clickCount >= maxClicks) {
            $(".slick-prev").hide();
            clickCount = 0;
        }
    });

    // slider sản phẩm trong các khung cảnh
    // mặc đỉnh ẩn
    $('.button-slider-wrap-item-hide').hide();
    var temp = 0; //trạng thái mặc đinh
    // lấy tất cả các button trong div có id là "button-slider-wrap"
    $('.button-slider-wrap .button-slider-wrap-click').each(function (index) {//bat dau index =0
        //mặc định hiện button đầu tiên khi load
        //ẩn icon của index
        $('.button-slider-wrap-click-icon').eq(0).hide();
        //ẩn title
        $('.button-slider-wrap-title').eq(0).hide();
        // Hiện item tương ứng với vị trí button
        $('.button-slider-wrap-item-hide').eq(0).show();

        //thêm sự kiện click vào mỗi button
        $(this).click(function () {
            temp = index;// gán lại để cho click next vs prev
            // ẩn tất cả các item trước khi hiện item mới
            $('.button-slider-wrap-item-hide').hide();

            //hiện lại các nút k liên quan
            $('.button-slider-wrap-click-icon').show();
            $('.button-slider-wrap-title').show();

            //ẩn icon của index
            $('.button-slider-wrap-click-icon').eq(index).hide();

            //ẩn title
            $('.button-slider-wrap-title').eq(index).hide();

            // hiện item tương ứng với vị trí button
            $('.button-slider-wrap-item-hide').eq(index).show();
        });
    });


    // chuyển silder trong mobile
    $('.button-slider-wrap-mobile .button-slider-wrap-mobile-click').each(function (index) {
        $(this).click(function () {
            console.log(index);
            $('.button-slider-wrap-mobile-active').removeClass("button-slider-wrap-mobile-active");
            $(this).addClass("button-slider-wrap-mobile-active");
        })

    });
    // chuyển hiệu ứng khi click next slider
    $('.button_slider_icon-blue').click(function () {//next
        temp++;
        if (temp == 4) { //nếu =4 nghĩa là hết slider có 0123
            temp = 0;// gán lại =0
        }

        $('.button-slider-wrap-item-hide').hide();

        //hiện lại các nút k liên quan
        $('.button-slider-wrap-click-icon').show();
        $('.button-slider-wrap-title').show();

        //ẩn icon của index
        $('.button-slider-wrap-click-icon').eq(temp).hide();

        //ẩn title
        $('.button-slider-wrap-title').eq(temp).hide();

        // hiện item tương ứng với vị trí button
        $('.button-slider-wrap-item-hide').eq(temp).show();
    });

    // chuyển hiệu ứng khi click prev slider
    $('.button_slider_icon-white').click(function () {//prev
        temp--;
        if (temp < 0) { //nếu <0 nghĩa là hết slider có 0123
            temp = 3;// gán lại =3
        }

        $('.button-slider-wrap-item-hide').hide();

        //hiện lại các nút k liên quan
        $('.button-slider-wrap-click-icon').show();
        $('.button-slider-wrap-title').show();

        //ẩn icon của index
        $('.button-slider-wrap-click-icon').eq(temp).hide();

        //ẩn title
        $('.button-slider-wrap-title').eq(temp).hide();

        // hiện item tương ứng với vị trí button
        $('.button-slider-wrap-item-hide').eq(temp).show();
    });

    $('.product-smart-home').hide();
    // khi click vào kệt tác về an ninh hiện ra các sản phẩm khác
    $(".button-detail-camera-security").click(function () {
        $(".button-detail-click").removeClass("button-detail-click");// tìm các phần tử có class click và xóa
        $(this).addClass("button-detail-click");
        $('.product-smart-home').hide();
        $('.product-camera-security').show();
    });

    $(".button-detail-smart-home").click(function () {
        $(".button-detail-click").removeClass("button-detail-click");// tìm các phần tử có class click và xóa
        $(this).addClass("button-detail-click");
        $('.product-camera-security').hide();
        $('.product-smart-home').show();

    });

    //thay đổi icon khi click xem mật khẩu
    $('.fa-eye-slash').click(function () {
        var passwordInput = $('#password');
        var eyeIcon = $(this);
        //kiểm tra loại của thẻ input
        if (passwordInput.attr('type') === 'password') {
            //đổi sang 'text' để hiện mật khẩu
            passwordInput.attr('type', 'text');
            // đổi icon thành con mắt mở
            eyeIcon.removeClass('fa-eye-slash');
            eyeIcon.addClass('fa-eye');
        } else {
            //đổi lại sang 'password' để ẩn mật khẩu
            passwordInput.attr('type', 'password');
            //đổi lại icon thành con mắt đóng
            eyeIcon.removeClass('fa-eye');
            eyeIcon.addClass('fa-eye-slash');
        }
    });

    // MOBILE
    if (window.innerWidth < 740) {
        // khi click vào các nav thì mở ra các tùy chọn khác
        $('.nav-menu-item-link-mobile').click(function (event) {
            event.preventDefault(); // tắt điều hướng trang của thẻ a khi click mobile
            $('.nav-menu-sub-mobile').not($(this).next().next()).slideUp();//đóng tất cả các sub-menu khác
            $(this).next().next().slideToggle();//mở hoặc đóng sub-menu được click
            $(this).closest('.nav-menu-item-link').addClass('no-hover'); // đè hover của nav menu item link

            // thay đổi icon mũi tên lên xuống
            var icon = $(this).next().next().next().find("i"); // thẻ i này trong div thứ 3 nên next 3 lần
            if (icon.hasClass("fa-angle-down")) { // nếu có
                $('.fa-angle-up').removeClass("fa-angle-up").addClass("fa-angle-down"); // đổi các up lại thành down
                icon.removeClass("fa-angle-down").addClass("fa-angle-up");
            }
            else { // nếu có up
                icon.removeClass("fa-angle-up").addClass("fa-angle-down");
            }
        });

        //khi click vào nav k có lựa chọn thì hủy hover mặc định
        $('.nav-menu-item-link').click(function () {
            $(this).closest('.nav-menu-item-link').addClass('no-hover'); // đè hover của nav menu item link
        });
    }

    $('.nav-menu-sub-mobile').slideUp(); // đóng hết nav khi load lên
    // thay đổi icon menu khi click
    $('.bars_mobile').click(function () {
        // tìm thẻ i trong class bars
        var icon = $(this).find("i");
        $('.fa-angle-up').removeClass("fa-angle-up").addClass("fa-angle-down"); // đổi các up lại thành down của các nav chi tiết
        $('.nav-menu-sub-mobile').slideUp(); // đóng hết nav con khi tắt mở

        // kiểm tra xem nó có class fa-bars không, đổi icon
        if (icon.hasClass("fa-bars")) {
            icon.removeClass("fa-bars").addClass("fa-xmark");
            $('.search_mobile').css("display", "none");// khi click mở menu thì ẩn icon search
        } else {
            icon.removeClass("fa-xmark").addClass("fa-bars");
            $('.search_mobile').css("display", "flex");// hiện icon search
        }

        // hiện tất cả menu khi click vào icon menu sổ xuống
        if ($('.header__nav').css('overflow') === 'hidden') {
            // Nếu đang ẩn thì hiện
            $('.header__nav').css('overflow', 'visible');
        } else {
            // Nếu đang hiển thị thì ẩn
            $('.header__nav').css('overflow', 'hidden');
        }
    });

    // click đóng search trên mobile thì ẩn
    $('.search_mobile-nav-close').click(function () {
        $('.search_mobile-nav').css('display', 'none');
    });
    // mở search mobile
    $('.search_mobile').click(function () {
        $('.search_mobile-nav').css('display', 'block');
    });

    let index = 0;
    const items = $('.product_list'); // Chọn tất cả các thẻ div có class product_list

    document.querySelectorAll('.memory-options-button').forEach(button => {
        button.addEventListener('click', function () {
            // Lấy thông tin từ thuộc tính data-*
            const id = this.getAttribute('data-id');
            const price = this.getAttribute('data-price');
            const discount = this.getAttribute('data-discount');

            // Nếu ID tồn tại, gửi fetch đến server để lấy giá
            if (id) {
                fetchPriceFromServer(id);
            } else {
                // Nếu không, cập nhật giá từ data-attribute
                updatePriceOnUI(price, discount);
            }

            // Thay đổi trạng thái active của nút
            updateActiveButton(this);
        });
    });

    // Hàm định dạng giá với dấu '.'
    function formatPrice(price) {
        return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    // Hàm gửi yêu cầu fetch giá từ server để thay đổi giá khi hiển thị biến thể khác của sp
    function fetchPriceFromServer(id) {
        fetch(`/product/get-price/${id}`)
            .then(response => response.json())
            .then(data => {
                updatePriceOnUI(data.sku, data.price, data.discount,data.economize, data.discountPercentage);
//                console.log("Price from server:", data.economize);
            })
            .catch(error => console.error('Error fetching price:', error));
    }

    // Hàm cập nhật giao diện với giá
    function updatePriceOnUI(sku, price, discount,economize,discountPercentage) {
        document.querySelector('.product_code').textContent = `${sku}`;
        document.querySelector('.discount').textContent = `${discountPercentage}%`;

        // Định dạng giá trước khi hiển thị
        const formattedDiscount = formatPrice(discount);
        const formattedPrice = formatPrice(price);
        const formattedEconomize = formatPrice(economize);
        const formattedDiscountPercentage = formatPrice(discountPercentage);


        document.querySelector('.current-price').textContent = `${formattedDiscount}đ`;
        document.querySelector('.old-price').textContent = `${formattedPrice}đ`;
        document.querySelector('.economize').textContent = `${formattedEconomize}đ`;
//        document.querySelector('.discount').textContent = `${formattedDiscountPercentage}%`;


    }

    // Hàm thay đổi trạng thái active của nút
    function updateActiveButton(activeButton) {
        document.querySelectorAll('.memory-options-button').forEach(button => {
            button.classList.remove('memory-options-button-active');
        });
        activeButton.classList.add('memory-options-button-active');
    }
});

// lấy id của biến thể vá số lượng sp để thanh toán
document.addEventListener('DOMContentLoaded', function () {
        const memoryButtons = document.querySelectorAll('.memory-options-button');
        const buyNowLink = document.getElementById('buy-now-link');
        const quantityInput = document.querySelector('.product_quantity');
        const incrementButton = document.querySelector('.increment');
        const decrementButton = document.querySelector('.decrement');

        // Lấy giá trị nút mặc định "Không thẻ"
        const defaultVariant = document.querySelector('.memory-options-button-active');
        let selectedVariantId = defaultVariant.getAttribute('data-id');
        let selectedQuantity = parseInt(quantityInput.value);

//        gọi hàm nếu người dùng chọn biến thể mặc định
        updateBuyNowLink(selectedVariantId, selectedQuantity);

        // Cập nhật URL khi người dùng chọn biến thể khác
        memoryButtons.forEach(button => {
            button.addEventListener('click', function () {
                memoryButtons.forEach(btn => btn.classList.remove('memory-options-button-active'));
                this.classList.add('memory-options-button-active');

                // Cập nhật ID và số lượng khi người dùng chọn biến thể
                selectedVariantId = this.getAttribute('data-id');
                selectedQuantity = parseInt(quantityInput.value);
                updateBuyNowLink(selectedVariantId, selectedQuantity);
            });
        });

        // Cập nhật khi người dùng thay đổi số lượng
        quantityInput.addEventListener('input', function () {
            selectedQuantity = parseInt(this.value);
            updateBuyNowLink(selectedVariantId, selectedQuantity);
        });

        incrementButton.addEventListener('click', function () {
            selectedQuantity++;
            quantityInput.value = selectedQuantity;
            updateBuyNowLink(selectedVariantId, selectedQuantity);
        });

        decrementButton.addEventListener('click', function () {
            if (selectedQuantity > 1) {
                selectedQuantity--;
                quantityInput.value = selectedQuantity;
                updateBuyNowLink(selectedVariantId, selectedQuantity);
            }
        });

        function updateBuyNowLink(variantId, quantity) {
            if (variantId && quantity) {
                const baseUrl = buyNowLink.getAttribute('href').split('?')[0];
                buyNowLink.setAttribute('href', `${baseUrl}?variantId=${variantId}&quantity=${quantity}`);
            }
        }

    // nee
        // Lấy thông tin từ các thuộc tính data-* của nút
        const addToCartButton = document.getElementById('add-to-cart-button');
        let variantId = defaultVariant.getAttribute('data-id');
        let price = document.querySelector('data-discount'); // Lấy số lượng sản phẩm
        let quantity = document.querySelector('.product_quantity').value; // Số lượng sản phẩm

        // Kiểm tra nếu nút "Thêm vào giỏ hàng" tồn tại
        if (addToCartButton) {
            // Cập nhật giá trị ID và giá ban đầu (giá trị mặc định)
            const defaultVariant = document.querySelector('.memory-options-button-active');  // Lấy nút được chọn mặc định
            if (defaultVariant) {
                variantId = defaultVariant.getAttribute('data-id');
                price = defaultVariant.getAttribute('data-discount');
            }

            // Lắng nghe sự kiện click khi người dùng chọn biến thể
            memoryButtons.forEach(button => {
                button.addEventListener('click', function () {
                    // Cập nhật lớp active khi người dùng chọn biến thể
                    memoryButtons.forEach(btn => btn.classList.remove('memory-options-button-active'));
                    this.classList.add('memory-options-button-active');

                    // Cập nhật lại giá trị ID và giá của biến thể được chọn
                    variantId = this.getAttribute('data-id');
                    price = this.getAttribute('data-discount');
                });
            });

            // Lắng nghe sự kiện click khi người dùng nhấn nút "Thêm vào giỏ hàng"
            addToCartButton.addEventListener('click', function () {
            // Lấy thông tin từ các thuộc tính và giá trị input
            const productName = addToCartButton.getAttribute('data-name');
            quantity = document.querySelector('.product_quantity').value; // Số lượng sản phẩm

            // Tạo đối tượng dữ liệu sẽ gửi đi
            const requestData = {
                variantId: variantId,
                quantity: quantity,
                price: price
            };

            // Gửi yêu cầu POST đến server
            fetch('/cart/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert(data.message); // Hiển thị thông báo thành công
                    } else {
                        alert(data.message); // Hiển thị thông báo lỗi
                    }
                })
                .catch(error => {
                    alert('Vui lòng đăng nhập trước!.');
                });
            });
        }
});
    // định dạng formatVND chỗ tổng tiền thanh toán
    function formatVND(amount) {
        return amount.toLocaleString('vi-VN').replace(/₫/, '') + 'đ';
    }

// hiển thị tiền khi click chọn sản phẩm cần mua trong giỏ hàng
    function updateTotal() {
        let checkboxes = document.querySelectorAll('.product-checkbox');
        let total = 0;
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                let price = parseFloat(checkbox.getAttribute('data-price'));
                if (!isNaN(price)) {
                    total += price;
                }
            }
        });
        document.getElementById('total-price').innerText = formatVND(total);
    }

function submitSelectedProducts() {
    var selectedProducts = [];
    var checkboxes = document.querySelectorAll('.product-checkbox:checked');

    checkboxes.forEach(function(checkbox) {
        var product = {
            id: checkbox.getAttribute('data-product-id'),
            price: checkbox.getAttribute('data-price')
        };
        selectedProducts.push(product);
    });

    if (selectedProducts.length > 0) {
        // Gửi mảng sản phẩm đã chọn qua AJAX
        fetch('/cart/checkout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(selectedProducts) // Gửi mảng sản phẩm dưới dạng JSON
        })
        .then(response => {
            if (response.ok) {
                // Nếu POST thành công, chuyển hướng đến trang thanh toán
                window.location.href = "/cart/payment";
            } else {
                throw new Error("Lỗi khi gửi yêu cầu thanh toán.");
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("Đã xảy ra lỗi trong quá trình thanh toán.");
        });
    } else {
        alert("Vui lòng chọn sản phẩm trước khi thanh toán.");
    }
}







