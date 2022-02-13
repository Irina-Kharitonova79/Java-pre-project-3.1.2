$(function () {
    fetch('rest_admin/currentUser')
        .then(response => response.json())
        .then(user => {
            let isAdmin = false;
            user.roles.forEach(role => {
                if (role.roleName === 'ROLE_ADMIN') {
                    isAdmin = true;
                }
            });
            if (isAdmin === true) {
                $(async function () {
                    getCurrentUserNavbar();
                    await getTableWithUsers();
                    getDefaultModal();
                    addNewUser();
                    getCurrentUser();
                })
            } else {
                $('#userTab').click();
                getCurrentUserNavbar();
                getCurrentUser();
            }
        })
})


    const userFetchService = {
        head: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        },
        getCurrentUser: async () => await fetch('rest_admin/currentUser'),
        findAllUsers: async () => await fetch('rest_admin'),
        findOneUser: async (id) => await fetch(`rest_admin/${id}`),
        addNewUser: async (user) => await fetch('/rest_admin', {
            method: 'POST',
            headers: userFetchService.head,
            body: JSON.stringify(user)
        }),
        updateUser: async (id, user) => await fetch(`/rest_admin/${id}`, {
            method: 'PUT',
            headers: userFetchService.head,
            body: JSON.stringify(user)
        }),
        deleteUser: async (id) => await fetch(`/rest_admin/${id}`, {
            method: 'DELETE',
            headers: userFetchService.head
        }),
        findAllRoles: async () => await fetch('rest_admin/roles')
    }

//заполняем данные по текущему юзеру в навбар
    async function getCurrentUserNavbar() {
        let userNameLabel = $('#userNameLabel');
        let userSurnameLabel = $('#userSurnameLabel');
        let userRolesLabel = $('#userRolesLabel');

        await userFetchService.getCurrentUser()
            .then(res => {
                return res.json()
            })
            .then(data => {
                userNameLabel.html('<p>' + data.name + '</p>');
                userSurnameLabel.html('<p>' + data.surname + '</p>');
                userRolesLabel.html('<p>' + data.roleList + '</p>');
            })
    }

