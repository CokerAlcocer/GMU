let modal = new bootstrap.Modal(document.getElementById('confirm-modal'));
let students = [];
let student = {};
let r = {};

const showGralError = m => {
    document.getElementById('content1').classList.add('visually-hidden');
    document.getElementById('content2').classList.add('visually-hidden');
    document.getElementById('error-c').classList.remove('visually-hidden');
    document.getElementById('err-m').textContent = m;
}

const showSpecError = m => {
    document.getElementById('error-c').classList.remove('visually-hidden');
    document.getElementById('err-m').textContent = m;
}

const showFormErr = m => {
    document.getElementById('f-succ').classList.add('visually-hidden');
    document.getElementById('f-err').classList.remove('visually-hidden');
    document.getElementById('f-err-mess').textContent = m;
}

const showSuccessMess = m => {
    document.getElementById('f-succ').classList.remove('visually-hidden');
    document.getElementById('f-err').classList.add('visually-hidden');
    document.getElementById('f-succ-mess').textContent = m;
    document.getElementById('form').reset();
    document.getElementById('username').disabled = false;
    document.getElementById('fb-save').classList.remove('visually-hidden');
    document.getElementById('fb-update').classList.add('visually-hidden');
}

const findAll = async () => {
    const { data, message, status, error } = await doGet(`${API_URL}/student`);

    if (status) {
        if (!error) {
            let container = document.getElementById('content1');
            let content = '';

            if (!!data && data.length !== 0) {
                students = data;
                data.forEach((item, index) => {
                    content += `<div class="card rounded-4 mb-3">
                                    <div class="card-body p-0 d-flex align-items-stretch">
                                        <div class="bg-body-tertiary me-2 rounded-start-4 d-flex align-items-center">
                                            <span class="px-2 fw-bold">${index + 1}</span>
                                        </div>
                                        <div class="d-flex align-items-center">
                                            <i class="bi bi-person-circle fs-2 opacity-25 me-3"></i>
                                        </div>
                                        <div class="me-auto py-2">
                                            <p class="mb-0">${item.fullname}</p>
                                            <p class="mb-0 text-secondary fw-lighter">${item.enroll}</p>
                                        </div>
                                        <div class="d-flex align-items-center pe-3">
                                            <button onclick="showConfirmModal(${item.id})" class="btn btn-danger rounded-3"><i class="bi bi-trash"></i></button>
                                            <button onclick="loadDataOnForm(${item.id})" class="btn btn-primary rounded-3 ms-2"><i class="bi bi-pencil"></i></button>
                                        </div>
                                    </div>
                                </div>`;
                })

                container.innerHTML = content;
            } else {
                container.innerHTML = `<div class="alert alert-secondary border-0 rounded-4">
                                            <p class="mb-0">No hay registros</p>
                                        </div>`;
            }
        } else {
            showGralError(message);
        }
    } else {
        showGralError('El servidor no responde');
    }
}

findAll();

const findById = async id => {
    const { data, message, status, error } = await doGet(`${API_URL}/student/${id}`);

    if (status) {
        if (!error) {
            student = data;
        } else {
            showSpecError(message);
        }
    } else {
        showGralError('El servidor no responde');
    }
}

const loadDataOnForm = async id => {
    await findById(id);

    document.getElementById('fullname').value = student.fullname;
    document.getElementById('username').value = student.username;
    document.getElementById('username').disabled = true;
    document.getElementById('email').value = student.email;
    document.getElementById('fb-save').classList.add('visually-hidden');
    document.getElementById('fb-update').classList.remove('visually-hidden');
}

const saveOrUpdate = async isUpdate => {
    let data = {
        fullname: document.getElementById('fullname').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value
    }

    if (isUpdate) {
        data = {
            id: student.id,
            ...data
        }
    }

    let response = undefined;
    if (isUpdate) {
        response = await doPut(`${API_URL}/student`, data);
    } else {
        response = await doPost(`${API_URL}/student`, data);
    }

    if (response && response.status) {
        if (!response.error) {
            showSuccessMess(response.message);
            await findAll();
        } else {
            showFormErr(response.message);
        }
    }
}

const resetForm = () => {
    document.getElementById('fb-save').classList.remove('visually-hidden');
    document.getElementById('fb-update').classList.add('visually-hidden');
    document.getElementById('username').disabled = false;
    document.getElementById('form').reset();
}

const showConfirmModal = async id => {
    resetForm();
    await findById(id);
    modal.show();
}

const remove = async () => {
    const { data, message, status, error } = await doDelete(`${API_URL}/student`, student);

    if(status) {
        if(!error) {
            modal.hide();
            showSuccessMess(message);
            await findAll();
        } else {
            showSpecError(message);
        }
    } else {
        showSpecError('Error en la solicitud');
    }
}

