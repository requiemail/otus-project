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
    resource: 'test',
    login: 'test',
    password: 'test',
    startDt: new Date(),
    remark: 'test'
  }
};

const createEntry = () => {
  let entry = promptForEntry();
  restApi(createEntrysWithUrl, entry)
    .then(() => renderEntrysTable())
    .catch(err => console.log(err));
};
const updateEntry = (id) => {
  let entry = {
    id: id,
    ...promptForEntry()
  }
  let isConfirmed = confirm('Изменить?');
  if (isConfirmed) {
    restApi(updateEntrysWithUrl, entry)
      .then(() => renderEntrysTable())
      .catch(err => console.log(err));
  }
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
  let updateButton = document.createElement('button');
  updateButton.onclick = () => {
    updateEntry(id);
  };
  updateButton.innerText = 'Изменить';
  row.appendChild(updateButton);
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
  let button = document.createElement('button');
  button.onclick = createEntry;
  button.innerText = 'Создать';
  tableBody.appendChild(button);
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