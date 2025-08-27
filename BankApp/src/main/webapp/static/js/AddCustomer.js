document.addEventListener("DOMContentLoaded", function() {
    const dobInput = document.getElementById("dob");

    // Calculate today's date - 18 years
    let today = new Date();
    today.setFullYear(today.getFullYear() - 18);

    // Format as YYYY-MM-DD
    let yyyy = today.getFullYear();
    let mm = String(today.getMonth() + 1).padStart(2, '0');
    let dd = String(today.getDate()).padStart(2, '0');
    let maxDate = yyyy + "-" + mm + "-" + dd;

    // Set max attribute
    dobInput.setAttribute("max", maxDate);
});