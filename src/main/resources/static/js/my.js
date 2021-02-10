// HEADER
function auth( jQuery ) {
    return fetch('http://localhost:8088/admin/users/whoAmI')
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
        'data-target': '#delete_' + user.id
    }).data({
        toogle: "modal",
        target: '#delete_' + user.id
    }).text('Удалить');
}

function buttonEditUser(user) {
    return $("<button>").attr({
        type: "button",
        class: "btn btn-info",
        'data-toggle': "modal",
        'data-target': '#edit_' + user.id
    }).data({
        toogle: "modal",
        target: '#edit_' + user.id
    }).text('Изменить');
}

// // $( document ).ready( authUserInfo ).ready( getAllUsers );