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
function getAllUsersJson( jQuery ) {
    return fetch('http://localhost:8088/admin/users/getAllUsers')
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}
// function getAllUsers(jQuery) {
//     getAllUsersJson( jQuery )
//         .then(gau =>
//
//             document.getElementById('allUsersTable')
//                 .innerHTML = gau.map(
//                 function (user) {
//                     return '<tr><th scope="row">' + user.id + '</th>'
//                         + '<td>' + user.firstName + '</td>'
//                         + '<td>' + user.lastName + '</td>'
//                         + '<td>' + user.age + '</td>'
//                         + '<td>' + user.email + '</td>'
//
//                         + '<td>' + user.roles.map(
//                             function (role) {
//                                 return " " + role.name.replace("ROLE_", "")
//                             }) + '</td>'
//
//                         + '<td>'
//                             + '<button type="button" class="btn btn-info" data-toggle="modal" data-target="#edit_' + user.id + '">Изменить</button>'
//
//                                         //<!-- Modal -->
//                             + '<div class="modal fade" id="edit_' + user.id + '" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">'
//                             + '<div class="modal-dialog" role="document">'
//                             + buttonModalEdit(user)
//                             + '</div>'
//                             + '</div>'
//                         + '</td>'
//
//                         + '<td>Delete</td>'
//                         + '</tr>'
//                 }).join("")
//         )}

// // $( document ).ready( authUserInfo ).ready( getAllUsers );