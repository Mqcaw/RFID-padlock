//function for update student form on student page.
//gets the form data and makes api call to update the student
function updateStudent() {
    const formData = new FormData(document.getElementById('studentForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    
    console.log(data); //debugging line to check the contents of data

    // Send a PUT request 
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

function createStudent() {
    const formData = new FormData(document.getElementById('newStudentForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
        if (key == "id") {
            data[key] = Number(value);
        }
        data[key] = value;
    });


    console.log(data); //debugging line to check the contents of data

    // Send a POST request
    fetch('/api/students', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .catch(error => {
        console.error('Error creating student:', error);
        alert('Failed to create student');
    });
}


function deleteStudent(id) {
    confirm("Are you sure you want to delete this student\nPres OK to continue or Cancel.");
    // Send a PUT request using fetch
    fetch('/api/students/' + id, {
        method: 'DELETE'
    })
    .catch(error => {
        console.error('Error deleting student:', error);
        alert('Failed to delete student');
    });

    window.location.href = window.location.origin + "/registry";
}










//function for update lock form on lock page.
//gets the form data and makes api call to update the lock
function updateLock() {
    const formData = new FormData(document.getElementById('lockForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });


    console.log(data); //debugging line to check the contents of data

    // Send a PUT request
    fetch('/api/locks/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(updatedStudent => {
        window.location.href = '/lock/' + updatedStudent.id;
    })
    .catch(error => {
        console.error('Error updating lock:', error);
        alert('Failed to update lock');
    });
}

function createLock() {
    const formData = new FormData(document.getElementById('newLockForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
        if (key != "id") {
            data[key] = value;
        }

    });


    console.log(data); //debugging line to check the contents of data

    // Send a POST request
    fetch('/api/locks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .catch(error => {
        console.error('Error creating lock:', error);
        alert('Failed to create lock');
    });
}

function deleteLock(id) {
    confirm("Are you sure you want to delete this lock\nPres OK to continue or Cancel.");
    // Send a PUT request using fetch
    fetch('/api/locks/' + id, {
        method: 'DELETE'
    })
    .catch(error => {
        console.error('Error deleting lock:', error);
        alert('Failed to delete lock');
    });

    window.location.href = window.location.origin + "/locks";
}




function createKeyCard() {
    const formData = new FormData(document.getElementById('newKeyCardForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
        if (key == "studentId") {
            data[key] = value;
        }

    });


    console.log(data); //debugging line to check the contents of data

    // Send a POST request
    fetch('/api/key_cards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .catch(error => {
        console.error('Error creating key card:', error);
        alert('Failed to create key card');
    });
}

//function for update key card form on key card page.
//gets the form data and makes api call to update the key card
function updateKeyCard() {
    const formData = new FormData(document.getElementById('keyCardForm'));

    //map objects from the form data
    const data = {};
    formData.forEach((value, key) => {
         data[key] = value;
    });


    console.log(data); //debugging line to check the contents of data

    // Send a PUT request
    fetch('/api/key_cards/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(updatedKeyCard => {
        window.location.href = '/key-card/' + updatedKeyCard.id;
    })
    .catch(error => {
        console.error('Error updating key card:', error);
        alert('Failed to update key card');
    });
}

function deleteKeyCard(id) {
    confirm("Are you sure you want to delete this key card\nPres OK to continue or Cancel.");
    // Send a PUT request using fetch
    fetch('/api/key_cards/' + id, {
        method: 'DELETE'
    })
    .catch(error => {
        console.error('Error deleting key card:', error);
        alert('Failed to delete key card');
    });

    window.location.href = window.location.origin + "/key_cards";
}

function clearKeyCardList(id) {
    confirm("Are you sure you want to clear this this key cards locks\nPres OK to continue or Cancel.");
        // Send a PUT request using fetch
        fetch('/api/key_cards/' + id + '/reset_list', {
            method: 'Post'
        })
        .catch(error => {
            console.error('Error clearing list:', error);
            alert('Failed to clear lock list');
        });

        window.location.href = window.location.origin + '/key-card/' + id;
}
