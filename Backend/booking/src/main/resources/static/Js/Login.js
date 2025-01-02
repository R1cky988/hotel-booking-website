document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault(); // Ngăn chặn load lại trang

    const email = document.querySelector("input[placeholder='Email']").value;
    const password = document.querySelector("input[placeholder='Mật khẩu']").value;

    if (!email || !password) {
        alert("Vui lòng nhập đầy đủ thông tin đăng nhập!");
        return;
    }

    const userLoginDTO = { email, password };

    try {
        const response = await fetch("http://localhost:8088/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userLoginDTO),
        });

        if (response.ok) {
            // Xử lý khi đăng nhập thành công
            const userData = await response.json();
            alert("Đăng nhập thành công!");
            console.log(userData);

            // Chuyển hướng sang trang chính (index)
            window.location.href = "http://localhost:8088";
        } else {
            // Xử lý phản hồi lỗi từ server
            const contentType = response.headers.get("Content-Type");
            let errorMessage;
            if (contentType && contentType.includes("application/json")) {
                const errorData = await response.json();
                errorMessage = errorData.message || "Lỗi không xác định!";
            } else {
                errorMessage = await response.text();
            }
            alert("Đăng nhập thất bại: " + errorMessage);
        }
    } catch (err) {
        // Xử lý lỗi khi không thể kết nối tới server
        alert("Có lỗi xảy ra khi đăng nhập: " + err.message);
    }
});
