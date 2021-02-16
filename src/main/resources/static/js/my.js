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














//GET ALL USERS
function getAllUsersJson(jQuery) {
    return fetch('http://localhost:8088/admin/users/getAllUsers')
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

// // $( document ).ready( authUserInfo ).ready( getAllUsers );