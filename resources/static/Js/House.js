  document.addEventListener("DOMContentLoaded", () => {
  const roomId = 1;
  const apiUrl = `http://localhost:8088/summary/room/${roomId}`;

  // Hàm hiển thị feedback
  function displayFeedback(FeedbackSummaryResponse) {
    const container = document.querySelector(".slick-slider");
    if (!container) {
      console.error("Không tìm thấy phần tử .slick-slider");
      return;
    }

    // Làm sạch nội dung cũ
    container.innerHTML = "";

    if (!FeedbackSummaryResponse || FeedbackSummaryResponse.feedbackDetails.length === 0) {
      container.innerHTML = "<p>Không có đánh giá nào cho phòng này.</p>";
      return;
    }

    // Thêm từng đánh giá vào container
    FeedbackSummaryResponse.feedbackDetails.forEach((feedbackDetails) => {
      const feedbackHTML = `
        <div class="review-card">
          <img
            alt="Portrait of ${feedbackDetails.name}"
            height="80"
            src="https://storage.googleapis.com/a1aa/image/NMrHU2RTww7VIFM2AfoF4Dt56YeSKEeoMR7dkMP5hAfEjgVPB.jpg"
            width="80"
          />
          <h5>${feedbackDetails.name}</h5>
          <p>Việt Nam</p>
          <div class="stars" aria-label="Rating: ${feedbackDetails.rate} out of 5">
            ${getStars(feedbackDetails.rate)}
          </div>
          <p>${feedbackDetails.comment}</p>
          <a
            href=""
            class="read-more"
            data-bs-toggle="modal"
            data-bs-target="#feedbackDetailModal"
            >Tìm hiểu thêm</a
          >
        </div>
      `;
      container.innerHTML += feedbackHTML;
    });

    // Kiểm tra nếu phần tử .slick-slider có tồn tại trước khi gọi slick
    if ($(".slick-slider").length > 0) {
      setTimeout(() => {
        $(".slick-slider").slick({
          slidesToShow: 3,
          slidesToScroll: 1,
          autoplay: true,
          autoplaySpeed: 2000,
        });
      }, 100);
    } else {
      console.error("Không thể khởi tạo Slick Slider: .slick-slider không tồn tại.");
    }
  }

  // Hàm tạo sao
  function getStars(rating) {
    let starsHTML = "";
    for (let i = 1; i <= 5; i++) {
      starsHTML += i <= rating ? '<i class="fas fa-star"></i>' : '<i class="far fa-star"></i>';
    }
    return starsHTML;
  }

  // Fetch dữ liệu từ API
  fetch(apiUrl, {
    method: "GET",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Lỗi API: ${response.statusText}`);
      }
      return response.json();
    })
    .then((data) => {
      displayFeedback(data);
    })
    .catch((error) => {
      console.error("Lỗi:", error);
      document.querySelector(".slick-slider").innerHTML = "<p>Lỗi khi tải dữ liệu: " + error.message + "</p>";
    });
});
