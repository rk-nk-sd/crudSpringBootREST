/***********************************
 *
 *           HEADER
 *
 ***********************************/
function auth( jQuery ) {
    return fetch('http://localhost:8088/users/whoAmI')
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

function authUserInfo(jQuery) {
    auth( jQuery )
        .then(wai => {

            document.getElementById('whoami')
                .innerHTML = wai.principal.username + " with roles:" + wai.authorities.map(
                function (name) {
                    return " " + name.authority.replace("ROLE_", "")
                }
            );

            if(wai.principal.username != null) {
                document.getElementById('logout')
                    .innerHTML = '<form id="logoutForm" method="post" action="/logout"></form>'
// <!--                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>\ -->
//                                 < /form>'
            }

        })
}

/***********************************
 *
 *           USER PROFILE
 *
 ***********************************/
function emailJson( email ) {
    return fetch('http://localhost:8088/users/get/by/email', {
        method: "post",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },

        body: JSON.stringify(email)
    })
        .then(response => {
            if(response.ok){
                // console.log(email);
                return response.json()
            } else {
                throw Error
            }
        })
}

function profile() {
    auth( jQuery )
        .then(wai => {

            let data = { "email": wai.principal.username };

            if(wai.principal.username != null) {
                emailJson( data ).then(function (user){
                    $('#currentUsersTable').empty();
                    let userRole = user.roles.map(
                        function (role) {
                            return " " + role.name.replace("ROLE_", "")
                        });
                    $('<tr></tr>').append(
                        $("<th></th>").text( user.id ),
                        $("<td></td>").text( user.firstName ),
                        $("<td></td>").text( user.lastName ),
                        $("<td></td>").text( user.age ),
                        $("<td></td>").text( user.email ),
                        $("<td></td>").text( userRole )
                    ).appendTo('#currentUsersTable');
                }).catch(function(err){
                    console.log(err.message); //выведет сообщение "не удалось выполнить..."
                });
            }

        })
}

/***********************************
 *
 *           FETCH ЗАПРОСЫ
 *
 ***********************************/