//оформляем таблицу, которая содержит полный список юзеров
    async function getTableWithUsers() {
        let table = $('#tableAllUsers tbody');
        table.empty();

        await userFetchService.findAllUsers()
            .then(response => response.json())
            .then(users => {
                users.forEach(user => {
                    let tableContent = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roleList}</td>
                    <td>
                        <button type="button" data-userId="${user.id}" data-action="edit" class="btn btn-primary"
                        data-toggle="modal" data-target="#someDefaultModal">Edit</button>
                    </td>
                    <td>
                        <button type="button" data-userId="${user.id}" data-action="delete" class="btn btn-danger"
                        data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                    </td>
                 </tr>
                    )`;
                    table.append(tableContent);

                })
            })

        // нажатие на кнопки edit или delete
        // открываем модальное окно
        $("#tableAllUsers").find('button').on('click', (event) => {
            let defaultModal = $('#someDefaultModal');

            let targetButton = $(event.target);
            let buttonUserId = targetButton.attr('data-userid');
            let buttonAction = targetButton.attr('data-action');

            defaultModal.attr('data-userid', buttonUserId);
            defaultModal.attr('data-action', buttonAction);
            defaultModal.modal('show');
        })
    }

// работа с модальными окнами edit и delete
    async function getDefaultModal() {
        $('#someDefaultModal').modal({
            keyboard: true,
            backdrop: "static",
            show: false
        }).on("show.bs.modal", (event) => {
            let thisModal = $(event.target);
            let userid = thisModal.attr('data-userid');
            let action = thisModal.attr('data-action');
            switch (action) {
                case 'edit':
                    editUser(thisModal, userid);
                    break;
                case 'delete':
                    deleteUser(thisModal, userid);
                    break;
            }
        }).on("hidden.bs.modal", (e) => {
            let thisModal = $(e.target);
            thisModal.find('.modal-title').html('');
            thisModal.find('.modal-body').html('');
            thisModal.find('.modal-footer').html('');
        })
    }


// редактируем юзера в модальном окне, зкарываем окно, обновляем таблицу юзеров
    async function editUser(modal, id) {
        let userToBeUpdate = await userFetchService.findOneUser(id);
        let user = userToBeUpdate.json();
        let listRoles = await userFetchService.findAllRoles();
        let roles = listRoles.json();

        modal.find('.modal-title').html('Edit user');


        let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`;
        let editButton = `<button type="submit"  class="btn btn-primary" id="editButton">Edit</button>`;
        modal.find('.modal-footer').append(closeButton);
        modal.find('.modal-footer').append(editButton);

        user.then(user => {

            let bodyForm = `
            <form class="form-group" id="editUser">
                <label class="font-weight-bold">ID
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled></label><br>
                <label class="font-weight-bold">First name
                <input class="form-control" type="text" id="name" value="${user.name}"></label><br>
                <label class="font-weight-bold">Last name
                <input class="form-control" type="text" id="surname" value="${user.surname}"></label><br>
                <label class="font-weight-bold">Age
                <input class="form-control" type="number" id="age" value="${user.age}"></label><br>
                <label class="font-weight-bold">Email
                <input class="form-control" type="email" id="email" value="${user.email}"></label><br>
                <label class="font-weight-bold">Password
                <input class="form-control" type="password" id="password" value="${user.password}"></label><br>
                <label class="font-weight-bold">Role
                <div id="roleSelector"></div>
                </label>
            </form>
        `;

            modal.find('.modal-body').append(bodyForm);

            roles.then(roles => {
                let placeToInsert = $('#roleSelector');
                let bodyForm = `<select class="form-control" name="listRoles" id="listRoles" multiple size="${roles.length}">`;
                roles.forEach(role => {
                    bodyForm = bodyForm + `<option value="${role.roleName}">${role.roleName}</option>`;
                })
                bodyForm = bodyForm + `</select>`
                placeToInsert.append(bodyForm);
            })
        })

        $("#editButton").on('click', async () => {
            let id = modal.find("#id").val().trim();
            let name = modal.find("#name").val().trim();
            let surname = modal.find("#surname").val().trim();
            let age = modal.find("#age").val().trim();
            let email = modal.find("#email").val().trim();
            let password = modal.find("#password").val().trim();
            let roles = modal.find("#listRoles").val();
            let data = {
                id: id,
                name: name,
                surname: surname,
                age: age,
                email: email,
                password: password,
                roles: roles
            }
            await userFetchService.updateUser(id, data)
                .then(() => {
                    modal.modal('hide');
                    getTableWithUsers();
                })
        })
    }

// удаляем юзера в модальном окне, зкарываем окно, обновляем таблицу юзеров
    async function deleteUser(modal, id) {
        let userToBeUpdate = await userFetchService.findOneUser(id);
        let user = userToBeUpdate.json();
        let listRoles = await userFetchService.findAllRoles();
        let roles = listRoles.json();

        modal.find('.modal-title').html('Delete user');

        let closeButton2 = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`;
        let deleteButton = `<button type="submit"  class="btn btn-danger" id="deleteButton">Delete</button>`;
        modal.find('.modal-footer').append(closeButton2);
        modal.find('.modal-footer').append(deleteButton);

        user.then(user => {

            let bodyForm = `
            <form class="form-group" id="deleteUser">
                <label class="font-weight-bold">ID
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled></label><br>
                <label class="font-weight-bold">First name
                <input class="form-control" type="text" id="name" value="${user.name}" disabled></label><br>
                <label class="font-weight-bold">Last name
                <input class="form-control" type="text" id="surname" value="${user.surname}" disabled></label><br>
                <label class="font-weight-bold">Age
                <input class="form-control" type="number" id="age" value="${user.age}" disabled></label><br>
                <label class="font-weight-bold">Email
                <input class="form-control" type="email" id="email" value="${user.email}" disabled></label><br>
                <label class="font-weight-bold">Password
                <input class="form-control" type="password" id="password" value="${user.password}" disabled></label><br>
                <label class="font-weight-bold">Role
                <div id="roleSelector"></div>
                </label>
            </form>
        `;

            modal.find('.modal-body').append(bodyForm);

            roles.then(roles => {
                let placeToInsert = $('#roleSelector');
                let bodyForm = `<select class="form-control" name="listRoles" id="listRoles" multiple size="${roles.length}">`;
                roles.forEach(role => {
                    bodyForm = bodyForm + `<option value="${role.roleName}" disabled>${role.roleName}</option>`;
                })
                bodyForm = bodyForm + `</select>`
                placeToInsert.append(bodyForm);
            })
        })

        $("#deleteButton").on('click', async () =>
            await userFetchService.deleteUser(id).then(() => {
                modal.modal('hide');
                getTableWithUsers();
            })
        )
    }

//вкладка для создания нового юзера
    async function addNewUser() {
        let listRoles = await userFetchService.findAllRoles();
        let roles = listRoles.json();

        roles.then(roles => {
            let placeToInsert = $('#addNewUserRoleSelector');
            let bodyForm = `<select class="form-control" name="listRoles" id="addNewUserListRoles" multiple size="${roles.length}">`;
            roles.forEach(role => {
                bodyForm = bodyForm + `<option value="${role.roleName}">${role.roleName}</option>`;
            })
            bodyForm = bodyForm + `</select>`
            placeToInsert.append(bodyForm);
        })

        $('#addNewUserButton').on('click', async () => {
            let addUserForm = $('#defaultSomeForm')
            let name = addUserForm.find('#addNewUserName').val().trim();
            let surname = addUserForm.find('#addNewUserSurname').val().trim();
            let age = addUserForm.find('#addNewUserAge').val().trim();
            let email = addUserForm.find('#addNewUserEmail').val().trim();
            let password = addUserForm.find('#addNewUserPassword').val().trim();
            let roles = addUserForm.find('#addNewUserListRoles').val();
            let data = {
                name: name,
                surname: surname,
                age: age,
                email: email,
                password: password,
                roles: roles
            }

            await userFetchService.addNewUser(data)
                .then(() => {
                    addUserForm.find('#addNewUserName').val('');
                    addUserForm.find('#addNewUserSurname').val('');
                    addUserForm.find('#AddNewUserAge').val('');
                    addUserForm.find('#addNewUserEmail').val('');
                    addUserForm.find('#addNewUserPassword').val('');
                    $('#SliderUserTable').click();
                    getTableWithUsers();
                })
        })
    }

//заполнение панели юзера
    async function getCurrentUser() {
        let table = $('#tableOneUser tbody');
        table.empty();

        await userFetchService.getCurrentUser()
            .then(response => response.json())
            .then(user => {
                let tableContent = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roleList}</td>
                </tr>
                )`;

                table.append(tableContent);
            })
    }

