document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault(); // Ngăn chặn load lại trang
    const firstName = document.querySelector("input[placeholder='Họ']").value;
    const lastName = document.querySelector("input[placeholder='Tên']").value;
    const email = document.querySelector("input[placeholder='Email']").value;
    const password = document.querySelector("input[placeholder='Mật khẩu']").value;

    const user = { first_name: firstName, last_name: lastName, email, password };

    try {
        const response = await fetch("http://localhost:8088/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });

        if (response.ok) {
            const user = await response.json();
            // Lưu thông tin người dùng vào localStorage
            localStorage.setItem('user', JSON.stringify({
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email
            }));
            alert("Đăng ký thành công!");
            console.log(user);
            window.location.href = "http://localhost:8088/users/login";
        } else {
            const error = await response.json();
            alert("Đăng ký thất bại: " + error.join(", "));
        }
    } catch (err) {
        alert("Có lỗi xảy ra! " + err.message);
    }
});