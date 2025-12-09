const HEADERS = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}

const doGet = async url => {
    return await fetch(url, {
        method: 'GET',
        headers: HEADERS
    }).then(r => r.json())
    .then(r => r)
    .catch(e => e);
}

const doPost = async (url, body) => {
    return await fetch(url, {
        method: 'POST',
        headers: HEADERS,
        body: JSON.stringify(body)
    }).then(r => r.json())
    .then(r => r)
    .catch(e => e);
}

const doPut = async (url, body) => {
    return await fetch(url, {
        method: 'PUT',
        headers: HEADERS,
        body: JSON.stringify(body)
    }).then(r => r.json())
    .then(r => r)
    .catch(e => e);
}

const doDelete = async (url, body) => {
    return await fetch(url, {
        method: 'DELETE',
        headers: HEADERS,
        body: JSON.stringify(body)
    }).then(r => r.json())
    .then(r => r)
    .catch(e => e);
}