//GET ALL USERS
function getAllUsersJson(jQuery) {
    return fetch('http://localhost:8088/admin/users/getAllUsers',{cache: "reload"})
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

//GET ALL ROLES
function getAllRolesJson(jQuery) {
    return fetch('http://localhost:8088/admin/roles')
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

/***********************************
 *
 *  КНОПКИ EDIT И DELETE В ТАБЛИЦЕ
 *
 ***********************************/

//BUTTONS
function buttonDeleteUser(user) {
    return $("<button>").attr({
        type: "button",
        class: "btn btn-danger",
        'data-toggle': "modal",
        'data-target': '#deleteModal'
    }).data({
        user: {
            id: user.id,
            firstName: user.firstName,
            lastName: user.lastName,
            age: user.age,
            email: user.email,
            password: user.password,
            roles: user.roles
        }
    }).text('Удалить');
}

function buttonEditUser(user) {
    return $("<button>").attr({
        type: "button",
        class: "btn btn-info",
        'data-toggle': "modal",
        'data-target': '#editModal'
    }).data({
        user: {
            id: user.id,
            firstName: user.firstName,
            lastName: user.lastName,
            age: user.age,
            email: user.email,
            password: user.password,
            roles: user.roles
        }
    }).text('Изменить');
}

/***********************************
 *
 *           ТАБЛИЦА
 *
 ***********************************/

function getAllRoles($){
    getAllRolesJson($).then(function (roles) {
        $("#list-new-user select").empty();
        for (var i = 0; i < roles.length; i++) {
            $('<option>').attr({ name: "name", value: roles[i].id }).text(roles[i].name.replace("ROLE_", "")).appendTo("#list-new-user select");
        }
    })
}

function table(users) {
    $('#allUsersTable').empty();
    for (var i = 0; i < users.length; i++) {
        let userRole = users[i].roles.map(
            function (role) {
                return " " + role.name.replace("ROLE_", "")
            });
        $('<tr></tr>').append(
            $("<th></th>").text(users[i].id),
            $("<td></td>").text(users[i].firstName),
            $("<td></td>").text(users[i].lastName),
            $("<td></td>").text(users[i].age),
            $("<td></td>").text(users[i].email),
            $("<td></td>").text(userRole),//.prop( {id: role} ),
            $("<td></td>").append(buttonEditUser(users[i]).click(editUser())), //функция с параметрами (users[i], userRole), modalEdit(users[i], users[i].roles, "edit")
            $("<td></td>").append(buttonDeleteUser(users[i]).click(deleteUser()))
        ).appendTo('#allUsersTable');
    }
}

function listUsers($) {
    getAllUsersJson( jQuery )
        .then((data) =>
            table(data)
        )
        .catch(function(err){
            console.log(err.message); //выведет сообщение "не удалось выполнить..."
        });

}

/***********************************
 *
 *           MODAL
 *
 ***********************************/

//
// MODAL FOR DELETE USER
//

function deleteUser() {

    $("#deleteModal form").empty();

    $('<form>').attr({  }).append(
        $('<div>').attr({ class: "modal-body text-center" }).append(
            $('<p>').text('ID:'),
            $('<input>').attr({ id: "duId", type: "text", name: "name", class: "form-control", 'disabled': true }),
            $('<p>').text('Имя:'),
            $('<input>').attr({ id: "duFirstName", type: "text", name: "firstname", class: "form-control", 'disabled': true }),
            $('<p>').text('Фамилия:'),
            $('<input>').attr({ id: "duLastName", type: "text", name: "lastname", class: "form-control", 'disabled': true }),
            $('<p>').text('Возраст:'),
            $('<input>').attr({ id: "duAge", type: "number", step: "1", name: "age", class: "form-control", 'disabled': true }),
            $('<p>').text('E-mail:'),
            $('<input>').attr({ id: "duEmail", type: "email", name: "email", class: "form-control", 'disabled': true }),
            $('<p>').text('Пароль:'),
            $('<input>').attr({ id: "duPwd", type: "password", name: "password", class: "form-control", 'disabled': true }),
            $('<p>').text('Роль:'),
            $('<p>').append(
                $('<select>').prop({ id: "duRoles", 'multiple': true, size: "3", name: "addrole", class: "form-control", 'disabled': true })
            )
        )
    ).appendTo("#deleteModal div[class='modal-body']");

    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        var usr = button.data('user')
        let options = '';
        for (var i = 0; i < usr.roles.length; i++) {
            options += '<option>' + usr.roles[i].name.replace("ROLE_", "") + '</option>';
        }
        var modal = $(this)
        modal.find('.modal-title').text('Delete user')
        modal.find('.modal-body #duId').val(usr.id)
        modal.find('.modal-body #duFirstName').val(usr.firstName)
        modal.find('.modal-body #duLastName').val(usr.lastName)
        modal.find('.modal-body #duAge').val(usr.age)
        modal.find('.modal-body #duEmail').val(usr.email)
        modal.find('.modal-body #duPwd').val(usr.password)
        modal.find('.modal-body #duRoles').html(options)

    })
}

function modalDel($) {
    $('<div>').attr({ class: "modal fade", id: "deleteModal", tabindex: "-1", 'aria-labelledby': "deleteModalLabel", 'aria-hidden': "true" }).append(
        $('<div>').attr({ class: "modal-dialog" }).append(
            $('<div>').attr({ class: "modal-content" }).append(
                $('<div>').attr({ class: "modal-header" }).append(
                    $('<h5>').attr({ class: "modal-title", id: "deleteModalLabel" }).text("Delete user"),
                    $('<button>').attr({ type: "button", class: "close", 'data-dismiss': "modal", 'aria-label': "Close" }).append(
                        $('<span>').attr({ 'aria-hidden': "true" }).html('&times;')
                    )
                ),
                $('<div>').attr({ class: "modal-body" }),
                $('<div>').attr({ class: "modal-footer" }).append(
                    $('<button>').attr({ type: "button", class: "btn btn-secondary", 'data-dismiss': "modal" }).text('Close'),
                    $('<input>').attr({ type: "submit", class: "btn btn-danger", value: "Delete user" }).on('click', eventButtonDelete )
                )
            )
        )
    ).appendTo("#mod-del");
}

