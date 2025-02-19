const allSideMenu = document.querySelectorAll('#sidebar .side-menu.top li a');

allSideMenu.forEach(item => {
	const li = item.parentElement;

	item.addEventListener('click', function () {
		allSideMenu.forEach(i => {
			i.parentElement.classList.remove('active');
		})
		li.classList.add('active');
	})
});

// TOGGLE SIDEBAR
const menuBar = document.querySelector('#content nav .bx.bx-menu');
const sidebar = document.getElementById('sidebar');

menuBar.addEventListener('click', function () {
	sidebar.classList.toggle('hide');
})

const searchButton = document.querySelector('#content nav form .form-input button');
const searchButtonIcon = document.querySelector('#content nav form .form-input button .bx');
const searchForm = document.querySelector('#content nav form');

searchButton.addEventListener('click', function (e) {
	if (window.innerWidth < 576) {
		e.preventDefault();
		searchForm.classList.toggle('show');
		if (searchForm.classList.contains('show')) {
			searchButtonIcon.classList.replace('bx-search', 'bx-x');
		} else {
			searchButtonIcon.classList.replace('bx-x', 'bx-search');
		}
	}
})

if (window.innerWidth < 768) {
	sidebar.classList.add('hide');
} else if (window.innerWidth > 576) {
	searchButtonIcon.classList.replace('bx-x', 'bx-search');
	searchForm.classList.remove('show');
}


window.addEventListener('resize', function () {
	if (this.innerWidth > 576) {
		searchButtonIcon.classList.replace('bx-x', 'bx-search');
		searchForm.classList.remove('show');
	}
})

// Xử lý thêm/xóa biến thể
// document.getElementById('addVariant').addEventListener('click', function () {
$('#addVariant').on('click', function () {
	const variantContainer = document.getElementById('variantContainer');
	const newVariant = document.createElement('div');
	newVariant.classList.add('variant', 'row', 'mb-3');
	newVariant.innerHTML = `
	<hr style="margin-bottom: 30px;">
    	<div class="col-md-5">
            <input type="text" name="variantSku[]" class="form-control"
                placeholder="Sku biến thể (VD: Hc1, Hc1-32, Hc1-64 ...)" >
        </div>
        <div class="col-md-5">
            <input type="text" name="variantStorage[]" class="form-control"
                placeholder="Tên biến thể (VD: Không thẻ, 64GB, 128GB, ...)" >
        </div>
        <div class="col-md-5">
            <input type="number" name="variantDiscount[]" class="form-control"
                placeholder="Giá  giảm (VNĐ)"  oninput="this.value = this.value.replace(/[^0-9]/g, '');">
        </div>
    	<div class="col-md-5">
            <input type="number" name="variantPrice[]" class="form-control" placeholder="Giá gốc (VNĐ)"
                 oninput="this.value = this.value.replace(/[^0-9]/g, '');">
        </div>
    	<div class="col-md-2">
    		<button type="button" class="btn btn-danger btn-remove-variant ">Xóa biến thể</button>
    	</div>
`;
	variantContainer.appendChild(newVariant);

	// Gắn sự kiện xóa cho nút "Xóa"
	newVariant.querySelector('.btn-remove-variant').addEventListener('click', function () {
		this.closest('.variant').remove();
	});
});

// Xóa biến thể hiện tại
document.querySelectorAll('.btn-remove-variant').forEach(button => {
	button.addEventListener('click', function () {
		this.closest('.variant').remove();
	});
});

// Bắt sự kiện click trên thẻ <a> bên trong thẻ <li>
$("#sidebar .side-menu li a").click(function (e) {
    // Lấy ID hoặc chỉ số của thẻ <li> chứa <a> được click
    const activeMenuId = $(this).parent("li").index();

    // Lưu ID này vào localStorage
    localStorage.setItem("activeMenu", activeMenuId);

    // Xóa class active khỏi tất cả các thẻ <li>
    $("#sidebar .side-menu li").removeClass("active");

    // Thêm class active vào thẻ <li> chứa <a> được click
    $(this).parent("li").addClass("active");
});

// Khi tải lại trang, khôi phục trạng thái active từ localStorage
$(document).ready(function () {
    const activeMenuId = localStorage.getItem("activeMenu");
    if (activeMenuId !== null) {
        // Xóa class active khỏi tất cả các thẻ <li>
        $("#sidebar .side-menu li").removeClass("active");

        // Thêm class active vào thẻ <li> tương ứng
        $("#sidebar .side-menu li").eq(activeMenuId).addClass("active");
    }
});

// Xóa trạng thái menu khi nhấn đăng xuất
$(".logout").click(function () {
    localStorage.removeItem("activeMenu");
    localStorage.removeItem('theme');
});


