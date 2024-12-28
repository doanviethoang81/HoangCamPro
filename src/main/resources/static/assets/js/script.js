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
	// Xóa class active khỏi tất cả các thẻ <li>
	$("#sidebar .side-menu li").removeClass("active");

	// Thêm class active vào thẻ <li> chứa <a> được click
	$(this).parent("li").addClass("active");
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
                <td><input type="text" name="variantSku[]" class="form-control" placeholder="Mã biến thể" required></td>
                <td><input type="text"  name="variantStorage[]" class="form-control" placeholder="Dung lượng" required></td>
                <td><input type="number" name="variantDiscount[]" class="form-control" placeholder="Giá giảm" required oninput="this.value = this.value.replace(/[^0-9]/g, '');"></td>
                <td><input type="number" name="variantPrice[]" class="form-control" placeholder="Giá gốc" required oninput="this.value = this.value.replace(/[^0-9]/g, '');"></td>
                <td>
                    <button type="button" id="btn-remove-edit-variant" class="form-control">Xóa</button>
                </td>
            </tr>
        `;
		$('.table-edit-product tbody').append(newRow); // Thêm dòng mới vào cuối bảng
	});
});

 function getTextareaValue() {
        const textareaValue = document.getElementById('product-description').value;
        alert("dc");
        console.log(textareaValue); // In ra giá trị nhập vào của người dùng
    }