function eventButtonDelete() {
    var id = $('#deleteModal').find('.modal-body #duId').val();
    if(id != null && id != undefined) {
        DeleteUserJson(id);
        $('#deleteModal').modal("toggle");
        getAllUsersJson(jQuery).then(function () {
            getAllUsersJson(jQuery).then((data) => table(data))
        });
        // обновить таблицу ...
    }
}

//
// MODAL FOR EDIT USER
//
function getAllRolesWithParam($) {
    getAllRolesJson().then(function (roles) {
        $("#editModal select").empty();
        let options = '';
        for (var i = 0; i < roles.length; i++) {
            options +='<option value="' + roles[i].id + '">' + roles[i].name.replace("ROLE_", "") + '</option>';
        }
        $('#editModal select').html(options);
    })

}

function editUser() {

    $("#editModal form").empty();

    $('<form>').attr({  }).append(
        $('<div>').attr({ class: "modal-body text-center" }).append(
            $('<p>').text('ID:'),
            $('<input>').attr({ id: "euId", type: "text", name: "id", class: "form-control", 'disabled': true }),
            $('<p>').text('Имя:'),
            $('<input>').attr({ id: "euFirstName", type: "text", name: "firstName", class: "form-control", 'required': true }),
            $('<p>').text('Фамилия:'),
            $('<input>').attr({ id: "euLastName", type: "text", name: "lastName", class: "form-control", 'required': true }),
            $('<p>').text('Возраст:'),
            $('<input>').attr({ id: "euAge", type: "number", step: "1", name: "age", class: "form-control", 'required': true }),
            $('<p>').text('E-mail:'),
            $('<input>').attr({ id: "euEmail", type: "email", name: "email", class: "form-control", 'required': true }),
            $('<p>').text('Пароль:'),
            $('<input>').attr({ id: "euPwd", type: "password", name: "password", class: "form-control", 'required': true }),
            $('<p>').text('Роль:'),
            $('<p>').append(
                $('<select>').prop({ id: "euRoles", 'multiple': true, size: "3", name: "roles", class: "form-control", 'required': true })
            )
        )
    ).appendTo("#editModal div[class='modal-body']");

    getAllRolesWithParam($)

    $('#editModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        var usr = button.data('user')

        for (var i = 0; i < usr.roles.length; i++) {
            $('#editModal option[value=' + usr.roles[i].id + ']').attr({ selected: true });
        }
        var modal = $(this)
        modal.find('.modal-title').text('Edit user')
        modal.find('.modal-body #euId').val(usr.id)
        modal.find('.modal-body #euFirstName').val(usr.firstName)
        modal.find('.modal-body #euLastName').val(usr.lastName)
        modal.find('.modal-body #euAge').val(usr.age)
        modal.find('.modal-body #euEmail').val(usr.email)
        // modal.find('.modal-body #euPwd').val()
    })

}

function modalEdit($) {
    $('<div>').attr({ class: "modal fade", id: "editModal", tabindex: "-1", 'aria-labelledby': "editModalLabel", 'aria-hidden': "true" }).append(
        $('<div>').attr({ class: "modal-dialog" }).append(
            $('<div>').attr({ class: "modal-content" }).append(
                $('<div>').attr({ class: "modal-header" }).append(
                    $('<h5>').attr({ class: "modal-title", id: "editModalLabel" }).text("Edit user"),
                    $('<button>').attr({ type: "button", class: "close", 'data-dismiss': "modal", 'aria-label': "Close" }).append(
                        $('<span>').attr({ 'aria-hidden': "true" }).html('&times;')
                    )
                ),
                $('<div>').attr({ class: "modal-body" }),
                $('<div>').attr({ class: "modal-footer" }).append(
                    $('<button>').attr({ type: "button", class: "btn btn-secondary", 'data-dismiss': "modal" }).text('Close'),
                    $('<input>').attr({ type: "submit", class: "btn btn-primary", value: "Update user" }).on('click', eventButtonEdit )
                )
            )
        )
    ).appendTo("#mod-edit");
}

