// Simple search + filter on client side
document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("searchInput");
    const filterChips = document.querySelectorAll(".filter-chip");
    const rows = document.querySelectorAll(".styled-table tbody tr");

    // Search by name or ID
    searchInput.addEventListener("input", () => {
        const query = searchInput.value.toLowerCase();
        rows.forEach(row => {
            const text = row.innerText.toLowerCase();
            row.style.display = text.includes(query) ? "" : "none";
        });
    });
});
