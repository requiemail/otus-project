import {getJson, deleteJson, postJson, putJson, restApi} from "./utilsNet.js";

const URL = 'api/v1';

const getEntrysWithUrl = () => {
  return getJson(`${URL}/entry/all`)
};
const deleteEntrysWithUrl = (id) => {
  return deleteJson(`${URL}/entry/${id}`)
};
const createEntrysWithUrl = (data) => {
  return postJson(`${URL}/entry/`, data)
};
const updateEntrysWithUrl = (data) => {
  return putJson(`${URL}/entry/${data.id}`, data)
};
const removeChildren = node => {
  while (node.firstChild) {
    node.removeChild(node.lastChild);
  }
};

const promptForEntry = () => {
  return {
    resource: document.getElementById("resource-name").value,
    login: document.getElementById("login").value,
    password: document.getElementById("password").value,
    startDt: new Date(),
    remark: document.getElementById("remark").value,
  }
};

const createEntry = () => {
  let entry = promptForEntry();
  restApi(createEntrysWithUrl, entry)
    .then(() => renderEntrysTable())
    .catch(err => console.log(err));
};
const deleteEntry = (id) => {
  let isConfirmed = confirm('Точно удалить?');
  if (isConfirmed) {
    restApi(deleteEntrysWithUrl, id)
      .then(() => renderEntrysTable())
      .catch(err => console.log(err));
  }
};

function createRow(props, id) {
  let row = document.createElement('tr');
  let td;
  props.forEach(prop => {
    td = document.createElement('td');
    td.innerText = prop;
    row.appendChild(td);
  });
  let deleteButton = document.createElement('button');
  deleteButton.onclick = () => {
    deleteEntry(id);
  };
  deleteButton.innerText = 'Удалить';
  row.appendChild(deleteButton);
  return row;
}

function createFinalRow() {
  let row = document.createElement('tr');
  let td;
  td = document.createElement('td');
  let inputResourceName = document.createElement('input');
  inputResourceName.setAttribute("id", "resource-name");
  td.appendChild(inputResourceName);
  row.appendChild(td);

  td = document.createElement('td');
  let inputLogin = document.createElement('input');
  inputLogin.setAttribute("id", "login");
  td.appendChild(inputLogin);
  row.appendChild(td);

  td = document.createElement('td');
  let inputPassword = document.createElement('input');
  inputPassword.setAttribute("id", "password");
  td.appendChild(inputPassword);
  row.appendChild(td);

  td = document.createElement('td');
  row.appendChild(td);

  td = document.createElement('td');
  let inputRemark = document.createElement('input');
  inputRemark.setAttribute("id", "remark");
  td.appendChild(inputRemark);
  row.appendChild(td);


  td = document.createElement('td');
  let button = document.createElement('button');
  button.onclick = createEntry;
  button.innerText = 'Создать';
  td.appendChild(button);
  row.appendChild(td);
  return row;
}


function createTable(entries, tableBody) {
  entries.forEach(entry => {
    let props = [
      entry.resource,
      entry.login,
      entry.password,
      entry.startDt,
      entry.remark
    ];
    tableBody.appendChild(createRow(props, entry.id));
  });
  tableBody.appendChild(createFinalRow());
}

const renderEntrysTable = () => {
  restApi(getEntrysWithUrl).then(entries => {
    let tableBody = document.getElementById('entries-table-body');
    removeChildren(tableBody);
    createTable(entries, tableBody);
  })
    .catch(err => console.log(err));
  ;
};

renderEntrysTable();