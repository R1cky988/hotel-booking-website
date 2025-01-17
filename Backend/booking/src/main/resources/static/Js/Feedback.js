document.querySelectorAll(".rating i").forEach((star) => {
    star.addEventListener("click", function () {
        let value = this.getAttribute("data-value");

        // Update the hidden input field with the selected rating value
        document.getElementById("rating").value = value;

        // Remove 'fas' class (filled star) from all stars and add 'far' (empty star)
        document.querySelectorAll(".rating i").forEach((s) => {
            s.classList.remove("fas");
            s.classList.add("far");
        });

        // Add 'fas' class to the selected number of stars (filled)
        for (let i = 0; i < value; i++) {
            document.querySelectorAll(".rating i")[i].classList.remove("far");
            document.querySelectorAll(".rating i")[i].classList.add("fas");
        }
    });
});