function eventButtonEdit() {
    var $data = {};
    let listUserRoles = [];
    $('#editModal').find('input[type!="submit"], select').each(function() {

        if( this.name === "roles" ) {
            for(var i=0; i<$(this).val().length; i++){
                listUserRoles[i] = { "id": $(this).val()[i] }
            }
        }

        $data[this.name] = $(this).val();

    });

    $data['roles'] = listUserRoles;
    updatedUserJson($data).then(function (){getAllUsersJson(jQuery).then((data) => table(data))});
    $('#editModal').modal("toggle");
}

/***********************************
 *
 *           CREATE USER
 *
 ***********************************/

function addNewUser($) {
    $('<form>').attr({ id: "createUserForm" }).append(
        $('<p>').text('Введите имя:'),
        $('<input>').attr({ type: "text",  name: "firstName", 'required': true }),
        $('<p>').text('Введите фамилию:'),
        $('<input>').attr({ type: "text",  name: "lastName", 'required': true }),
        $('<p>').text('Введите возраст:'),
        $('<input>').attr({ type: "text", name: "age", 'required': true }),
        $('<p>').text('Введите адрес эл. почты:'),
        $('<input>').attr({ type: "email",  name: "email", 'required': true }),
        $('<p>').text('Введите пароль:'),
        $('<input>').attr({ type: "password",  name: "password", 'required': true }),
        $('<p>').text('Роль:'),
        $('<p>').append(
            $('<select>').prop({ 'multiple': true, size: "3", name: "roles", 'required': true })
        ),
        $('<input>').attr({ class: "btn btn-success", type: "submit", value: "Создать пользователя" })
    ).appendTo("#list-new-user [class = 'text-center']");
    getAllRoles($);
}

function createNewUserJson(user) {
    return fetch('http://localhost:8088/admin/users/create/user', {
        method: 'POST',
        headers: {
            // 'Authorization': 'Basic dXNlckBtYWlsLnJ1OnVzZXI=',
            // 'Authorization': 'Basic YWRtaW5AbWFpbC5ydTphZG1pbg==', //+btoa('admin@mail.ru:admin'),
            'Content-Type': 'application/json;charset=utf-8'
        },

        body: JSON.stringify(user)
    })
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

function sendFormCreateNewUser($) {

    $("#createUserForm").submit(function (e) {
        e.preventDefault();

        var $data = {};
        let listRoles = [];
        $('#createUserForm').find('input[type!="submit"], select').each(function() {

            if( this.name === "roles" ) {
                for(var i=0; i<$(this).val().length; i++){
                    listRoles[i] = { "id": $(this).val()[i] }
                }
            }

            $data[this.name] = $(this).val();

        });

        $data['roles'] = listRoles;

        $('#createUserForm').empty();
        addNewUser($);
        createNewUserJson($data);
        $('.nav-tabs li:first-child a').tab('show');
        getAllUsersJson(jQuery).then(function () {
            getAllUsersJson(jQuery).then((data) => table(data))
        });
        getAllUsersJson(jQuery).then((data) => table(data))
    });
}

/***********************************
 *
 *           DELETE USER
 *
 ***********************************/

function DeleteUserJson(id) {
    return fetch('http://localhost:8088/admin/users/'+id, {
        method: "delete"
    })
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

/***********************************
 *
 *           UPDATE USER
 *
 ***********************************/

function updatedUserJson(user) {
    return fetch('http://localhost:8088/admin/users/'+user.id+'/edit', {
        method: 'PATCH',
        headers: {
            // 'Authorization': 'Basic dXNlckBtYWlsLnJ1OnVzZXI=',
            // 'Authorization': 'Basic '+btoa('admin@mail.ru:admin'),
            'Content-Type': 'application/json;charset=utf-8'
        },

        body: JSON.stringify(user)
    })
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}

$( document ).ready( authUserInfo ).ready( listUsers ).ready( addNewUser ).ready( sendFormCreateNewUser ).ready(profile).ready(modalDel).ready( modalEdit );