$(document).ready(function () {
	// Xóa một dòng biến thể
	$(document).on('click', '#btn-remove-edit-variant', function () {
		$(this).closest('tr').remove(); // Xóa dòng chứa nút "Xóa" được click

	});

	// Thêm một dòng biến thể mới
	$('#addVariant_editProduct').on('click', function () {
		let newRow = `
            <tr>
                <td><input type="text" name="variantSku[]" class="form-control" placeholder="Mã biến thể" ></td>
                <td><input type="text"  name="variantStorage[]" class="form-control" placeholder="Dung lượng" ></td>
                <td><input type="number" name="variantDiscount[]" class="form-control" placeholder="Giá giảm"  oninput="this.value = this.value.replace(/[^0-9]/g, '');"></td>
                <td><input type="number" name="variantPrice[]" class="form-control" placeholder="Giá gốc"  oninput="this.value = this.value.replace(/[^0-9]/g, '');"></td>
                <td>
                    <button type="button" id="btn-remove-edit-variant" class="form-control">Xóa</button>
                </td>
            </tr>
        `;
		$('.table-edit-product tbody').append(newRow); // Thêm dòng mới vào cuối bảng
	});
});

//function getTextareaValue() {
//        const textareaValue = document.getElementById('product-description').value;
//        alert("dc");
//        console.log(textareaValue); // In ra giá trị nhập vào của người dùng
//}
//
//    const $icon = $(".task_order_admin");
//    const $menu = $(".task_order_wrap");
//
//    // Toggle hiển thị menu khi click vào icon
//    $icon.on("click", function (event) {
//        $menu.toggleClass("show");
////        alert("mọe đc r");
//    });
//
//    // Ẩn menu nếu click ra ngoài
//    $(document).on("click", function (event) {
//        if (!$icon.is(event.target) && !$menu.is(event.target) && $menu.has(event.target).length === 0) {
//            $menu.removeClass("show");
////            alert("mọe");
//        }
//    });

//document.addEventListener("DOMContentLoaded", function () {
//// Lấy icon và menu
//  const icon = document.querySelector(".task_order_admin");
//  const menu = document.querySelector(".task_order_wrap");
//
//
//
//  // Toggle hiển thị menu khi click vào icon
//  icon.addEventListener("click", function (event) {
//      menu.classList.toggle("show");
////      event.stopPropagation(); // Ngăn chặn click ra ngoài
//      alert("mọe");
//  });
//}

//  // Ẩn menu nếu click ra ngoài
//  document.addEventListener("click", function (event) {
//      if (!icon.contains(event.target) && !menu.contains(event.target)) {
//          menu.classList.remove("show");
//            alert("mọe");
//      }
//  });
//}




// đổi màu nền đen trắng
const switchMode = document.getElementById('switch-mode');

// Kiểm tra trạng thái lưu trong localStorage khi tải trang
document.addEventListener("DOMContentLoaded", function () {
    const theme = localStorage.getItem('theme');
    if (theme === 'dark') {
        document.body.classList.add('dark');
        switchMode.checked = true; // Đảm bảo checkbox phản ánh đúng trạng thái
    }
});

// Lưu trạng thái khi thay đổi chế độ
switchMode.addEventListener('change', function () {
    if (this.checked) {
        document.body.classList.add('dark');
        localStorage.setItem('theme', 'dark'); // Lưu trạng thái vào localStorage
    } else {
        document.body.classList.remove('dark');
        localStorage.setItem('theme', 'light'); // Lưu trạng thái vào localStorage
    }
});

//xóa biến thể sp
    document.querySelector('.form-edit-product').addEventListener('submit', function (event) {
        const variantRows = document.querySelectorAll('.table-edit-product tbody tr');
        if (variantRows.length === 0) {
            event.preventDefault(); // Ngăn không cho gửi form
            alert('Không có biến thể sản phẩm để lưu. Vui lòng thêm ít nhất một biến thể.');
        }
    });

//    // Xử lý nút "Xóa" biến thể
//    document.querySelectorAll('#btn-remove-edit-variant').forEach(button => {
//        button.addEventListener('click', function () {
//            const row = this.closest('tr');
//            row.parentNode.removeChild(row); // Xóa hàng biến thể
//        });
//    });


//$(document).ready(function () {
//  // Bắt sự kiện thay đổi trên các input
//  $('.filter-input').on('change', function () {
//    // Thu thập tất cả các tiêu chí lọc
//    const filters = {};
//    $('#filters input:checked').each(function () {
//      const name = $(this).attr('name'); // Tên tiêu chí (e.g., resolution, price)
//      if (!filters[name]) {
//        filters[name] = [];
//      }
//      filters[name].push($(this).val()); // Giá trị được chọn
//    });
//
//    // Gửi yêu cầu AJAX
//    $.ajax({
//      url: '/filter', // Đường dẫn tới API xử lý lọc
//      method: 'GET',
//      data: filters, // Dữ liệu gửi tới server
//      success: function (response) {
//        // Cập nhật vùng hiển thị sản phẩm với dữ liệu trả về
//        $('#product-list').html(response);
//      },
//      error: function () {
//        alert('Lỗi khi lọc sản phẩm!');
//      }
//    });
//  });
//});




