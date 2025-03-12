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

    function assignKeyCard() {
        document.getElementById('keyCardModal').style.display = 'block';
    }

    function closeKeyCardModal() {
        document.getElementById('keyCardModal').style.display = 'none';
    }

    function updateStudent() {
    const formData = new FormData(document.getElementById('studentForm'));

    // Create an object from the form data
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    // Log the data to check if the ID is included
    console.log(data); // Debugging line to check the content of data object

    // Send a PUT request using fetch
    fetch('/api/students/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(updatedStudent => {
        window.location.href = '/student/' + updatedStudent.id;
    })
    .catch(error => {
        console.error('Error updating student:', error);
        alert('Failed to update student');
    });
}