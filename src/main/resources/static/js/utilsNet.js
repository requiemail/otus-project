export function restApi(apiCall, data = {}) {
  return apiCall(data)
    .then((res) => {
      return res;
    })
    .then((res) => {
      switch (res.status) {
        case 200: {
          return res.json();
        }
        default: {
          return res.json().then((err) => {
            throw err;
          });
        }
      }
    });
}

export function getJson(url) {
  return fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });
}

export function deleteJson(url) {
  return fetch(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    }
  });
}

export function postJson(url, data) {
  return fetch(url, {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    }
  });
}

export function putJson(url, data) {
  return fetch(url, {
    method: 'PUT',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json',
    }
  });
}