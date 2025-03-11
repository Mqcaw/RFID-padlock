let sortDirection = {};

function sortTable(colIndex) {
const table = document.querySelector("table tbody");
const rows = Array.from(table.rows);
const arrow = document.getElementById(`arrow-${colIndex}`);

sortDirection[colIndex] = !sortDirection[colIndex];
rows.sort((rowA, rowB) => {
let cellA = rowA.cells[colIndex].innerText.toLowerCase();
let cellB = rowB.cells[colIndex].innerText.toLowerCase();

if (!isNaN(cellA) && !isNaN(cellB)) {
cellA = parseFloat(cellA);
cellB = parseFloat(cellB);
}

return sortDirection[colIndex] ? cellA.localeCompare(cellB, undefined, {numeric: true}) : cellB.localeCompare(cellA, undefined, {numeric: true});


});

table.append(...rows);


document.querySelectorAll(".arrow").forEach(arrow => arrow.innerText = "🞁");
arrow.innerText = sortDirection[colIndex] ? "🞁" : "🞃";
}