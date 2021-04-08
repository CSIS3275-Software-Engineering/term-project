function setError(e) {
    document.getElementById('error').textContent = e;
}

function setSmallError(id, e) {
    var elt = document.getElementById(id);
    if (e === '')
        elt.classList.remove('border-danger');
    else
        elt.classList.add('border-danger');
    document.getElementById(id + '-error').textContent = e;
}

function addFreeSpots() {
    var select = document.getElementById('spot-select');
    fetch('http://127.0.0.1:8080/api/spots')
        .then(x => x.json())
        .then(x => x.forEach(y => {
            var opt = document.createElement('option');
            opt.text = y.id;
            select.add(opt);
        }));
}

function validateSelect(id) {
    var select = document.getElementById(id);
    var i = select.selectedIndex;
    if (i === -1) {
        setSmallError(id, 'Please select a spot');
        return null;
    }
    setSmallError(id, '');
    return select.options[i].value;
}

function validateInput(id, err) {
    var input = document.getElementById(id);
    if (input.value === '') {
        setSmallError(id, err);
        return null;
    }
    setSmallError(id, '');
    return input.value;
}

function getTicketButtonClicked() {
    var spot = validateSelect('spot-select');
    var name = validateInput('full-name', 'Full name must be set');
    var plate = validateInput('license-plate', 'License plate must be set');
    var hours = validateInput('num-hours', 'Number of hours must be set');
    if (spot === null || name === null || plate === null || hours === null)
        return;

    var startTime = JSJoda.LocalDateTime.now();
    var endTime = startTime.plusHours(hours);

    var ticket = {
         id: '',
         startTime: startTime,
         endTime: endTime,
         spotId: spot,
         fullName: name,
         licensePlate: plate,
     };
     console.log(ticket);

    fetch('http://127.0.0.1:8080/api/ticket/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(ticket)
    })
    .then(x => x.json())
    .then(x => window.location.href = '/booking-success/' + x.id);
}

function proceedButtonClicked() {
    var ticketId = document.getElementById('ticket-id').value;
    if (ticketId === '') {
        setError('Ticket id is empty');
        return;
    }
    fetch('http://127.0.0.1:8080/api/payment/' + ticketId)
        .then(response => {
            if (response.ok) {
                window.location.href = '/payment/' + ticketId;
            } else {
                response.json().then(x => setError(x.message));
            }
        });
}
