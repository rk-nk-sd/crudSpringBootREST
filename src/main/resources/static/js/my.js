// HEADER
// function auth( jQuery ) {
//     return fetch('http://localhost:8088/admin/users/whoami')
//         .then(response => {
//             if(response.ok){
//                 return response.json()
//             } else {
//                 throw Error
//             }
//         })
// }
//
// function authUserInfo(jQuery) {
//     auth( jQuery )
//         .then(wai => {
//
//             document.getElementById('whoami')
//                 .innerHTML = wai.principal.username + " with roles:" + wai.authorities.map(
//                 function (name) {
//                     return " " + name.authority.replace("ROLE_", "")
//                 }
//             );
//
//             if(wai.principal.username != null) {
//                 document.getElementById('logout')
//                     .innerHTML = '<form id="logoutForm" method="post" action="/logout"></form>'
// // <!--                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>\ -->
// //                                 < /form>'
//             }
//
//         })
// }
//
// // $( document ).ready( authUserInfo );