document.addEventListener("DOMContentLoaded", function() {
	const fromDate = document.getElementById("fromDate");
	const toDate = document.getElementById("toDate");
	const minAmount = document.getElementById("minAmount");
	const maxAmount = document.getElementById("maxAmount");

	// Today's date in YYYY-MM-DD format
	const today = new Date().toISOString().split("T")[0];
	fromDate.setAttribute("max", today);
	toDate.setAttribute("max", today);

	// Ensure 'fromDate' <= 'toDate'
	fromDate.addEventListener("change", function() {
		if (toDate.value && fromDate.value > toDate.value) {
			toDate.value = fromDate.value;
		}
		toDate.setAttribute("min", fromDate.value);
	});

	toDate.addEventListener("change", function() {
		if (fromDate.value && toDate.value < fromDate.value) {
			fromDate.value = toDate.value;
		}
		fromDate.setAttribute("max", toDate.value);
	});

	minAmount.addEventListener("input", function() {
		const minVal = parseFloat(minAmount.value);
		const maxVal = parseFloat(maxAmount.value);

		if (!isNaN(minVal)) {
			maxAmount.setAttribute("min", minVal);
			}
	});

	maxAmount.addEventListener("input", function() {
		const minVal = parseFloat(minAmount.value);
		const maxVal = parseFloat(maxAmount.value);

		if (!isNaN(maxVal)) {
			minAmount.setAttribute("max", maxVal);
		}
	});

});