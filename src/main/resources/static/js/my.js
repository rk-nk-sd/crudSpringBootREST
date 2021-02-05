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

// function authUserInfo(jQuery) {
//     authUserInfo( jQuery )
//         .then(wai => {
//                 document.getElementById('whoami')
//                     .innerHTML = wai.principal.username + " with roles:" + wai.authorities.map(
//                     function (name) {
//                         return " " + name.authority.replace("ROLE_", "")
//                     }
//                 );
//                 document.getElementById()
//             }
//
//         )
// }

// $( document ).ready( authUserInfo );